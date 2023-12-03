package e3;

//imports
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
//import java.util.ArrayList;
import java.util.HashMap;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Classe de test de Teenager
public class TeenagerTest {
	
	//teste si le constructeur de teenager fonctionne correctement
	/**
	 * teste si le constructeur de teenager fonctionne correctement
	 */
	@Test
    public void testTeenagerConstructor() {
        // crée un teenager manuellement et avec le constructeur puis les compare pour voir si ils sont bien identiques.
        String forename = "John";
        String name = "Doe";
        String country = "USA";
        LocalDate birthDate = LocalDate.parse("2004-01-10");
        String gender = "male";
        HashMap<CriterionName, Criterion> requirements = new HashMap<>();
        requirements.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("yes", CriterionName.GUEST_ANIMAL_ALLERGY));
        requirements.put(CriterionName.PAIR_GENDER, new Criterion("female", CriterionName.PAIR_GENDER));
        requirements.put(CriterionName.HISTORY, new Criterion("other", CriterionName.HISTORY));

        // crée un teenager avec le constructeur
        Teenager teenager = new Teenager(forename,name,country,"2004-01-10",gender,requirements);

        // vérifie que le teenager créé manuellement et que le teenager créé par le constructeur donne les mêmes résultats.
        assertEquals(forename, teenager.getForename());
        assertEquals(name, teenager.getName());
        assertEquals(country, teenager.getCountry());
        assertEquals(birthDate, teenager.getBirthDate());
        assertEquals(gender, teenager.getGender());
        assertEquals(requirements, teenager.getRequirements());
    }
	
	//Teste si la méthode CompatibleWithGuest() fonctionne correctement
	/**
	 * Teste si la méthode CompatibleWithGuest() fonctionne correctement
	 */
	@Test
	public void testCompatibleWithGuest() {
		HashMap<CriterionName, Criterion> requirements = new HashMap<>();
	    requirements.put(CriterionName.HOST_HAS_ANIMAL, new Criterion("yes", CriterionName.HOST_HAS_ANIMAL));
	    requirements.put(CriterionName.PAIR_GENDER, new Criterion("female", CriterionName.PAIR_GENDER));
	    requirements.put(CriterionName.HOBBIES, new Criterion("science", CriterionName.HOBBIES));
	    requirements.put(CriterionName.HOST_FOOD, new Criterion("vegetarian", CriterionName.HOST_FOOD));

		HashMap<CriterionName, Criterion> requirements2 = new HashMap<>();
	    requirements2.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("no", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements2.put(CriterionName.PAIR_GENDER, new Criterion("male", CriterionName.PAIR_GENDER));
	    requirements2.put(CriterionName.HOBBIES, new Criterion("sports", CriterionName.HOBBIES));
	    requirements2.put(CriterionName.GUEST_FOOD, new Criterion("vegetarian", CriterionName.GUEST_FOOD));

		HashMap<CriterionName, Criterion> requirements3 = new HashMap<>();
	    requirements3.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("yes", CriterionName.GUEST_ANIMAL_ALLERGY));
	    requirements3.put(CriterionName.PAIR_GENDER, new Criterion("male", CriterionName.PAIR_GENDER));
	    requirements3.put(CriterionName.HOBBIES, new Criterion("cars", CriterionName.HOBBIES));
	    requirements3.put(CriterionName.GUEST_FOOD, new Criterion("nonuts", CriterionName.GUEST_FOOD));
	    
        // Crée un ado hôte
        Teenager host = new Teenager("John", "Doe", "USA", "2006-05-15","male",requirements);

        // crée un ado invité
        Teenager guest = new Teenager("Jane", "Smith", "UK", "2005-01-21","female",requirements2);
        
        // crée un 2eme ado invité
        Teenager guest2 = new Teenager("Dalthu","Tanjin","ITALY","2009-06-22","female",requirements3);
        
        // test qui vérifie qu'ils sont bien compatible.
        assertEquals(true, host.compatibleWithGuest(guest));
        
     // test qui vérifie que le 2eme invité est bel et bien imcompatible avec l'hôte.
        assertEquals(false, host.compatibleWithGuest(guest2));
    }
	
	
	// Teste si la méthode purgeInvaliderequirement() fonctionne correctement
	/**
	 * Teste si la méthode purgeInvaliderequirement() fonctionne correctement
	 */
	@Test
	public void testPurgeInvaliderequirement() {

	    // Création d'un teenager avec des critères valides et invalides
	    HashMap<CriterionName, Criterion> requirements = new HashMap<>();
        requirements.put(CriterionName.GUEST_ANIMAL_ALLERGY, new Criterion("maybe", CriterionName.GUEST_ANIMAL_ALLERGY));
        requirements.put(CriterionName.PAIR_GENDER, new Criterion("none", CriterionName.PAIR_GENDER));
        requirements.put(CriterionName.HISTORY, new Criterion("other", CriterionName.HISTORY));

	    Teenager t = new Teenager("John", "Doe", "USA", "2001-01-01","male",requirements);

	    // Vérification que tous les critères sont présents avant la purge
	    assertEquals(3, t.getRequirements().size());

	    // Suppression des critères invalides
	    t.purgeInvalideRequirement();

	    // Vérification que seuls les critères valides sont présents après la purge
	    assertEquals(1, t.getRequirements().size());
	    assertTrue(t.getRequirements().containsKey(CriterionName.HISTORY));
	    
	}

	
}
