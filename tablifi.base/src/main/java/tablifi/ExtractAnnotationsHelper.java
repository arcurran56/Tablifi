package tablifi;

import org.apache.commons.imaging.ImageReadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.List;

public class ExtractAnnotationsHelper {
    private static final Logger logger = LogManager.getLogger();

    public static String extract(File imagesFolder) {

        String outputFileName = "Image List.xlsx";
        File outputFile = new File(imagesFolder, outputFileName);

        File[] imagesArray = imagesFolder.listFiles(f -> f.getName().endsWith(".JPG"));

        ResultSS resultSS = new ResultSS();
        List<String> ssHeaderRow = resultSS.getSsHeaderRow();
        ssHeaderRow.add("File Name");
        ssHeaderRow.add("Date Taken");
        ssHeaderRow.add("Time Taken");
        ssHeaderRow.add("Description");

        String completionState = "Done.";
        for (File imageFile : imagesArray) {

            ImageAnnotations imageAnnotations = null;
            try {
                imageAnnotations = new ImageAnnotations(imageFile);
            } catch (ImageReadException | IOException e) {
                logger.warn("Failed to read image file: " + imageFile);

            }

            List<SSRow> ssRows = resultSS.getSsRows();
            SSRow ssRow = new SSRow();
            ssRow.setFileName(imageAnnotations.getFileName());
            ssRow.setDateTaken(imageAnnotations.getDateTaken());
            ssRow.setTimeTaken(imageAnnotations.getTimeTaken());
            ssRow.setDescription(imageAnnotations.getDescription());

            ssRows.add(ssRow);
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outputFile);

            resultSS.createSpreadsheet(outputStream);

        } catch (IOException e) {
            completionState = "Write Failure";
        }
        return completionState;
    }
}