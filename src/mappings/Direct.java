package mappings;

import java.util.ArrayList;

import utils.Convert;
import utils.FileManager;

public class Direct {

    private String path;
    private Integer addressBits;
    private Integer tagBits;
    private Integer lineBits;
    private Integer wordBits;
    private String[] cahce;
    private int acertos;
    private int erros;

    public Direct(String path) {
        setPath(path);
        initialize();
    }

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
        setCahce(new String[convertLineToDecimalBits(cacheBytes, wordBytes, Integer.parseInt(lineConfig[2]))]);

        // Values in bits
        setAddressBits(calcAddress(memoryBytes, wordBytes));
        setWordBits(calcWord(wordBytes));
        setLineBits(calcLine(cacheBytes, wordBytes, Integer.parseInt(lineConfig[2])));
        setTagBits(calcTag(addressBits, wordBits, lineBits));

        // Read memory
        readMemory();
    }

    private void direct(String[] info) {
        Integer line = Integer.parseInt(info[1], 2);
        if (!(getCahce()[line] != null && getCahce()[line].equals(info[0]))) {
            getCahce()[line] = info[0];
            setErros(getErros() + 1);
        } else {
            setAcertos(getAcertos() + 1);
        }
    }

    private void readMemory() {
        ArrayList<String> memoryData = FileManager.stringReader(getPath() + "data/others/memory1.txt");
        for (String memory : memoryData) {
            direct(getInfo(Integer.parseInt(memory)));
        }
        System.out.println("Acertos: " + getAcertos());
        System.out.println("Erros: " + getErros());
        System.out.println("Porcentagem: " + (Double.valueOf(getAcertos()) / Double.valueOf(memoryData.size())) * 100);
    }

    private Integer convertToBits(Integer space, String unit) {
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

    private Integer convertLineToDecimalBits(Integer cacheBytes, Integer wordBytes, Integer lineConfig) {
        return (cacheBytes / wordBytes) / lineConfig;
    }

    private Integer calcAddress(Integer memoryBytes, Integer wordBits) {
        return Integer.parseInt("" + Math.round(Math.log(memoryBytes / wordBits) / Math.log(2)));
    }

    private Integer calcLine(Integer cacheBytes, Integer wordBytes, Integer lineConfig) {
        return Integer.parseInt("" + Math.round(Math.log((cacheBytes / wordBytes) / lineConfig) / Math.log(2)));
    }

    private Integer calcWord(Integer word) {
        return Integer.parseInt("" + Math.round(Math.log(word) / Math.log(2)));
    }

    private Integer calcTag(Integer addressBits, Integer wordBits2, Integer lineBits2) {
        return addressBits - wordBits2 - lineBits2;
    }

    private String[] getInfo(Integer value) {
        String[] info = new String[3];
        String addressBinary = Convert.intToBinaryString(value, getAddressBits());
        info[0] = addressBinary.substring(0, getTagBits()); // Tag
        info[1] = addressBinary.substring(getTagBits(), getLineBits() + getTagBits()); // Line
        info[2] = addressBinary.substring(getLineBits() + getTagBits(), getAddressBits()); // Word
        return info;
    }

    // Gets and Sets
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getAddressBits() {
        return addressBits;
    }

    public void setAddressBits(Integer addressBits) {
        this.addressBits = addressBits;
    }

    public Integer getTagBits() {
        return tagBits;
    }

    public void setTagBits(Integer tagBits) {
        this.tagBits = tagBits;
    }

    public Integer getLineBits() {
        return lineBits;
    }

    public void setLineBits(Integer lineBits) {
        this.lineBits = lineBits;
    }

    public Integer getWordBits() {
        return wordBits;
    }

    public void setWordBits(Integer wordBits) {
        this.wordBits = wordBits;
    }

    public String[] getCahce() {
        return cahce;
    }

    public void setCahce(String[] cahce) {
        this.cahce = cahce;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }
}