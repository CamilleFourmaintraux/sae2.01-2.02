package e3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import fr.ulille.but.sae2_02.graphes.*;

import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

//Classe de test de AffectationUtil pour la deuxième partie du sujet
public class TestV2AffectationUtil {
	
	@Test
	public void testHistoriqueV1() {
		HashMap<CriterionName, Criterion> requirements1 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements2 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements3 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements4 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements5 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements6 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements7 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements8 = new HashMap<>();
	    
		requirements1.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements2.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements3.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements4.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements5.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements6.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements7.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements8.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    
	    requirements1.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements2.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements3.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements4.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements5.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements6.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements7.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements8.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    
	    requirements1.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements2.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements3.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements4.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements5.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements6.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements7.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements8.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    
	    requirements1.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements2.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements3.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements4.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements5.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements6.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements7.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements8.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    
	    
	    requirements1.put(CriterionName.HOBBIES, new Criterion("", CriterionName.HOBBIES));
	    requirements2.put(CriterionName.HOBBIES, new Criterion("", CriterionName.HOBBIES));
	    requirements3.put(CriterionName.HOBBIES, new Criterion("", CriterionName.HOBBIES));
	    requirements4.put(CriterionName.HOBBIES, new Criterion("", CriterionName.HOBBIES));
	    requirements5.put(CriterionName.HOBBIES, new Criterion("", CriterionName.HOBBIES));
	    requirements6.put(CriterionName.HOBBIES, new Criterion("", CriterionName.HOBBIES));
	    requirements7.put(CriterionName.HOBBIES, new Criterion("", CriterionName.HOBBIES));
	    requirements8.put(CriterionName.HOBBIES, new Criterion("", CriterionName.HOBBIES));
	    
	    requirements1.put(CriterionName.PAIR_GENDER, new Criterion("", CriterionName.PAIR_GENDER));
	    requirements2.put(CriterionName.PAIR_GENDER, new Criterion("", CriterionName.PAIR_GENDER));
	    requirements3.put(CriterionName.PAIR_GENDER, new Criterion("", CriterionName.PAIR_GENDER));
	    requirements4.put(CriterionName.PAIR_GENDER, new Criterion("", CriterionName.PAIR_GENDER));
	    requirements5.put(CriterionName.PAIR_GENDER, new Criterion("", CriterionName.PAIR_GENDER));
	    requirements6.put(CriterionName.PAIR_GENDER, new Criterion("", CriterionName.PAIR_GENDER));
	    requirements7.put(CriterionName.PAIR_GENDER, new Criterion("", CriterionName.PAIR_GENDER));
	    requirements8.put(CriterionName.PAIR_GENDER, new Criterion("", CriterionName.PAIR_GENDER));
	    
	    //On les met vide dans un premier temps, on recommencera une affectation apres mais avec les conditions history completes
	    requirements1.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements2.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements3.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements4.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements5.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements6.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements7.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements8.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    
	    /*requirements1.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements2.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements3.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements4.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements5.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements6.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements7.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements8.put(CriterionName.HISTORY, new Criterion("same","HISTORY"));*/
		
		Teenager A = new Teenager("Adonia","A","FRANCE","2004-07-15","female",requirements1);
	    Teenager B = new Teenager("Bellatrix","B","FRANCE","2004-07-15","female",requirements2);
	    Teenager C = new Teenager("Callista","C","FRANCE","2004-07-15","female",requirements3);
	    Teenager D = new Teenager("Denise","D","FRANCE","2004-07-15","female",requirements4);
	    Teenager W = new Teenager("Waluigi","W","ITALY","2004-07-15","male",requirements5);
	    Teenager X = new Teenager("Xolag","X","ITALY","2004-07-15","male",requirements6);
		Teenager Y = new Teenager("Yak","Y","ITALY","2004-07-15","male",requirements7);
		Teenager Z = new Teenager("Zander","Z","ITALY","2004-07-15","male",requirements8);
		
		ArrayList<Teenager> students = new ArrayList<Teenager>();
		students.add(A);
		students.add(B);
		students.add(C);
		students.add(D);
		students.add(W);
		students.add(X);
		students.add(Y);
		students.add(Z);
		
		GrapheNonOrienteValue<Teenager> graphe = AffectationUtil.initGraph(students,"TestV2AffectationUtil");
		ArrayList<ArrayList<Teenager>> moities = AffectationUtil.moitie(students);
		CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(graphe,moities.get(0),moities.get(1));
		List<Arete<Teenager>> resultat = calcul.calculerAffectation();
		
		//Préparation aux tests
		Arete<Teenager> AW = new Arete<Teenager>(A, W);
		Arete<Teenager> BX = new Arete<Teenager>(B, X);
		Arete<Teenager> CY = new Arete<Teenager>(C, Y);
		Arete<Teenager> DZ = new Arete<Teenager>(D, Z);
		
		/*
		System.out.println("TESTv1.1");
		System.out.println(students);
		System.out.println("RESULT1"+resultat);
		System.out.println("GRAPHE"+graphe);
		*/
		
		//Censé obtenir ; [Arête(B, X, 70.000000), Arête(D, Z, 70.000000), Arête(A, W, 500.000000), Arête(C, Y, 70.000000)]
		for(Arete<Teenager> a:resultat) {
			if(a.getExtremite1().getName().equals(AW.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(AW.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(BX.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(BX.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(CY.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(CY.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(DZ.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(DZ.getExtremite2().getName()));
			}
		}
		
		//On enrengistre l'historique
		History.reinitialisationHistorique(System.getProperty("user.dir")+"/bin/"+"TestV2AffectationUtil");
		History historique = new History("TestV2AffectationUtil",resultat, 2022);
		historique.serialization(historique.fileName);
		
		
		// On recommence mais en mettant une valeur :same, on devrait ainsi avoir le même résultat.
		students.clear();
		A.getCriterion(CriterionName.HISTORY).setValue("same");
		B.getCriterion(CriterionName.HISTORY).setValue("same");
		C.getCriterion(CriterionName.HISTORY).setValue("same");
		D.getCriterion(CriterionName.HISTORY).setValue("same");
		W.getCriterion(CriterionName.HISTORY).setValue("same");
		X.getCriterion(CriterionName.HISTORY).setValue("same");
		Y.getCriterion(CriterionName.HISTORY).setValue("same");
		Z.getCriterion(CriterionName.HISTORY).setValue("same");
		students.add(A);
		students.add(B);
		students.add(C);
		students.add(D);
		students.add(W);
		students.add(X);
		students.add(Y);
		students.add(Z);
		graphe = AffectationUtil.initGraph(students,"TestV2AffectationUtil");
		moities = AffectationUtil.moitie(students);
		
		for(int i=0; i<students.size();i+=1) {
			assertEquals("same",students.get(i).getCriterionValue(CriterionName.HISTORY));
		}
		
		CalculAffectation<Teenager> calcul2 = new CalculAffectation<Teenager>(graphe,moities.get(0),moities.get(1));
		List<Arete<Teenager>> resultat2 = calcul2.calculerAffectation();
		
		/*
		System.out.println("TESTv1.2");
		System.out.println("RESULT2"+resultat2);
		System.out.println("GRAPHE"+graphe);
		*/
		
		for(int i=0; i<resultat2.size();i+=1) {
			assertEquals(resultat2.get(i).getExtremite1().getCriterionValue(CriterionName.HISTORY),resultat2.get(i).getExtremite2().getCriterionValue(CriterionName.HISTORY));
		}
		//Censé obtenir : [Arête(B, X, 70.000000), Arête(D, Z, 70.000000), Arête(A, W, 500.000000), Arête(C, Y, 70.000000)]
		//obtenu ->>>>> : [Arête(B, X, 70.000000), Arête(C, Y, 70.000000), Arête(A, W, 70.000000), Arête(D, Z, 70.000000)]
		for(Arete<Teenager> a:resultat2) {
			if(a.getExtremite1().getName().equals(AW.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(AW.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(BX.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(BX.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(CY.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(CY.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(DZ.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(DZ.getExtremite2().getName()));
			}
		}
		
		// On recommence mais cette fois-ci avec des valeurs dans la condition historique pour vérifier si il y a bien des changements.
		students.clear();
		A.getCriterion(CriterionName.HISTORY).setValue("same");
		B.getCriterion(CriterionName.HISTORY).setValue("other");
		C.getCriterion(CriterionName.HISTORY).setValue("other");
		D.getCriterion(CriterionName.HISTORY).setValue("");
		W.getCriterion(CriterionName.HISTORY).setValue("same");
		X.getCriterion(CriterionName.HISTORY).setValue("other");
		Y.getCriterion(CriterionName.HISTORY).setValue("same");
		Z.getCriterion(CriterionName.HISTORY).setValue("");
		students.add(A);
		students.add(B);
		students.add(C);
		students.add(D);
		students.add(W);
		students.add(X);
		students.add(Y);
		students.add(Z);
		graphe = AffectationUtil.initGraph(students,"TestV2AffectationUtil");
		moities = AffectationUtil.moitie(students);
		
		CalculAffectation<Teenager> calcul3 = new CalculAffectation<Teenager>(graphe,moities.get(0),moities.get(1));
		List<Arete<Teenager>> resultat3 = calcul3.calculerAffectation();
		
		/*
		System.out.println("TESTv1.3");
		System.out.println("RESULT3"+resultat3);
		System.out.println("GRAPHE"+graphe);
		*/
		
		for(Arete<Teenager> a:resultat3) {
			if(a.getExtremite1().getName().equals(AW.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(AW.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(BX.getExtremite1().getName())){
				assertFalse(a.getExtremite2().getName().equals(BX.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(CY.getExtremite1().getName())){
				assertFalse(a.getExtremite2().getName().equals(CY.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(DZ.getExtremite1().getName())){
				assertFalse(a.getExtremite2().getName().equals(DZ.getExtremite2().getName()));
			}
		}
		System.out.println("TESTv1.4");
		System.out.println("RESULT1"+resultat);
		System.out.println("RESULT2"+resultat2);
		System.out.println("RESULT3"+resultat3);
		System.out.println("GRAPHE"+graphe);
		
	}

	@Test
	public void testHistoriqueV2() {
		HashMap<CriterionName, Criterion> requirements1 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements2 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements3 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements4 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements5 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements6 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements7 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements8 = new HashMap<>();
	    
		requirements1.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements2.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements3.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements4.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements5.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements6.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements7.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements8.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    
	    requirements1.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements2.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements3.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements4.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements5.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements6.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements7.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements8.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    
	    requirements1.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements2.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements3.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements4.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements5.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements6.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements7.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    requirements8.put(CriterionName.GUEST_FOOD, new Criterion("", CriterionName.GUEST_FOOD));
	    
	    requirements1.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements2.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements3.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements4.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements5.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements6.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements7.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    requirements8.put(CriterionName.HOST_FOOD, new Criterion("", CriterionName.HOST_FOOD));
	    
	    
	    requirements1.put(CriterionName.HOBBIES, new Criterion("science,movies,games", CriterionName.HOBBIES));
	    requirements2.put(CriterionName.HOBBIES, new Criterion("technology", CriterionName.HOBBIES));
	    requirements3.put(CriterionName.HOBBIES, new Criterion("culture", CriterionName.HOBBIES));
	    requirements4.put(CriterionName.HOBBIES, new Criterion("movies", CriterionName.HOBBIES));
	    requirements5.put(CriterionName.HOBBIES, new Criterion("science,games", CriterionName.HOBBIES));
	    requirements6.put(CriterionName.HOBBIES, new Criterion("technology,games", CriterionName.HOBBIES));
	    requirements7.put(CriterionName.HOBBIES, new Criterion("movies", CriterionName.HOBBIES));
	    requirements8.put(CriterionName.HOBBIES, new Criterion("culture,science",CriterionName.HOBBIES));
	    
	    requirements1.put(CriterionName.PAIR_GENDER, new Criterion("",CriterionName.PAIR_GENDER));
	    requirements2.put(CriterionName.PAIR_GENDER, new Criterion("",CriterionName.PAIR_GENDER));
	    requirements3.put(CriterionName.PAIR_GENDER, new Criterion("",CriterionName.PAIR_GENDER));
	    requirements4.put(CriterionName.PAIR_GENDER, new Criterion("",CriterionName.PAIR_GENDER));
	    requirements5.put(CriterionName.PAIR_GENDER, new Criterion("",CriterionName.PAIR_GENDER));
	    requirements6.put(CriterionName.PAIR_GENDER, new Criterion("",CriterionName.PAIR_GENDER));
	    requirements7.put(CriterionName.PAIR_GENDER, new Criterion("",CriterionName.PAIR_GENDER));
	    requirements8.put(CriterionName.PAIR_GENDER, new Criterion("",CriterionName.PAIR_GENDER));
	    
	    //On les met vide dans un premier temps, on recommencera une affectation apres mais avec les conditions history completes
	    requirements1.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements2.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements3.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements4.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements5.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements6.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements7.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    requirements8.put(CriterionName.HISTORY, new Criterion("", CriterionName.HISTORY));
	    
	    /*requirements1.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements2.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements3.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements4.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements5.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements6.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements7.put(CriterionName.HISTORY, new Criterion("same", "HISTORY"));
	    requirements8.put(CriterionName.HISTORY, new Criterion("same","HISTORY"));*/
		
		Teenager A = new Teenager("Adonia","A","FRANCE","2004-07-15","female",requirements1);
	    Teenager B = new Teenager("Bellatrix","B","FRANCE","2004-07-15","female",requirements2);
	    Teenager C = new Teenager("Callista","C","FRANCE","2004-07-15","female",requirements3);
	    Teenager D = new Teenager("Denise","D","FRANCE","2004-07-15","female",requirements4);
	    Teenager W = new Teenager("Waluigi","W","ITALY","2004-07-15","male",requirements5);
	    Teenager X = new Teenager("Xolag","X","ITALY","2004-07-15","male",requirements6);
		Teenager Y = new Teenager("Yak","Y","ITALY","2004-07-15","male",requirements7);
		Teenager Z = new Teenager("Zander","Z","ITALY","2004-07-15","male",requirements8);
		
		ArrayList<Teenager> students = new ArrayList<Teenager>();
		students.add(A);
		students.add(B);
		students.add(C);
		students.add(D);
		students.add(W);
		students.add(X);
		students.add(Y);
		students.add(Z);
		
		GrapheNonOrienteValue<Teenager> graphe = AffectationUtil.initGraph(students,"TestV2AffectationUtil");
		ArrayList<ArrayList<Teenager>> moities = AffectationUtil.moitie(students);
		CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(graphe,moities.get(0),moities.get(1));
		List<Arete<Teenager>> resultat = calcul.calculerAffectation();
		
		//Préparation aux tests
		Arete<Teenager> AW = new Arete<Teenager>(A, W);
		Arete<Teenager> BX = new Arete<Teenager>(B, X);
		Arete<Teenager> CZ = new Arete<Teenager>(C, Z);
		Arete<Teenager> DY = new Arete<Teenager>(D, Y);
		
		//Censé obtenir ; [Arête(B, X, 70.000000), Arête(D, Y, 70.000000), Arête(A, W, 500.000000), Arête(C, Z, 70.000000)]
		for(Arete<Teenager> a:resultat) {
			if(a.getExtremite1().getName().equals(AW.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(AW.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(BX.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(BX.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(CZ.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(CZ.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(DY.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(DY.getExtremite2().getName()));
			}
		}
		
		//On enrengistre l'historique
		History.reinitialisationHistorique(System.getProperty("user.dir")+"/bin/"+"TestV2AffectationUtil");
		History historique = new History("TestV2AffectationUtil",resultat, 2022);
		historique.serialization(historique.fileName);
		
		
		// On recommence mais en mettant une valeur :same, on devrait ainsi avoir le même résultat.
		students.clear();
		A.getCriterion(CriterionName.HISTORY).setValue("same");
		B.getCriterion(CriterionName.HISTORY).setValue("same");
		C.getCriterion(CriterionName.HISTORY).setValue("same");
		D.getCriterion(CriterionName.HISTORY).setValue("same");
		W.getCriterion(CriterionName.HISTORY).setValue("same");
		X.getCriterion(CriterionName.HISTORY).setValue("same");
		Y.getCriterion(CriterionName.HISTORY).setValue("same");
		Z.getCriterion(CriterionName.HISTORY).setValue("same");
		students.add(A);
		students.add(B);
		students.add(C);
		students.add(D);
		students.add(W);
		students.add(X);
		students.add(Y);
		students.add(Z);
		graphe = AffectationUtil.initGraph(students,"TestV2AffectationUtil");
		moities = AffectationUtil.moitie(students);
		
		for(int i=0; i<students.size();i+=1) {
			assertEquals("same",students.get(i).getCriterionValue(CriterionName.HISTORY));
		}
		
		CalculAffectation<Teenager> calcul2 = new CalculAffectation<Teenager>(graphe,moities.get(0),moities.get(1));
		List<Arete<Teenager>> resultat2 = calcul2.calculerAffectation();
		for(int i=0; i<resultat2.size();i+=1) {
			assertEquals(resultat2.get(i).getExtremite1().getCriterionValue(CriterionName.HISTORY),resultat2.get(i).getExtremite2().getCriterionValue(CriterionName.HISTORY));
		}
		//Censé obtenir : [Arête(B, X, 70.000000), Arête(D, Y, 70.000000), Arête(A, W, 500.000000), Arête(C, Z, 70.000000)]
		for(Arete<Teenager> a:resultat2) {
			if(a.getExtremite1().getName().equals(AW.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(AW.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(BX.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(BX.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(CZ.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(CZ.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(DY.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(DY.getExtremite2().getName()));
			}
		}
		
		// On recommence mais cette fois-ci avec des valeurs dans la condition historique pour vérifier si il y a bien des changements.
		students.clear();
		A.getCriterion(CriterionName.HISTORY).setValue("same");
		B.getCriterion(CriterionName.HISTORY).setValue("other");
		C.getCriterion(CriterionName.HISTORY).setValue("other");
		D.getCriterion(CriterionName.HISTORY).setValue("");
		W.getCriterion(CriterionName.HISTORY).setValue("same");
		X.getCriterion(CriterionName.HISTORY).setValue("other");
		Y.getCriterion(CriterionName.HISTORY).setValue("same");
		Z.getCriterion(CriterionName.HISTORY).setValue("");
		students.add(A);
		students.add(B);
		students.add(C);
		students.add(D);
		students.add(W);
		students.add(X);
		students.add(Y);
		students.add(Z);
		graphe = AffectationUtil.initGraph(students,"TestV2AffectationUtil");
		moities = AffectationUtil.moitie(students);
		
		CalculAffectation<Teenager> calcul3 = new CalculAffectation<Teenager>(graphe,moities.get(0),moities.get(1));
		List<Arete<Teenager>> resultat3 = calcul3.calculerAffectation();
		
		for(Arete<Teenager> a:resultat3) {
			if(a.getExtremite1().getName().equals(AW.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(AW.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(BX.getExtremite1().getName())){
				assertFalse(a.getExtremite2().getName().equals(BX.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(CZ.getExtremite1().getName())){
				assertFalse(a.getExtremite2().getName().equals(CZ.getExtremite2().getName()));
			} else if(a.getExtremite1().getName().equals(DY.getExtremite1().getName())){
				assertTrue(a.getExtremite2().getName().equals(DY.getExtremite2().getName()));
			}
		}
		System.out.println("TESTv2");
		System.out.println("RESULT1"+resultat);
		System.out.println("RESULT2"+resultat2);
		System.out.println("RESULT3"+resultat3);
		System.out.println("GRAPHE"+graphe);
		
	}
}