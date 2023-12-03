package e3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae2_02.graphes.Arete;

public class History implements Serializable{
	protected ArrayList<Sejours> history = new ArrayList<Sejours>();
	protected String fileName; //A revoir absolument
	private static final long serialVersionUID=1L; 
	
	/**
	 * Constructeur de la classe History
	 * @param fileName Nom du fichier
	 * @param history un historique de sejours
	 */
	public History(String fileName, ArrayList<Sejours> history) {
		this.history=history;
		this.fileName=fileName;
		 File h = new File(System.getProperty("user.dir")+"/bin/"+fileName);
		 try {
		 h.createNewFile();
		 }catch(Exception e) {
			 System.out.println("Le fichier correspondant à l'historique n'a pas pu être créé");
		 }
	}
	
	/**
	 * Constructeur de la classe History
	 * @param fileName Nom du fichier
	 */
	public History(String fileName) {
		this(fileName,new ArrayList<Sejours>());
	}
	
	/**
	 * Constructeur de la classe History
	 * @param fileName Nom du fichier
	 * @param List<Arete<Teenager>> appariements liste des appariements
	 * @param int annee associé à l'historique
	 */
	public History(String fileName,List<Arete<Teenager>> appariements, int annee) {
		this.fileName=fileName;
		this.history=new ArrayList<Sejours>();
		this.sauvegardeAnnee(appariements,annee);
	}
	
	/**
	 * Ajoute un séjour à l'historique
	 * @param sejour à ajouter
	 */
	public void ajouterSejour(Sejours sejour) {
		this.history.add(sejour);
	}
	
	/**
	 * Retire un séjour à l'historique
	 * @param sejour Séjour a retirer
	 */
	public void retirerSejour(Sejours sejour) {
		this.history.remove(sejour);
	}
	
	/**
	 * @return Renvoie l'historique des séjours
	 */
	public ArrayList<Sejours> getHistory(){
		return this.history;
	}
	
	//
	/**
	 * Prend une liste d'arête d'appariements et la sauvegarde dans l'historique
	 * @param appariements liste d'arête d'appariement
	 * @param annee année du séjour
	 */
	public void sauvegardeAnnee(List<Arete<Teenager>> appariements, int annee) {
		for(Arete<Teenager> binome : appariements) {
			history.add(new Sejours(annee, binome.getExtremite1(), binome.getExtremite2()));
		}
	}
	
	//
	/**
	 * @param echange Liste de séjours
	 * @return renvoie l'année la plus récente où un séjour à eu lieu.
	 */
	public static int getLastYear(ArrayList<Sejours> echange) {
		Sejours SejourMostRecentAnnee=echange.get(0);
		for (Sejours sejour : echange) {
	        if (sejour.getAnnee() > SejourMostRecentAnnee.getAnnee()) {
	        	SejourMostRecentAnnee = sejour;
	        }
	    }
		return SejourMostRecentAnnee.getAnnee();
	}
	
	public static int getLastYear(History historique) {
		return getLastYear(historique.getHistory());
	}
	
	/**
	 * @param rechercher Séjour à rechercher
	 * @param echange Liste de séjours dans laquelle on cherche
	 * @return Renvoie le séjours qu'on cherche si il est trouver sinon renvoie null
	 */
	public Sejours rechercheSejour(Sejours recherche) {
		for(Sejours s: this.getHistory()) {
			//System.out.println("TEST recherche: "+rechercher+"=="+s+"-->"+s.equals(rechercher));
			if(s.equals(recherche)){
				return s;
			}
		}
		return null;
	}
	
	/**
	 * @param annee année du séjour
	 * @param host hôte du séjour
	 * @param guest invité du séjour
	 * @param echange Liste de séjours
	 * @return Renvoie le séjours qu'on cherche si il est trouver sinon renvoie null
	 */
	public Sejours rechercheSejour(int annee, Teenager host, Teenager guest) {
		return this.rechercheSejour(new Sejours(annee,host,guest));
	}
	
	//Gestion des CSV
	/**
	 * Gestion des CSV
	 * @param tab Liste de String
	 * @return Les Strings à la suite
	 */
	public static String StringTabToString(String[] tab) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<tab.length;i++) {
			sb.append("("+tab[i]+")");
		}
		return sb.toString();
	}
	
	//Convertit l'historique en fichier binaire sérialisé.
	/**
	 * Convertit l'historique en fichier binaire sérialisé.
	 * @param fileName Nom du fichier resultant
	 */
	public void serialization(String fileName) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir")+"/bin/"+fileName))) {
            oos.writeObject(this);
            System.out.println("L'historique a été sauvegardé avec succès.");
        } catch (IOException e) {
            System.out.println("IOException : L'historique n'a pas pu être sauvegardé.");
            e.printStackTrace();
        }catch (Exception e) {
        	System.out.println("Exception : L'historique n'a pas pu être sauvegardé.");
            e.printStackTrace();
        }
	}
	
	/**
	 * Convertit l'historique en fichier binaire sérialisé. avec le nom par défaut "HISTORY"
	 */
	public void serialization() {
		this.serialization("HISTORY");
	}
	
	public static void reinitialisationHistorique(String fileName) {
		try {
		File hist = new File(System.getProperty("user.dir")+"/bin/"+fileName);
		hist.delete();
		}catch (Exception e) {
			System.out.println("Exception in reinitialisationHistorique");
		}
	}
	
	public void reinitialisationHistorique() {
		try {
		File hist = new File(System.getProperty("user.dir")+"/bin/"+this.fileName);
		hist.delete();
		}catch (Exception e) {
			System.out.println("Exception in reinitialisationHistorique");
		}
	}
	
	/**
	 * Prend un fichier binaire sérialisé et en renvoie une arrayList<Sejours>.
	 * @param fileName nom du fichier a désérialisé
	 * @return Liste de séjours
	 */
	public static History deSerialization(String fileName) {
		if(History.fileIsEmpty(System.getProperty("user.dir")+"/bin/"+fileName)) {
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(System.getProperty("user.dir")+"/bin/"+fileName))) {
			History temp = (History) ois.readObject(); //A voir
            //System.out.println("L'historique a été chargé avec succès : "+temp);
            return temp;
		}catch (FileNotFoundException fnfe) {
			 System.out.println("FileNotFoundException : L'historique n'a pas pu être chargé.");
	         //fnfe.printStackTrace();
	         return null;
        } catch (IOException ioe)   {
            System.out.println("IOException : L'historique n'a pas pu être chargé.");
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException clfe) {
            System.out.println("ClassNotFoundException : L'historique n'a pas pu être chargé.");
        	clfe.printStackTrace();
        	return null;
        } catch (Exception e) {
        	System.out.println("Exception : L'historique n'a pas pu être chargé.");
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * Prend un fichier binaire sérialisé et en renvoie une arrayList<Sejours>. si le fichier a le nom par défault
	 * @return Liste de séjours 
	 */
	public static History deSerialization() {
		return History.deSerialization("HISTORY");
	}
	
	/**
	 * Prend un chemin vers un fichier et teste s'il existe
	 * @param cheminVersLeFichier String dui doit indiquer le chemin vers le fichier qu'on souhaite tester
	 * @return un boolean e nfonction de si le fichier existe
	 */
	public static boolean isFileCreated(String cheminVersLeFichier) {
		File f = new File(cheminVersLeFichier);
		return (f.exists() && !f.isDirectory());
	}
	
	/**
	 * Prend un chemin vers un historique et teste s'il existe
	 * @param cheminVersLeFichier String qui doit indiquer le chemin vers l'historique qu'on souhaite tester
	 * @return un boolean en fonction de si le fichier existe
	 */
	public static boolean isHistoryCreated(String cheminVersLeFichier) {
		File f = new File(System.getProperty("user.dir")+"/bin/"+cheminVersLeFichier);
		return (f.exists() && !f.isDirectory());
	}
	
	/*private static void saveSerialization(History newObject, String fileName) {
		try {
			 FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir")+"/bin/"+fileName);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(newObject);
	         out.close();
	         fileOut.close();
	         System.out.println("La nouvelle sérialisation a été sauvegardée.");
	    } catch (IOException e) {
	    	System.out.println("La nouvelle sérialisation n'a pas pu être sauvegardée.");
	        e.printStackTrace();
	    }
	}*/
	
	public String toString() {
		return this.getHistory().toString();
	}
	
	/**
	 * Prend un chemin vers un fichier et teste s'il est vide
	 * @param fileName String doit indiquer le chemin vers le fichier qu'on souhaite tester
	 * @return un boolean en fonction de si le fichier est vide
	 */
	public static boolean fileIsEmpty(String fileName) {
		File file = new File(fileName);
		 
        try {
        	if(file!=null) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            if (br.readLine() == null) {
            	br.close();
                return true;
            }else {
            	br.close();
            	return false;
            }
        	}
        } catch (IOException e) {
            //e.printStackTrace();
        }
		return true;
	}
}
