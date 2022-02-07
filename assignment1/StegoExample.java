
public class StegoExample {

    public static void main (String[] argv)
    {
	MyStegoImage image = new MyStegoImage ("washdc.jpg", "Washington DC");
	image.show ();
	// Test #1.
	System.out.println (image.getHiddenWatermark());  // Prints 11010010


	// Test #2.
	image.setHiddenWatermark ("01011001");
	image.writeToFile ("washdc-secret.png");
	// Now the stego image is written.

	// Pretend to be the receiver and read the file.
	MyStegoImage image2 = new MyStegoImage ("washdc-secret.png", "Washington DC");
	image2.show ();
	// Extract the hidden message:
	System.out.println (image2.getHiddenWatermark()); // Prints 01011001
    }

}
