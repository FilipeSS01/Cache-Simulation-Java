import java.util.ArrayList;

import mappings.Associative;
import mappings.Direct;
import utils.FileManager;

public class App {
    public static void main(String[] args) {
        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        ArrayList<String> memoryData = FileManager.stringReader(path + "data/others/memory3.txt");
        ArrayList<String> dataConfig = FileManager.stringReader(path + "data/config/config.txt");

        new Direct(memoryData, dataConfig);
        new Associative(memoryData, dataConfig, 1);
        // new Associative(memoryData, dataConfig, 2);
        // new Associative(memoryData, dataConfig, 3);
        // new Associative(memoryData, dataConfig, 4);
    }

}
