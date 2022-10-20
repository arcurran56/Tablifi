package tablifi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
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
public class AnnotatedImage {


        public AnnotatedImage() {
        }
        public static void metadataExample(final File file) throws ImageReadException,
                    IOException {
                // get all metadata stored in EXIF format (ie. from JPEG or TIFF).
                final ImageMetadata metadata = Imaging.getMetadata(file);

                // System.out.println(metadata);

                if (metadata instanceof JpegImageMetadata) {
                    final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

                    // Jpeg EXIF metadata is stored in a TIFF-based directory structure
                    // and is identified with TIFF tags.
                    // Here we look for the "x resolution" tag, but
                    // we could just as easily search for any other tag.
                    //
                    // see the TiffConstants file for a list of TIFF tags.

                    System.out.println("file path: " + file.getPath());
                    System.out.println("file name: " + file.getName());

                    // print out various interesting EXIF tags.
                    printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_XRESOLUTION);
                    printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_DATE_TIME);
                    printTagValue(jpegMetadata,
                            ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                    printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED);
                    printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_ISO);
                    printTagValue(jpegMetadata,
                            ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE);
                    printTagValue(jpegMetadata,
                            ExifTagConstants.EXIF_TAG_APERTURE_VALUE);
                    printTagValue(jpegMetadata,
                            ExifTagConstants.EXIF_TAG_BRIGHTNESS_VALUE);

                    System.out.println();

                    final List<ImageMetadataItem> items = jpegMetadata.getItems();
                    for (final ImageMetadataItem item : items) {
                        System.out.println("    " + "item: " + item);
                    }

                    System.out.println();
                }
            }private static void printTagValue(final JpegImageMetadata jpegMetadata,
        final TagInfo tagInfo) {
            final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
            if (field == null) {
                System.out.println(tagInfo.name + ": " + "Not Found.");
            } else {
                System.out.println(tagInfo.name + ": "
                        + field.getValueDescription());
            }
        }
}
