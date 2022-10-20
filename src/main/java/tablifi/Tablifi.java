package tablifi;

import org.apache.commons.imaging.ImageReadException;

import java.io.File;
import java.io.IOException;

public class Tablifi {
    public Tablifi() {
    }
public static void main(String[] args) throws IOException, ImageReadException {
        String imageFileNameString = args[0];
        String folderPath = "D:\\Users\\arcur\\Pictures\\GetInVOLEd\\Inspected\\WV5\\1009";
        File imageFile = new File(folderPath, imageFileNameString);

        AnnotatedImage.metadataExample(imageFile);
}
}
