package sg.edu.nus.cs2020;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/*
 * Textual Answers
 * 
 * 2.c 	The test should catch the thrown exception and generate a positive response to the expected
 * 		error inducing case. Unit tests are supposed to check that the code is performing correctly
 * 		and not let exceptions go uncatched to indicate a correct error response. It should also
 * 		check that the thrown exception is of the correct type.
 * 
 * 2.d	I would take the md5 hash of the password so that I get a fixed length 32 seed for a size 32
 * 		register. Each bit of the seed will be the parity bit of the 32 hexadecimal digits. The tap
 * 		will be determined by the last 5 bits of the seed as an integer. For each colour channel of
 * 		each pixel of the image (assuming 8 bit colour depth), 8 bits will be extracted from the
 * 		shift register and added to the original magnitude, modulo 255. To decrypt the image the
 * 		process is repeated and the 8 bits is now deducted from the channel magnitudes instead.
 * 
 * 		The mystery image is that of the tutors.
 */

public class ShiftRegister implements ILFShiftRegister
{
	//Class variables to store the seed and tap
	private int[] seed;
	private int tap;
	
	//Constructor
	public ShiftRegister(int size, int tap)
	{
		//Bounds checking on tap
		if(tap < 0 || tap >= size)
		{
			throw new IllegalArgumentException("Tap must be between 0 and size-1");
		}
		//Sanity check on size
		else if(size < 1)
		{
			throw new IllegalArgumentException("Size must be at least 1");
		}
		else
		{
			this.seed = new int[size];
			this.tap = tap;
		}
	}
	
	//Alternative constructor
	public ShiftRegister(String password)
	{
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		byte[] digest = md.digest(password.getBytes());
		digest.toString();
		this.seed = new int[32];
	}
	
	@Override
	public void setSeed(int[] seed)
	{
		//Checking size of seed
		if(seed.length != this.seed.length)
		{
			throw new IllegalArgumentException("Supplied seed length is different from constructor");
		}
		
		//Check that each element is 0 or 1
		for(int x = 0; x < seed.length; x++)
		{
			if(seed[x] < 0 || seed[x] > 1)
			{
				throw new IllegalArgumentException("Seed must only contain 0s or 1s ");
			}
		}
		
		//Set the seed
		this.seed = seed;
	}

	@Override
	public int shift()
	{
		//Calculate feedback bit
		int feedback = (this.seed[this.seed.length - 1]) ^ this.seed[this.tap];
		
		for(int x = this.seed.length - 1; x > 0; x--)
		{
			//Shift left by 1
			this.seed[x] = this.seed[x - 1];
		}
		
		//Set LSB to feedback bit
		this.seed[0] = feedback;
		
		return feedback;
	}

	@Override
	public int generate(int k)
	{
		//List to store feedback bits
		List<Integer> bits = new ArrayList<Integer>();
		int result = 0;
		
		//Shift and store feedback bits k times
		for(; k > 0; k--)
		{
			bits.add(this.shift());
		}
		
		//Binary multiplier, *2 per bit
		int multiplier = 1;
		
		for(int x = 0; x < bits.size(); x++)
		{
			//Bits are generated MSB first, so reverse index
			int index = bits.size() - x - 1;
			
			//Convert binary bits to integer
			result += bits.get(index) * multiplier;
			multiplier *= 2;
		}
		
		return result;
	}
}
