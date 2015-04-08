package sg.edu.nus.cs2020;

import org.junit.Test;

public class MarkovModelTest {
	@Test
	public void substringTest() {
		int order = 2;
		String text = "gagggagaggcgagaaa";
		
		for(int x = 0; x < text.length() - order + 1; x++) {
			System.out.println(text.substring(x, x + order));
		}
	}
	
	@Test
	public void markovModelConstructorTest() {
		@SuppressWarnings("unused")
		MarkovModel model = new MarkovModel("gagggagaggcgagaaa", 2);
	}
}
