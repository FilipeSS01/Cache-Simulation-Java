package mappings;

import java.util.ArrayList;
import abstracts.Mappings;
import utils.Convert;
import utils.FileManager;

public class Direct extends Mappings {
    private String[] cache;

    public Direct(String path) {
        super(path);
    }

    @Override
    public void initialize() {
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

        // Create cache
        setLimitCache(convertLineToDecimalBits(cacheBytes, wordBytes, Integer.parseInt(lineConfig[2])));
        setCache(new String[getLimitCache()]);

        // Values in bits
        setAddressBits(calcAddress(memoryBytes, wordBytes));
        setWordBits(calcWord(wordBytes));
        setLineBits(calcLine(cacheBytes, wordBytes, Integer.parseInt(lineConfig[2])));
        setTagBits(calcTag(getAddressBits(), getWordBits(), getLineBits()));

        // Read memory
        readMemory();
    }

    @Override
    protected void mapping(String[] partAddress) {
        Integer line = Integer.parseInt(partAddress[1], 2);
        if (line < getLimitCache()) {
            if (!(getCache()[line] != null && getCache()[line].equals(partAddress[0]))) {
                setMiss(getMiss() + 1);
                getCache()[line] = partAddress[0];
            } else {
                setHits(getHits() + 1);
            }
        }
    }

    @Override
    protected String[] getPartAddress(Integer value) {
        String[] partAddress = new String[3];
        String addressBinary = Convert.intToBinaryString(value, getAddressBits());
        partAddress[0] = addressBinary.substring(0, getTagBits()); // Tag
        partAddress[1] = addressBinary.substring(getTagBits(), getLineBits() + getTagBits()); // Line
        partAddress[2] = addressBinary.substring(getLineBits() + getTagBits(), getAddressBits()); // Word
        return partAddress;
    }

    // Gets and Sets

    public String[] getCache() {
        return cache;
    }

    private void setCache(String[] cache) {
        this.cache = cache;
    }

}