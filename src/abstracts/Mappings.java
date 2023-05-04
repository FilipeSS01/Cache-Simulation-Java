package abstracts;

import java.util.ArrayList;

public abstract class Mappings {
    protected ArrayList<String> dataConfig;
    protected ArrayList<String> memoryData;
    protected Long addressBits;
    protected Long tagBits;
    protected Integer lineBits;
    protected Integer wordBits;
    protected Long limitCache;
    protected Integer replace;
    protected int hits;
    protected int miss;

    public Mappings(ArrayList<String> memoryData, ArrayList<String> dataConfig) {
        setDataConfig(dataConfig);
        setMemoryData(memoryData);
        initialize();
        showResult();
    }

    public Mappings(ArrayList<String> memoryData, ArrayList<String> dataConfig, Integer replace) {
        setDataConfig(dataConfig);
        setMemoryData(memoryData);
        setReplace(replace);
        initialize();
        showResult();
    }

    protected abstract void initialize();

    protected abstract void mapping(String[] partAddress);

    protected abstract String[] getPartAddress(Long value);

    protected void readMemory() {
        for (String memory : getMemoryData()) {
            mapping(getPartAddress(Long.parseLong(memory)));
        }
    }

    protected void showResult() {
        System.out.println("Hits: " + getHits());
        System.out.println("Miss: " + getMiss());
        System.out.println(
                "Percentage: " + (Double.valueOf(getHits()) / Double.valueOf(getMemoryData().size())) * 100 + " %\n");
    }

    protected Long convertToBits(Long bits, String unit) {
        switch (unit) {
            case "KB":
                bits = bits * 1024;
                break;
            case "MB":
                bits = bits * 1024 * 1024;
                break;
            case "GB":
                bits = bits * 1024 * 1024 * 1024;
                break;
        }
        return bits;
    }

    protected Long convertLineToDecimalBits(Long cacheBytes, Long wordBytes, Integer lineConfig) {
        return (cacheBytes / wordBytes) / lineConfig;
    }

    protected Long calcAddress(Long memoryBytes, Long wordBits) {
        return Math.round(Math.log(memoryBytes / wordBits) / Math.log(2));
    }

    protected Integer calcLine(Long cacheBytes, Long wordBytes, Integer lineConfig) {
        return (int) Math.round(Math.log((cacheBytes / wordBytes) / lineConfig) / Math.log(2));
    }

    protected Integer calcWord(Long word) {
        return (int) Math.round(Math.log(word) / Math.log(2));
    }

    protected Long calcTag(Long addressBits, Integer wordBits2, Integer lineBits2) {
        return addressBits - wordBits2 - lineBits2;
    }

    // Gets and Sets
    public ArrayList<String> getMemoryData() {
        return memoryData;
    }

    public void setMemoryData(ArrayList<String> memoryData) {
        this.memoryData = memoryData;
    }

    public ArrayList<String> getDataConfig() {
        return dataConfig;
    }

    public void setDataConfig(ArrayList<String> dataConfig) {
        this.dataConfig = dataConfig;
    }

    public Long getAddressBits() {
        return addressBits;
    }

    protected void setAddressBits(Long addressBits) {
        this.addressBits = addressBits;
    }

    public Long getTagBits() {
        return tagBits;
    }

    protected void setTagBits(Long tagBits) {
        this.tagBits = tagBits;
    }

    public Integer getLineBits() {
        return lineBits;
    }

    protected void setLineBits(Integer lineBits) {
        this.lineBits = lineBits;
    }

    public Integer getWordBits() {
        return wordBits;
    }

    protected void setWordBits(Integer wordBits) {
        this.wordBits = wordBits;
    }

    public Long getLimitCache() {
        return limitCache;
    }

    protected void setLimitCache(Long limitCache) {
        this.limitCache = limitCache;
    }

    public Integer getReplace() {
        return replace;
    }

    protected void setReplace(Integer replace) {
        this.replace = replace;
    }

    public int getHits() {
        return hits;
    }

    protected void setHits(int hits) {
        this.hits = hits;
    }

    public int getMiss() {
        return miss;
    }

    protected void setMiss(int miss) {
        this.miss = miss;
    }
}