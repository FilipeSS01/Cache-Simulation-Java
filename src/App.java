import mappings.Direct;

public class App {
    public static void main(String[] args) {
        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        Direct direct = new Direct(path);
    }

}
