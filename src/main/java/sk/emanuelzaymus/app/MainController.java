package sk.emanuelzaymus.app;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import sk.emanuelzaymus.robot.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class MainController {

    private static final int ESTIMATIONS_COUNT = 1000;
    private static final String MOVES_COUNT_CHART_TITLE = "Average Moves Count";
    private static final String K_MOVES_CHART_TITLE = "More Than K Moves Probability";
    private static final String READY_STATE = "Ready";
    private static final String RUNNING_STATE = "Running";

    private int replicationsCount;
    private RobotMonteCarlo monteCarlo;

    public TextField widthTxtFld;
    public TextField heightTxtFld;
    public TextField xTxtFld;
    public TextField yTxtFld;
    public TextField replicCountTxtFld;
    public TextField skipPercentTxtFld;
    public TextField kMovesTxtFld;
    public CheckBox useCustomStrategyCheckBox;
    public Label stateLabel;

    public LineChart<Integer, Double> movesCountLineChart;
    public NumberAxis movesCountXAxis;
    public NumberAxis movesCountYAxis;

    public LineChart<Integer, Double> moreThanKMovesLineChart;
    public NumberAxis kMovesXAxis;
    public NumberAxis kMovesYAxis;

    public void onStart() {
        if (!inputsValid()) {
            showInvalidInputAlert();
            return;
        }

        monteCarlo = getNewExperiment();
        Thread simulationThread = new Thread(monteCarlo);
        simulationThread.start();

        stateLabel.setText(RUNNING_STATE);
    }

    public void onStop() {
        monteCarlo.stop();
    }

    private RobotMonteCarlo getNewExperiment() {
        final int width = toInt(widthTxtFld);
        final int height = toInt(heightTxtFld);
        final int x = toInt(xTxtFld);
        final int y = toInt(yTxtFld);

        replicationsCount = toInt(replicCountTxtFld);
        final double skipPercent = toDouble(skipPercentTxtFld);
        final int kMoves = toInt(kMovesTxtFld);
        final boolean useCustomStrategy = useCustomStrategyCheckBox.isSelected();

        final var robot = !useCustomStrategy ? new RandomRobot() : new StrategyRobot();
        final var robotRun = new RobotRun(new Playground(width, height), robot, new Position(x, y));
        return new RobotMonteCarlo(robotRun, replicationsCount, skipPercent, kMoves, ESTIMATIONS_COUNT, this::showResults);
    }

    private void showResults() {
        showMovesCountResults();
        showKMovesResults();
        stateLabel.setText(READY_STATE);
    }

    private void showMovesCountResults() {
        setLineChart(movesCountLineChart, movesCountXAxis, movesCountYAxis, monteCarlo.getSavedEstimations());
        movesCountLineChart.setTitle(MOVES_COUNT_CHART_TITLE + ": " + monteCarlo.getAverageMovesCount());
    }

    private void showKMovesResults() {
        setLineChart(moreThanKMovesLineChart, kMovesXAxis, kMovesYAxis, monteCarlo.getMoreThanKMovesProbabilities());
        moreThanKMovesLineChart.setTitle(K_MOVES_CHART_TITLE + ": " + monteCarlo.getMoreThanKMovesProbability());
    }

    private void setLineChart(LineChart<Integer, Double> chart, NumberAxis xAxis, NumberAxis yAxis, List<Pair<Integer, Double>> estimations) {
        chart.getData().clear();
        final var series = new XYChart.Series<Integer, Double>();

        final var data = estimations.stream()
                .map(x -> new XYChart.Data<>(x.getKey(), x.getValue())).collect(Collectors.toList());

        try {
            xAxis.setLowerBound(estimations.stream().map(Pair::getKey).min(Integer::compareTo).orElseThrow());
            xAxis.setUpperBound(estimations.stream().map(Pair::getKey).max(Integer::compareTo).orElseThrow());
            xAxis.setTickUnit(replicationsCount / 10.0);

            yAxis.setLowerBound(estimations.stream().map(Pair::getValue).min(Double::compareTo).orElseThrow());
            yAxis.setUpperBound(estimations.stream().map(Pair::getValue).max(Double::compareTo).orElseThrow());
            yAxis.setTickUnit(100.0 / replicationsCount);
        } catch (NoSuchElementException ignored) {
        }

        series.getData().addAll(data);
        chart.getData().add(series);
    }

    private boolean inputsValid() {
        return isInt(widthTxtFld) && isInt(heightTxtFld) && isInt(xTxtFld) && isInt(yTxtFld) &&
                isInt(replicCountTxtFld) && isDouble(skipPercentTxtFld) && isInt(kMovesTxtFld);
    }

    private boolean isInt(final TextField textField) {
        try {
            toInt(textField);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int toInt(final TextField textField) {
        return Integer.parseInt(textField.getText());
    }

    private boolean isDouble(final TextField textField) {
        try {
            toDouble(textField);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double toDouble(final TextField textField) {
        return Double.parseDouble(textField.getText());
    }

    private void showInvalidInputAlert() {
        final var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Attention");
        alert.setHeaderText("Invalid Inputs");
        alert.setContentText("Make sure you put valid inputs, please.");
        alert.showAndWait();
    }

}
