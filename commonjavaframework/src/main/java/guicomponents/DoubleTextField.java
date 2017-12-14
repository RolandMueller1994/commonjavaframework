package guicomponents;

public class DoubleTextField extends AbstractNumberTextField<Double> {

	public DoubleTextField(Double value, Double minValue, Double maxValue) {
		super(value, minValue, maxValue);
	}

	public DoubleTextField(Double value) {
		super(value, Double.MAX_VALUE, Double.MAX_VALUE);
	}

	@Override
	protected String checkInput(String input) {
		
		String sign = "";
		
		if(input.indexOf("-") == 0) {
			input = input.substring(1);
			sign = "-";
		} else if(input.indexOf("+") == 0) {
			input = input.substring(1);
		}
		
		// if there is no comma, -1 is returned
		int i = input.indexOf(".");

		if (i != -1) {
			// separate string in integers and decimals
			String integerString = input.substring(0, i);
			String decimalString = input.substring(i + 1);

			// \\: Backslash character
			// \\d -> \d : digit (0...9)
			if (!decimalString.matches("\\d")) {
				// [^\\d] : any character except digits
				decimalString = decimalString.replaceAll("[^\\d]", "");
			}
			if (!integerString.matches("\\d")) {
				// [^\\d] : any character except digits
				integerString = integerString.replaceAll("[^\\d]", "");
			}
			
			return sign + integerString + "." + decimalString;
		} else {
			return sign + input.replaceAll("[^\\d.]", "");
		}
	}

	@Override
	protected Double parseInput(String input) {
		
		return new Double(input);
	}
	
	
}
