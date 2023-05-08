package mappings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import abstracts.Mappings;
import replacement.Replacement;
import utils.Convert;

public class Associative extends Mappings {
    private Map<Integer, String> cache;
    private Map<Integer, Integer> auxCache;
    private ArrayList<Integer> listAuxCache;

    public Associative(ArrayList<String> memoryData, ArrayList<String> dataConfig, String replace) {
        super(memoryData, dataConfig, replace);
    }

    @Override
    protected void initialize() {
        // Get config
        String[] memoryConfig = getDataConfig().get(0).split("[ #@_\\/.*;]");
        String[] wordConfig = getDataConfig().get(1).split("[ #@_\\/.*;]");
        String[] cacheConfig = getDataConfig().get(2).split("[ #@_\\/.*;]");
        String[] lineConfig = getDataConfig().get(3).split("[ #@_\\/.*;]");

        // Value in Bytes
        Long memoryBytes = convertToBits(Long.parseLong(memoryConfig[2]), memoryConfig[3]);
        Long wordBytes = convertToBits(Long.parseLong(wordConfig[2]), wordConfig[3]);
        Long cacheBytes = convertToBits(Long.parseLong(cacheConfig[2]), cacheConfig[3]);

        // Create cache
        setCache(new HashMap<Integer, String>());
        setLimitCache(convertLineToDecimalBits(cacheBytes, wordBytes, Integer.parseInt(lineConfig[2])));

        // Aux caches
        setAuxCache(new HashMap<Integer, Integer>());
        setListAuxCache(new ArrayList<Integer>());

        // Values in bits
        setAddressBits(calcAddress(memoryBytes, wordBytes));
        setWordBits(calcWord(wordBytes));
        setLineBits(0);
        setTagBits(calcTag(getAddressBits(), getWordBits(), getLineBits()));

        // Read memory
        readMemory();
    }

    @Override
    protected void mapping(String[] partAddress) {
        Integer tag = Integer.parseInt(partAddress[0], 2);
        if (!getCache().containsKey(tag)) {
            setMiss(getMiss() + 1);
            if (!(getCache().size() < getLimitCache())) {
                // Replacement
                switch (getReplace()) {
                    case "LFU":
                        Replacement.lfu(getCache(), getAuxCache());
                        break;
                    case "FIFO":
                        Replacement.fifo(getCache());
                        break;
                    case "LRU":
                        Replacement.lru(getCache(), getListAuxCache());
                        break;
                    case "RANDOM":
                        Replacement.random(getCache());
                        break;
                }
            }
            getCache().put(tag, partAddress[1]);
            if (getReplace() == "LFU")
                getAuxCache().put(tag, 0);
            else if (getReplace() == "LRU")
                getListAuxCache().add(tag);
        } else {
            setHits(getHits() + 1);
            if (getReplace() == "LFU")
                getAuxCache().replace(tag, getAuxCache().get(tag) + 1);
            else if (getReplace() == "LRU") {
                getListAuxCache().remove(getListAuxCache().indexOf(tag));
                getListAuxCache().add(tag);
            }
        }
    }

    @Override
    protected String[] getPartAddress(Long value) {
        String[] partAddress = new String[2];
        String addressBinary = Convert.intToBinaryString(value, getAddressBits());
        partAddress[0] = addressBinary.substring(0, getTagBits().intValue()); // Tag
        partAddress[1] = addressBinary.substring(getTagBits().intValue(), getAddressBits().intValue()); // Word
        return partAddress;
    }

    @Override
    public String toString() {
        String mapping = "Mapping: Associative\n";
        return mapping + super.toString();
    }

    // Gets and Sets
    public Map<Integer, String> getCache() {
        return cache;
    }

    private void setCache(Map<Integer, String> cache) {
        this.cache = cache;
    }

    public Map<Integer, Integer> getAuxCache() {
        return auxCache;
    }

    private void setAuxCache(Map<Integer, Integer> auxCache) {
        this.auxCache = auxCache;
    }

    public ArrayList<Integer> getListAuxCache() {
        return listAuxCache;
    }

    public void setListAuxCache(ArrayList<Integer> listAuxCache) {
        this.listAuxCache = listAuxCache;
    }
}
