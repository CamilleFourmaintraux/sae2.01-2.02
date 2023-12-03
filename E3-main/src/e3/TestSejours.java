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

//Classe de test de Sejours
public class TestSejours {
	Teenager toto = new Teenager("toto","HA","FRANCE","2004-01-15","male",new HashMap<>());
	Teenager tata = new Teenager("tata","AH","ITALY","2004-01-15","female",new HashMap<>());
	 
	/**
	 * teste la méthode getAnnee()
	 */
	@Test
	public void testGetValue() {
		Sejours s = new Sejours(2023, toto, tata);
		assertEquals(2023, s.getAnnee());
	}
	
	/**
	 * teste la méthode getHost()
	 */
	@Test
	public void testGetHost() {
		Sejours s = new Sejours(2023, toto, tata);
		assertEquals(toto, s.getHost());
	}
	
	/**
	 * teste la méthode getGuest()
	 */
	@Test
	public void testGetGuest() {
		Sejours s = new Sejours(2023, toto, tata);
		assertEquals(tata, s.getGuest());
	}
	
	// Teste que le equals de Sejours fonctionne correctement
	@Test
	public void testSejoursEquals() {
		Teenager A = new Teenager("Adonia","A","FRANCE","2004-07-15","female",new HashMap<>());
	    Teenager B = new Teenager("Bellatrix","B","FRANCE","2004-07-15","female",new HashMap<>());
	    Teenager C = new Teenager("Callista","C","FRANCE","2004-07-15","female",new HashMap<>());
		Sejours s1 = new Sejours(2022,A,B);
		Sejours s2 = new Sejours(2022,A,B);
		Sejours s3 = new Sejours(2022,A,C);
		assertTrue(s1.equals(s2));
		assertFalse(s1.equals(s3));
	}
	
	
}