package replacement;

import java.util.*;

public class Replacement {
    private static final Random random = new Random();
    private static final Comparator<Map.Entry<Integer, Integer>> LFU_COMPARATOR = Comparator
            .comparing(Map.Entry::getValue);

    public static void random(Map<Integer, String> cache) {
        Integer randomTag = cache.keySet().iterator().next();
        cache.remove(randomTag);
    }

    public static void random(ArrayList<Integer> arrayList) {
        arrayList.remove(random.nextInt(arrayList.size()));
    }

    public static void fifo(Map<Integer, String> cache) {
        Integer firstTag = cache.keySet().iterator().next();
        cache.remove(firstTag);
    }

    public static void fifo(ArrayList<Integer> arrayList) {
        arrayList.remove(0);
    }

    public static void lfu(Map<Integer, String> cache, Map<Integer, Integer> auxCache) {
        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue = new PriorityQueue<>(LFU_COMPARATOR);
        priorityQueue.addAll(auxCache.entrySet());
        Map.Entry<Integer, Integer> min = priorityQueue.poll();
        cache.remove(min.getKey());
        auxCache.remove(min.getKey());
    }

    public static void lru(Map<Integer, String> cache, ArrayList<Integer> listAuxCache) {
        Iterator<Integer> iterator = listAuxCache.iterator();
        Integer firstTag = iterator.next();
        iterator.remove();
        cache.remove(firstTag);
    }

    public static void lru(ArrayList<Integer> arrayList, boolean hit, Integer tag) {
        if (hit) {
            int index = arrayList.indexOf(tag);
            Integer aux = arrayList.get(index);
            arrayList.remove(index);
            arrayList.add(aux);
        } else {
            arrayList.remove(0);
        }
    }
}