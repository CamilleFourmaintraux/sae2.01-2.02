package e3;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

//classe à jour P00v2

/**
 * Classe codant un teenager
 */
public class Teenager implements Serializable{
	private String forename;
	private String name;
	private String country;
	private transient LocalDate birthDate;
	private transient String gender;
	private transient HashMap<CriterionName, Criterion> requirements;
	private static final long serialVersionUID=1L;
	
	//public static final 
	
	// constructeur de teenager
	/**
	 * constructeur de teenager
	 * @param forename Prénom du teenager
	 * @param name Nom du teenage
	 * @param country Pays de résidence du teenager
	 * @param birthDate Date de naissance du teenager
	 * @param gender Genre du teenager
	 * @param requirements Critère requis par le teenager
	 */
	public Teenager(String forename, String name, String country, String birthDate, String gender, HashMap<CriterionName, Criterion> requirements) {
		this.forename = forename;
		this.name = name;
		this.country = country;
		this.birthDate = LocalDate.parse(birthDate);
		this.gender = gender;
	 	this.requirements = requirements;
	}
	
	// getForeName() ne prend pas de paramètres et renvoie un String.
	/**
	 * @return Renvoie le prénom
	 */
	public String getForename() {
        return forename;
    }

	// getName() ne prend pas de paramètres et renvoie un String
    /**
     * @return Renvoie le nom
     */
    public String getName() {
        return name;
    }
    
 // getCountry()ne prend pas de paramètres et renvoie un String
    /**
     * @return Renvoie le pays de résidence
     */
    public String getCountry() {
        return country;
    }

 // getBirthDate() ne prend pas de paramètres et renvoie un String
    /**
     * @return Renvoie la date de naissance
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

 // getGender() ne prend pas de paramètres et renvoie un String
    /**
     * @return Renvoie le genre
     */
    public String getGender() {
        return gender;
    }

    //Renvoie les requirments soit une Map<CriterionName, Criterion>
    public HashMap<CriterionName, Criterion> getRequirements() {
        return requirements;
    }
    
    /**
     * @param name Nom du criterion
     * @return Renvoie la valeur du criterion associé au nom du criterion
     */
    public String getCriterionValue(CriterionName name) {
    	try {
    		return this.getCriterion(name).getValue();
    	}catch(NullPointerException npe) {
    		System.out.println("ExceptionPointerNull in StringGetCriterionValue");
    		return "";
    	}
    }
	
    //renvoie le criterion associé au criterionName donné en paramètre
	/**
	 * @param name Nom du criterion
	 * @return Renvoie le criterion associé au nom du criterion
	 */
	public Criterion getCriterion(CriterionName name) {
		try {
	        if (name == null) {
	            throw new IllegalArgumentException("Erreur méthode getCriterion : le paramètre 'name' ne peut être nul.");
	        }
	    } catch (IllegalArgumentException e) {
	        System.out.println(e.getMessage());
	    };
		return requirements.get(name);
	}
	
	//Vérifie qu'un criterionName est une clé dans requirements
	/**
	 * @param name Nom d'un criterion
	 * @return Renvoie le nom d'un critérion si il est sans requirements
	 */
	public boolean hasCriterion(CriterionName name) {
		try {
	        if (name == null) {
	            throw new IllegalArgumentException("Erreur méthode hasCriterion : le paramètre 'name' ne peut être nul.");
	        }
	    } catch (IllegalArgumentException e) {
	        System.out.println(e.getMessage());
	    };
	    return requirements.containsKey(name);
	}
	
	/**
	 * Ajoute un criterion a requirements
	 * @param criterionName Nom du criterion
	 * @param criterion Criterion
	 */
	public void addCriterion(CriterionName criterionName,Criterion criterion) {
		try {
	        if (criterionName == null || criterion == null) {
	            throw new IllegalArgumentException("Erreur méthode addCriterion : les paramètres ne peuvent êtres nuls.");
	        }
	    } catch (IllegalArgumentException e) {
	        System.out.println(e.getMessage());
	    };
		requirements.put(criterionName, criterion);
	}

	
	//Cette méthode prend en paramètre un autre adolescent et vérifie que les deux soient compatibles.
	/**
	 * Cette méthode prend en paramètre un autre adolescent et vérifie que les deux soient compatibles.
	 * @param t invité teenager
	 * @return Renvoie true si les teenagers sont compatible
	 */
	public boolean compatibleWithGuest(Teenager t) {
		/*//Assignation de variables à Host pour rendre le code plus lisible.
		String hostHistory=this.getCriterion(CriterionName.HISTORY).getValue();
		//Assignation de variables à Guest pour rendre le code plus lisible.
		String guestHistory=t.getCriterion(CriterionName.HISTORY).getValue();*/
		//if (hostHistory.equals("other") || guestHistory.equals("other")) return false; //Problématique, renverrai toujours faux
		boolean compatible = true;
		try {
			if (this.nbrFoodForGuest(t)!=t.getCriterion(CriterionName.GUEST_FOOD).getValue().split(",").length) compatible = false;
			if (this.getCriterion(CriterionName.HOST_HAS_ANIMAL).getValue().equals("yes") && t.getCriterion(CriterionName.GUEST_ANIMAL_ALLERGY).getValue().equals("yes")) compatible = false;
			if ( ( this.getCountry().equals("FRANCE") || t.getCountry().equals("FRANCE") ) && (this.nbrHobbiesCommun(t)==0) ) compatible = false;
		}
		catch(NullPointerException npe){
			if(!this.hasCriterion(CriterionName.HOST_FOOD)) {
				this.addCriterion(CriterionName.HOST_FOOD, new Criterion("",CriterionName.HOST_FOOD));
			}
			if(!t.hasCriterion(CriterionName.GUEST_FOOD)) {
				t.addCriterion(CriterionName.GUEST_FOOD, new Criterion("",CriterionName.GUEST_FOOD));
			}
			if(!this.hasCriterion(CriterionName.HOST_HAS_ANIMAL)) {
				this.addCriterion(CriterionName.HOST_HAS_ANIMAL, new Criterion("",CriterionName.HOST_HAS_ANIMAL));
			}
			if(!t.hasCriterion(CriterionName.GUEST_ANIMAL_ALLERGY)) {
				t.addCriterion(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("",CriterionName.GUEST_ANIMAL_ALLERGY));
			}
			if(!this.hasCriterion(CriterionName.HOBBIES)) {
				this.addCriterion(CriterionName.HOBBIES, new Criterion("",CriterionName.HOBBIES));
			}if(!t.hasCriterion(CriterionName.HOBBIES)) {
				t.addCriterion(CriterionName.HOBBIES, new Criterion("",CriterionName.HOBBIES));
			}
			System.out.println("NullPointerExceptions : method compatibleWithGuest in Teenager (Missing Criterions) -> Retry");
			return this.compatibleWithGuest(t);
		}
		catch(Exception e) {
			System.out.println("Exceptions : method compatibleWithGuest in Teenager -> false by default");
			return false;
		}
		return compatible;
	} 
	
	//Version de compatibleWithGuest adapté pour la version 1 du graphe 
	/**
	 * Version de compatibleWithGuest adapté pour la version 1 du graphe
	 * @param t teenager avec lequel on vérifie la compatibilité
	 * @return Renvoie true si compatible
	 */
	public boolean compatibleWithGuestV1Graphe(Teenager t) {
		try {
	        if (!this.hasCriterion(CriterionName.GUEST_ANIMAL_ALLERGY)) {
	            throw new IllegalArgumentException();
	        }
	        if (!t.hasCriterion(CriterionName.HOST_HAS_ANIMAL)) {
	            throw new IllegalArgumentException();
	        }
	    } catch (Exception e) {
	    	return false;
	    };
	    return (!(t.getCriterion(CriterionName.HOST_HAS_ANIMAL).getValue().equals("yes") && this.getCriterion(CriterionName.GUEST_ANIMAL_ALLERGY).getValue().equals("yes")));
	}
	
	//Renvoie le nombre d'hobbies que les deux adolescents ont en commun
	/**
	 * @param other teenager avec lequel on compte le nombre de hobbies en commun
	 * @return Renvoie le nombre d'hobbies que les deux adolescents ont en commun
	 */
	public int nbrHobbiesCommun(Teenager other) {
		int compteur = 0;
		String[] thisHobbies;
		String[] otherHobbies;
		try {
			thisHobbies = this.getCriterion(CriterionName.HOBBIES).getValue().split(",");
			otherHobbies = other.getCriterion(CriterionName.HOBBIES).getValue().split(",");
			for(int ind1=0; ind1<thisHobbies.length;ind1++) {
				if(!thisHobbies[ind1].equals("")) {
					for(int ind2=0; ind2<otherHobbies.length;ind2++) if(thisHobbies[ind1].equals(otherHobbies[ind2])) {compteur++;}
				}
			}
		}
		catch(NullPointerException npe){
			System.out.println("NullPointeurException : nbrHobbiesCommun in Teenager");
			return compteur;
		}
		catch(Exception e) {
			System.out.println("Exception : nbrHobbiesCommun in Teenager");
			return compteur;
		}
		return compteur;
	}
	
	//Compte le nombre de nourriture qui sont servies par l'hôte pour le guest. Si ce nombre est inférieur au nombre d'éléments que le guest demande, alors l'hôte ne correspond pas aux critères.
	/**
	 * ompte le nombre de nourriture qui sont servies par l'hôte pour le guest. Si ce nombre est inférieur au nombre d'éléments que le guest demande, alors l'hôte ne correspond pas aux critères.
	 * @param guest teenager invité
	 * @return Renvoie le nombre de nourriture qui sont servies par l'hôte pour le guest
	 */
	public int nbrFoodForGuest(Teenager guest) {
		int compteur = 0;
		try {
			/*if(this.getCriterion(CriterionName.HOST_FOOD).getValue().equals("")) {
				return 0;
			}
			if(guest.getCriterion(CriterionName.GUEST_FOOD).getValue().equals("")) {
				return 0;
			}*/
			String[] foodProposed = this.getCriterion(CriterionName.HOST_FOOD).getValue().split(",");
			String[] foodRequise = guest.getCriterion(CriterionName.GUEST_FOOD).getValue().split(",");
			if(foodRequise.length==1 && foodRequise[0].equals("")) {
				return 1;
			}else {
				for(int ind1=0; ind1<foodProposed.length;ind1++) {
					if(!foodProposed[ind1].equals("")) {
						for(int ind2=0; ind2<foodRequise.length;ind2++) if(foodProposed[ind1].equals(foodRequise[ind2])) {compteur++;}
					}
				}
			}
		}
		catch(NullPointerException npe){
			System.out.println("NullPointeurException : nbrFoodForGuest in Teenager");
			npe.printStackTrace();
			return compteur;
		}
		catch(Exception e) {
			System.out.println("Exception : nbrFoodForGuest in Teenager");
			return compteur;
		}
		return compteur;
	}
	 
	// Cette méthode ne prend pas de paramètres et ne renvoie rien, elle retire tous les critères de requirements qui ne sont pas valides.
	/**
	 * Cette meéthode retire tous les critères de requirements qui ne sont pas valides.
	 */
	public void purgeInvalideRequirement() {
		try {
			Iterator<Map.Entry<CriterionName, Criterion>> iter = requirements.entrySet().iterator();
	        while (iter.hasNext()) {
	            Map.Entry<CriterionName, Criterion> entry = iter.next();
	            if (!entry.getValue().isValid()) {
	                iter.remove();
	            }
	        }
		}
		catch(Exception e) {
			System.out.println("Exception : purgeInvalidRequirement in Teenager");
		}
        
    }
	
	public static ArrayList<String> comptePays(ArrayList<Teenager> students){
		statsPays(students);
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<students.size();i++) {
			if(!(list.contains(students.get(i).getCountry()))) {
				list.add(students.get(i).getCountry());
			}
		}
		return list;
	}
	
	public static void statsPays(ArrayList<Teenager> students) {
		int nbrSPAIN=0;
		int nbrGERMANY=0;
		int nbrFRANCE=0;
		int nbrITALY=0;
		int nbrOTHER =0;
		for(int i=0; i<students.size();i++) {
			if(students.get(i).getCountry().equals("SPAIN")) {
				nbrSPAIN++;
			}else if(students.get(i).getCountry().equals("ITALY")) {
				nbrITALY++;
			}else if(students.get(i).getCountry().equals("FRANCE")) {
				nbrFRANCE++;
			}else if(students.get(i).getCountry().equals("GERMANY")) {
				nbrGERMANY++;
			}else {
				nbrOTHER++;
			}
		}
		System.out.println("FRANCAIS:"+nbrFRANCE);
		System.out.println("ESPAGNOL:"+nbrSPAIN);
		System.out.println("ALLEMAND:"+nbrGERMANY);
		System.out.println("ITALIEN:"+nbrITALY);
		System.out.println("ANOMALIES:"+nbrOTHER);
	}
	
	//Méthode toString n'affichant que le nom
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(this.getName()+"("+this.getCountry()+")");//+" "+this.getForename()+": birth("+birthDate+"), Gender("+gender+"), Origin("+country+");");
	    //sb.append(this.getName()+" "+this.getForename()+": birth("+birthDate+"), Gender("+gender+"), Origin("+country+");");
	    /*sb.append("Requirements:\n");

	    for (Map.Entry<CriterionName, Criterion> entry : requirements.entrySet()) {
	        sb.append(entry.getValue().toString()).append("\n"); //sb.append(entry.getKey()).append(": ")...
	    }*/
	    

	    return sb.toString();
	}
	
	//Méthode toString affichant la totalité des informations sur un étudiant.
	public String toStringMax() {
		    StringBuilder sb = new StringBuilder();
		    sb.append(this.getName()+" "+this.getForename()+": birth("+birthDate+"), Gender("+gender+"), Origin("+country+");");
		    sb.append(this.getName()+" "+this.getForename()+": birth("+birthDate+"), Gender("+gender+"), Origin("+country+");");
		    sb.append("Requirements:\n");

		    for (Map.Entry<CriterionName, Criterion> entry : requirements.entrySet()) {
		        sb.append(entry.getValue().toString()).append("\n"); //sb.append(entry.getKey()).append(": ")...
		    }
		    return sb.toString();
		}
	
	
}
