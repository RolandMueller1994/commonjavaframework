package guicomponents;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class IntegerTextField extends TextField {

	public IntegerTextField() {
		
		new TextField();
		
		NumberFormat format = new DecimalFormat("0,0");

		this.setTextFormatter(new TextFormatter<>(c -> {
			if (c.getControlNewText().isEmpty()) {
				return c;
			}

			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(c.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
				return null;
			} else {
				return c;
			}
		}));
		
	}

}
