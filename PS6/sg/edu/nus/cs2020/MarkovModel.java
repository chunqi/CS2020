package sg.edu.nus.cs2020;

import java.util.HashMap;
import java.util.Random;

public class MarkovModel {
	public static final char NOCHAR = (char) 255;
	
	private int m_order;
	private Random m_random;
	private HashMap<String, KGramInfo> m_lookup;
	
	private class KGramInfo {
		//Index 128 represents kgram frequency, 0-127 is ASCII char frequency
		private int[] m_frequency = new int[129];
		
		public KGramInfo(char nextChar) {
			//KGramInfo object created when kgram is first encountered
			m_frequency[128] = 1;
			
			//Update ASCII frequency
			if(nextChar != NOCHAR) {
				m_frequency[(int) nextChar] = 1;
			}
		}
		
		//Return frequency of kgram
		public int getFrequency() {return m_frequency[128];}
		
		//Return frequency of a character following the kgram
		public int getFrequency(char c) {return m_frequency[(int) c];}
		
		//Increment frequency of the kgram
		public void incrementFrequency() {m_frequency[128]++;}
		
		//Increment frequency of char encountered after kgram
		public void incrementFrequency(char c) {
			if(c != NOCHAR) m_frequency[(int) c]++;
		}
	}
	
	public MarkovModel(String text, int order) {
		//Initialize the private variables
		m_order = order;
		m_lookup = new HashMap<String, KGramInfo>();
		m_random = new Random();
		
		//Iterate through all kgrams of size order in the text
		for(int x = 0; x < text.length() - order + 1; x++) {
			String kgram = text.substring(x, x + order);
			
			//Check if kgramInfo already stored
			KGramInfo kgramInfo = m_lookup.get(kgram);
			
			char nextChar;
			//Grab the char after the kgram
			if(x == text.length() - order) nextChar = NOCHAR;
			else nextChar = text.charAt(x + order);
			
			//Create new kgramInfo object and store that
			if(kgramInfo == null) {
				kgramInfo = new KGramInfo(nextChar);
			}
			//Update existing kgramInfo object
			else {
				kgramInfo.incrementFrequency();
				kgramInfo.incrementFrequency(nextChar);
			}
			
			//Put the new / update existing kgramInfo in hash map
			m_lookup.put(kgram, kgramInfo);
		}
	}
	
	public int order() {return m_order;}
	
	//Retrieves the required attribute from the KGramInfo object
	private int _getFrequency(String kgram, char c) {
		if(kgram.length() != m_order) {
			throw new IllegalArgumentException("Error: kgram length different from Markov order");
		}
		
		KGramInfo kgramInfo = m_lookup.get(kgram);
		
		//Not found frequency is always 0
		if(kgramInfo == null) return 0;
		//Character not provided, return kgram frequency
		else if(c == NOCHAR) return kgramInfo.getFrequency();
		//Return character frequency wrt kgram
		else return kgramInfo.getFrequency(c);
	}
	
	public int getFrequency(String kgram) {return _getFrequency(kgram, NOCHAR);}
	public int getFrequency(String kgram, char c) {return _getFrequency(kgram, c);}
	
	public char nextCharacter(String kgram) {
		if(kgram.length() != m_order) {
			throw new IllegalArgumentException("Error: kgram length different from Markov order");
		}
		
		KGramInfo kgramInfo = m_lookup.get(kgram);
		
		//System.out.println("kgram: " + kgram);
		
		if(kgramInfo == null) {
			//System.out.println("next: NOCHAR");
			return NOCHAR;
		}
		else
		{
			//System.out.println("freq: " + kgramInfo.getFrequency());
			
			//Get a random integer up to but excluding kgram frequency
			int random = m_random.nextInt(kgramInfo.getFrequency());
			int frequencySum = 0;
			
			//System.out.println("rand: " + random);
			
			//Iterate through all ASCII characters until cumulative frequency > random
			for(int x = 0; x < 128; x++) {
				int frequency = kgramInfo.getFrequency((char) x);
				frequencySum += frequency;
				
				if(frequencySum >= random && frequency != 0) {
					//System.out.println("next: " + x);
					return (char) x;
				}
			}
			
			//Just in case
			throw new RuntimeException("Error: random number out of bounds");
		}
	}
	
	//Instantiate the Random object with specified seed
	void setRandomSeed(long s) {m_random = new Random(s);}
}