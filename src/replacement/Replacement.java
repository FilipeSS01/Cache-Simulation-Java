package replacement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Replacement {
    public static void random(Map<Integer, String> cache) {
        Random random = new Random();
        Integer[] tagList = cache.keySet().toArray(new Integer[cache.size()]);
        Integer randomTag = tagList[random.nextInt(tagList.length)];
        cache.remove(randomTag);
    }

    public static void fifo(Map<Integer, String> cache) {
        Integer[] tagList = cache.keySet().toArray(new Integer[cache.size()]);
        Integer firstTag = tagList[0];
        cache.remove(firstTag);
    }
}
