package e3;

//imports
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae2_02.graphes.Arete;
//import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

//import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//classe à jour : GRAPHE-v1

//Classe de test de AffectationUtil pour la première partie du sujet
public class TestAffectationUtil {
	
	// Teste que la méthode créant un échantillon le fasse correctement
	@Test
	public void testEchantillon() {
		HashMap<CriterionName, Criterion> requirements1 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements2 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements3 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements4 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements5 = new HashMap<>();
		HashMap<CriterionName, Criterion> requirements6 = new HashMap<>();
	    
		requirements1.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements2.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("yes", CriterionName.GUEST_ANIMAL_ALLERGY));
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
		
		ArrayList<Teenager> students2 = AffectationUtil.creerEchantillonDeTeenager();
		
		assertFalse(students.isEmpty());
		assertFalse(students2.isEmpty());
		assertTrue(students.size()==students2.size());
		for(int i=1; i<students.size();i+=1) {
			assertEquals(students.get(i).getName(),students2.get(i).getName());
			assertEquals(students.get(i).getForename(),students2.get(i).getForename());
			assertEquals(students.get(i).getCountry(),students2.get(i).getCountry());
			assertEquals(students.get(i).getBirthDate(),students2.get(i).getBirthDate());
			assertEquals(students.get(i).getGender(),students2.get(i).getGender());
		}
	}
	
	// Teste la première version de InitGraph
	@Test
	public void testInitGraphV1() {
		Teenager A = new Teenager("Adonia","A","FRANCE","2004-07-15","female",new HashMap<>());
	    Teenager B = new Teenager("Bellatrix","B","FRANCE","2004-07-15","female",new HashMap<>());
	    Teenager C = new Teenager("Callista","C","FRANCE","2004-07-15","female",new HashMap<>());
	    Teenager X = new Teenager("Xolag","X","ITALY","2004-07-15","male",new HashMap<>());
		Teenager Y = new Teenager("Yak","Y","ITALY","2004-07-15","male",new HashMap<>());
		Teenager Z = new Teenager("Zander","Z","ITALY","2004-07-15","male",new HashMap<>());
		ArrayList<Teenager>adolescents=new ArrayList<Teenager>();
		adolescents.add(A);
		adolescents.add(B);
		adolescents.add(C);
		adolescents.add(X);
		adolescents.add(Y);
		adolescents.add(Z);
		GrapheNonOrienteValue<Teenager> graphe = new GrapheNonOrienteValue<Teenager>();
		for(int i=0; i<adolescents.size();i++) {
			graphe.ajouterSommet(adolescents.get(i));
		}
		graphe.ajouterArete(A,X,AffectationUtil.weightV1(A,X));
		graphe.ajouterArete(A,Y,AffectationUtil.weightV1(A,Y));
		graphe.ajouterArete(A,Z,AffectationUtil.weightV1(A,Z));
		graphe.ajouterArete(B,X,AffectationUtil.weightV1(B,X));
		graphe.ajouterArete(B,Y,AffectationUtil.weightV1(B,Y));
		graphe.ajouterArete(B,Z,AffectationUtil.weightV1(B,Z));
		graphe.ajouterArete(C,X,AffectationUtil.weightV1(C,X));
		graphe.ajouterArete(C,Y,AffectationUtil.weightV1(C,Y));
		graphe.ajouterArete(C,Z,AffectationUtil.weightV1(C,Z));
		GrapheNonOrienteValue<Teenager> grapheAuto = AffectationUtil.initGraphV1(adolescents);
		assertTrue(graphe.aretes().size()==grapheAuto.aretes().size());
		for(int i=0; i<graphe.aretes().size();i+=1) {
			assertEquals(graphe.aretes().get(i).getExtremite1(),grapheAuto.aretes().get(i).getExtremite1());
			assertEquals(graphe.aretes().get(i).getExtremite2(),grapheAuto.aretes().get(i).getExtremite2());
			assertEquals(graphe.aretes().get(i).getPoids(),grapheAuto.aretes().get(i).getPoids());
		}
		ArrayList<ArrayList<Teenager>> moities = AffectationUtil.moitie(adolescents);
		CalculAffectation<Teenager> calcul1 = new CalculAffectation<Teenager>(graphe,moities.get(0),moities.get(1));
		CalculAffectation<Teenager> calcul2 = new CalculAffectation<Teenager>(grapheAuto,moities.get(0),moities.get(1));
		calcul1.calculerAffectation();
		calcul2.calculerAffectation();
		assertTrue(calcul1.getCout()==calcul2.getCout());
	}
	
	// Teste que la fonction moitié coupe correctement en deux
	@Test
	public void testMoitie() {
		Teenager A = new Teenager("Adonia","A","FRANCE","2004-07-15","female",new HashMap<>());
	    Teenager B = new Teenager("Bellatrix","B","FRANCE","2004-07-15","female",new HashMap<>());
	    Teenager C = new Teenager("Callista","C","FRANCE","2004-07-15","female",new HashMap<>());
	    Teenager X = new Teenager("Xolag","X","ITALY","2004-07-15","male",new HashMap<>());
		Teenager Y = new Teenager("Yak","Y","ITALY","2004-07-15","male",new HashMap<>());
		Teenager Z = new Teenager("Zander","Z","ITALY","2004-07-15","male",new HashMap<>());
		ArrayList<Teenager>adolescents=new ArrayList<Teenager>();
		adolescents.add(A);
		adolescents.add(B);
		adolescents.add(C);
		adolescents.add(X);
		adolescents.add(Y);
		adolescents.add(Z);
		ArrayList<Teenager> partie1=new ArrayList<Teenager>();
		ArrayList<Teenager> partie2=new ArrayList<Teenager>();
		partie1.add(A);
		partie1.add(B);
		partie1.add(C);
		partie2.add(X);
		partie2.add(Y);
		partie2.add(Z);
		//assertEquals(partie1,AffectationUtil.moitie(adolescents).get(0));
		assertEquals(partie2,AffectationUtil.moitie(adolescents).get(1));
	}
	
	// Teste le bon fonctionnement de la compatiblité pour un fichier problématique
	@Test
	public void testIncompatibilityVsHobbies() {
		ArrayList<Teenager> students = Platform.CSVtoTEENAGERS(System.getProperty("user.dir")+"/csv/"+"incompatibilityVsHobbies"+".csv");
		GrapheNonOrienteValue<Teenager> grapheV1 = AffectationUtil.initGraphV1(students);
		ArrayList<ArrayList<Teenager>> moities = AffectationUtil.moitie(students);
		CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(grapheV1,moities.get(0),moities.get(1));
		List<Arete<Teenager>> resultat = calcul.calculerAffectation();
		for(int i=0; i<resultat.size();i++) {
			assertTrue(resultat.get(i).getExtremite1().compatibleWithGuestV1Graphe(resultat.get(i).getExtremite2()));
		}
	}
}
