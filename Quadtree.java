package quadtree;
import java.util.ArrayList;
public class QuadTree {
        /*
         * @param Node root
         */
        private Node root;
        /*
         * @param int size:size of the whole QuadTree
         */
        private int size;
        /*
         * @param int sizeOfPicture:the length of a side of picture
         */
        private int sizeOfPicture;
        /*
         * @param int leaves: The number of leaves in a a QuadTree
         */
        private int leaves;
        /*
         * @param double compressionLevel: the number of leaves divide total pixels
         */
        private double compressionLevel;
        /*
         * @param Picture picture
         * @param boolean compression
         * @param int errorThreshold
         * This is the constructor of a QuadTree that takes a picture and boolean whether to
         * compress and an errorThreshold, call the method makeTree(), populateTree and deleteNodes
         * and calculate the compressionLvel.
         */
        public QuadTree(Picture picture, boolean compression, int errorThreshhold) {
                this.root = null;
                size = 0;
                sizeOfPicture = picture.getPicture().size();
                makeTree();
                populateTree(picture);
                deleteNodes(root, compression, errorThreshhold);
                compressionLevel = ((double)leaves / (double)Math.pow(sizeOfPicture, 2));
        }
        /*
         * @return compressionLevel
         * This is the getter of instance variable compressionLevel
         */
        public double getCompressionLevel() {
                return compressionLevel;
        }
        //the nested Node Class
        public class Node{
                /*
                 * @param int startX: the x coordinate of the left upper most pixel
                 */
                private int startX;
                /*
                 * @param int startY: the y coordinate of the left upper most pixel
                 */
                private int startY;
                /*
                 * @param int length: the number of pixels in a row of this Node
                 */
                private int length;
                /*
                 * @param int red: the average red value of this Node
                 */
                private int  red;
                /*
                 * @param int blue: the average blue value of this Node
                 */
                private int  blue;
                /*
                 * @param int green: the average green value of this Node
                 */
                private int  green;
                /*
                 * @param Node southWest: the southWest quarter subpicture as a child
                 */
                private Node southWest;
                /*
                 * @param Node southWest: the southEast quarter subpicture as a child
                 */
                private Node southEast;
                /*
                 * @param Node northWest: the northWest quarter subpicture as a child
                 */
                private Node northWest;
                /*
                 * @param Node northEast: the northEast quarter subpicture as a child
                 */
                private Node northEast;
                /*
                 * @param: the  parent of this Node
                 */
                private Node parent;
                /*
                 * @param int red
                 * @param int blue
                 * @param int green
                 * @param int startX
                 * @param int startY
                 * @param int length
                 * This is the constructor of a Node that assigns all the related
                 * values to instance variables.
                 */
                public Node(int red, int blue, int green, int startX, int startY, int length) {
                        this.red = red;
                        this.blue = blue;
                        this.green = green;
                        southWest = null;
                        southEast = null;
                        northEast = null;
                        northWest = null;
                        parent = null;
                        this.length = length;
                        this.startX = startX;
                        this.startY = startY;
                }
                /*
                 * below are a series of getters and setters of
                 * instance variables
                 */
                public int getStartX() {
                        return startX;
                }

                public void setStartX(int startX) {
                        this.startX = startX;
                }

                public int getStartY() {
                        return startY;
                }

                public void setStartY(int startY) {
                        this.startY = startY;
                }

                public int getLength() {
                        return length;
                }

                public void setLength(int length) {
                        this.length = length;
                }

                public void setSouthWest(Node southWest) {
                        this.southWest = southWest;
                }

                public void setSouthEast(Node southEast) {
                        this.southEast = southEast;
                }

                public void setNorthWest(Node northWest) {
                        this.northWest = northWest;
                }

                public void setNorthEast(Node northEast) {
                        this.northEast = northEast;
                }

                public void setParent(Node parent) {
                        this.parent = parent;
                }

                public int getRed() {
                        return red;
                }
                public void setRed(int red) {
                        this.red = red;
                }
                public int getBlue() {
                        return blue;
                }
                public void setBlue(int blue) {
                        this.blue = blue;
                }
                public int getGreen() {
                        return green;
                }
                public void setGreen(int green) {
                        this.green = green;
                }
                public Node getSouthWest() {
                        return southWest;
                }

                public Node getSouthEast() {
                        return southEast;
                }

                public Node getNorthWest() {
                        return northWest;
                }

                public Node getNorthEast() {
                        return northEast;
                }

                public Node getParent() {
                        return parent;
                }
                /*
                 * @return String.valueOf(red) + " " + String.valueOf(green)
                        + " " + String.valueOf(blue);
                        This is the toString() method that returns the
                        r,g,b values of a Node in average
                 */
                public String toString() {
                        return String.valueOf(red) + " " + String.valueOf(green)
                        + " " + String.valueOf(blue);
                }
                /*
                 * @param Node parent
                 * This method calculates the squared difference of a
                 * Node to its parent and yield a integer as indicate for
                 * difference threshold.
                 */
                private int compareColor(Node parent) {
                        double comparisonInt = Math.pow(this.red - parent.getRed(), 2) +
                                        Math.pow(this.blue - parent.getBlue(), 2) +
                                        Math.pow(this.green - parent.getGreen(), 2);
                        return (int) comparisonInt;
                }
        }
        /*
         * @param int number
         * This method uses the change base formula of logt to
         * calculate the log base four of a number.
         */
        private int logBaseFour(int number) {
                return (int)(Math.log(number) / Math.log(4));
        }
        /*
         * This method creates an instance of a QuadTree using the number of
         * pixels to calculate the depth.
         */
        public void makeTree() {
                Node root = new Node(-1, -1, -1, 0, 0, sizeOfPicture);
                size++;
                this.root = root;
                makeTree(this.root, logBaseFour((int)Math.pow(sizeOfPicture, 2)));
                leaves = (int)Math.pow(sizeOfPicture, 2);
        }
        /*
         * @param Node node
         * @param int depth
         * This method recursivel y create Nodes and their children in the
         * Quadtree until the number of leaves at the bottom level equal to
         * number of pixels. This creates a perfect filled tree.
         */
        public void makeTree(Node node, int depth) {
                //when it doesn't reach the leaves
                if(depth != 0) {
                                Node nw = new Node(-1, -1, -1, node.getStartX(),
                                                node.getStartY(), node.getLength() / 2);
                                node.setNorthWest(nw);
                                node.getNorthWest().setParent(node);
                                makeTree(node.getNorthWest(), depth -1);
                                Node ne = new Node(-1, -1, -1, node.getStartX() + node.getLength() / 2,
                                                node.getStartY(), node.getLength() / 2 );
                                node.setNorthEast(ne);
                                node.getNorthEast().setParent(node);
                                makeTree(node.getNorthEast(), depth -1);
                                Node sw = new Node(-1, -1, -1, node.getStartX(), node.getStartY() +
                                                node.getLength() / 2, node.getLength() / 2);
                                node.setSouthWest(sw);
                                node.getSouthWest().setParent(node);
                                makeTree(node.getSouthWest(), depth -1);
                                Node se = new Node(-1, -1, -1, node.getStartX() + node.getLength() / 2,
                                                node.getStartY() + node.getLength() / 2,
                                                node.getLength() / 2);
                                node.setSouthEast(se);
                                node.getSouthEast().setParent(node);
                                makeTree(node.getSouthEast(), depth -1);
                                size += 4;
                }
                else {
                        return;
                }
        }
        /*
         * @param Picture picture
         * This method calls the recursive method findLeaf to fill in
         *  the data for all the leaves
         */
        public void populateTree(Picture picture) {
                for(int i = 0; i < picture.getPicture().size(); i++) {
                        for(int j = 0; j < picture.getPicture().get(i).size(); j++) {
                                findLeaf(root, picture.getPicture().get(i).get(j));
                        }
                }
        }
        /*
         * @param Node node
         * @param Pixel pixel
         * Recursively go through the tree to find the node with right range of
         * coordinates for a pixel, and put that pixel into the leaf Node.
         */
        private void findLeaf(Node node, Pixel pixel) {
                //Base case when we find the leaf
                if(node.getNorthWest() == null) {
                        node.setRed(pixel.getRed());
                        node.setGreen(pixel.getGreen());
                        node.setBlue(pixel.getBlue());
                        return;
                }
                if(pixel.getX() < node.getNorthEast().getStartX() && pixel.getY() < node.getSouthWest().getStartY()) {
                        findLeaf(node.getNorthWest(), pixel);
                }
                else if(pixel.getX() >= node.getNorthEast().getStartX() && pixel.getY() < node.getSouthEast().getStartY()) {
                        findLeaf(node.getNorthEast(), pixel);
                }
                else if(pixel.getX() < node.getNorthEast().getStartX() && pixel.getY() >= node.getSouthWest().getStartY()) {
                        findLeaf(node.getSouthWest(), pixel);
                }
                else{
                        findLeaf(node.getSouthEast(), pixel);
                }
        }
        /*
         * @param Node node
         * @param boolean compression
         * @param int errorThreshhold
         * This method compares children with the calculated average of their parentand
         * see if the difference threshhold is small enough so that the leaf Nodes can be deleted
         * with their parent in replacement. In the case where we shouldn't do compression, only
         * delete Nodes when all of the four Nodes are exactly the same.
         */
        public void deleteNodes(Node node, boolean compression, int errorThreshhold) {
                if(node.getNorthWest() == null) {
                        return;
                }
                deleteNodes(node.getNorthWest(),compression, errorThreshhold);
                deleteNodes(node.getNorthEast(),compression, errorThreshhold);
                deleteNodes(node.getSouthWest(),compression, errorThreshhold);
                deleteNodes(node.getSouthEast(),compression, errorThreshhold);
                //calculate the three color averages
                int redAverage = (int) (node.getNorthWest().getRed() + node.getNorthEast().getRed() +
                                node.getSouthWest().getRed() + node.getSouthEast().getRed()) / 4;
                int greenAverage = (int) (node.getNorthWest().getGreen() + node.getNorthEast().getGreen() +
                                node.getSouthWest().getGreen() + node.getSouthEast().getGreen()) / 4;
                int blueAverage = (int) (node.getNorthWest().getBlue() + node.getNorthEast().getBlue() +
                                node.getSouthWest().getBlue() + node.getSouthEast().getBlue()) / 4;
                node.setRed(redAverage);
                node.setGreen(greenAverage);
                node.setBlue(blueAverage);
                //calculate the four difference thresholds four the Nodes
                int northwest = node.getNorthWest().compareColor(node);
                int northeast = node.getNorthEast().compareColor(node);
                int southwest = node.getSouthWest().compareColor(node);
                int southeast = node.getSouthEast().compareColor(node);
                /*the situation where we do compress, delete the Nodes if
                 * all of the four have thresholds smaller than a value
                 */
                if(compression) {
                        if(northwest < errorThreshhold && northeast < errorThreshhold &&
                                        southwest < errorThreshhold && southeast < errorThreshhold &&
                                        !(oneChildHasChildren(node))) {
                                leaves -= 3;
                                size -= 4;
                                node.getNorthWest().setParent(null);
                                node.getNorthEast().setParent(null);
                                node.getSouthEast().setParent(null);
                                node.getSouthWest().setParent(null);
                                node.setNorthEast(null);
                                node.setNorthWest(null);
                                node.setSouthEast(null);
                                node.setSouthWest(null);
                        }
                }
                //when not compressed, only delete when they are the same
                else {
                        if(northwest == 0 && northeast == 0 &&
                                        southwest  == 0 && southeast  == 0 &&
                                        !(oneChildHasChildren(node))) {
                                leaves -= 3;
                                size -= 4;
                                node.getNorthWest().setParent(null);
                                node.getNorthEast().setParent(null);
                                node.getSouthEast().setParent(null);
                                node.getSouthWest().setParent(null);
                                node.setNorthEast(null);
                                node.setNorthWest(null);
                                node.setSouthEast(null);
                                node.setSouthWest(null);
                        }
                }
        }
        /*
         * @param Node node
         * This method tests if a node has children
         */
        private boolean oneChildHasChildren(Node node) {
                if(node.getNorthWest().getNorthWest() != null &&
                                node.getNorthEast().getNorthWest() != null &&
                                node.getSouthWest().getNorthWest() != null &&
                                node.getSouthEast().getNorthWest() != null) {
                        return true;
                }
                return false;
        }
        /*
public void divide(Picture picture) {
        sizeOfPicture = picture.getPicture().size();
        divideRec(picture, null, null);
}

private void divideRec(Picture picture, Node parent, String position) {
        if(picture.getPicture().size() <= 1) {
                return;
        }
        int red = 0;
        int blue = 0;
        int green = 0;
        ArrayList<ArrayList<Pixel>> northWest = new ArrayList<ArrayList<Pixel>>();
        ArrayList<ArrayList<Pixel>> southWest = new ArrayList<ArrayList<Pixel>>();
        ArrayList<ArrayList<Pixel>> northEast = new ArrayList<ArrayList<Pixel>>();
        ArrayList<ArrayList<Pixel>> southEast = new ArrayList<ArrayList<Pixel>>();
        for (int i = 0; i < picture.getPicture().size(); i++) {
                ArrayList<Pixel> nwPixel = new ArrayList<Pixel>();
                ArrayList<Pixel> swPixel = new ArrayList<Pixel>();
                ArrayList<Pixel> nePixel = new ArrayList<Pixel>();
                ArrayList<Pixel> sePixel = new ArrayList<Pixel>();
                for(int j = 0; j < picture.getPicture().get(i).size(); j++) {
                        if(i < picture.getPicture().size() / 2 && j < picture.getPicture().get(i).size() / 2) {
                                nwPixel.add(picture.getPicture().get(i).get(j));
                        }
                        else if(i >= picture.getPicture().size() / 2 && j < picture.getPicture().get(i).size() / 2) {
                                swPixel.add(picture.getPicture().get(i).get(j));
                        }
                        else if(i < picture.getPicture().size() / 2 && j >= picture.getPicture().get(i).size() / 2) {
                                nePixel.add(picture.getPicture().get(i).get(j));
                        }
                        else {
                                sePixel.add(picture.getPicture().get(i).get(j));
                        }

                        red += picture.getPicture().get(i).get(j).getRed();
                        blue += picture.getPicture().get(i).get(j).getBlue();
                        green += picture.getPicture().get(i).get(j).getGreen();
                }
                if(nwPixel.size() != 0) {
                northWest.add(nwPixel);
                }
                if(swPixel.size() != 0)
                southWest.add(swPixel);
                if(nePixel.size() != 0)
                northEast.add(nePixel);
                if(sePixel.size() != 0)
                southEast.add(sePixel);
        }
        Picture nwPicture = new Picture(northWest,0);
        System.out.println(nwPicture.getPicture().size());
        System.out.println(nwPicture.getPicture().get(0).size());
        Picture swPicture = new Picture(southWest,0);
        Picture nePicture = new Picture(northEast,0);
        Picture sePicture = new Picture(southEast,0);
        int redAverage = red / (picture.getPicture().size() * picture.getPicture().get(0).size());
        int blueAverage = blue / (picture.getPicture().size() * picture.getPicture().get(0).size());
        int greenAverage = green / (picture.getPicture().size() * picture.getPicture().get(0).size());
        int start = picture.getPicture().get(0).get(0).getX();
        int end = picture.getPicture().get(0).get(picture.getPicture().get(0).size() - 1).getX();
        Node newNode = new Node(redAverage, blueAverage, greenAverage, start, end);
        size++;

        if(parent == null) {
                this.root = newNode;
                divideRec(nwPicture, newNode,"nw");
                divideRec(swPicture, newNode,"sw");
                divideRec(nePicture, newNode,"ne");
                divideRec(sePicture, newNode,"se");
        }
        else if(newNode.compareColor(parent) > 14) {
                if(position.equals("nw")) {
                        parent.setNorthWest(newNode);
                }
                else if(position.equals("sw")) {
                        parent.setSouthWest(newNode);
                }
                else if(position.equals("ne")) {
                        parent.setNorthEast(newNode);
                }
                else {
                        parent.setSouthEast(newNode);
                }
                newNode.setParent(parent);
                divideRec(nwPicture, newNode,"nw");
                divideRec(swPicture, newNode,"sw");
                divideRec(nePicture, newNode,"ne");
                divideRec(sePicture, newNode,"se");
        }
        else {
                if(position.equals("nw")) {
                        parent.setNorthWest(newNode);
                }
                else if(position.equals("sw")) {
                        parent.setSouthWest(newNode);
                }
                else if(position.equals("ne")) {
                        parent.setNorthEast(newNode);
                }
                else {
                        parent.setSouthEast(newNode);
                }
                newNode.setParent(parent);
                return;
        }
}
         */
        /*
         * @param Node node
         * @param int[][] inputArray
         * This method takes a Node and a two dimensional array of r,g,b values,
         * and apply edge detection to small enough Nodes.
         */
        public int[][] edgeDetection(Node node, int[][] inputArray) {
                if(node.getNorthEast() != null) {
                        int [][] returnNE;
                        int [][] returnNW;
                        int [][] returnSW;
                        int [][] returnSE;
                        returnNW = edgeDetection(node.getNorthWest(), inputArray);
                        returnNE = edgeDetection(node.getNorthEast(), inputArray);
                        returnSW = edgeDetection(node.getSouthWest(), inputArray);
                        returnSE = edgeDetection(node.getSouthEast(), inputArray);
                        //create the new array so that the change of previous pixels doesn't affect future calculations
                        int [][] newArray = new int [inputArray.length][inputArray[0].length];
                        for(int i = node.getNorthWest().getStartY(); i < node.getNorthWest().getStartY()
                                        + node.getNorthWest().getLength(); i++) {
                                for(int j = node.getNorthWest().getStartX() * 3; j < (node.getNorthWest().getStartX()
                                                + node.getNorthWest().getLength())* 3; j++) {
                                        newArray[i][j] = returnNW[i][j];
                                }
                        }
                        for(int i = node.getNorthEast().getStartY(); i < node.getNorthEast().getStartY() + node.
                                        getNorthEast().getLength(); i++) {
                                for(int j = node.getNorthEast().getStartX() * 3; j < (node.getNorthEast().getStartX() +
                                                node.getNorthEast().getLength())* 3; j++) {
                                        newArray[i][j] = returnNE[i][j];
                                }
                        }
                        for(int i = node.getSouthWest().getStartY(); i < node.getSouthWest().getStartY() + node.
                                        getSouthWest().getLength(); i++) {
                                for(int j = node.getSouthWest().getStartX() * 3; j < (node.getSouthWest().getStartX() +
                                                node.getSouthWest().getLength()) * 3; j++) {
                                        newArray[i][j] = returnSW[i][j];
                                }
                        }
                        for(int i = node.getSouthEast().getStartY(); i < node.getSouthEast().getStartY() + node.
                                        getSouthEast().getLength(); i++) {
                                for(int j = node.getSouthEast().getStartX() * 3; j < (node.getSouthEast().getStartX() +
                                                node.getSouthEast().getLength()) * 3; j++) {
                                        newArray[i][j] = returnSE[i][j];
                                }
                        }
                        return newArray;
                }
                // the situation where the Nodes aren't small enough, change them to black
                else {
                        if(node.getLength() > sizeOfPicture * 0.3) {
                                for(int i = node.getStartY(); i < (node.getStartY() + node.getLength()); i++) {
                                        for(int j = node.getStartX() * 3; j < (node.getStartX() + node.getLength()) * 3; j+= 3) {
                                                inputArray[i][j] = 0;
                                                inputArray[i][j + 1] = 0;
                                                inputArray[i][j + 2] = 0;
                                        }
                                }
                                return inputArray;
                        }
                        //applies edge detection to the smaller Nodes
                        else {
                                int [][] newArray = new int [inputArray.length][inputArray[0].length];
                                for(int i = node.getStartY(); i < (node.getStartY() + node.getLength()); i++) {
                                        for(int j = node.getStartX() * 3; j < (node.getStartX() + node.getLength()) * 3; j++) {
                                                //handle the corner cases where there are not 8 surrouding pixels
                                                if(i == 0 && (j == 0 || j == 1 || j == 2)) {
                                                        int newColor = inputArray[i][j] * 3 - inputArray[i + 1][j] - inputArray[i][j + 3] -
                                                                        inputArray[i + 1][j + 3];
                                                        if(newColor > 255) {
                                                                newColor = 255;
                                                        }
                                                        else if(newColor < 0) {
                                                                newColor = 0;
                                                        }
                                                        newArray[i][j] = newColor;
                                                }
                                                else if (i == sizeOfPicture - 1 && (j == sizeOfPicture * 3 - 1 || j ==sizeOfPicture * 3 - 2
                                                                ||j ==  sizeOfPicture * 3 -3)) {
                                                        int newColor = inputArray[i][j] * 3 - inputArray[i - 1][j] - inputArray[i][j - 3] -
                                                                        inputArray[i - 1][j - 3];
                                                        if(newColor > 255) {
                                                                newColor = 255;
                                                        }
                                                        else if(newColor < 0) {
                                                                newColor = 0;
                                                        }
                                                        newArray[i][j] = newColor;
                                                }
                                                else if (i == 0 && (j == sizeOfPicture * 3 - 1 || j ==sizeOfPicture * 3 - 2
                                                                ||j ==  sizeOfPicture * 3 -3) ) {
                                                        int newColor = inputArray[i][j] * 3 - inputArray[i + 1][j] - inputArray[i][j - 3] -
                                                                        inputArray[i + 1][j - 3];
                                                        if(newColor > 255) {
                                                                newColor = 255;
                                                        }
                                                        else if(newColor < 0) {
                                                                newColor = 0;
                                                        }
                                                        newArray[i][j] = newColor;
                                                }
                                                else if (i == sizeOfPicture - 1 && (j == 0 || j == 1 || j == 2)) {
                                                        int newColor = inputArray[i][j] * 3 - inputArray[i - 1][j] - inputArray[i][j + 3] -
                                                                        inputArray[i - 1][j + 3];
                                                        if(newColor > 255) {
                                                                newColor = 255;
                                                        }
                                                        else if(newColor < 0) {
                                                                newColor = 0;
                                                        }
                                                        newArray[i][j] = newColor;
                                                }
                                                else if(i == 0) {
                                                        int newColor = inputArray[i][j] * 5 - inputArray[i][j - 3] - inputArray[i][j + 3] -
                                                                        inputArray[i + 1][j - 3] -inputArray[i + 1][j] - inputArray[i +1][j + 3];
                                                        if(newColor > 255) {
                                                                newColor = 255;
                                                        }
                                                        else if(newColor < 0) {
                                                                newColor = 0;
                                                        }
                                                        newArray[i][j] = newColor;
                                                }
                                                else if(i == sizeOfPicture - 1) {
                                                        int newColor = inputArray[i][j] * 5 - inputArray[i][j - 3] - inputArray[i][j + 3] -
                                                                        inputArray[i - 1][j - 3] -inputArray[i - 1][j] - inputArray[i -1][j + 3];
                                                        if(newColor > 255) {
                                                                newColor = 255;
                                                        }
                                                        else if(newColor < 0) {
                                                                newColor = 0;
                                                        }
                                                        newArray[i][j] = newColor;
                                                }
                                                else if(j == 0 || j == 1 || j == 2) {
                                                        int newColor = inputArray[i][j] * 5 - inputArray[i - 1][j ] - inputArray[i - 1][j + 3] -
                                                                        inputArray[i][j + 3] -inputArray[i + 1][j] - inputArray[i + 1][j + 3];
                                                        if(newColor > 255) {
                                                                newColor = 255;
                                                        }
                                                        else if(newColor < 0) {
                                                                newColor = 0;
                                                        }
                                                        newArray[i][j] = newColor;
                                                }
                                                else if(j == sizeOfPicture * 3 - 1 || j == sizeOfPicture * 3 - 2 || j == sizeOfPicture * 3 - 3) {
                                                        int newColor = inputArray[i][j] * 5 - inputArray[i - 1][j ] - inputArray[i - 1][j - 3] -
                                                                        inputArray[i][j - 3] -inputArray[i + 1][j] - inputArray[i + 1][j - 3];
                                                        if(newColor > 255) {
                                                                newColor = 255;
                                                        }
                                                        else if(newColor < 0) {
                                                                newColor = 0;
                                                        }
                                                        newArray[i][j] = newColor;
                                                }
                                                //the normal case with 8 surroundng pixels
                                                else {
                                                        int newColor = inputArray[i - 1][j - 3] * (-1) + inputArray[i - 1][j] *(-1) +
                                                                        inputArray[i - 1][j + 3] * (-1) + inputArray[i][j -3] * (-1) +
                                                                        inputArray[i][j + 3] * (-1) + inputArray[i + 1][j - 3] * (-1) + inputArray[i + 1][j] * (-1) +
                                                                        inputArray[i + 1][j +3] * (-1) + inputArray[i][j] * 8;
                                                        //handle the situation where negative or large numbers appear
                                                        if(newColor > 255) {
                                                                newColor = 255;
                                                        }
                                                        else if(newColor < 0) {
                                                                newColor = 0;
                                                        }
                                                        newArray[i][j] = newColor;
                                                }
                                        }
                                }
                                return newArray;
                        }
                }
        }
        /*
         * @param boolean box
         * This method calls compressionPhoto() and edgeDetection() and returns
         * String to wrtie to file.
         */
        public String compressionPhotoWithEdgeDetection(boolean box) {
                int [][] returnArray = new int[sizeOfPicture][sizeOfPicture * 3];
                returnArray =  compressionPhoto(root, returnArray, false, box);
                returnArray = edgeDetection(this.root, returnArray);
                StringBuilder returnString = new StringBuilder();
                for(int i = 0; i < sizeOfPicture; i ++) {
                        for(int j = 0; j < sizeOfPicture* 3; j++) {
                                returnString.append(returnArray[i][j] );
                                returnString.append(" ");

                        }
                        returnString.append("\n");
                }
                return returnString.toString();
        }

        /*
         * @param boolean filter
         * @param boolean box
         * This method calls compressionPhoto() with or wihtout filter and box,
         * and put the results into a String to wrtie to file.
         */
        public String compressionPhoto(boolean filter, boolean box) {
                int [][] returnArray = new int[sizeOfPicture][sizeOfPicture * 3];
                returnArray =  compressionPhoto(root,returnArray,filter,box);
                StringBuilder returnString = new StringBuilder();
                for(int i = 0; i < sizeOfPicture; i ++) {
                        for(int j = 0; j < sizeOfPicture* 3; j++) {
                                returnString.append(returnArray[i][j] );
                                returnString.append(" ");
                        }
                        returnString.append("\n");
                }
                return returnString.toString();
        }
        /*
         * @param Node node
         * @param int [][]returnArray
         * @param boolean filter
         * @param boolean box
         * This method goes throug hthe QuadTree and putthe leaf Nodes into a two dimensional int array.
         * It also applies filter and/or boxes if necessary.
         */
        private int[][] compressionPhoto(Node node, int[][] returnArray, boolean filter, boolean box) {
                //the cases where it's not a leaf
                if(node.getNorthWest() != null) {
                        int [][] returnNE;
                        int [][] returnNW;
                        int [][] returnSW;
                        int [][] returnSE;
                        returnNW = compressionPhoto(node.getNorthWest(), returnArray, filter, box);
                        returnNE =compressionPhoto(node.getNorthEast(), returnArray, filter, box);
                        returnSW = compressionPhoto(node.getSouthWest(), returnArray, filter, box);
                        returnSE = compressionPhoto(node.getSouthEast(), returnArray, filter, box);
                        int [][] newArray = new int [returnArray.length][returnArray[0].length];
                        //continue going to the four children and put the pixels in that Node into the array
                        for(int i = node.getNorthWest().getStartY(); i < node.getNorthWest().getStartY()
                                        + node.getNorthWest().getLength(); i++) {
                                for(int j = node.getNorthWest().getStartX() * 3; j < (node.getNorthWest().getStartX()
                                                + node.getNorthWest().getLength())* 3; j++) {
                                        newArray[i][j] = returnNW[i][j];
                                }
                        }
                        for(int i = node.getNorthEast().getStartY(); i < node.getNorthEast().getStartY() + node.
                                        getNorthEast().getLength(); i++) {
                                for(int j = node.getNorthEast().getStartX() * 3; j < (node.getNorthEast().getStartX() +
                                                node.getNorthEast().getLength())* 3; j++) {
                                        newArray[i][j] = returnNE[i][j];
                                }
                        }
                        for(int i = node.getSouthWest().getStartY(); i < node.getSouthWest().getStartY() + node.
                                        getSouthWest().getLength(); i++) {
                                for(int j = node.getSouthWest().getStartX() * 3; j < (node.getSouthWest().getStartX() +
                                                node.getSouthWest().getLength()) * 3; j++) {
                                        newArray[i][j] = returnSW[i][j];
                                }
                        }
                        for(int i = node.getSouthEast().getStartY(); i < node.getSouthEast().getStartY() + node.
                                        getSouthEast().getLength(); i++) {
                                for(int j = node.getSouthEast().getStartX() * 3; j < (node.getSouthEast().getStartX() +
                                                node.getSouthEast().getLength()) * 3; j++) {
                                        newArray[i][j] = returnSE[i][j];
                                }
                        }
                        return newArray;
                }
                //when it reaches leaf
                else {
                        for(int i = node.getStartY(); i < node.getStartY() + node.getLength(); i++) {
                                for(int j = node.getStartX() * 3; j < (node.getStartX() + node.getLength()) * 3; j+= 3) {
                                        //when both box and filter are applied
                                        if(box && filter) {
                                                //change the edges to be black
                                                if(i == node.getStartY() || j == node.getStartX() * 3) {
                                                        returnArray[i][j] = 0;
                                                        returnArray[i][j + 1] = 0;
                                                        returnArray[i][j + 2] = 0;
                                                }
                                                //and other pixels with a negative filter
                                                else {
                                                        returnArray[i][j] = 255 - node.getRed();
                                                        returnArray[i][j + 1] = 255 - node.getGreen();
                                                        returnArray[i][j + 2] = 255 -node.getBlue();
                                                }
                                        }
                                        //only boxes
                                        else if (box) {
                                                if(i == node.getStartY() || j == node.getStartX() * 3) {
                                                        returnArray[i][j] = 0;
                                                        returnArray[i][j + 1] = 0;
                                                        returnArray[i][j + 2] = 0;
                                                }
                                                else {
                                                        returnArray[i][j] = node.getRed();
                                                        returnArray[i][j + 1] =  node.getGreen();
                                                        returnArray[i][j + 2] = node.getBlue();
                                                }
                                        }
                                        //only filter
                                        else if(filter) {
                                                returnArray[i][j] = 255 - node.getRed();
                                                returnArray[i][j + 1] = 255 - node.getGreen();
                                                returnArray[i][j + 2] = 255 -node.getBlue();
                                        }
                                        //when neither filter nor box is applied
                                        else {
                                                returnArray[i][j] = node.getRed();
                                                returnArray[i][j + 1] =  node.getGreen();
                                                returnArray[i][j + 2] = node.getBlue();
                                        }
                                }
                        }
                        return returnArray;
                }
        }
        /*
public String compressionPhotoWithBoxes(boolean filter) {
        int [][] returnArray = new int[sizeOfPicture][sizeOfPicture * 3];
        returnArray =  compressionPhotoWithBoxes(root,returnArray,filter);
        String returnString = "";
        for(int i = 0; i < sizeOfPicture; i ++) {
                for(int j = 0; j < sizeOfPicture* 3; j++) {
                        returnString += returnArray[i][j] + " ";
                }
                returnString += "\n";
        }
        return returnString;
}
         */
        /*
private int[][] compressionPhotoWithBoxes(Node node, int[][] returnArray, boolean filter) {

        if(node.getNorthWest() != null) {
                int [][] returnNE;
                int [][] returnNW;
                int [][] returnSW;
                int [][] returnSE;
                returnNW = compressionPhotoWithBoxes(node.getNorthWest(), returnArray, filter);
                returnNE =compressionPhotoWithBoxes(node.getNorthEast(), returnArray,filter);
                returnSW = compressionPhotoWithBoxes(node.getSouthWest(), returnArray,filter);
                returnSE = compressionPhotoWithBoxes(node.getSouthEast(), returnArray,filter);
                int [][] newArray = new int [returnArray.length][returnArray[0].length];
                for(int i = node.getNorthWest().getStartY(); i < node.getNorthWest().getStartY()
                                + node.getNorthWest().getLength(); i++) {
                        for(int j = node.getNorthWest().getStartX() * 3; j < (node.getNorthWest().getStartX()
                                        + node.getNorthWest().getLength())* 3; j++) {
                                newArray[i][j] = returnNW[i][j];
                        }
                }
                for(int i = node.getNorthEast().getStartY(); i < node.getNorthEast().getStartY() + node.
                                getNorthEast().getLength(); i++) {
                        for(int j = node.getNorthEast().getStartX() * 3; j < (node.getNorthEast().getStartX() +
                                node.getNorthEast().getLength())* 3; j++) {
                                newArray[i][j] = returnNE[i][j];
                        }
                }
                for(int i = node.getSouthWest().getStartY(); i < node.getSouthWest().getStartY() + node.
                                getSouthWest().getLength(); i++) {
                        for(int j = node.getSouthWest().getStartX() * 3; j < (node.getSouthWest().getStartX() +
                                        node.getSouthWest().getLength()) * 3; j++) {
                                newArray[i][j] = returnSW[i][j];
                        }
                }
                for(int i = node.getSouthEast().getStartY(); i < node.getSouthEast().getStartY() + node.
                                getSouthEast().getLength(); i++) {
                        for(int j = node.getSouthEast().getStartX() * 3; j < (node.getSouthEast().getStartX() +
                                node.getSouthEast().getLength()) * 3; j++) {
                                newArray[i][j] = returnSE[i][j];
                        }
                }
                return newArray;
        }
        else {
                for(int i = node.getStartY(); i < node.getStartY() + node.getLength(); i++) {
                        for(int j = node.getStartX() * 3; j < (node.getStartX() + node.getLength()) * 3; j+= 3) {
                                if(i == node.getStartY() || j == node.getStartX() * 3) {
                                        returnArray[i][j] = 0;
                                        returnArray[i][j + 1] = 0;
                                        returnArray[i][j + 2] = 0;
                                }
                                else {
                                        if(!filter) {
                                        returnArray[i][j] = node.getRed();
                                        returnArray[i][j + 1] = node.getGreen();
                                        returnArray[i][j + 2] = node.getBlue();
                                        }
                                        else {
                                                returnArray[i][j] = 255 - node.getRed();
                                                returnArray[i][j + 1] = 255 - node.getGreen();
                                                returnArray[i][j + 2] = 255 - node.getBlue();
                                        }
                                }
                        }
                }

                return returnArray;
        }
}
         */
        /*
         * below are a bunch of getters and setters for instance variables
         */
        public Node getRoot() {
                return root;
        }

        public void setRoot(Node root) {
                this.root = root;
        }

        public int getSize() {
                return size;
        }

        public void setSize(int size) {
                this.size = size;
        }

        public int getSizeOfPicture() {
                return sizeOfPicture;
        }

        public void setSizeOfPicture(int sizeOfPicture) {
                this.sizeOfPicture = sizeOfPicture;
        }
}
