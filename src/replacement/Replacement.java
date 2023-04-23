package replacement;

import java.util.Map;
import java.util.Random;

public class Replacement {
    public static void random(Map<Integer, String> cache, int tag){
        Random rm = new Random();
        Integer randomTag = 0;
        do {
            randomTag = rm.nextInt((tag - 0) + 1) + 0;
        } while (!(cache.containsKey(randomTag)));
        cache.remove(randomTag);
    }
}
