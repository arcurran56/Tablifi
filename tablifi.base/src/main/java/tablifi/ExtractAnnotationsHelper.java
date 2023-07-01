package tablifi;

import org.apache.commons.imaging.ImageReadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.List;

public class ExtractAnnotationsHelper {
    private static final Logger logger = LogManager.getLogger();

    private static class EmptySelectionException extends Exception {
    }


    public static String extract(File imagesFolder) {
        String completionState;
        String lastImageFileName = "";

        try {
        if( imagesFolder == null ) throw new EmptySelectionException();

        String outputFileName = "Image List.xlsx";
        File outputFile = new File(imagesFolder, outputFileName);

        File[] imagesArray = imagesFolder.listFiles(f -> f.getName().endsWith(".JPG"));

        ResultSS resultSS = new ResultSS();
        List<String> ssHeaderRow = resultSS.getSsHeaderRow();
        ssHeaderRow.add("File Name");
        ssHeaderRow.add("Date Taken");
        ssHeaderRow.add("Time Taken");
        ssHeaderRow.add("Description");

        completionState = "Done.";

        for (File imageFile : imagesArray) {
            lastImageFileName = imageFile.getName();
            logger.debug("Image file: " + imageFile.getName());

            ImageAnnotations imageAnnotations = null;

            imageAnnotations = new ImageAnnotations(imageFile);

            List<SSRow> ssRows = resultSS.getSsRows();
            SSRow ssRow = new SSRow();
            ssRow.setFileName(imageAnnotations.getFileName());
            ssRow.setDateTaken(imageAnnotations.getDateTaken());
            ssRow.setTimeTaken(imageAnnotations.getTimeTaken());
            ssRow.setDescription(imageAnnotations.getDescription());

            ssRows.add(ssRow);
        }
        OutputStream outputStream = null;

            outputStream = new FileOutputStream(outputFile);

            resultSS.createSpreadsheet(outputStream);

        } catch (ImageReadException | IOException e) {
            logger.warn("Failed to read image file: " + lastImageFileName);
            completionState = "IO Failure";

        } catch (EmptySelectionException e) {
            completionState = "No folder selected.";
        }
        return completionState;
    }
}