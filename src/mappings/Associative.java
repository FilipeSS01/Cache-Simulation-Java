package mappings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import abstracts.Mappings;
import replacement.Replacement;
import utils.Convert;
import utils.FileManager;

public class Associative extends Mappings {

    private Integer replace;
    private Map<Integer, String> cache;

    public Associative(String path, Integer replace) {
        super(path);
        setReplace(replace);
    }

    @Override
    protected void initialize() {
        // Read config
        ArrayList<String> dadosConfig = FileManager.stringReader(getPath() + "data/config/config.txt");
        String[] memoryConfig = dadosConfig.get(0).split("[ #@_\\/.*;]");
        String[] wordConfig = dadosConfig.get(1).split("[ #@_\\/.*;]");
        String[] cacheConfig = dadosConfig.get(2).split("[ #@_\\/.*;]");
        String[] lineConfig = dadosConfig.get(3).split("[ #@_\\/.*;]");

        // Value in Bytes
        Integer memoryBytes = convertToBits(Integer.parseInt(memoryConfig[2]), memoryConfig[3]);
        Integer wordBytes = convertToBits(Integer.parseInt(wordConfig[2]), wordConfig[3]);
        Integer cacheBytes = convertToBits(Integer.parseInt(cacheConfig[2]), cacheConfig[3]);

        // Cache
        setCache(new HashMap<Integer, String>());
        setLimitCache(convertLineToDecimalBits(cacheBytes, wordBytes, Integer.parseInt(lineConfig[2])));

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
                Replacement.fifo(cache);
            }
            getCache().put(tag, partAddress[1]);
        } else {
            setHits(getHits() + 1);
        }
    }

    @Override
    protected String[] getPartAddress(Integer value) {
        String[] partAddress = new String[2];
        String addressBinary = Convert.intToBinaryString(value, getAddressBits());
        partAddress[0] = addressBinary.substring(0, getTagBits()); // Tag
        partAddress[1] = addressBinary.substring(getTagBits(), getAddressBits()); // Word
        return partAddress;
    }

    // Gets and Sets
    public Integer getReplace() {
        return replace;
    }

    public void setReplace(Integer replace) {
        this.replace = replace;
    }

    public Map<Integer, String> getCache() {
        return cache;
    }

    private void setCache(Map<Integer, String> cache) {
        this.cache = cache;
    }

}
