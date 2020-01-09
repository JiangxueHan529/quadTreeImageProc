package quadtree;
public class Pixel {
        /*
         * @param: int red: This is the
         * integer that stores the value of red
         */
        private int red;
        /*
         * @param: int green: This is the
         * integer that stores the value of green
         */
        private int green;
        /*
         * @param: int blue: This is the
         * integer that stores the value of blue
         */
        private int blue;
        /*
         * @param: int x
         * This is the x coordinate of the pixel
         */
        private int x;
        /*
         * @param int y
         * This is the y coordinate of the pixel
         */
        private int y;
        /*
         * @param int red
         * @param int green
         * @param int blue
         * @param int x
         * @param int y
         * This is the constructor of a  Pixel that
         * takes the five integers and assign them to instance variables
         */
        public Pixel(int red, int green, int blue, int x, int y) {
                this.red = red;
                this.green = green;
                this.blue = blue;
                this.x = x;
                this.y = y;
        }
        /*
         * @return x
         * This isthe getter for x coordinate
         */
        public int getX() {
                return x;
        }
        /*
         * @param int x
         * This is the setter for x coordinate
         */
        public void setX(int x) {
                this.x = x;
        }
        /*
         * @return y
         * This is he getter for y coordinate
         */
        public int getY() {
                return y;
        }
        /*
         * @param int y
         * This is the setter for y coordinate
         */
        public void setY(int y) {
                this.y = y;
        }
        /*
         * @return red
         * This is the getter for instance variable red
         */
        public int getRed() {
                return red;
        }
        /*
         * @return green
         * This is the getter for instance variable green
         */
        public int getGreen() {
                return green;
        }
        /*
         * @return blue
         * This is the getter for instance variable blue
         */
        public int getBlue() {
                return blue;
        }
        /*
         * @return  Integer.toString(red) + " " + Integer.toString(green)
                + " " + Integer.toString(blue)
                This is the toString method that returns the r,g,b values.
         */
        public String toString() {
                return Integer.toString(red) + " " + Integer.toString(green)
                + " " + Integer.toString(blue);
        }
}