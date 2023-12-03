package e3;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Classe qui servira à faire la jonction entre le csv et le code ou à implémenter une interface humain-machine.
 */ //Interface en ligne de commande de l'application
public class Platform {
	
	public static void main(String[] args) {
		//Programme totalement fonctionnelle directement dans la console.*
		
		//Titre
		System.out.println("-Saé 2.01/2.02-  Aplication dans le terminal sans interface graphique\n");
		
		//initialisation du scanner
		Scanner sc = new Scanner(System.in);
		
		//Choix initial du CSV
		System.out.println("Entrez le nom d'un fichier CSV pour l'ouvrir ou entrez \\\"quit\\\" pour quiter l'application :");
		System.out.println("Les fichiers CSV possibles sont :");
		System.out.println(Platform.afficherCSVpossible());
		System.out.print("\n--->");
		String rep;
		ArrayList<Teenager> students=CSVvalide(sc);
		History historique = new History("historique");
		
		//Trier les pays, si plus de deux pays, demander entre qui faire l'appariement, rajoute ce code dans la partie choixCSV pour pouvoir faire les différnentes parties sans quitter l'appli.
		
		ArrayList<ArrayList<Teenager>> prep = preparationGraphe(students);
		
		ArrayList<Teenager> partie1 = prep.get(0);
		ArrayList<Teenager> partie2 = prep.get(1);
		ArrayList<Teenager> misDeCotes = prep.get(2);
		
		ArrayList<Arete<Teenager>> aEmpecher = new ArrayList<Arete<Teenager>>();
		ArrayList<Arete<Teenager>> aForcer = new ArrayList<Arete<Teenager>>();
		
		double coefAllergies=1;
		double coefDiet=1;
		double coefAge=1;
		double coefHistory=1;
		double coefGender=1;
		
		students.clear();
		students.addAll(partie1);
		students.addAll(partie2);
		GrapheNonOrienteValue<Teenager> graphe = AffectationUtil.initGraph(students,"historique",coefAllergies,coefDiet,coefAge,coefHistory,coefGender,aForcer,aEmpecher);
		
		
		//initialisation de la liste d'étudiants, du graphe et du menu.
		ArrayList<Teenager> studentsCopy= new ArrayList<Teenager>();
		for(int i=0; i<students.size();i++){
			studentsCopy.add(students.get(i));
		}
		
		ArrayList<String> mPrincipal = initMenuPrincipal();
		ArrayList<String> mAppariements = initMenuAppariements();
		
		boolean enCours = true;
		boolean appariementsLaunched=false;
		
		
		
		//Menu
		System.out.println("Graphe calculé avec succès !");
		System.out.println("Que voulez-vous faire ?");
		
		while(enCours) {
			System.out.println("\nMENU PRINCIPAL:");
			afficheMenu(mPrincipal);
			int choix = choixMenu(mPrincipal,sc);
			System.out.println("Vous avez choisi : ["+(choix+1)+"] "+mPrincipal.get(choix));
			
			if(choix==0) {
				graphe = AffectationUtil.initGraph(students,"historique",coefAllergies,coefDiet,coefAge,coefHistory,coefGender,aForcer,aEmpecher);
				CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(graphe,partie1,partie2);
				List<Arete<Teenager>> resultat = calcul.calculerAffectation();
				
				appariementsLaunched=true;
				boolean dejaSaveEnCSV=false;
				boolean dejaSaveDansHistorique=false;
				
				while(appariementsLaunched) {
					System.out.println("\nMENU APPARIEMENTS:");
					afficheMenu(mAppariements);
					int choixAp = choixMenu(mAppariements,sc);
					System.out.println("Vous avez choisi : ["+(choixAp+1)+"] "+mAppariements.get(choixAp));
					if(choixAp==0) {
						System.out.println("Appariements:"+resultat);
						System.out.println("Coût:"+calcul.getCout());
						System.out.println("Etudiants non appariés:"+misDeCotes);
					}else if(choixAp==1) {
						afficherGraphe(graphe,sc);
					}else if(choixAp==2) {
						if(dejaSaveEnCSV) {
							System.out.println("Le fichier à déjà été exporté.");
						}else {
							System.out.println("Entrer un nom pour votre fichier csv :");
							rep=sc.nextLine();
							if(resultToCSV(resultat, rep)) {
								System.out.println("Export en CSV du résultat réussi !");
								dejaSaveEnCSV=true;
							}else {
								System.out.println("Erreur lors de l'export en csv du résultat.");
							}
						}
					}else if(choixAp==3) {
						if(dejaSaveDansHistorique) {
							System.out.println("Le fichier à déjà été sauvegardé dans l'historique.");
						}else {
							historique.sauvegardeAnnee(resultat, LocalDate.now().getYear());
							dejaSaveDansHistorique=true;
							System.out.println("Sauvegarde dans l'historique réussi !");
						}
					}else if(choixAp==4) {
						System.out.println("Retour au menu principal...");
						appariementsLaunched=false;
					}else if(choixAp==5) {
						System.out.println("Sortie de l'application...");
						System.exit(0);
					}else {
						System.out.println("Fonctionnalité indisponible");
					}
				}
			}else if(choix==1) {
				afficherGraphe(graphe,sc);
			}else if(choix==2) {
				coefAllergies=choixInt(3,"Entrer un coefficient pour le critère d'allergies (par défaut 1, entre 0 et 3):",sc);
				coefDiet=choixInt(3,"Entrer un coefficient pour le critère de régimes (par défaut 1, entre 0 et 3):",sc);
				coefAge=choixInt(3,"Entrer un coefficient pour le critère d'âge (par défaut 1, entre 0 et 3):",sc);
				coefHistory=choixInt(3,"Entrer un coefficient pour le critère d'historique (par défaut 1, entre 0 et 3):",sc);
				coefGender=choixInt(3,"Entrer un coefficient pour le critère de genre (par défaut 1, entre 0 et 3):",sc);
				
				graphe = AffectationUtil.initGraph(students,"historique",coefAllergies,coefDiet,coefAge,coefHistory,coefGender,aForcer,aEmpecher);
			}else if(choix==3) {
				ajoutBinome(aForcer,students,sc);
			}else if(choix==4) {
				ajoutBinome(aEmpecher,students,sc);
			}else if(choix==5) {
				aEmpecher.clear();
				aForcer.clear();
				students.clear();
				for(int i=0; i<studentsCopy.size();i++){
					students.add(studentsCopy.get(i));
				}
			}else if(choix==6) {
				System.out.println("Entrez le nom d'un fichier CSV pour l'ouvrir ou entrez \\\"quit\\\" pour quiter l'application :");
				System.out.println("Les fichiers CSV possibles sont :");
				System.out.println(Platform.afficherCSVpossible());
				System.out.print("\n--->");
				students = CSVvalide(sc);
				///
				prep = preparationGraphe(students);
				
				partie1 = prep.get(0);
				partie2 = prep.get(1);
				misDeCotes = prep.get(2);
				
				aEmpecher.clear();
				aForcer.clear();
				
				students.clear();
				students.addAll(partie1);
				students.addAll(partie2);
				graphe = AffectationUtil.initGraph(students,"historique",coefAllergies,coefDiet,coefAge,coefHistory,coefGender,aForcer,aEmpecher);
				///
				System.out.println("CSV mis à jours.");
			}else if(choix==7) {
				System.out.println("Paramètres :");
				System.out.println("Affectations forcées :"+aForcer);
				System.out.println("Affectations empêchées :"+aEmpecher);
				System.out.println("Pondérations :");
				System.out.println("Allergies->"+coefAllergies);
				System.out.println("Régimes->"+coefDiet);
				System.out.println("Ages->"+coefAge);
				System.out.println("Historique->"+coefHistory);
				System.out.println("Genres->"+coefGender);
			}else if(choix==8) {
				System.out.println("Sortie de l'application...");
				System.exit(0);
			}else {
				System.out.println("Fonctionnalité indisponible");
			}
			
		}
	
		//sc.close();
	}
	
	public static void ajoutBinome(ArrayList<Arete<Teenager>> liste, ArrayList<Teenager> students,Scanner sc) {
		System.out.print("Etudiants disponible :"+students);
		System.out.print("\nEntrez le nom du premier étudiant recherché:");
		String rep = sc.nextLine();
		Teenager t1=rechercheTeenager(students,rep);
		while(t1==null) {
			System.out.print("\nSaisie incorrecte :"+rep);
			System.out.print("\nEntrez le nom du premier étudiant recherché:");
			rep = sc.nextLine();
			t1=rechercheTeenager(students,rep);
		}
		
		Teenager t2;
		do{
			System.out.print("Etudiants disponible :"+countryFilter(t1.getCountry(),students));
			System.out.print("\nEntrez le nom du deuxième étudiant recherché:");
			rep = sc.nextLine();
			t2=rechercheTeenager(countryFilter(t1.getCountry(),students),rep);
			if(t2!=null) {
				if(t2.getCountry().equals(t1.getCountry())) {
					t2=null;
				}
			}
		}while(t2==null);
		Arete<Teenager> temp=new Arete<Teenager>(t1,t2);
		if(combinaisonValide(temp,liste)) {
			liste.add(temp);
		}
		
	}
	
	protected static boolean combinaisonValide(Arete<Teenager> at, ArrayList<Arete<Teenager>>liste) {
    	if(at.getExtremite1().equals(at.getExtremite2())) {
    		return false;
    	}
    	for(Arete<Teenager> a:liste) {
    		if(at.getExtremite1().equals(a.getExtremite1())||at.getExtremite1().equals(a.getExtremite2())||at.getExtremite2().equals(a.getExtremite1())||at.getExtremite2().equals(a.getExtremite2())) {
    			return false;
    		}
    	}
    	return true;
    }
	
	public static ArrayList<Teenager> countryFilter(String country, ArrayList<Teenager> students){
		ArrayList<Teenager> listeFiltree = new ArrayList<Teenager>();
		for(Teenager t:students) {
			if(!t.getCountry().equals(country)) {
				listeFiltree.add(t);
			}
		}
		return listeFiltree;
	}
	
	// Cherche un adolescent grâce à son nom, renvoie null si rien trouvé.
	public static Teenager rechercheTeenager(ArrayList<Teenager> students, String nom) {
		for(Teenager t:students) {
			if(t.getName().equals(nom)) {
				return t;
			}
		}
		return null;
	}
	
	public static void afficherGraphe(GrapheNonOrienteValue<Teenager> graphe,Scanner sc) {
		int compteur=0;
		
		System.out.println("Sommets du graphe :");
		for(int i=0; i<graphe.sommets().size(); i+=1) {
			System.out.print(graphe.sommets().get(i)+" - ");
			compteur++;
			if(compteur>9) {
				compteur=0;
				System.out.println();
			}
		}
		
		//Pause
		System.out.print("\nAppuyer sur n'importe quelles touches pour afficher les arètes.");
		sc.nextLine();
		
		System.out.println("Arètes du graphe :");
		for(int i=0; i<graphe.aretes().size(); i+=1) {
			System.out.print(graphe.aretes().get(i)+" - ");
			compteur++;
			if(compteur>5) {
				compteur=0;
				System.out.println();
			}
		}
		
		//Pause
		System.out.print("\nAppuyer sur n'importe quelles touches pour continuer.");
		sc.nextLine();
	}
	
	public static String afficherCSVpossible() {
		File REPCSV = new File(System.getProperty("user.dir")+"/csv/");
		StringBuilder sb = new StringBuilder();
		String temp;
		for(int i=0; i<REPCSV.listFiles().length;i++) {
			temp=REPCSV.listFiles()[i].getName();
			if(temp.substring(temp.length()-4).equals(".csv")) {
				sb.append("-");
				sb.append(temp);
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	public static ArrayList<Teenager> CSVvalide(Scanner sc) {
		ArrayList<Teenager> students = new ArrayList<Teenager>();
		String rep = sc.nextLine();
		do {
			if(rep.equals("quit")) {
				System.out.println("Sortie de l'application...");
				System.exit(0);
			}else if (History.isFileCreated(System.getProperty("user.dir")+"/csv/"+rep)){
				System.out.println("Fichier CSV trouvé");
				students = contenuCSVValide(rep,students,sc);
				if(students!=null) {
					return students;
				}else {
					System.out.println("Le contenu du fichier CSV est invalide.");
					System.out.println("Entrez le nom d'un fichier csv pour l'ouvrir ou entrez \\\"quit\\\" pour quiter l'application :");
					System.out.print("->");
					rep = sc.nextLine();
				}
			}else {
				System.out.println("/!\\\\ Le fichier "+(rep)+" à "+System.getProperty("user.dir")+"/csv/"+rep+" est introuvable.\n");
				System.out.println("Entrez le nom d'un fichier csv pour l'ouvrir ou entrez \\\"quit\\\" pour quiter l'application :");
				System.out.print("->");
				rep = sc.nextLine();
			}
		}while(true);
	}
	
	//Renvoie une list de list de teenager  : partie1,partie2,misDeCotes
	public static ArrayList<ArrayList<Teenager>> preparationGraphe(ArrayList<Teenager> students) {
		ArrayList<Teenager> partie1=new ArrayList<Teenager>();
		ArrayList<Teenager> partie2=new ArrayList<Teenager>();
		ArrayList<Teenager> misDeCotes=new ArrayList<Teenager>();
		ArrayList<ArrayList<Teenager>> listFinal = new ArrayList<ArrayList<Teenager>>();
		String country=students.get(0).getCountry();
		int index=0;
		while(students.get(index).getCountry().equals(country)) {
			partie1.add(students.get(index));
			//System.out.println("Ajouté à la partie1:"+students.get(i));
			index++;
		}
		for(int i=index; i<students.size();i++) {
			//System.out.println("Ajouté à la partie2:"+students.get(i));
			partie2.add(students.get(i));
		}
		////
		if(partie1.size()>partie2.size()) {
			for(int id = partie2.size(); id<partie1.size(); id++) {
				misDeCotes.add(partie1.get(id));
			}
			partie1.removeAll(misDeCotes);
		}else if(partie2.size()>partie1.size()) {
			for(int id = partie1.size(); id<partie2.size(); id++) {
				misDeCotes.add(partie2.get(id));
			}
			partie2.removeAll(misDeCotes);
		}
		listFinal.add(partie1);
		listFinal.add(partie2);
		listFinal.add(misDeCotes);
		return listFinal;
	}
	
	public static ArrayList<Teenager> contenuCSVValide(String nomFichier, ArrayList<Teenager> students,Scanner sc) {
		students = Platform.CSVtoTEENAGERS(System.getProperty("user.dir")+"/csv/"+nomFichier);
		ArrayList<String> listePays=Teenager.comptePays(students);
		if(listePays.size()<2) {
			System.out.println("Le fichier CSV est incorrecte, il ne contient qu'un seul pays. Veuiller fournir un csv contenant au moins deux pays.");
			return null;
		}else if(listePays.size()>2) {
			System.out.println("Veuillez choisir entre quels pays l'échange se fera :");
			String rep="";
			do {
				System.out.println("Les pays possibles sont : "+listePays);
				System.out.print("Pays hôte ->");
				rep = sc.nextLine();
				System.out.println();
			}while(!listePays.contains(rep));
			String paysHote = rep;
			listePays.remove(rep);
			
			do {
				System.out.println("Les pays possibles sont : "+listePays);
				System.out.print("Pays invité ->");
				rep = sc.nextLine();
				System.out.println();		
			}while(!listePays.contains(rep));
			String paysInvi = rep;
			listePays.remove(rep);
			
			//System.out.println("TESTAVANT:"+students);
			//System.out.println("TEST taille students:"+students.size());
			ArrayList<Teenager> pays1 = new ArrayList<Teenager>();
			ArrayList<Teenager> pays2 = new ArrayList<Teenager>();
			
			//int compteurPays1=0;
			//int compteurPays2=0;
			for(Teenager t:students) {
				//System.out.println(""+t+":"+listePays.contains(t.getCountry()));
				if(t.getCountry().equals(paysHote)) {
					pays1.add(t);
					//compteurPays1++;
				}else if(t.getCountry().equals(paysInvi)) {
					pays2.add(t);
					//compteurPays2++;
				}
			}
			/*System.out.println("TEST compteurPays1:"+compteurPays1);
			System.out.println("TEST compteurPays2:"+compteurPays2);
			System.out.println("TEST taille du pays1:"+pays1.size());
			System.out.println("TEST taille du pays2:"+pays2.size());
			System.out.println("TEST pays+pays2 :"+(pays1.size()+pays2.size()));*/
			students.clear();
			students.addAll(pays1);
			students.addAll(pays2);
			/*
			for(Teenager t: pays1) {
				students.add(t);
			}
			for(Teenager t: pays2) {
				students.add(t);
			}			
			*/
			//System.out.println("TESTtailleSTUDENTS:"+students.size());
			//System.out.println("STUDENTS:"+students);
			return students;
		}else {
			return students;
		}
	}
	
	public static int choixMenu(ArrayList<String> menu, Scanner sc) {
		System.out.print("Veuillez saisir le numéro associé au menu souhaité : ");
		String rep="";
		rep = sc.nextLine();
		System.out.println();
		
		while(rep.charAt(0)<='0'||rep.charAt(0)>((char)('0'+menu.size()))) {
			System.out.println("Saisie invalide : " + rep);
			System.out.print("Veuillez saisir le numéro associé au menu souhaité : ");
			rep = sc.nextLine();
			System.out.println();
		}
		return (int)(rep.charAt(0)-'0')-1;
	}
	
	//Demande à l'utilisateur de choisir un nombre entre 0 et max exclus
	public static int choixInt(int max, String message, Scanner sc) {
		System.out.print(message);
		String rep="";
		rep = sc.nextLine();
		System.out.println();
		
		while(rep.charAt(0)<'0'||rep.charAt(0)>((char)('0'+max))) {
			System.out.println("Saisie invalide : " + rep);
			System.out.print(message);
			rep = sc.nextLine();
			System.out.println();
		}
		return (int)(rep.charAt(0)-'0')-1;
	}
	
	public static void afficheMenu(ArrayList<String> menu) {
		for(int i=0; i<menu.size(); i++) {
			System.out.print("["+(i+1)+"] "+menu.get(i)+"\n");
		}
	}
	
	public static ArrayList<String> initMenuPrincipal(){
		ArrayList<String> menu = new ArrayList<String>();
		menu.add("Lancer les appariements");
		menu.add("Afficher le graphe");
		menu.add("Ajuster les pondérations");
		menu.add("Forcer une affectation");
		menu.add("Empêcher une affectation");
		menu.add("Réinitialiser Forcer/Empêcher");
		menu.add("Importer un autre CSV");
		menu.add("Afficher les paramètres");
		menu.add("Quitter l'application");
		return menu;
	}
	
	public static ArrayList<String> initMenuAppariements(){
		ArrayList<String> menu = new ArrayList<String>();
		menu.add("Afficher les résultats");
		menu.add("Afficher le graphe");
		menu.add("Exporter au format csv");
		menu.add("Enrengistrer dans l'historique");
		menu.add("Retour");
		menu.add("Quitter l'aplication");
		return menu;
	}
	
	/**
	 * Cette fonction sers creer un historique
	 * @param fileName Nom de votre historique
	 * @return renvoie true si il arrive a créer un historique, false sinon 
	 */
	public static boolean creationHistory(String fileName) {
        File file = new File(fileName);
        // Vérifier si le fichier existe
        if (file.exists()) {
            return true;
        } else {
            try {
                // Créer le fichier
                if (file.createNewFile()) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } 
        }
	}
	
	public static ArrayList<String> teenagersToString(ArrayList<Teenager> teens){
		ArrayList<String> temp = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		Teenager t;
		for(int i = 0; i<teens.size();i++) {
			t=teens.get(i);
			sb.setLength(0);
			sb.append(String.format("%03d", i));
			sb.append(" | ");
			sb.append(t.getName());
			sb.append(" ");
			sb.append(t.getForename());
			temp.add(sb.toString());
		}
		return temp;
	}
	
	public static ArrayList<String> teenagersToString(List<Arete<Teenager>> teens){
		ArrayList<String> temp = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		Arete<Teenager> at;
		for(int i = 0; i<teens.size();i++) {
			at=teens.get(i);
			sb.setLength(0);
			sb.append(String.format("%03d", i));
			sb.append(" | ");
			sb.append("Binôme ");
			sb.append(at.getExtremite1().getName());
			sb.append(" ");
			sb.append(at.getExtremite1().getForename());
			sb.append(" / ");
			sb.append(at.getExtremite2().getName());
			sb.append(" ");
			sb.append(at.getExtremite2().getForename());
			temp.add(sb.toString());
		}
		return temp;
	}
	
	//Sous méthode de CSVtoTeenagers qui prend une ligne du csv découpé en un tableau de String et l'utilise pour créer un teenager.
		/**
		 * Sous méthode de CSVtoTeenagers qui prend une ligne du csv découpé en un tableau de String et l'utilise pour créer un teenager.
		 * @param cutter Ligne du fichier CSV
		 * @return Renvoie un étudiant crée
		 */
		public static Teenager createTeen(String[] cutter) {
			HashMap<CriterionName, Criterion> conditions = new HashMap<CriterionName, Criterion>();
			conditions.put(CriterionName.HOBBIES, new Criterion(cutter[4],CriterionName.HOBBIES));
			conditions.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion(cutter[5],CriterionName.GUEST_ANIMAL_ALLERGY));
			conditions.put(CriterionName.HOST_HAS_ANIMAL, new Criterion(cutter[6],CriterionName.HOST_HAS_ANIMAL));
			conditions.put(CriterionName.GUEST_FOOD, new Criterion(cutter[7],CriterionName.GUEST_FOOD));
			conditions.put(CriterionName.HOST_FOOD, new Criterion(cutter[8],CriterionName.HOST_FOOD));
			conditions.put(CriterionName.PAIR_GENDER, new Criterion(cutter[10],CriterionName.PAIR_GENDER));
			conditions.put(CriterionName.HISTORY, new Criterion(cutter[11],CriterionName.HISTORY));
			return new Teenager(cutter[0], cutter[1], cutter[2], cutter[3], cutter[9], conditions);
		}
	
	//Convertit le CSV en une ArrayList de Teenagers)
		/**
		 * Convertit le CSV en une ArrayList de Teenagers)
		 * @param pathFile chemin du fichier CSV
		 * @return Une liste d'étudiant 
		 */
		public static ArrayList<Teenager> CSVtoTEENAGERS(String pathFile) {
			ArrayList<Teenager> listTeens = new ArrayList<Teenager>();
			String[] cutter = new String[12];
			try(BufferedReader buff = new BufferedReader(new FileReader(pathFile))){
				String line = buff.readLine();
				line = buff.readLine();	//Passer l'entête
				while(line!=null) {
					cutter=line.split(";",-1);
					listTeens.add(Platform.createTeen(cutter));
					line = buff.readLine();
				}
				return listTeens;
				
			}catch(FileNotFoundException fnfe) {
				System.out.println("FileNotFoundException in class Platform -> method chargerCSV:"+fnfe.getMessage());
				return null;
			}catch(IOException ioe) {
				System.out.println("IOException in class Platform -> method chargerCSV:"+ioe.getMessage());
				return null;
			}catch (ArrayIndexOutOfBoundsException aioobe) {
				System.out.println("ArrayIndexOutOfBoundsException in class Platform -> method chargerCSV:"+aioobe.getMessage());
				return null;
			}catch(Exception e) {
				System.out.println("Exception in class Platform -> method chargerCSV:"+e.getMessage());
				return null;
			}
		}
		
		//Convertit les résultat en CSV (version lourde)
		/**
		 * Convertit les résultat en CSV (version lourde)
		 * @param appariements Couple d'adeolecent
		 * @param fileName Nom du fichier CSV créer
		 */
		public static boolean resultToCSV(List<Arete<Teenager>> appariements, String fileName) {
			try (FileWriter writer = new FileWriter(System.getProperty("user.dir")+"/csv/Resultats/"+fileName+".csv")) {
	            // Écriture de l'en-tête du CSV si nécessaire
	            writer.write("T1-FORENAME,T1-NAME,T1-COUNTRY,T1-BIRTH_DATE,T1-HOBBIES,T1-GUEST_ANIMAL_ALLERGY,T1-HOST_HAS_ANIMAL,T1-GUEST_FOOD,T1-HOST_FOOD,T1-GENDER,T1-PAIR_GENDER,T1-HISTORY,T2-FORENAME,T2-NAME,T2-COUNTRY,T2-BIRTH_DATE,T2-HOBBIES,T2-GUEST_ANIMAL_ALLERGY,T2-HOST_HAS_ANIMAL,T2-GUEST_FOOD,T2-HOST_FOOD,T2-GENDER,T2-PAIR_GENDER,T2-HISTORY,COST\n");
	            
	            Teenager t1;
	            Teenager t2;
	            double cost;
	            // Écriture des objets dans le CSV
	            for (Arete<Teenager> duo : appariements) {
	            	t1 = duo.getExtremite1();
	            	t2 = duo.getExtremite2();
	            	cost = duo.getPoids();
	                writer.write(t1.getForename()+";"+t1.getName()+";"+t1.getCountry()+";"+t1.getBirthDate()+";"+(t1.getCriterionValue(CriterionName.HOBBIES))+";"+(t1.getCriterionValue(CriterionName.GUEST_ANIMAL_ALLERGY))+";"+(t1.getCriterionValue(CriterionName.HOST_HAS_ANIMAL))+";"+(t1.getCriterionValue(CriterionName.GUEST_FOOD))+";"+(t1.getCriterionValue(CriterionName.HOST_FOOD))+";"+(t1.getGender())+";"+(t1.getCriterionValue(CriterionName.PAIR_GENDER))+";"+(t1.getCriterionValue(CriterionName.HISTORY))+";"+t2.getForename()+";"+t2.getName()+";"+t2.getCountry()+";"+t2.getBirthDate()+";"+(t2.getCriterionValue(CriterionName.HOBBIES))+";"+(t2.getCriterionValue(CriterionName.GUEST_ANIMAL_ALLERGY))+";"+(t2.getCriterionValue(CriterionName.HOST_HAS_ANIMAL))+";"+(t2.getCriterionValue(CriterionName.GUEST_FOOD))+";"+(t2.getCriterionValue(CriterionName.HOST_FOOD))+";"+(t2.getGender())+";"+(t2.getCriterionValue(CriterionName.PAIR_GENDER))+";"+(t2.getCriterionValue(CriterionName.HISTORY))+";"+cost+"\n");
	            }
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        } catch (Exception e) {
	        	return false;
	        }
		}
		
		//Convertit les résultat en CSV (version simplifié)
		/**
		 * Convertit les résultat en CSV (version simplifié)
		 * @param appariements Couple d'adeolecent
		 * @param fileName Nom du fichier CSV créer
		 */
		public static boolean resultToCSV_ligth(List<Arete<Teenager>> appariements, String fileName) {
			try (FileWriter writer = new FileWriter(System.getProperty("user.dir")+"/csv/Resultats/"+fileName+".csv")) {
	            // Écriture de l'en-tête du CSV si nécessaire
	            writer.write("T1-FORENAME;T1-NAME;T1-COUNTRY;T2-FORENAME;T2-NAME;T2-COUNTRY;COST\n");
	            Teenager t1;
	            Teenager t2;
	            double cost;
	            // Écriture des objets dans le CSV
	            for (Arete<Teenager> duo : appariements) {
	            	t1 = duo.getExtremite1();
	            	t2 = duo.getExtremite2();
	            	cost = duo.getPoids();
	            	writer.write(t1.getForename()+";"+t1.getName()+";"+t1.getCountry()+";"+t2.getForename()+";"+t2.getName()+";"+t2.getCountry()+";"+cost+"\n");
	            	System.out.println("Le fichier CSV "+fileName+" a été généré avec succès.");
	            }
	            return true;
	        } catch (IOException e) {
	        	System.out.println("Le fichier CSV "+fileName+"n'a pas pu être généré.");
	            e.printStackTrace();
	            return false;
	        } catch (Exception e) {
	        	return false;
	        }
		}
		

		/**
		 * @return renvoie un échantillon d'étudiants
		 */
		public static ArrayList<Teenager> creerEchantillonDeTeenager(){
			HashMap<CriterionName, Criterion> requirements1 = new HashMap<>();
			HashMap<CriterionName, Criterion> requirements2 = new HashMap<>();
			HashMap<CriterionName, Criterion> requirements3 = new HashMap<>();
			HashMap<CriterionName, Criterion> requirements4 = new HashMap<>();
			HashMap<CriterionName, Criterion> requirements5 = new HashMap<>();
			HashMap<CriterionName, Criterion> requirements6 = new HashMap<>();
	        
			requirements1.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	        requirements2.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("yes",CriterionName.GUEST_ANIMAL_ALLERGY));
	        requirements3.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	        requirements4.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	        requirements5.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	        requirements6.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	        
	        requirements1.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	        requirements2.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	        requirements3.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	        requirements4.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	        requirements5.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("yes",CriterionName.GUEST_ANIMAL_ALLERGY));
	        requirements6.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	        
	        requirements1.put(CriterionName.HOBBIES, new Criterion("sports,technology", CriterionName.HOBBIES));
	        requirements2.put(CriterionName.HOBBIES, new Criterion("culture,science", 	CriterionName.HOBBIES));
	        requirements3.put(CriterionName.HOBBIES, new Criterion("science,reading", 	CriterionName.HOBBIES));
	        requirements4.put(CriterionName.HOBBIES, new Criterion("culture,technology",CriterionName.HOBBIES));
	        requirements5.put(CriterionName.HOBBIES, new Criterion("science,reading", 	CriterionName.HOBBIES));
	        requirements6.put(CriterionName.HOBBIES, new Criterion("technology", 		CriterionName.HOBBIES));
	        
	        requirements1.put(CriterionName.HOST_FOOD, new Criterion("nonuts,vegetarian", 	CriterionName.HOST_FOOD));
	        requirements2.put(CriterionName.HOST_FOOD, new Criterion("nonuts,vegetarian", 	CriterionName.HOST_FOOD));
	        requirements3.put(CriterionName.HOST_FOOD, new Criterion("nonuts,vegetarian", 	CriterionName.HOST_FOOD));
	        requirements4.put(CriterionName.HOST_FOOD, new Criterion("vegetarian", 			CriterionName.HOST_FOOD));
	        requirements5.put(CriterionName.HOST_FOOD, new Criterion("", 					CriterionName.HOST_FOOD));
	        requirements6.put(CriterionName.HOST_FOOD, new Criterion("vegetarian", 			CriterionName.HOST_FOOD));
	        
	        requirements1.put(CriterionName.GUEST_FOOD, new Criterion("nonuts,vegetarian", 	CriterionName.GUEST_FOOD));
	        requirements2.put(CriterionName.GUEST_FOOD, new Criterion("nonuts", 			CriterionName.GUEST_FOOD));
	        requirements3.put(CriterionName.GUEST_FOOD, new Criterion("", 					CriterionName.GUEST_FOOD));
	        requirements4.put(CriterionName.GUEST_FOOD, new Criterion("", 					CriterionName.GUEST_FOOD));
	        requirements5.put(CriterionName.GUEST_FOOD, new Criterion("", 					CriterionName.GUEST_FOOD));
	        requirements6.put(CriterionName.GUEST_FOOD, new Criterion("vegetarian", 		CriterionName.GUEST_FOOD));
	        
	        requirements1.put(CriterionName.PAIR_GENDER, new Criterion("female", 	CriterionName.PAIR_GENDER));
	        requirements2.put(CriterionName.PAIR_GENDER, new Criterion("male",		CriterionName.PAIR_GENDER));
	        requirements3.put(CriterionName.PAIR_GENDER, new Criterion("", 			CriterionName.PAIR_GENDER));
	        requirements4.put(CriterionName.PAIR_GENDER, new Criterion("", 			CriterionName.PAIR_GENDER));
	        requirements5.put(CriterionName.PAIR_GENDER, new Criterion("other", 	CriterionName.PAIR_GENDER));
	        requirements6.put(CriterionName.PAIR_GENDER, new Criterion("female", 	CriterionName.PAIR_GENDER));
	        
	        requirements1.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	        requirements2.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	        requirements3.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	        requirements4.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	        requirements5.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	        requirements6.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
			
			Teenager A = new Teenager("Adonia","A","FRANCE","2004-07-15","female",requirements1);
	        Teenager B = new Teenager("Bellatrix","B","FRANCE","2004-07-15","female",requirements2);
	        Teenager C = new Teenager("Callista","C","FRANCE","2004-07-15","female",requirements3);
	        Teenager X = new Teenager("Xolag","X","ITALY","2004-07-15","male",requirements4);
			Teenager Y = new Teenager("Yak","Y","ITALY","2004-07-15","male",requirements5);
			Teenager Z = new Teenager("Zander","Z","ITALY","2004-07-15","male",requirements6);
			
			ArrayList<Teenager> echantillon = new ArrayList<Teenager>();
			echantillon.add(A);
			echantillon.add(B);
			echantillon.add(C);
			echantillon.add(X);
			echantillon.add(Y);
			echantillon.add(Z);
			return echantillon;
		}
	
	
	
	
		
}
