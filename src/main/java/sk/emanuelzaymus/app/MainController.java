package sk.emanuelzaymus.app;


import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class MainController {

    public TextField widthTxtFld;
    public TextField heightTxtFld;
    public TextField xTxtFld;
    public TextField yTxtFld;
    public TextField replicCountTxtFld;
    public TextField skipPercentTxtFld;
    public TextField kTxtFld;
    public CheckBox useCustomStrategyCheckBox;

    public LineChart movesCountLineChart;
    public LineChart moreThanKMovesLineChart;

    public MainController() {

        final NumberAxis xAxis = new NumberAxis();

        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");

        movesCountLineChart = new LineChart<Number, Number>(xAxis, yAxis);

        //        //defining a series

        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));

        movesCountLineChart.getData().add(series);

    }
}
