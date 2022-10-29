package tablifi;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ImageAnnotations {
    private static final Logger logger = LogManager.getLogger();

    private final static SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();

    private String fileName;
    private Date dateTaken;

    private Date timeTaken;
    private String description;

    public String getFileName() {
        return fileName;
    }

    public Date getTimeTaken() {
        return timeTaken;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public String getDescription() {
        return description;
    }

    public ImageAnnotations(final File file) throws ImageReadException,
            IOException {

        // get all metadata stored in EXIF format (ie. from JPEG or TIFF).
        final ImageMetadata metadata = Imaging.getMetadata(file);

        if (metadata instanceof JpegImageMetadata) {
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

            // Jpeg EXIF metadata is stored in a TIFF-based directory structure
            // and is identified with TIFF tags.
            // Here we look for the "x resolution" tag, but
            // we could just as easily search for any other tag.
            //
            // see the TiffConstants file for a list of TIFF tags.

            fileName = file.getName();

            String dateString = getTagValueString(jpegMetadata,
                    TiffTagConstants.TIFF_TAG_DATE_TIME);

            dateTaken = extractDate(dateString);
            timeTaken = dateTaken;

            description = getTagValueString(jpegMetadata,
                    TiffTagConstants.TIFF_TAG_IMAGE_DESCRIPTION);


        }
    }


    private String getTagValueString(final JpegImageMetadata jpegMetadata,
                                     final TagInfo tagInfo) throws ImageReadException {
        final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
        String stringValue = "";
        if (field == null) {
            logger.debug(tagInfo.name + ": " + "Not Found.");
        } else {
            logger.debug(tagInfo.name + ": "
                    + field.getValueDescription());
            stringValue = field.getValue().toString();
        }
        return stringValue;
    }

    private Date extractDate(String dateTimeString) {
        dateFormat.applyPattern("yyyy:MM:dd HH:mm:ss");
        StringBuilder builder = new StringBuilder(90);
        builder.append(dateTimeString.substring(8, 10))
                .append("/")
                .append(dateTimeString.substring(5, 7))
                .append("/")
                .append(dateTimeString.substring(0, 4));

        Date date = null;
        try {
            date = dateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            logger.warn("Failed to parse date: " + dateTimeString);
            date = dateFormat.get2DigitYearStart();
        }
        return date;

    }
}
