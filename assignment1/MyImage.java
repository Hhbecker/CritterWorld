public class MyImage {

    ImageTool imTool = new ImageTool ();   
    int[][][] pixels;
    int M, N;                              // #row, #cols
    String title = "no title";

    public MyImage (String filename, String imageTitle)
    {
	pixels = imTool.imageFileToPixels (filename);
	M = pixels.length;
	N = pixels[0].length;
    }


    public void show ()
    {
	imTool.showImage (pixels, title);
    }

    // This will be useful for writing.

    public void writeToFile (String filename)
    {
	imTool.writeToFile (pixels, filename);
    }

}