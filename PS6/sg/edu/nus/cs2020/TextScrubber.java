package sg.edu.nus.cs2020;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextScrubber {
	public static void main(String[] args) {
		try {
			FileReader fileReader = new FileReader("sg/edu/nus/cs2020/input.txt");
			FileWriter fileWriter = new FileWriter("sg/edu/nus/cs2020/output.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			while(bufferedReader.ready()) {
				int c = bufferedReader.read();
				if(c < 128) bufferedWriter.write(c);
			}
			
			bufferedReader.close();
			bufferedWriter.flush();
			bufferedWriter.close();
			
			fileReader.close();
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Error: IOException");
			e.printStackTrace();
		}
		
		System.out.println("Done.");
	}
}
