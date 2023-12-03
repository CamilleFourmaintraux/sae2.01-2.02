package e3;
import fr.ulille.but.sae2_02.graphes.*;
import java.util.HashMap;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

//classe à jour GRAPHE-v1

//Classe temporaire en attendant de faire la partie graphe
public class AffectationUtil {
	
	static int compteur=0;
	static boolean isHistoryCreated=History.isHistoryCreated("HISTORY");
	
	/**
   	 * Crée, initialise et renvoi un graphe à partir des paramètres
   	 * @param ArrayList<Teenager> students liste des sommets du graphe
   	 * @param String nom du fichie ou se situe l'historique
   	 * @return GrapheNonOrienteValue<Teenager> graphe crée à partir des données
   	 */
	public static GrapheNonOrienteValue<Teenager> initGraph(ArrayList<Teenager> adolescents, String nomHistorique){
		
		GrapheNonOrienteValue<Teenager> graphe = new GrapheNonOrienteValue<Teenager>();
		History historique=History.deSerialization(nomHistorique);
		
		for(int i=0; i<adolescents.size();i++) {
			graphe.ajouterSommet(adolescents.get(i));
		}
		Teenager temp1;
		Teenager temp2;
		for(int tp1=0; tp1<adolescents.size()/2;tp1++) {
			for(int tp2=adolescents.size()/2; tp2<adolescents.size();tp2++) {
				temp1=adolescents.get(tp1);
				temp2=adolescents.get(tp2);
				graphe.ajouterArete(temp1,temp2,weight(temp1,temp2,historique));
			}
		}
		return graphe;
	}

	/**
   	 * Crée, initialise et renvoi un graphe à partir des paramètres
   	 * @param ArrayList<Teenager> students liste des sommets du graphe
   	 * @param String nom du fichie ou se situe l'historique
   	 * @param Double coef*** pondérations pour la méthode weigth influant sur les appariments
   	 * @param ArrayList<Arete<Teenager>> aForcer/aEmpecher binômes qui doivent être forcer ou empêcher
   	 * @return GrapheNonOrienteValue<Teenager> graphe crée à partir des données
   	 */
	public static GrapheNonOrienteValue<Teenager> initGraph(ArrayList<Teenager> adolescents, String nomHistorique, double coefAllergies, double coefDiet, double coefAge, double coefHistory, double coefGender, ArrayList<Arete<Teenager>> aForcer, ArrayList<Arete<Teenager>> aEmpecher){
		
		GrapheNonOrienteValue<Teenager> graphe = new GrapheNonOrienteValue<Teenager>();
		History historique=History.deSerialization(nomHistorique);
		
		for(int i=0; i<adolescents.size();i++) {
			graphe.ajouterSommet(adolescents.get(i));
		}
		Teenager temp1;
		Teenager temp2;
		for(int tp1=0; tp1<adolescents.size()/2;tp1++) {
			for(int tp2=adolescents.size()/2; tp2<adolescents.size();tp2++) {
				temp1=adolescents.get(tp1);
				temp2=adolescents.get(tp2);
				graphe.ajouterArete(temp1,temp2,advancedWeight(temp1,temp2,historique,coefAllergies,coefDiet,coefAge,coefHistory,coefGender,aForcer,aEmpecher));
			}
		}
		return graphe;
	}
	
	
	/**
   	 * Crée, initialise et renvoi un graphe à partir des paramètres, version simplifié des autres méthodes similaires.
   	 * @param ArrayList<Teenager> students liste des sommets du graphe
   	 * @return GrapheNonOrienteValue<Teenager> graphe crée à partir des données
   	 */
public static GrapheNonOrienteValue<Teenager> initGraphV1(ArrayList<Teenager> adolescents){
		
		GrapheNonOrienteValue<Teenager> graphe = new GrapheNonOrienteValue<Teenager>();
		
		for(int i=0; i<adolescents.size();i++) {
			graphe.ajouterSommet(adolescents.get(i));
		}
		Teenager temp1;
		Teenager temp2;
		for(int tp1=0; tp1<adolescents.size()/2;tp1++) {
			for(int tp2=adolescents.size()/2; tp2<adolescents.size();tp2++) {
				temp1=adolescents.get(tp1);
				temp2=adolescents.get(tp2);
				graphe.ajouterArete(temp1,temp2,weightV1(temp1,temp2));
			}
		}
		return graphe;
	}
	
	/**
	 * Crée un calcul, et l'enregistre automatiquement. ->obsolète
	 * @param GrapheNonOrienteValue<Teenager> graphe sur leqeul se base les calculs
	 * @param ArrayList<Teenager> partie1 des sommets
	 * @param ArrayList<Teenager> partie2 des sommets
	 * @return List<Arete<Teenager>> liste des binômes(Aretes) qui en résulte
	 */
	public static List<Arete<Teenager>> initCalcul(GrapheNonOrienteValue<Teenager> graphe,ArrayList<Teenager> partie1,ArrayList<Teenager> partie2) {
		CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(graphe,partie1,partie2);
		List<Arete<Teenager>> resultat = calcul.calculerAffectation();
		Platform.resultToCSV(resultat,"testMonCSV");
		System.out.println("Résultat enrengistré en CSV. Le coût est:"+calcul.getCout());
		return resultat;
	}
	
	/**
	 * Coupe une liste en deux partie.
	 * @param GrapheNonOrienteValue<Teenager> graphe sur lequel se base les calculs
	 * @param ArrayList<Teenager> ados liste des étudiants
	 * @return ArrayList<ArrayList<Teenager>> liste listant les deux listes qui en résulte
	 */
	public static ArrayList<ArrayList<Teenager>> moitie(ArrayList<Teenager> ados) {
		ArrayList<ArrayList<Teenager>> parties = new ArrayList<ArrayList<Teenager>>();
		ArrayList<Teenager> partie1 = new ArrayList<Teenager>();
		ArrayList<Teenager> partie2 = new ArrayList<Teenager>();
		for(int tp1=0; tp1<ados.size()/2;tp1++) {
			partie1.add(ados.get(tp1));
		}
		for(int tp2=ados.size()/2; tp2<ados.size();tp2++) {
			partie2.add(ados.get(tp2));
		}
		System.out.println("TAILLESTEST:p1"+partie1.size()+ " p2:"+partie2.size());
		parties.add(partie1);
		parties.add(partie2);
		return parties;
	}
	
	// Calcule et renvoie le score de bonne entente entre deux adolescent. Plus le score est petit, mieux les adolescents s'entendent.
	//Avec 0 une entente parfaite et 300 une entente inexistante.
	/**
	 * Calcule le poid
	 * @param Teenager host étudiant hôte
	 * @param Teenager guest étudiant invité
	 * @param History historique utilisé pour les calculs, peut être passé vide si absent.
	 * @return double renvoie le poids calculé avec 0 un score parfait et 30 ou + un score mauvais.
	 */
	public static double weight (Teenager host, Teenager guest, History historique) {
		try{
			double weight = 300;
			//ALLERGIES
			if (host.getCriterion(CriterionName.HOST_HAS_ANIMAL).getValue().equals("yes") && guest.getCriterion(CriterionName.GUEST_ANIMAL_ALLERGY).getValue().equals("yes")) {
				weight+=100;
			}
			//DIET
			weight += 10*(guest.getCriterion(CriterionName.GUEST_FOOD).getValue().split(",").length-host.nbrFoodForGuest(guest));
			//HOBBIES
			weight-=host.nbrHobbiesCommun(guest)*10;
			//AGE
			Period period = Period.between(host.getBirthDate(), guest.getBirthDate());
			if(Math.abs(period.getYears())<2 && Math.abs(period.getMonths())<7) {
				weight-=15;
			}
			//HISTORY //Regarder si le fichier history existe, sinon passe, regarder si il est vide sinon passer, si rempli et données coorecte, les tester.
			/*if(isHistoryCreated) {
				historique = History.deSerialization();
			}else {
				historique = new ArrayList<Sejours>();
			}*/
			if(!historique.history.isEmpty()) {
				Sejours temp = historique.rechercheSejour(2022, host, guest);
				//System.out.println("VERFIListSejoursNull");
				//System.out.println(temp);
				if(temp!=null) {
					//System.out.println("TEST condition:"+(host.getCriterionValue(CriterionName.HISTORY).equals("same")&&guest.getCriterionValue(CriterionName.HISTORY).equals("same")));
					if(host.getCriterionValue(CriterionName.HISTORY).equals("same")&&guest.getCriterionValue(CriterionName.HISTORY).equals("same")) {
						weight-=100;
					}else if(host.getCriterionValue(CriterionName.HISTORY).equals("other")||guest.getCriterionValue(CriterionName.HISTORY).equals("other")) {
						weight+=50;
					}else if (host.getCriterionValue(CriterionName.HISTORY).equals("same")||guest.getCriterionValue(CriterionName.HISTORY).equals("same")) {
						weight-=15;
					}
				}else {
					if(host.getCriterionValue(CriterionName.HISTORY).equals("other")||guest.getCriterionValue(CriterionName.HISTORY).equals("other")) {
						weight-=20;
					}
					if (host.getCriterionValue(CriterionName.HISTORY).equals("same")||guest.getCriterionValue(CriterionName.HISTORY).equals("same")) {
						weight+=20;
					}
				}
			}
			//GENDER
			String hostPrefGender = host.getCriterion(CriterionName.PAIR_GENDER).getValue();
			String guestPrefGender = guest.getCriterion(CriterionName.PAIR_GENDER).getValue();
			if (hostPrefGender.equals(guest.getGender()) || hostPrefGender.equals("") ) {
				weight-=7.5;
			}
			if (guestPrefGender.equals(host.getGender()) || guestPrefGender.equals("") ) {
				weight-=7.5;	
			}
			//RESULT
			compteur=0;
			return weight;
			
		} catch (NullPointerException e) {
			verifConditionsBinome(host,guest);
			//System.out.println("NullPointerExceptions : method weight in AffectationUtil (Missing Criterions) -> Retry");
			compteur+=1;
			if(compteur>1) {
				return weightSimple(host,guest,historique);
			}else {
				return weight(host,guest,historique);//weight(host,guest);
			}
			
		}
		
	}
	
	/**
	 * Calcule le poid, méthode simplifié
	 * @param Teenager host étudiant hôte
	 * @param Teenager guest étudiant invité
	 * @param History historique utilisé pour les calculs, peut être passé vide si absent.
	 * @return double renvoie le poids calculé
	 */
	public static double weightSimple (Teenager host, Teenager guest, History historique) {
		try{
			double weight = 300;
			//ALLERGIES
			if (host.getCriterion(CriterionName.HOST_HAS_ANIMAL).getValue().equals("yes") && guest.getCriterion(CriterionName.GUEST_ANIMAL_ALLERGY).getValue().equals("yes")) {
				weight+=100;
			}
			//DIET
			weight += 10*(guest.getCriterion(CriterionName.GUEST_FOOD).getValue().split(",").length-host.nbrFoodForGuest(guest));
			//HOBBIES
			weight-=host.nbrHobbiesCommun(guest)*10;
			//AGE
			Period period = Period.between(host.getBirthDate(), guest.getBirthDate());
			if(Math.abs(period.getYears())<2 && Math.abs(period.getMonths())<7) {
				weight-=15;
			}
			//GENDER
			String hostPrefGender = host.getCriterion(CriterionName.PAIR_GENDER).getValue();
			String guestPrefGender = guest.getCriterion(CriterionName.PAIR_GENDER).getValue();
			if (hostPrefGender.equals(guest.getGender()) || hostPrefGender.equals("") ) {
				weight-=7.5;
			}
			if (guestPrefGender.equals(host.getGender()) || guestPrefGender.equals("") ) {
				weight-=7.5;	
			}
			//RESULT
			compteur=0;
			return weight;
			
		} catch (NullPointerException e) {
			verifConditionsBinome(host,guest);
			//System.out.println("NullPointerExceptions : method weight in AffectationUtil (Missing Criterions) -> Retry");
			compteur+=1;
			if(compteur>1) {
				return 969;
			}else {
				return weightSimple(host,guest,historique);//weight(host,guest);
			}
			
		}
		
	}
	
	/**
	 * Vérifie qu'un binome passé en paramètre à bien tous les Criterion nécéssaires de créés.
	 * @param Teenager host étudiant hôte
	 * @param Teenager guest étudiant invité
	 */
	public static void verifConditionsBinome(Teenager host, Teenager guest) {
		if(!host.hasCriterion(CriterionName.HOST_FOOD)) {
			host.addCriterion(CriterionName.HOST_FOOD, new Criterion("",CriterionName.HOST_FOOD));
		}
		if(!guest.hasCriterion(CriterionName.GUEST_FOOD)) {
			guest.addCriterion(CriterionName.GUEST_FOOD, new Criterion("",CriterionName.GUEST_FOOD));
		}
		if(!host.hasCriterion(CriterionName.HOST_HAS_ANIMAL)) {
			host.addCriterion(CriterionName.HOST_HAS_ANIMAL, new Criterion("",CriterionName.HOST_HAS_ANIMAL));
		}
		if(!guest.hasCriterion(CriterionName.GUEST_ANIMAL_ALLERGY)) {
			guest.addCriterion(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("",CriterionName.GUEST_ANIMAL_ALLERGY));
		}
		if(!host.hasCriterion(CriterionName.HOBBIES)) {
			host.addCriterion(CriterionName.HOBBIES, new Criterion("",CriterionName.HOBBIES));
		}
		if(!guest.hasCriterion(CriterionName.HOBBIES)) {
			guest.addCriterion(CriterionName.HOBBIES, new Criterion("",CriterionName.HOBBIES));
		}
		if(!host.hasCriterion(CriterionName.PAIR_GENDER)) {
			host.addCriterion(CriterionName.PAIR_GENDER, new Criterion("",CriterionName.PAIR_GENDER));
		}
		if(!guest.hasCriterion(CriterionName.PAIR_GENDER)) {
			guest.addCriterion(CriterionName.PAIR_GENDER, new Criterion("",CriterionName.PAIR_GENDER));
		}
		if(!host.hasCriterion(CriterionName.HISTORY)) {
			host.addCriterion(CriterionName.HISTORY, new Criterion("",CriterionName.HISTORY));
		}
		if(!guest.hasCriterion(CriterionName.HISTORY)) {
			guest.addCriterion(CriterionName.HISTORY, new Criterion("",CriterionName.HISTORY));
		}
	}
	
	/**
	 * Calcule le poid pour la première version du sujet, n'utilise pas d'historique
	 * @param Teenager host étudiant hôte
	 * @param Teenager guest étudiant invité
	 * @return double renvoie le poids calculé
	 */
	//Méthode alternative de weight pour la version 1 du graphe
	public static double weightV1 (Teenager host, Teenager guest) {
		double weight = 96.0;
		if(host.compatibleWithGuestV1Graphe(guest)) { //Vérifie la contrainte des animaux
			weight-=66;
			weight-=host.nbrHobbiesCommun(guest)*10; //Calcule le score d'affinité à partir des hobbies 
		}
		return weight;
	}
	
	/**
	 * Méthode obselète et inutile
	 * @param Teenager host étudiant hôte
	 * @param Teenager guest étudiant invité
	 * @return boolean si la liste passe les vérifications
	 */
	public static boolean verifListParam(ArrayList<Teenager> list, Teenager host, Teenager guest) {
		for(int i=0; i<list.size();i+=2) {
			if(host.equals(list.get(i))) {
				if(guest.equals(list.get(i+1))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
   	 * Calcul un poid en fonction des paramètres et des réglages.
   	 * @param Teenager host étudiant hôte
   	 * @param Teenager guest étudiant invité
   	 * @param String nom du fichie ou se situe l'historique
   	 * @param Double coef*** pondérations pour la méthode weigth influant sur les appariments
   	 * @param ArrayList<Arete<Teenager>> aForcer/aEmpecher binômes qui doivent être forcer ou empêcher
   	 * @return double renvoie le poid calculé
   	 */
	public static double advancedWeight (Teenager host, Teenager guest, History historique, double coefAllergies, double coefDiet, double coefAge, double coefHistory, double coefGender, ArrayList<Arete<Teenager>> aForcer, ArrayList<Arete<Teenager>> aEmpecher) {
		try{
			//System.out.println("TEST advancedWeight"); //VALIDE
			for(Arete<Teenager>at:aForcer) {
				
				if(host.equals(at.getExtremite1()) && guest.equals(at.getExtremite2())||host.equals(at.getExtremite2()) && guest.equals(at.getExtremite1())) {
					return 0;
				}
			}
			for(Arete<Teenager>at:aEmpecher) {
				if(host.equals(at.getExtremite1()) && guest.equals(at.getExtremite2())||host.equals(at.getExtremite2()) && guest.equals(at.getExtremite1())) {
					return 500;
				}
			}
			double weight = 300;
			//ALLERGIES
			if (host.getCriterion(CriterionName.HOST_HAS_ANIMAL).getValue().equals("yes") && guest.getCriterion(CriterionName.GUEST_ANIMAL_ALLERGY).getValue().equals("yes")) {
				weight+=100*coefAllergies;
			}
			//DIET
			weight += coefDiet*(10*(guest.getCriterion(CriterionName.GUEST_FOOD).getValue().split(",").length-host.nbrFoodForGuest(guest)));
			//HOBBIES
			weight-=host.nbrHobbiesCommun(guest)*10;
			//AGE
			Period period = Period.between(host.getBirthDate(), guest.getBirthDate());
			if(Math.abs(period.getYears())<2 && Math.abs(period.getMonths())<7) {
				weight-=15*coefAge;
			}
			//HISTORY //Regarder si le fichier history existe, sinon passe, regarder si il est vide sinon passer, si rempli et données coorecte, les tester.
			/*if(isHistoryCreated) {
				historique = History.deSerialization();
			}else {
				historique = new ArrayList<Sejours>();
			}*/
			if(!historique.history.isEmpty()) {
				Sejours temp = historique.rechercheSejour(2022, host, guest);
				//System.out.println("VERFIListSejoursNull");
				//System.out.println(temp);
				if(temp!=null) {
					//System.out.println("TEST condition:"+(host.getCriterionValue(CriterionName.HISTORY).equals("same")&&guest.getCriterionValue(CriterionName.HISTORY).equals("same")));
					if(host.getCriterionValue(CriterionName.HISTORY).equals("same")&&guest.getCriterionValue(CriterionName.HISTORY).equals("same")) {
						weight-=100*coefHistory;
					}else if(host.getCriterionValue(CriterionName.HISTORY).equals("other")||guest.getCriterionValue(CriterionName.HISTORY).equals("other")) {
						weight+=50*coefHistory;
					}else if (host.getCriterionValue(CriterionName.HISTORY).equals("same")||guest.getCriterionValue(CriterionName.HISTORY).equals("same")) {
						weight-=15*coefHistory;
					}
				}else {
					if(host.getCriterionValue(CriterionName.HISTORY).equals("other")||guest.getCriterionValue(CriterionName.HISTORY).equals("other")) {
						weight-=20*coefHistory;
					}
					if (host.getCriterionValue(CriterionName.HISTORY).equals("same")||guest.getCriterionValue(CriterionName.HISTORY).equals("same")) {
						weight+=20*coefHistory;
					}
				}
			}
			//GENDER
			String hostPrefGender = host.getCriterion(CriterionName.PAIR_GENDER).getValue();
			String guestPrefGender = guest.getCriterion(CriterionName.PAIR_GENDER).getValue();
			if (hostPrefGender.equals(guest.getGender()) || hostPrefGender.equals("") ) {
				weight-=7.5*coefGender;
			}
			if (guestPrefGender.equals(host.getGender()) || guestPrefGender.equals("") ) {
				weight-=7.5*coefGender;	
				
			}
			//RESULT
			compteur=0;
			//if(weight<0) weight=0;
			return weight;
			
		} catch (NullPointerException e) {
			verifConditionsBinome(host,guest);
			//System.out.println("NullPointerExceptions : method weight in AffectationUtil (Missing Criterions) -> Retry");
			compteur+=1;
			if(compteur>1) {
				return 404;
			}else {
				return weight(host,guest,historique);//weight(host,guest);
			}
			
		}
		
	}
	
	/**
   	 * Crée un échantillon d'étudiant, pratique pour tester.
   	 * @return ArrayList<Teenager> echantillon d'étudiants créé
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
        requirements2.put(CriterionName.HOBBIES, new Criterion("culture,science", CriterionName.HOBBIES));
        requirements3.put(CriterionName.HOBBIES, new Criterion("science,reading", CriterionName.HOBBIES));
        requirements4.put(CriterionName.HOBBIES, new Criterion("culture,technology", CriterionName.HOBBIES));
        requirements5.put(CriterionName.HOBBIES, new Criterion("science,reading", CriterionName.HOBBIES));
        requirements6.put(CriterionName.HOBBIES, new Criterion("technology", CriterionName.HOBBIES));
		
		Teenager A = new Teenager("Adonia","A","FRANCE","2004-07-15","female",requirements1);
        Teenager B = new Teenager("Bellatrix","B","FRANCE","2004-07-15","female",requirements2);
        Teenager C = new Teenager("Callista","C","FRANCE","2004-07-15","female",requirements3);
        Teenager X = new Teenager("Xolag","X","ITALY","2004-07-15","male",requirements4);
		Teenager Y = new Teenager("Yak","Y","ITALY","2004-07-15","male",requirements5);
		Teenager Z = new Teenager("Zander","Z","ITALY","2004-07-15","male",requirements6);
		
		ArrayList<Teenager> students = new ArrayList<Teenager>();
		students.add(A);
		students.add(B);
		students.add(C);
		students.add(X);
		students.add(Y);
		students.add(Z);
		return students;
	}
}