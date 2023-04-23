package abstracts;

import java.util.ArrayList;
import utils.FileManager;

public abstract class Mappings {
    protected String path;
    protected Integer addressBits;
    protected Integer tagBits;
    protected Integer lineBits;
    protected Integer wordBits;
    protected int hits;
    protected int miss;

    public Mappings(String path) {
        setPath(path);
        initialize();
    }

    protected abstract void initialize();

    protected abstract void mapping(String[] partAddress);

    protected abstract String[] getPartAddress(Integer value);

    protected void readMemory() {
        ArrayList<String> memoryData = FileManager.stringReader(getPath() + "data/others/memory1.txt");
        for (String memory : memoryData) {
            mapping(getPartAddress(Integer.parseInt(memory)));
        }
        System.out.println("Hits: " + getHits());
        System.out.println("Miss: " + getMiss());
        System.out.println("Percentage: " + (Double.valueOf(getHits()) / Double.valueOf(memoryData.size())) * 100);
    }

    protected Integer convertToBits(Integer space, String unit) {
        Integer bits = space;
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

    protected Integer convertLineToDecimalBits(Integer cacheBytes, Integer wordBytes, Integer lineConfig) {
        return (cacheBytes / wordBytes) / lineConfig;
    }

    protected Integer calcAddress(Integer memoryBytes, Integer wordBits) {
        return Integer.parseInt("" + Math.round(Math.log(memoryBytes / wordBits) / Math.log(2)));
    }

    protected Integer calcLine(Integer cacheBytes, Integer wordBytes, Integer lineConfig) {
        return Integer.parseInt("" + Math.round(Math.log((cacheBytes / wordBytes) / lineConfig) / Math.log(2)));
    }

    protected Integer calcWord(Integer word) {
        return Integer.parseInt("" + Math.round(Math.log(word) / Math.log(2)));
    }

    protected Integer calcTag(Integer addressBits, Integer wordBits2, Integer lineBits2) {
        return addressBits - wordBits2 - lineBits2;
    }

    // Gets and Sets
    public String getPath() {
        return path;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    public Integer getAddressBits() {
        return addressBits;
    }

    protected void setAddressBits(Integer addressBits) {
        this.addressBits = addressBits;
    }

    public Integer getTagBits() {
        return tagBits;
    }

    protected void setTagBits(Integer tagBits) {
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