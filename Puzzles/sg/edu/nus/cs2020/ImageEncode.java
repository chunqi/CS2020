package sg.edu.nus.cs2020;

/**
 * class ImageEncode
 * @author dcsslg
 * Description: Encodes and decodes images using a simple shift-register scheme
 */
public class ImageEncode
{

	/**
	 * transform
	 * @param image is the SimpleImage to manipulate
	 * @param s
	 * @throws Exception
	 */
	static void transform(SimpleImage image)
	{		

		// If no image, do nothing and return
		if (image == null) return;
		
		// Get the height and width of the image
		int iWidth = image.getImgWidth();
		int iHeight = image.getImgHeight();
				
		// Catch all exceptions
		try
		{
			// Iterate over every pixel in the image
			for (int i=0; i<iWidth; i++){
				for (int j=0; j<iHeight; j++){							
					// For each pixel, get the red, green, and blue components of the color
					int red = image.getRed(j,i);
					int green = image.getGreen(j,i);
					int blue = image.getBlue(j,i);
					
					//Mark the LSB that hide data
					blue = blue % 2 == 0 ? 0 : 255;
					
					//Draw red lines to visualize data stride
					if(j % 8 == 0) red = 255;
					
					//Bits 0 - 7 empty
					//Bits 8 - 15 data
					if(j % 16 == 0 && i < 3) {
						int eightBitByte = 0;
						
						//Decode the byte data
						for(int x = 8; x < 16; x++) {
							int LSB = image.getBlue(j + x, i) % 2;
							eightBitByte = eightBitByte << 1;
							eightBitByte = eightBitByte | LSB;
						}
						
						//System.out.println("Byte @ " + j + ": " + (char) eightBitByte);
						System.out.print((char) eightBitByte);
					}
					
					// Update the image
					image.setRGB(j,i,red,green,blue);
				}
			}
		}
		catch(Exception e)
		{
			// Print out any errors
			System.out.println("Error with transformation: " + e);
		}		
	}
	
	/**
	 * main procedure
	 * @param args
	 */	
	public static void main(String[] args)
	{
		// Open an image
		SimpleImage image = new SimpleImage("Mystery Image", "mystery.bmp");
		
		// Transform the image using a shift register
		try
		{
			transform(image);			
		}
		catch(Exception e)
		{
			System.out.println("Error in transforming image: " + e);
		}
	}	
}


