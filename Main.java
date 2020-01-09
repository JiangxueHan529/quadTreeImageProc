package quadtree;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
        /*
         * @param String[] args
         * This is the main method that takes user input and determines which operations to perform.
         * We read data in from ppm files and create instances of Picture and
         *  quadtree to perform the operations, and write it back
         * to String form after. The data for compression level and
         * threshold are also  processed here.
         */
        public static void main(String[] args) {
                ArrayList<String[]> fileLines = new ArrayList<String[]>();
                try {
                         final String BOX = "-t";
                         final String FILENAMEOUT = "-o";
                         final String COMPRESSION = "-c";
                         final String FILTER = "-x";
                         final String EDGEDETECTION = "-e";
                         final String FILENAMEIN = "-i";
                        /*the four boolean variabels and two strings from command line input
                         *   that determines which pictures and operations to be done
                         */
                        boolean compression = false;
                        boolean filter = false;
                        boolean boxes = false;
                        boolean edgeDetection = false;
                        String fileName = "";
                        String outputName = "";
                        for(int i = 0; i < args.length; i++) {
                                if(args[i].equals(BOX)) {
                                        boxes = true;
                                }
                                else if(args[i].equals(FILENAMEOUT)) {
                                        outputName = args[i + 1];
                                }
                                else if(args[i].equals(COMPRESSION)) {
                                        compression = true;
                                }
                                else if(args[i].equals(FILTER)) {
                                        filter = true;
                                }
                                else if(args[i].equals(EDGEDETECTION)) {
                                        edgeDetection = true;
                                }
                                else if(args[i].equals(FILENAMEIN)) {
                                        fileName = args[i + 1];
                                }
                        }
                        // Readign the file in in  appropriate form(ArrayList of String [])
                        BufferedReader br = new BufferedReader(new FileReader(new File("images/" + fileName + ".ppm")));
                        String line;
                        while ((line = br.readLine()) != null) {
                                String[] lineArr = line.split("\\s+");
                                fileLines.add(lineArr);
                        }
                        br.close();
                        //create an instance of the data storage class Picture
                        Picture testPicture = new Picture(fileLines);
                        /*if the picture is not to be compressed, create a QuadTree that only
                         * deletes leaf Nodes when every pixel is exactly the same
                         */
                        if(!compression) {
                                QuadTree newTree = new QuadTree(testPicture, compression, 0);
                                String newString;
                                //if edgeDetection is not to be performed, do filter with or without boxes
                                if(!edgeDetection) {
                                        newString = newTree.compressionPhoto(filter,boxes);
                                }
                                //Do edge detection with or without boxes
                                else {
                                        newString = newTree.compressionPhotoWithEdgeDetection(boxes);
                                }
                                //write the file back
                                BufferedWriter writer = new BufferedWriter(new FileWriter(outputName+ ".ppm"));
                                writer.write("P3\n" + testPicture.getPixelsPerSide() + " " +
                                                testPicture.getPixelsPerSide() + "\n" +
                                                "255\n" + newString);
                                writer.close();
                        }
                        else {
                                int error = 0; //error threshhold
                                boolean zeroZeroTwothreshholdFound = false; //whether or not the
                                        //.002 threshhold was found
                                boolean zeroZeroFourthreshholdFound = false; //whether or not the
                                //.004 threshhold was found
                                boolean zeroOnethreshholdFound = false; //whether or not the
                                //.01 threshhold was found
                                boolean zeroThreeThreethreshholdFound = false; //whether or not the
                                //.033 threshhold was found
                                boolean zeroSevenSeventhreshholdFound = false; //whether or not the
                                //.077 threshhold was found
                                boolean twothreshholdFound = false; //whether or not the
                                //.2 threshhold was found
                                boolean fivethreshholdFound = false; //whether or not the
                                //.5 threshhold was found
                                boolean sevenFivethreshholdFound = false; //whether or not the
                                //.75 threshhold was found
                                int pictureCounter = 1; //the number picture we are on for
                                        //the output file name
                                QuadTree newTree = new QuadTree(testPicture, compression, error); //the
                                        //tree with the desired error threshhold
                                String photoString; //the string we will put into the writer
                                        //for the file reader to create a picture

                                while(!sevenFivethreshholdFound && newTree.getCompressionLevel() > .74) { //keep
                                        //going through larger and larger error threshholds until the desired
                                        //compression level is found

                                        newTree = new QuadTree(testPicture, compression, error);
                                        if(newTree.getCompressionLevel() > .74 && newTree.getCompressionLevel() < .76) { //if found
                                                photoString = newTree.compressionPhoto(filter, boxes);
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(outputName+ "-" + pictureCounter
                                                        + ".ppm"));
                                                writer.write("P3\n" + testPicture.getPixelsPerSide() + " " +
                                                                testPicture.getPixelsPerSide() + "\n" +
                                                                "255\n" + photoString);
                                                writer.close();
                                                pictureCounter++;
                                                sevenFivethreshholdFound = true;
                                        }
                                        error++;
                                }

                                if(sevenFivethreshholdFound == false) {
                                        System.out.println(".75 threshhold not possible");
                                }

                                while(!fivethreshholdFound && newTree.getCompressionLevel() > .49) {

                                        newTree = new QuadTree(testPicture, compression, error);
                                        if(newTree.getCompressionLevel() > .49 && newTree.getCompressionLevel() < .51){
                                                photoString = newTree.compressionPhoto(filter, boxes);
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(outputName+ "-" + pictureCounter
                                                                + ".ppm"));
                                                writer.write("P3\n" + testPicture.getPixelsPerSide() + " " +
                                                                testPicture.getPixelsPerSide() + "\n" +
                                                                "255\n" + photoString);
                                                writer.close();
                                                pictureCounter++;
                                                fivethreshholdFound = true;
                                        }
                                        error++;
                                }

                                if(fivethreshholdFound == false) {
                                        System.out.println(".5 threshhold not possible");
                                }

                                while(!twothreshholdFound && newTree.getCompressionLevel() > .19) {

                                        newTree = new QuadTree(testPicture, compression, error);
                                        if(newTree.getCompressionLevel() > .19 && newTree.getCompressionLevel() < .21){
                                                photoString = newTree.compressionPhoto(filter, boxes);
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(outputName+ "-" + pictureCounter
                                                                + ".ppm"));
                                                writer.write("P3\n" + testPicture.getPixelsPerSide() + " " +
                                                                testPicture.getPixelsPerSide() + "\n" +
                                                                "255\n" + photoString);
                                                writer.close();
                                                pictureCounter++;
                                                twothreshholdFound = true;
                                        }
                                        error++;
                                }

                                if(twothreshholdFound == false) {
                                        System.out.println(".2 threshhold not possible");
                                }


                                while(!zeroSevenSeventhreshholdFound && newTree.getCompressionLevel() > .076) {

                                        newTree = new QuadTree(testPicture, compression, error);
                                        if(newTree.getCompressionLevel() > .076 && newTree.getCompressionLevel() < .078){
                                                photoString = newTree.compressionPhoto(filter, boxes);
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(outputName+ "-" + pictureCounter
                                                                + ".ppm"));
                                                writer.write("P3\n" + testPicture.getPixelsPerSide() + " " +
                                                                testPicture.getPixelsPerSide() + "\n" +
                                                                "255\n" + photoString);
                                                writer.close();
                                                pictureCounter++;
                                                zeroSevenSeventhreshholdFound = true;
                                        }
                                        error++;
                                }

                                if(zeroSevenSeventhreshholdFound == false) {
                                        System.out.println(".077 threshhold not possible");
                                }

                                while(!zeroThreeThreethreshholdFound && newTree.getCompressionLevel() > .032) {
                                        newTree = new QuadTree(testPicture, compression, error);
                                        if(newTree.getCompressionLevel() > .032 && newTree.getCompressionLevel() < .034){
                                                photoString = newTree.compressionPhoto(filter, boxes);
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(outputName+ "-" + pictureCounter
                                                                + ".ppm"));
                                                writer.write("P3\n" + testPicture.getPixelsPerSide() + " " +
                                                                testPicture.getPixelsPerSide() + "\n" +
                                                                "255\n" + photoString);
                                                writer.close();
                                                pictureCounter++;
                                                zeroThreeThreethreshholdFound = true;
                                        }
                                        error++;
                                }

                                if(zeroThreeThreethreshholdFound == false) {
                                        System.out.println(".033 threshhold not possible");
                                }

                                while(!zeroOnethreshholdFound && newTree.getCompressionLevel() > .009) {
                                        newTree = new QuadTree(testPicture, compression, error);
                                        if(newTree.getCompressionLevel() > .009 && newTree.getCompressionLevel() < .011){
                                                photoString = newTree.compressionPhoto(filter, boxes);
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(outputName+ "-" + pictureCounter
                                                                + ".ppm"));
                                                writer.write("P3\n" + testPicture.getPixelsPerSide() + " " +
                                                                testPicture.getPixelsPerSide() + "\n" +
                                                                "255\n" + photoString);
                                                writer.close();
                                                pictureCounter++;
                                                zeroOnethreshholdFound = true;
                                        }
                                        error++;
                                }

                                if(zeroOnethreshholdFound == false) {
                                        System.out.println(".01 threshhold not possible");
                                }

                                while(!zeroZeroFourthreshholdFound && newTree.getCompressionLevel() > .0039) {

                                        newTree = new QuadTree(testPicture, compression, error);
                                        if(newTree.getCompressionLevel() > .0039 && newTree.getCompressionLevel() < .0041){
                                                photoString = newTree.compressionPhoto(filter, boxes);
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(outputName+ "-" + pictureCounter
                                                                + ".ppm"));
                                                writer.write("P3\n" + testPicture.getPixelsPerSide() + " " +
                                                                testPicture.getPixelsPerSide() + "\n" +
                                                                "255\n" + photoString);
                                                writer.close();
                                                pictureCounter++;
                                                zeroZeroFourthreshholdFound = true;
                                        }
                                        error++;
                                }

                                if(zeroZeroFourthreshholdFound == false) {
                                        System.out.println(".004 threshhold not possible");
                                }

                                while(!zeroZeroTwothreshholdFound && newTree.getCompressionLevel() > .0019) {

                                        newTree = new QuadTree(testPicture, compression, error);
                                        if(newTree.getCompressionLevel() > .0019 && newTree.getCompressionLevel() < .0021){
                                                photoString = newTree.compressionPhoto(filter, boxes);
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(outputName+ "-" + pictureCounter
                                                        + ".ppm"));
                                                writer.write("P3\n" + testPicture.getPixelsPerSide() + " " +
                                                        testPicture.getPixelsPerSide() + "\n" +
                                                        "255\n" + photoString);
                                                writer.close();
                                                pictureCounter++;
                                                zeroZeroTwothreshholdFound = true;
                                        }
                                        error++;
                                }

                                if(zeroZeroTwothreshholdFound == false) {
                                        System.out.println(".002 threshhold not possible");
                                }

                        }
                }
                catch(IOException e) {
                        e.printStackTrace();
                }
        }
}
