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

	int minValue;
	int maxValue;

	public IntegerTextField() {

		new TextField();

		this.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				checkInput(newValue);
			}
		});
	}

	public IntegerTextField(int minValue, int maxValue) {

		new TextField();
		this.minValue = minValue;
		this.maxValue = maxValue;

		this.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					checkInput(newValue);
					if (!numberOutOfLimits(newValue)) {
						checkInput(newValue);
					}
				}
			}
		});
	}

	/**
	 * Sets the value of the property text.
	 * 
	 * @param value
	 *            The value to be set.
	 */
	public void setValue(int value) {
		this.setText(Integer.toString(value));
	}

	/**
	 * Gets the value of the property text.
	 * 
	 * @return The text of the text field as integer.
	 */
	public double getValue() {
		return Integer.parseInt(this.getText());
	}

	/**
	 * Checks if the value is out of the defined limits.
	 * 
	 * @param value
	 *            the string to be evaluated
	 * @return true, if the the value is out of the limits
	 */
	public boolean numberOutOfLimits(String value) {
		Integer number = Integer.parseInt(value);
		if (number != 0) {
			if (number < minValue) {
				setText(Integer.toString(minValue));
				return true;
			} else if (number > maxValue) {
				setText(Integer.toString(maxValue));
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks the input string for invalid characters and replaces them.
	 */
	public void checkInput(String value) {
		// \\: Backslash character
		// \\d -> \d : digit (0...9)
		if (!value.matches("\\d")) {
			// [^\\d] : any character except digits
			setText(value.replaceAll("[^\\d]", ""));
		}
	}
}
