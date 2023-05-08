package interfaces;

import java.net.URL;
import java.io.File;
import java.util.ArrayList;
import javafx.stage.FileChooser;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ChoiceBox;

import mappings.Direct;
import utils.FileManager;
import abstracts.Mappings;
import mappings.Associative;
import mappings.SetAssociative;

public class Controller implements Initializable {

    @FXML
    private Button btnAssociative;

    @FXML
    private Button btnConfig;

    @FXML
    private Button btnDirect;

    @FXML
    private Button btnMemory;

    @FXML
    private Button btnRun;

    @FXML
    private Button btnSetAssociative;

    @FXML
    private ChoiceBox<String> choiceReplace;

    @FXML
    private TextArea txtResponse;

    private String mapping;
    private Mappings mappings;
    private String response;
    private ArrayList<String> memoryData;
    private ArrayList<String> dataConfig;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        choiceReplace.getItems().addAll("RANDOM", "FIFO", "LFU", "LRU");
        response = "";
    }

    @FXML
    void readConfig(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Config");
        File file = fileChooser.showOpenDialog(null);
        setDataConfig(FileManager.stringReader(file.getAbsolutePath()));
    }

    @FXML
    void readMemory(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Memory");
        File file = fileChooser.showOpenDialog(null);
        setMemoryData(FileManager.stringReader(file.getAbsolutePath()));
    }

    @FXML
    void runAssociative(ActionEvent event) {
        setMapping("Associative");
    }

    @FXML
    void runSetAssociative(ActionEvent event) {
        setMapping("SetAssociative");
    }

    @FXML
    void runDirect(ActionEvent event) {
        setMapping("Direct");
    }

    @FXML
    void runMappings(ActionEvent event) {
        switch (getMapping()) {
            case "Direct":
                setMappings(new Direct(getMemoryData(), getDataConfig()));
                break;
            case "Associative":
                setMappings(new Associative(getMemoryData(), getDataConfig(), choiceReplace.getValue().toString()));
                break;
            case "SetAssociative":
                setMappings(new SetAssociative(getMemoryData(), getDataConfig(), choiceReplace.getValue().toString()));
                break;
        }
        setResponse(getMappings().toString());
        txtResponse.setText(getResponse());
        txtResponse.end();
    }

    // Gets and Sets
    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public Mappings getMappings() {
        return mappings;
    }

    public void setMappings(Mappings mappings) {
        this.mappings = mappings;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response += response;
    }

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

}
