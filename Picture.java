package quadtree;
import java.util.ArrayList;
public class Picture {
        /*
         * @param ArryList<ArrayList<Pixel>> picture
         * This is the instance variable that  holds data for a whole picture.
         */
        private ArrayList<ArrayList<Pixel>> picture;
        /*
         * @param int pixelPerside: This is the number of pixels on one row
         * or column in a picture.
         */
        int pixelsPerSide;
        /*
         * @param ArrayList<ArrayList<Pixel>> picture
         * @param int h
         *This is the constructor that takes an existing picture
         *and assigns the ArrayList to it. int h is an unnesessary
         *parameter, it's here to differentiate this constructor from
         *the other one.
         */
        public Picture(ArrayList<ArrayList<Pixel>> picture, int h) {
                this.picture = picture;
        }
        /*
         * @param: ArrayList<String[]> pictureList
         * This is the constructor that takes an ArrayList of String arrays from
         *  the reader and put the data into Pixels. It also keeps tracks of the number
         *  of Pixels so as to make sure that the ArrayList of ArryList is structured
         *  just like the original picture.
         */
        public Picture(ArrayList<String[]> pictureList) {
                //Declaration o new, empty ArrayLists
                picture = new ArrayList<ArrayList<Pixel>>();
                ArrayList<Pixel> pixelList = new ArrayList<Pixel>();
                pixelsPerSide = Integer.parseInt(pictureList.get(1)[0]);
                /*Go through the first three lines and remove them
                 * since they are not information of colors of pixels
                 */
                for(int i = 3; i < pictureList.size(); i++) {
                        if(pictureList.get(i)[0].equals("")) {
                                pictureList.remove(i);
                        }
                }
                /*Go through the whoole list and parse information
                 * in it into pixels and add them into ArryLists
                 */
                for(int i = 3; i < pictureList.size(); i++) {
                        for(int j = 0; j < pictureList.get(i).length; j +=3 ) { //it's get(i).length
                                //because we were afraid that length might be a little different for each row
                                /*
                                 * if this index is the length of that row minus 2,
                                 *  that means the last pixel in this
                                 * row is green instead of blue as anticipated. We handle this by
                                 * getting the first number in the
                                 * next row and start next row by index 1.
                                 */
                                if(j == pictureList.get(i).length - 2){
                                        Pixel newPixel = new Pixel(Integer.parseInt(pictureList.get(i)[j]),
                                                        Integer.parseInt(pictureList.get(i)[j + 1]),
                                                        Integer.parseInt(pictureList.get(i + 1)[0]),
                                                        pixelList.size(),picture.size());
                                        //Adding the pixel into the row List
                                        pixelList.add(newPixel);
                                        i++;
                                        j = 1;
                                }
                                //the situation where the last number is blue, which is the normal situation
                                else if(j == pictureList.get(i).length - 1){
                                        if(pictureList.get(i + 1).length == 1) {
                                                Pixel newPixel = new Pixel(Integer.parseInt(pictureList.get(i)[j]),
                                                                Integer.parseInt(pictureList.get(i + 1)[0]), Integer.parseInt(
                                                                                pictureList.get(i + 2)[0]),pixelList.size(),picture.size());
                                                pixelList.add(newPixel);
                                                i += 2;
                                        }
                                        /*the situation where the last number is red,
                                         *  handled similarly as the first situation, adding the first
                                         *  two numbers in the next rows and start the next row from the
                                         *  third number.
                                         */
                                        else {
                                                Pixel newPixel = new Pixel(Integer.parseInt(pictureList.get(i)[j]),
                                                                Integer.parseInt(pictureList.get(i + 1)[0]), Integer.parseInt(
                                                                                pictureList.get(i + 1)[1]),pixelList.size(),picture.size());
                                                pixelList.add(newPixel);
                                                i++;
                                                j = 2;
                                        }
                                }
                                // the normal cases where it's not the last few numbers
                                else {
                                        Pixel newPixel = new Pixel(Integer.parseInt(pictureList.get(i)[j]),
                                                        Integer.parseInt(pictureList.get(i)[j + 1]), Integer.parseInt
                                                        (pictureList.get(i)[j + 2]),pixelList.size(), picture.size());
                                        pixelList.add(newPixel);
                                }
                                /*when the size the pixelList reach the anticipated number, add it to
                                 * the picture and start a new ArrayList
                                 */
                                if(pixelList.size() == pixelsPerSide) {
                                        picture.add(pixelList);
                                        pixelList = new ArrayList<Pixel>();
                                }
                        }
                }

        }
        /*
         * @return ArrayList<ArryaLIst<Pixel>> picture
         * This is the getter for instance variable picture
         */
        public ArrayList<ArrayList<Pixel>> getPicture() {
                return picture;
        }
        /*
         * @param (ArrayList<ArrayList<Pixel>> picture
         * This is the setter for instance variable picture
         */
        public void setPicture(ArrayList<ArrayList<Pixel>> picture) {
                this.picture = picture;
        }
        /*
         * @return pixelsPerSide
         * This is the getter for instance
         * variable pixelsPerSide
         */
        public int getPixelsPerSide() {
                return pixelsPerSide;
        }
        /*
         * @return  strToReturn.toString()
         * This is the toString method that goes through the ArrayList of ArrayList of
         * Pixels and append them into a StirngBuilder and return it as a String
         */
        public String toString() {
                StringBuilder strToReturn = new StringBuilder(); //the string we're going to return
                for(int i = 0; i < picture.size(); i++) {
                        if(i != 0) {
                                strToReturn.append("\n");
                        }
                        for(int j = 0; j < picture.get(i).size(); j++) {
                                if(j != picture.get(i).size() - 1) {
                                        if(j != 0) {
                                                strToReturn.append(" ");
                                                strToReturn.append(picture.get(i).get(j).toString());
                                        }
                                        else {
                                                strToReturn.append(picture.get(i).get(j).toString());
                                        }
                                }
                        }
                }
                return strToReturn.toString();
        }
}
