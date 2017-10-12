package guicomponents;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * {@linkplain DecimalTextField} extends the javafx {@linkplain TextField}
 * class. This text field allows only digits and comma as inputs and replaces
 * any other entered characters.
 * 
 * @author Kone
 *
 */
public class DecimalTextField extends TextField {

	public DecimalTextField() {

		new TextField();

		this.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				// if there is no comma, -1 is returned
				int i = newValue.indexOf(",");

				if (i != -1) {
					// separate string in integers and decimals
					String integerString = newValue.substring(0, i + 1);
					String decimalString = newValue.substring(i + 1);

					// \\: Backslash character
					// \\d -> \d : digit (0...9)
					if (!decimalString.matches("\\d")) {
						// [^\\d] : any character except digits
						decimalString = decimalString.replaceAll("[^\\d]", "");
						setText(integerString + decimalString);
					}
				} else {
					setText(newValue.replaceAll("[^\\d,]", ""));
				}
			}
		});
	}
}
