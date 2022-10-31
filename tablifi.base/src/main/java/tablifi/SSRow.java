package tablifi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SSRow implements Comparable<SSRow> {
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void getFileName(String rank) {
        this.fileName = rank;
    }


    private Date dateTaken;
    private Date timeTaken;

    public Date getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }


    @Override
    public int compareTo(SSRow ssRow) {
        return this.fileName.compareTo(ssRow.fileName);
    }

}
