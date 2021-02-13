import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Overlap_Rectangles {
    public static Integer FIX_INSERT = 0;
    public IntervalNode root;
    public IntervalNode treeRoot = null;


    public class Interval {
        Double xLow;
        Double xHigh;
        Double low;
        Double high;
        int rectangleId;

        public Interval(double xLow, double xHigh, int rectangleId, Double low) {
            this.xLow = xLow;
            this.xHigh = xHigh;
            this.rectangleId = rectangleId;
        }

        public Interval(double low, double high, int rectangleId) {
            this.low = low;
            this.high = high;
            this.rectangleId = rectangleId;
        }

        public Interval(double low, double high, int rectangleId, double xLow, double xHigh) {
            this.low = low;
            this.high = high;
            this.rectangleId = rectangleId;
            this.xLow = xLow;
            this.xHigh = xHigh;
        }

        public double getLow() {
            return low;
        }

        public void setLow(double low) {
            this.low = low;
        }

        public double getHigh() {
            return high;
        }

        public void setHigh(double high) {
            this.high = high;
        }

        public int getRectangleId() {
            return rectangleId;
        }

        public void setRectangleId(int rectangleId) {
            this.rectangleId = rectangleId;
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "xLow=" + xLow +
                    ", xHigh=" + xHigh +
                    ", low=" + low +
                    ", high=" + high +
                    ", rectangleId=" + rectangleId +
                    '}';
        }
    }

    public class IntervalNode {
        Interval interval;
        IntervalNode left = null;
        IntervalNode parent = null;
        IntervalNode right = null;
        double maxY;
        int color = 1; // 0=BLACK, 1=RED

        public IntervalNode() {
        }

        public IntervalNode(IntervalNode x) {
            this.interval = x.interval;
            this.parent = x.parent;
            this.left = x.left;
            this.right = x.right;
            this.maxY = x.maxY;
            this.color = x.color;
        }

        public IntervalNode(Interval node) {
            this.interval = node;
            this.maxY = node.high;
        }

        public Interval getNode() {
            return interval;
        }

        public void setNode(Interval node) {
            this.interval = node;
        }

        public IntervalNode getLeft() {
            return left;
        }

        public void setLeft(IntervalNode left) {
            this.left = left;
        }

        public IntervalNode getRight() {
            return right;
        }

        public void setRight(IntervalNode right) {
            this.right = right;
        }

        public double getMaxY() {
            return maxY;
        }

        public void setMaxY(double maxY) {
            this.maxY = maxY;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "IntervalNode{" +
                    "interval=" + interval +
                    ", left=" + left +
                    ", right=" + right +
                    ", maxY=" + maxY +
                    ", color=" + color +
                    '}';
        }
    }

    public IntervalNode insert(IntervalNode root, IntervalNode newNode) {
        if (root == null) {
            if (newNode.parent == null) {
                newNode.color = 0;
                this.root = newNode; // rootNode
                return newNode;
            }

            if (newNode.parent.parent == null) {
                return newNode;
            }

            return newNode;
        }

        newNode.parent = root;
        if (newNode.maxY >= root.maxY) {
            IntervalNode node = insert(root.right, newNode);
            if (FIX_INSERT == 1) {
                root.right = node;
                if (root.right.parent == null) {

                } else if (root.right.parent.parent == null) {

                } else  {
                    fixInsert(root.right);
                }
                FIX_INSERT = 0;
            }
        } else {
            IntervalNode node= insert(root.left, newNode);
            if (FIX_INSERT == 1) {
                root.left = node;
                if (root.left.parent == null) {

                } else if (root.left.parent.parent == null) {

                } else  {
                    fixInsert(root.left);
                }
                FIX_INSERT = 0;
            }
        }

        return root;

    }

    public void rotateLeft(IntervalNode addedNode) {
        IntervalNode rightChild = addedNode.right;
        addedNode.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = addedNode;
        }
        rightChild.parent = addedNode.parent;
        if (addedNode.parent == null) {
            this.root = rightChild;
        } else if (addedNode == addedNode.parent.left) {
            addedNode.parent.left = rightChild;
        } else {
            addedNode.parent.right = rightChild;
        }
        rightChild.left = addedNode;
        addedNode.parent = rightChild;
    }

    public void rotateRight(IntervalNode addedNode) {
        IntervalNode leftChild = addedNode.left;
        addedNode.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = addedNode;
        }
        leftChild.parent = addedNode.parent;
        if (addedNode.parent == null) {
            this.root = leftChild;
        } else if (addedNode == addedNode.parent.right) {
            addedNode.parent.right = leftChild;
        } else {
            addedNode.parent.left = leftChild;
        }
        leftChild.right = addedNode;
        addedNode.parent = leftChild;
    }

    public void fixInsert(IntervalNode addedNode) {
        IntervalNode uncleNode;
        while (addedNode.parent.color == 1) {
                if (addedNode.parent == addedNode.parent.parent.right) {
                    uncleNode = addedNode.parent.parent.left;
                    if (uncleNode != null && uncleNode.color == 1) {
                        uncleNode.color = 0;
                        addedNode.parent.color = 0;
                        addedNode.parent.parent.color = 1;
                        addedNode = addedNode.parent.parent;
                    } else {
                        if (addedNode == addedNode.parent.left) {
                            addedNode = addedNode.parent;
                            rotateRight(addedNode);
                        }
                        addedNode.parent.color = 0;
                        addedNode.parent.parent.color = 1;
                        rotateLeft(addedNode.parent.parent);
                    }
                } else {
                    uncleNode = addedNode.parent.parent.right;

                    if (uncleNode != null && uncleNode.color == 1) {
                        uncleNode.color = 0;
                        addedNode.parent.color = 0;
                        addedNode.parent.parent.color = 1;
                        addedNode = addedNode.parent.parent;

                    } else {
                        if (addedNode == addedNode.parent.right) {
                            addedNode = addedNode.parent;
                            rotateLeft(addedNode);
                        }
                        addedNode.parent.color = 0;
                        addedNode.parent.parent.color = 1;
                        rotateRight(addedNode.parent.parent);
                    }
                }
            if (addedNode == this.root) {
                break;
            }

        }
        this.root.color = 0;
    }

    private void redBlackTransplant(IntervalNode u, IntervalNode v) {
        if (u.parent == null) {
            this.root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null) {
            v.parent = u.parent;
        }

    }

    public IntervalNode findMinimum(IntervalNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void remove(IntervalNode root, IntervalNode node) {
        if (root == null) {
            return;
        }

        if (node.interval.high == root.interval.high) {
            IntervalNode y = root;
            IntervalNode x = null;
            int yOriginalColor = y.color;

            // check if root node
            if (root == this.root) {
                this.root = null;
            }
            // check if leaf node
            else if (root.left == null && root.right == null) {
                if (root.parent.left == root) {
                    root.parent.left = null;
                } else if (root.parent.right == root) {
                    root.parent.right = null;
                }
            }
            else {
                if (root.left == null) {
                    x = root.right;
                    redBlackTransplant(root, root.right);
                } else if (root.right == null) {
                    x = root.left;
                    redBlackTransplant(root, root.left);
                } else {
                    y = findMinimum(root.right);
                    yOriginalColor = y.color;
                    x = y.right;
                    if (y.parent == root) {
                        x.parent = y;
                    } else  {
                        redBlackTransplant(y, y.right);
                        y.right = root.right;
                        y.right.parent = y;
                    }

                    redBlackTransplant(root, y);
                    y.left = root.left;
                    y.left.parent = y;
                    y.color = root.color;
                }

                if (yOriginalColor == 0) {
                    fixRemove(x);
                }
            }
        }


        node.parent = root;
        if (node.interval.high >= root.interval.high) {
            remove(root.right, node);
        } else {
            remove(root.left, node);
        }

    }

    private void fixRemove(IntervalNode x) {
        IntervalNode s;
        if (x != null) {

            while (x != this.root && x.color == 0) {
                if (x == x.parent.left) {
                    s = x.parent.right;
                    if (s.color == 1) {
                        s.color = 0;
                        x.parent.color = 1;
                        rotateLeft(x.parent);
                        s = x.parent.right;
                    }

                    if (s.left.color == 0 && s.right.color == 0) {
                        s.color = 1;
                        x = x.parent;
                    } else {
                        if (s.right.color == 0) {
                            s.left.color = 0;
                            s.color = 1;
                            rotateRight(s);
                            s = x.parent.right;
                        }

                        s.color = x.parent.color;
                        x.parent.color = 0;
                        s.right.color = 0;
                        rotateLeft(x.parent);
                        x = this.root;
                    }
                } else {
                    s = x.parent.left;
                    if (s.color == 1) {
                        s.color = 0;
                        x.parent.color = 1;
                        rotateRight(x.parent);
                        s = x.parent.left;
                    }

                    if (s.right.color == 0 && s.right.color == 0) {
                        s.color = 1;
                        x = x.parent;
                    } else {
                        if (s.left.color == 0) {
                            s.right.color = 0;
                            s.color = 1;
                            rotateLeft(s);
                            s = x.parent.left;
                        }

                        s.color = x.parent.color;
                        x.parent.color = 0;
                        s.left.color = 0;
                        rotateRight(x.parent);
                        x = this.root;
                    }
                }
            }
            x.color = 0;
        }
    }

    public Interval intervalSearch(IntervalNode root, Interval interval) {

        if (root == null) {
            return null;
        }

        if (root.interval.high >= interval.low && root.interval.low <= interval.high
                && ((root.interval.xLow >= interval.xLow && root.interval.xLow <= interval.xHigh) ||
                   (interval.xLow >= root.interval.xLow && interval.xLow <= root.interval.xHigh) )) {
            return root.interval;
        }

        if (root.left != null && root.left.maxY >= interval.low) {
            return intervalSearch(root.left, interval);
        } else {
            return intervalSearch(root.right, interval);
        }
    }

    public void fixMaxY(IntervalNode root) {
        if (root == null) {
            return;
        }
        fixMaxY(root.left);
        fixMaxY(root.right);

        root.maxY = root.interval.high;
        if (root != null) {
            if (root.left != null && root.maxY < root.left.maxY) {
                root.maxY = root.left.maxY;
            }
            if (root.right != null && root.maxY < root.right.maxY) {
                root.maxY = root.right.maxY;
            }
        }
    }

    public void sweepLine(List<Interval> rectangles, List<Interval> yIntervals) throws Exception {
        FileWriter output_path = new FileWriter("Output.txt");
        PrintWriter pw = new PrintWriter(output_path);

        Interval overlapInterval = null;
        List<Integer> intervalTreeRectangleIds = new ArrayList<>();
        for (Interval rectangle : rectangles) {
            int rectangleId = rectangle.getRectangleId();
            Interval yInterval = yIntervals.stream().filter(interval -> interval.rectangleId == rectangleId).findFirst().get();
            if (intervalTreeRectangleIds.contains(rectangleId)) {
                IntervalNode nodeToBeRemove = new IntervalNode(yInterval);
                remove(this.treeRoot, nodeToBeRemove);
                intervalTreeRectangleIds.remove(new Integer(rectangleId));
            } else {
                intervalTreeRectangleIds.add(rectangleId);

                overlapInterval = intervalSearch(this.root, new Interval(yInterval.low, yInterval.high, yInterval.rectangleId, rectangle.xLow, rectangle.xHigh));
                if (overlapInterval == null) {
                    IntervalNode nodeTobeAdded = new IntervalNode(new Interval(yInterval.low, yInterval.high, yInterval.rectangleId, rectangle.xLow, rectangle.xHigh));
                    FIX_INSERT = 1;
                    insert(this.root, nodeTobeAdded);
                    fixMaxY(this.root);
                    FIX_INSERT = 0;
                } else {
                    int p,q;

                    if(overlapInterval.rectangleId < yInterval.rectangleId) {
                        p = overlapInterval.rectangleId;
                        q = yInterval.rectangleId;
                    }
                    else {
                        p = yInterval.rectangleId;
                        q = overlapInterval.rectangleId;
                    }
                    System.out.println("Rectangle IDs " + p  + " and " + q + " overlap!!");

                    pw.print("Rectangle IDs " + p + " and " + q + " overlap!!");
                    pw.close();

                    return;
                }
            }
        }

        System.out.println("no overlap");
        pw.print("no overlap");
        pw.close();
        return;
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        // PLEASE ENTER INPUT FILE PATH HERE
        String input_path = "overlap7.txt";

        List<Interval> yIntervals = new ArrayList<Interval>();
        File input_file = new File(input_path);

        BufferedReader br = new BufferedReader(new FileReader(input_file));

        String st;
        List<Interval> rectangles = new ArrayList<>();
        while ((st = br.readLine()) != null) {
            String[] splited = st.split("\\s+");
            rectangles.add(new Overlap_Rectangles().new Interval(Double.parseDouble(splited[1]), Double.parseDouble(splited[3]), Integer.parseInt(splited[0]), null));
            rectangles.add(new Overlap_Rectangles().new Interval(Double.parseDouble(splited[3]), Double.parseDouble(splited[1]), Integer.parseInt(splited[0]), null));
            yIntervals.add(new Overlap_Rectangles().new Interval(Double.parseDouble(splited[2]), Double.parseDouble(splited[4]), Integer.parseInt(splited[0])));
        }
        Collections.sort(rectangles, new Comparator<Interval>() {

            public int compare(Interval o1, Interval o2) {
                return o1.xLow.compareTo(o2.xLow);
            }
        });


        new Overlap_Rectangles().sweepLine(rectangles, yIntervals);

    }

}
