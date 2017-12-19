package guicomponents;

import java.util.HashSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Abstract class for {@link TextField}s which should only display numbers.
 * Subclasses have to define which data type is supported and how insertes
 * strings have to be checked.
 * 
 * @author roland
 *
 * @param <T>
 *            the data type which should be handled
 */
public abstract class AbstractNumberTextField<T extends Number> extends TextField {

	private T minValue;
	private T maxValue;

	private T value;

	private HashSet<ValueChangedInterface> listeners = new HashSet<>();

	protected AbstractNumberTextField(T value, T minValue, T maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		setValue(value);

		textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				setStyle("-fx-text-fill: mediumblue; -fx-font-weight: bold");
				setText(checkInput(newValue));
			}
		});

		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					confirmInput();
				}
			}
		});
	}
	
	private void confirmInput() {
		if (!getText().isEmpty()) {
			setValue(parseInput(getText()));
			fireValueChanged();
			setStyle("-fx-text-fill: black");
		}
	}

	/**
	 * Set the value of the text field
	 * 
	 * @param value
	 *            the new value.
	 */
	public void setValue(T value) {
		this.value = checkValue(value);
		setText(this.value.toString());
		confirmInput();
	}

	private T checkValue(T value) {
		if (value.doubleValue() > maxValue.doubleValue()) {
			return maxValue;
		} else if (value.doubleValue() < minValue.doubleValue()) {
			return minValue;
		}
		return value;
	}

	/**
	 * Get the value which is currently valid.
	 * 
	 * @return the valid value.
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Registered listeners will be called if the valid value changes.
	 * 
	 * @param listener
	 *            the {@link ValueChangedInterface} to call.
	 */
	public void registerValueChangedListener(ValueChangedInterface listener) {
		listeners.add(listener);
	}

	/**
	 * Remove a listener
	 * 
	 * @param listener
	 *            the {@link ValueChangedInterface} to remove.
	 */
	public void removeValueChangedListener(ValueChangedInterface listener) {
		listeners.remove(listener);
	}

	private void fireValueChanged() {
		for (ValueChangedInterface listener : listeners) {
			listener.valueChanged(value);
		}
	}

	protected abstract String checkInput(String input);

	protected abstract T parseInput(String input);

	/**
	 * Listener which will be called if the valid value of
	 * {@link AbstractNumberTextField} changed.
	 * 
	 * @author roland
	 *
	 */
	public interface ValueChangedInterface {

		/**
		 * Called if valid value changed.
		 * 
		 * @param value
		 *            the new value as {@link Number}. Must be casted to the
		 *            generic type of text field.
		 */
		public void valueChanged(Number value);

	}
}
