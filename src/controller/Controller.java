package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
    private Label i_resultsInfo_1;
    @FXML
    private Label i_resultsInfo_2;
    @FXML
    private LineChart<String, Double> i_graph_1;
    @FXML
    private LineChart<String, Double> i_graph_2;
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
            ordinaryLeastSquares = new OrdinaryLeastSquares(num_of_formula, x, Double.parseDouble(i_comboBox.getValue().toString()));
            i_graph_1.getData().clear();
            XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
            for (int i = 0; i < ordinaryLeastSquares.getN(); i++) {
                series.getData().add(new XYChart.Data<String, Double>(ordinaryLeastSquares.getXi().get(i).toString(), ordinaryLeastSquares.getYi().get(i)));
            }
            i_graph_1.getData().add(series);
            series = new XYChart.Series<String, Double>();
            for (int i = 0; i < ordinaryLeastSquares.getN(); i++) {
                series.getData().add(new XYChart.Data<String, Double>(ordinaryLeastSquares.getXi().get(i).toString(), ordinaryLeastSquares.getYiDb().get(i)));
            }
            i_graph_1.getData().add(series);
            i_resultsInfo_1.setText("We have answer! :)\n" +
                    "y = "+String.format("%.4f", ordinaryLeastSquares.getA())+"*x^2 + "+String.format("%.4f", ordinaryLeastSquares.getB())+"*x + "+String.format("%.4f", ordinaryLeastSquares.getC())+"\n" +
                    "Discrepancy S = " + String.format("%.4f", ordinaryLeastSquares.getdPow2().get(8)));

            ordinaryLeastSquares.calculateNewOLS();
            i_graph_2.getData().clear();
            series = new XYChart.Series<String, Double>();
            for (int i = 0; i < ordinaryLeastSquares.getN(); i++) {
                if(i==ordinaryLeastSquares.getWorst_ind()) continue;
                series.getData().add(new XYChart.Data<String, Double>(ordinaryLeastSquares.getXi().get(i).toString(), ordinaryLeastSquares.getYi().get(i)));
            }
            i_graph_2.getData().add(series);
            series = new XYChart.Series<String, Double>();
            for (int i = 0; i < ordinaryLeastSquares.getN(); i++) {
                if(i==ordinaryLeastSquares.getWorst_ind()) continue;
                series.getData().add(new XYChart.Data<String, Double>(ordinaryLeastSquares.getXi().get(i).toString(), ordinaryLeastSquares.getYiDb().get(i)));
            }
            i_graph_2.getData().add(series);
            i_resultsInfo_2.setText("We have answer! :)\n" +
                    "y = "+String.format("%.4f", ordinaryLeastSquares.getA())+"*x^2 + "+String.format("%.4f", ordinaryLeastSquares.getB())+"*x + "+String.format("%.4f", ordinaryLeastSquares.getC())+"\n" +
                    "Discrepancy S = " + String.format("%.4f", ordinaryLeastSquares.getdPow2().get(8)-ordinaryLeastSquares.getdPow2().get(ordinaryLeastSquares.getWorst_ind())));

        } else {
            i_resultsInfo_1.setText("You mistake, don't u?");
            i_resultsInfo_2.setText("You mistake, don't u?");
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
