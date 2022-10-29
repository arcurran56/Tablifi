package tablifi;

import org.apache.commons.imaging.ImageReadException;

import java.io.*;
import java.util.List;

public class Tablifi {
    private final static FileFilter imageFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(".JPG");
        }
    };
    public Tablifi() {
    }

    public static void main(String[] args) throws IOException, ImageReadException {
        String folderPath = args[0];
        File imagesFolder = new File(folderPath);

        String outputFileName = "Image List.xlsx";
        File outputFile = new File(folderPath, outputFileName);

        File[] imagesArray = imagesFolder.listFiles(f -> f.getName().endsWith(".JPG"));

        ResultSS resultSS = new ResultSS();
        List<String> ssHeaderRow = resultSS.getSsHeaderRow();
        ssHeaderRow.add("File Name");
        ssHeaderRow.add("Date Taken");
        ssHeaderRow.add("Time Taken");
        ssHeaderRow.add("Description");


        for (File imageFile: imagesArray) {


            ImageAnnotations imageAnnotations = new ImageAnnotations(imageFile);

            List<SSRow> ssRows = resultSS.getSsRows();
            SSRow ssRow = new SSRow();
            ssRow.setFileName(imageAnnotations.getFileName());
            ssRow.setDateTaken(imageAnnotations.getDateTaken());
            ssRow.setTimeTaken(imageAnnotations.getTimeTaken());
            ssRow.setDescription(imageAnnotations.getDescription());

            ssRows.add(ssRow);
        }
        OutputStream outputStream = new FileOutputStream(outputFile);
        resultSS.createSpreadsheet(outputStream);
    }
}
