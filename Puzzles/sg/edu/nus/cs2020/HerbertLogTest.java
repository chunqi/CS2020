package sg.edu.nus.cs2020;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class HerbertLogTest {
	public static boolean UPPERCASE = true;
	public static boolean LOWERCASE = !UPPERCASE;
	
	private String int2bin(int n) {
		String bin = "";
		for(int x = 0; x < 8; x++) {
			bin = n % 2 == 0 ? "0" + bin : "1" + bin;
			n /= 2;
		}
		return bin;
	}
	
	private boolean _getCase(String s) {
		char f = s.charAt(0);
		if(f <= 'Z' && f >= 'A') return UPPERCASE;
		else if(f <= 'z' && f >= 'a') return LOWERCASE;
		else throw new IllegalArgumentException();
	}
	
	public void rewrite() {
		HerbertLog log = new HerbertLog("FridayHerbert.txt");
		
		try {
			FileWriter fileWriter = new FileWriter("input.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for(long x = 0; x < log.numMinutes(); x++) {
				Record record = log.get(x);
				bufferedWriter.write(record.getWages() + "\n");
			}
			bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void sortedRecords() {
		ArrayList<Record> records = new ArrayList<Record>();
		HerbertLog log = new HerbertLog("FridayHerbert.txt");
		for(long x = 0; x < log.numMinutes(); x++) {
			records.add(log.get(x));
		}
		Collections.sort(records);
		for(int x = 0; x < records.size(); x++) {
			Record record = records.get(x);
			System.out.println(record.getName() + ": " + record.getWages());
		}
	}
	
	@Test
	public void wageCounts() {
		HerbertLog log = new HerbertLog("FridayHerbert.txt");
		int[] counts = new int[100];
		for(long x = 0; x < log.numMinutes(); x++) {
			Record record = log.get(x);
			counts[record.getWages()]++;
		}
		for(int x = 0; x < counts.length; x++) {
			System.out.println(x + ":\t" + counts[x] + "\t" + (char) counts[x]);
		}
	}
	
	@Test
	public void zeroes() {
		HerbertLog log = new HerbertLog("FridayHerbert.txt");
		int count = 0;
		for(long x = 0; x < log.numMinutes(); x++) {
			Record record = log.get(x);
			if(record.getWages() == 0) {
				System.out.println("==" + record.getName() + ":" + count + "==");
				count = 0;
			}
			else {
				System.out.println(record.getWages() + "\t" + record.getName());
				count++;
			}
		}
	}
	
	@Test
	public void test() {
		HerbertLog log = new HerbertLog("FridayHerbert.txt");
		String employer = "";
		String upperCase = "";
		String lowerCase = "";
		
		int bitmash = 0;
		int sum = 0;
		for(long x = 0; x < log.numMinutes(); x++) {
			Record record = log.get(x);
			if(record.getName().compareTo(employer) != 0) {
				//System.out.println(int2bin(bitmash) + " " + bitmash + " " + (char) bitmash);
				//System.out.println("Sum: " + sum);
				bitmash = 0;
				sum = 0;
				employer = record.getName();
				//System.out.println("==" + employer + "==");
				if(employer.charAt(0) >= 'A' && employer.charAt(0) <= 'Z') {
					System.out.println(record.getWages());
				}
			}
			int wage = record.getWages();
			bitmash |= wage;
			sum += wage;
			if(wage <= 'Z' && wage >= 'A') upperCase += (char) wage;
			if(wage <= 'z' && wage >= 'a') lowerCase += (char) wage;
			//System.out.println(int2bin(record.getWages()) + " " + record.getWages());
		}
		System.out.println();
		//System.out.println(lowerCase);
		//System.out.println(upperCase);
	}
}
