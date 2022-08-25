import java.util.Objects;

class ChristmasTree {

    private String color;

    public ChristmasTree(String color) {
        this.color = color;
    }

    public void putTreeTopper(String color) {
        TreeTopper treeTopper = new TreeTopper(color);
        treeTopper.sparkle();
    }

    class TreeTopper {

        private String color;

        public TreeTopper(String color) {
            this.color = color;
        }

        public void sparkle() {
            if (Objects.equals(this.color, "silver") && Objects.equals(ChristmasTree.this.color, "green")) {
                System.out.println("Sparkling silver tree topper looks stunning with green Christmas tree!");
            }
        }
    }
}

// this code should work
class CreateHoliday {

    public static void main(String[] args) {

        ChristmasTree christmasTree = new ChristmasTree("green");
        christmasTree.putTreeTopper("silver");
    }
}