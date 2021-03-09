package sk.emanuelzaymus.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the application and shows the window.
     */
    @Override
    public void start(final Stage primaryStage) throws IOException {
        final Parent root = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
        primaryStage.setTitle("MonteCarlo - Robot");
        primaryStage.setScene(new Scene(root, 900, 600));

        /* Source: https://stackoverflow.com/questions/14357515/javafx-close-window-on-pressing-esc */
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                primaryStage.close();
            }
        });

        primaryStage.show();
    }

}
