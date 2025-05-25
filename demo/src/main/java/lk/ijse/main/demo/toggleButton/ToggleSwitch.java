package lk.ijse.main.demo.toggleButton;
import javafx.animation.TranslateTransition;
import javafx.scene.Cursor;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ToggleSwitch extends StackPane {
    private boolean switchedOn = false;
    private final Rectangle background = new Rectangle(60, 30);
    private final Circle trigger = new Circle(15);

    public ToggleSwitch(Boolean swich) {
        this.switchedOn = swich;
        background.setArcWidth(30);
        background.setArcHeight(30);

        // Set initial color and position based on switchedOn
        if (switchedOn) {
            background.setFill(Color.LIMEGREEN);
            trigger.setTranslateX(15); // Right
        } else {
            background.setFill(Color.LIGHTGRAY);
            trigger.setTranslateX(-15); // Left
        }

        trigger.setFill(Color.WHITE);
        trigger.setStroke(Color.LIGHTGRAY);

        getChildren().addAll(background, trigger);
        setCursor(Cursor.HAND);

        setOnMouseClicked(event -> {
            switchedOn = !switchedOn;

            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), trigger);
            transition.setToX(switchedOn ? 15 : -15);
            transition.play();
            background.setFill(switchedOn ? Color.LIMEGREEN : Color.LIGHTGRAY);
        });
    }

    public boolean getSwitchedOn() {
        return this.switchedOn;
    }
    public void setSwitchedOn(boolean switchedOn){
        this.switchedOn=switchedOn;
    }
}
