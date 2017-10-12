package guicomponents;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * {@linkplain IntegerTextField} extends the javafx {@linkplain TextField}
 * class. This text field allows only digits as inputs and replaces any other
 * entered characters.
 * 
 * @author Kone
 *
 */
public class IntegerTextField extends TextField {

	public IntegerTextField() {

		new TextField();

		this.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// \\: Backslash character
				// \\d -> \d : digit (0...9)
				if (!newValue.matches("\\d")) {
					// [^\\d] : any character except digits
					setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}
}
