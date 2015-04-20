package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * HerbertLogTest
 * 
 * Description: A set of tests to ensure that the correct salary is calculated
 * and the minimum number of minutes for selected goalIncomes are correct
 *
 * @author Goh Chun Teck
 */
public class HerbertLogTest {

	@Test
	/**
	 * Verify the correct salary calculated in veryShortNames
	 */
	public void testVeryShortNamesTotalSalary() {
		HerbertLog log = new HerbertLog("texts/veryShortNamesHerbert.txt");
		
		int salary = log.calculateSalary();
		assertEquals("veryShortNamesTotalSalary", 52, salary);
		System.out.println("veryShortNames: " + log.numGets() + " for " + log.numMinutes());
	}

	@Test
	/**
	 * Verify the correct salary calculated in shortNames
	 */
	public void testShortNamesTotalSalary() {
		HerbertLog log = new HerbertLog("texts/shortNamesHerbert.txt");
		
		int salary = log.calculateSalary();
		assertEquals("shortNamesTotalSalary", 401, salary);
		System.out.println("shortNames: " + log.numGets() + " for " + log.numMinutes());
	}
	
	@Test
	/**
	 * Verify the correct salary calculated in vmanyNames
	 */
	public void testManyNamesTotalSalary() {
		HerbertLog log = new HerbertLog("texts/manyNamesHerbert.txt");
		
		int salary = log.calculateSalary();
		assertEquals("manyNamesTotalSalary", 47803, salary);
		System.out.println("manyNames: " + log.numGets() + " for " + log.numMinutes());
	}
	
	@Test
	/**
	 * Verify the correct salary calculated in longNames
	 */
	public void testLongNamesTotalSalary() {
		HerbertLog log = new HerbertLog("texts/longNamesHerbert.txt");
		
		int salary = log.calculateSalary();
		assertEquals("longNamesTotalSalary", 2796254, salary);
		System.out.println("longNames: " + log.numGets() + " for " + log.numMinutes());
	}
	
	@Test
	public void test() {
		HerbertLog log = new HerbertLog("texts/FridayHerbert.txt");
		System.out.println("salary: " + log.calculateSalary());
		for(int x = 0; x < 10; x++) {
			System.out.println("Goal " + x + ":" + log.calculateMinimumWork(x));
		}
	}
}
