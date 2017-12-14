package guicomponents;

import java.util.HashSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
					if (!getText().isEmpty()) {
						setValue(parseInput(getText()));
						fireValueChanged();
						setStyle("-fx-text-fill: black");
					}
				}
			}
		});
	}
	
	public void setValue(T value) {
		this.value = checkValue(value);
		setText(this.value.toString());
	}
	
	private T checkValue(T value) {
		if(value.doubleValue() > maxValue.doubleValue()) {
			return maxValue;
		} else if(value.doubleValue() < minValue.doubleValue()) {
			return minValue;
		}
		return value;
	}
	
	public T getValue() {
		return value;
	}
	
	public void registerValueChangedListener(ValueChangedInterface listener) {
		listeners.add(listener);
	}
	
	public void removeValueChangedListener(ValueChangedInterface listener) {
		listeners.remove(listener);
	}
	
	private void fireValueChanged() {
		for(ValueChangedInterface listener : listeners) {
			listener.valueChanged(value);
		}
	}
	
	protected abstract String checkInput(String input);
	
	protected abstract T parseInput(String input);
	
	public interface ValueChangedInterface {
		
		public void valueChanged(Number value);
		
	}
}
