package e3;

//imports
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/*
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
*/

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//teste que les méthodes de critères fonctionennent comem il le faudrait.
public class CriterionTests {
	
	
	/**
	 * teste la méthode getValue()
	 */
	@Test
	public void testGetValue() {
		Criterion c = new Criterion("male", CriterionName.PAIR_GENDER);
		assertEquals("male", c.getValue());
	}
	
	//
	/**
	 * teste la méthode getLabel()
	 */
	@Test
	public void testGetLabel() {
		Criterion c = new Criterion("male", CriterionName.PAIR_GENDER);
		assertEquals(CriterionName.PAIR_GENDER, c.getLabel());
	}
	
	//
	/**
	 * teste la méthode getCriterionName()
	 */
	@Test
	public void testGetCriterionName() {
		Criterion c = new Criterion("male", CriterionName.PAIR_GENDER);
		assertEquals(CriterionName.PAIR_GENDER, c.getCriterionName());
	}
	
	//
	/**
	 * Teste la méthode isValid()
	 */
	@Test
	public void testIsValid() {
		Criterion c = new Criterion("male", CriterionName.PAIR_GENDER);
		assertTrue(c.isValid());
		Criterion c1 = new Criterion("wallmartbag", CriterionName.PAIR_GENDER);
		assertFalse(c1.isValid());
	}
	
	//
	/**
	 * Teste la méthode toString()
	 */
	@Test
	public void testToString() {
		Criterion c = new Criterion("male", CriterionName.PAIR_GENDER);
		String expected = "PAIR_GENDER: male\n";
		assertEquals(expected, c.toString());
	}
	
}
