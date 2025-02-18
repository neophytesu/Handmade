public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Try to do something");
            throw new RuntimeException("Runtime Exception");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Finally");
        }
    }
}