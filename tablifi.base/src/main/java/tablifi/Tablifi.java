package tablifi;

import java.io.File;

public class Tablifi {
    public Tablifi() {
    }
    public static void main(String[] args){
        String imagesFolderName = args[0];
        ExtractAnnotationsHelper extractAnnotationsHelper = new ExtractAnnotationsHelper();
        extractAnnotationsHelper.extract(imagesFolderName);
    }
}
