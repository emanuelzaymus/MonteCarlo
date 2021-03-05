package sk.emanuelzaymus.app;

import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import sk.emanuelzaymus.montecarlo.SeedGenerator;
import sk.emanuelzaymus.robot.*;

import java.util.stream.Collectors;


public class MainController {

    private static final int ESTIMATIONS_COUNT = 1000;
    private static final String MOVES_COUNT_CHART_TITLE = "Average Moves Count";

    private RobotMonteCarlo monteCarlo;

    public TextField widthTxtFld;
    public TextField heightTxtFld;
    public TextField xTxtFld;
    public TextField yTxtFld;
    public TextField replicCountTxtFld;
    public TextField skipPercentTxtFld;
    public TextField kTxtFld;
    public CheckBox useCustomStrategyCheckBox;

    public LineChart<Integer, Double> movesCountLineChart;
    public NumberAxis movesCountXAxis;
    public NumberAxis movesCountYAxis;

    public LineChart<Integer, Double> moreThanKMovesLineChart;

    public MainController() {
        SeedGenerator.setSeed(1);
    }

    public void onStart(ActionEvent actionEvent) {
        if (!inputsValid()) {
            showInvalidInputAlert();
            return;
        }
        doExperiment();
        showMovesCountResults();
    }

    private void showMovesCountResults() {
        movesCountLineChart.getData().clear();
        final var series = new XYChart.Series<Integer, Double>();

        var savedEstimations = monteCarlo.getSavedEstimations();
        var data = savedEstimations.stream()
                .map(x -> new XYChart.Data<>(x.getKey(), x.getValue())).collect(Collectors.toList());

        movesCountXAxis.setLowerBound(savedEstimations.stream().map(Pair::getKey).min(Integer::compareTo).orElseThrow());
        movesCountXAxis.setUpperBound(savedEstimations.stream().map(Pair::getKey).max(Integer::compareTo).orElseThrow());
        movesCountXAxis.setTickUnit(1000000);

        movesCountYAxis.setLowerBound(savedEstimations.stream().map(Pair::getValue).min(Double::compareTo).orElseThrow());
        movesCountYAxis.setUpperBound(savedEstimations.stream().map(Pair::getValue).max(Double::compareTo).orElseThrow());
        movesCountYAxis.setTickUnit(0.001);

        series.getData().addAll(data);
        movesCountLineChart.getData().add(series);
        movesCountLineChart.setTitle(MOVES_COUNT_CHART_TITLE + ": " + monteCarlo.getAverageMovesCount());
    }

    private void doExperiment() {
        final int width = toInt(widthTxtFld);
        final int height = toInt(heightTxtFld);
        final int x = toInt(xTxtFld);
        final int y = toInt(yTxtFld);

        final int replicCount = toInt(replicCountTxtFld);
        final double skipPercent = toDouble(skipPercentTxtFld);
        final double k = toDouble(kTxtFld);
        final boolean useCustomStrategy = useCustomStrategyCheckBox.isSelected();

        final var robotRun = new RobotRun(new Playground(width, height), new Robot(useCustomStrategy), new Position(x, y));
        monteCarlo = new RobotMonteCarlo(robotRun, replicCount, skipPercent, ESTIMATIONS_COUNT);
        monteCarlo.simulate();
    }

    private boolean inputsValid() {
        return isInt(widthTxtFld) && isInt(heightTxtFld) && isInt(xTxtFld) && isInt(yTxtFld) &&
                isInt(replicCountTxtFld) && isDouble(skipPercentTxtFld) && isDouble(kTxtFld);
    }

    private boolean isInt(TextField textField) {
        try {
            toInt(textField);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int toInt(TextField textField) {
        return Integer.parseInt(textField.getText());
    }

    private boolean isDouble(TextField textField) {
        try {
            toDouble(textField);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double toDouble(TextField textField) {
        return Double.parseDouble(textField.getText());
    }

    private void showInvalidInputAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Attention");
        alert.setHeaderText("Invalid Inputs");
        alert.setContentText("Make sure you put valid inputs, please.");
        alert.showAndWait();
    }

}
