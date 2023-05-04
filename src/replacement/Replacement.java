package replacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public static void lfu(Map<Integer, String> cache, Map<Integer, Integer> auxCache) {
        Map.Entry<Integer, Integer> min = Collections.min(auxCache.entrySet(),
                new Comparator<Map.Entry<Integer, Integer>>() {
                    public int compare(Map.Entry<Integer, Integer> entry1, Map.Entry<Integer, Integer> entry2) {
                        return entry1.getValue().compareTo(entry2.getValue());
                    }
                });

        cache.remove(min.getKey());
        auxCache.remove(min.getKey());
    }

    public static void lru(Map<Integer, String> cache, ArrayList<Integer> listAuxCache) {
        Integer firstTag = listAuxCache.get(0);
        listAuxCache.remove(0);
        cache.remove(firstTag);
    }
}
