package sk.emanuelzaymus.app;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class Sample2 {

    public Label helloWorld;

    public void sayHelloWorld(ActionEvent actionEvent) {
        helloWorld.setText("Hello!!!!");
    }
}
