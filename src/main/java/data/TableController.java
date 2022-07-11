package data;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TableController {

    @FXML
    private TableView<Data> tableView;

    @FXML
    private TableColumn<Data, Integer> code;

    @FXML
    private TableColumn<Data, String> name;

    @FXML
    private TableColumn<Data, Integer> numberOfMoves;


    @FXML
    private void initialize() throws IOException {
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberOfMoves.setCellValueFactory(new PropertyValueFactory<>("numberOfMoves"));
        List<Data> dataList = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .readValue(TableController.class.getResourceAsStream("/data.json"), new TypeReference<List<Data>>() {});
        ObservableList<Data> observableList = FXCollections.observableArrayList();
        observableList.addAll(dataList);
        tableView.setItems(observableList);
    }

}
