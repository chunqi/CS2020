package sg.edu.nus.cs2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

public class HerbertLogTest {
	@Test
	public void friday() {
		HerbertLog log = new HerbertLog("logs/FridayHerbert.txt");
		
		HashSet<String> uniqueNames = new HashSet<String>();
		
		System.out.println("Num minutes: " + log.numMinutes());
		
		for(long x = 0; x < log.numMinutes(); x++) {
			Record record = log.get(x);
			
			uniqueNames.add(record.getName().toLowerCase());
		}
		
		System.out.println("Unique names: " + uniqueNames.size());
		
		ArrayList<String> missedNames = new ArrayList<String>();
		
		try {
			FileReader f = new FileReader("logs/namesFullList.txt");
			BufferedReader b = new BufferedReader(f);
			
			while(b.ready()) {
				String name = b.readLine();
				if(!uniqueNames.contains(name.toLowerCase())) {
					missedNames.add(name.toLowerCase());
				}
			}
			
			b.close();
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Missed names: " + missedNames.size());
		
		for(int x = 0; x < missedNames.size(); x++) {
			System.out.println(missedNames.get(x));
		}
		
		for(int x = 0; x < missedNames.size(); x += 8) {
			int eightBitByte = 0;
			for(int y = 0; y < 8; y++) {
				String name = missedNames.get(x + y);
				int LSB = name.length() % 2;
				eightBitByte = eightBitByte << 1;
				eightBitByte = eightBitByte | LSB;
				System.out.print(name.length() % 2);
			}
			System.out.println(": " + (char) eightBitByte + " " + eightBitByte);
		}
	}
}
