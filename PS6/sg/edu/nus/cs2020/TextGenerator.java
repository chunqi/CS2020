package sg.edu.nus.cs2020;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TextGenerator {
	public static void main(String[] args) {
		//Debugging arguments
		if(true) {
			args = new String[3];
			args[0] = "10";
			args[1] = "5000";
			args[2] = "sg/edu/nus/cs2020/input.txt";
		}
		
		//Parse the strings into the correct types
		int order = Integer.parseInt(args[0]);
		int n = Integer.parseInt(args[1]);
		String filePath = args[2];
		
		//Read the file into a single string
		StringBuilder text = new StringBuilder();
		
		try {
			FileReader fileReader = null;
			fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while(bufferedReader.ready()) {
				String line = bufferedReader.readLine();
				text.append(line);
			}
			
			bufferedReader.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found <" + filePath + ">");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error: IOException");
			e.printStackTrace();
		}
		
		//Create the Markov model from the text
		MarkovModel model = new MarkovModel(text.toString(), order);
		
		//Seed it randomly
		Random random = new Random();
		long s = random.nextLong();
		model.setRandomSeed(7943828856264018565L);
		
		//Get the first k characters as a kgram
		String initialKGram = text.toString().substring(0, order);
		String kgram = initialKGram;
		
		//Start collecting output
		StringBuilder output = new StringBuilder();
		for(int x = 0; x < n;) {
			char c = model.nextCharacter(kgram);
			
			if(c == MarkovModel.NOCHAR) {
				kgram = initialKGram;
			}
			else {
				kgram = kgram.substring(1) + c;
				output.append(c);
				x++;
			}
		}
		
		//Output
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("sg/edu/nus/cs2020/output.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(output.toString());
			
			bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Error: IOException");
			e.printStackTrace();
		}
		
		System.out.println("Output: " + output.toString());
		System.out.println("Seed: " + s);
	}
}
