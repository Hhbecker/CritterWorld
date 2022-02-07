
public class MyStegoImage extends MyImage {

    // Write the correct constructor that invokes the super class constructor.
    public MyStegoImage (String filename, String imageTitle) {
        super (filename, imageTitle);
    }
 
    // Write setHiddenWatermark()
    public void setHiddenWatermark(String s){
    
        
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == '0' && pixels[0][i][1]%2 == 1){
                pixels[0][i][1] = pixels[0][i][1] + 1;

            }
            if(s.charAt(i) == '1' && pixels[0][i][1]%2 == 0){
                pixels[0][i][1] = pixels[0][i][1] + 1;

            }
            
            
        }
    }

    // Write getHiddenWatermark()
    public String getHiddenWatermark (){ 
        
        String s = "";
        for(int i=0; i<8; i++){

            s = s + (pixels[0][i][1] % 2);
        }

        return s;

    }
}