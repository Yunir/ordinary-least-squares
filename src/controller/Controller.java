package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.OrdinaryLeastSquares;

public class Controller {
    @FXML
    private RadioButton i_f1;
    @FXML
    private RadioButton i_f2;
    @FXML
    private RadioButton i_f3;
    @FXML
    private RadioButton i_f4;
    @FXML
    private ToggleGroup i_integrals;
    @FXML
    private TextField first_x;
    @FXML
    private ComboBox i_comboBox;
    @FXML
    private Button i_confirmButton;
    @FXML
    private Label i_resultsInfo;
    private OrdinaryLeastSquares ordinaryLeastSquares;

    @FXML
    private void initialize() {
    }

    @FXML
    private void computeIntegral(ActionEvent actionEvent) {
        if(checkFields()) {
            int num_of_formula;
            if(i_f1.isSelected()) num_of_formula = 1;
            else if(i_f2.isSelected()) num_of_formula = 2;
            else if(i_f3.isSelected()) num_of_formula = 3;
            else num_of_formula = 4;
            double x = Double.parseDouble(first_x.getText());
            //TODO: generate table of x'es and y'es
            ordinaryLeastSquares = new OrdinaryLeastSquares(num_of_formula, x, Double.parseDouble(i_comboBox.getValue().toString()));
            //if(ordinaryLeastSquares.getIsCorrect()) {

                i_resultsInfo.setText("We have answer! :)\n");
            //} else {
                //i_resultsInfo.setText("We don't have answer!");
            //}
        } else {
            i_resultsInfo.setText("You mistake, don't u?");
        }
    }

    private boolean checkFields() {
        if(first_x.getText().matches("-?((\\d*)|(\\d+\\.\\d*))")  &&
                !first_x.getText().equals("") &&
                i_comboBox.getValue() != null) {
            return true;
        } else {
            return false;
        }

    }

}
