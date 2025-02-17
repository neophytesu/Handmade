public class Address implements Cloneable{
    private String name;
    @Override
    protected Address clone() {
        try {
            return (Address) super.clone();
        }catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
