package mappings;

import java.util.ArrayList;
import abstracts.Mappings;
import utils.Convert;

public class Direct extends Mappings {
    private String[] cache;

    public Direct(ArrayList<String> memoryData, ArrayList<String> dataConfig) {
        super(memoryData, dataConfig);
    }

    @Override
    public void initialize() {
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
        setLimitCache(convertLineToDecimalBits(cacheBytes, wordBytes, Integer.parseInt(lineConfig[2])));
        setCache(new String[getLimitCache().intValue()]);

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
    protected String[] getPartAddress(Long value) {
        String[] partAddress = new String[3];
        String addressBinary = Convert.intToBinaryString(value, getAddressBits());
        partAddress[0] = addressBinary.substring(0, getTagBits().intValue()); // Tag
        partAddress[1] = addressBinary.substring(getTagBits().intValue(), getLineBits() + getTagBits().intValue()); // Line
        partAddress[2] = addressBinary.substring(getLineBits() + getTagBits().intValue(), getAddressBits().intValue()); // Word
        return partAddress;
    }

    @Override
    public String toString() {
        String mapping = "Mapping: Direct\n";
        return mapping + super.toString();
    }

    // Gets and Sets
    public String[] getCache() {
        return cache;
    }

    private void setCache(String[] cache) {
        this.cache = cache;
    }

}