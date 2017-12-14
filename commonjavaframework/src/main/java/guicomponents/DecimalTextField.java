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

	double minValue;
	double maxValue;

	public DecimalTextField() {

		this.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				checkInput(newValue);
			}
		});
	}

	public DecimalTextField(double minValue, double maxValue) {

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
	public void setValue(double value) {
		this.setText(Double.toString(value));
	}

	/**
	 * Gets the value of the property text.
	 * 
	 * @return The text of the text field as double.
	 */
	public double getValue() {
		return Double.parseDouble(this.getText());
	}

	/**
	 * Checks the input string for invalid characters and replaces them.
	 * 
	 * @param input
	 *            the string to be evaluated
	 */
	public void checkInput(String input) {
		// if there is no comma, -1 is returned
		int i = input.indexOf(".");

		if (i != -1) {
			// separate string in integers and decimals
			String integerString = input.substring(0, i + 1);
			String decimalString = input.substring(i + 1);

			// \\: Backslash character
			// \\d -> \d : digit (0...9)
			if (!decimalString.matches("\\d")) {
				// [^\\d] : any character except digits
				decimalString = decimalString.replaceAll("[^\\d]", "");
				setText(integerString + decimalString);
			}
		} else {
			setText(input.replaceAll("[^\\d.]", ""));
		}
	}

	/**
	 * Checks if the value is out of the defined limits.
	 * 
	 * @param value
	 *            the string to be evaluated
	 * @return true, if the the value is out of the limits
	 */
	public boolean numberOutOfLimits(String value) {
		Double number = Double.parseDouble(value);
		if (number != 0) {
			if (number < minValue) {
				setText(Double.toString(minValue));	
				return true;
			} else if (number > maxValue) {
				setText(Double.toString(maxValue));
				return true;
			}
		}
		return false;
	}
}
