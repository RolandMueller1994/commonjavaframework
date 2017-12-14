package guicomponents;

public class LongTextField extends AbstractNumberTextField<Long> {

	public LongTextField(Long value, Long minValue, Long maxValue) {
		super(value, minValue, maxValue);
	}

	public LongTextField(Long value) {
		super(value, Long.MIN_VALUE, Long.MAX_VALUE);
	}

	@Override
	protected String checkInput(String input) {
		// \\: Backslash character
		// \\d -> \d : digit (0...9)
		if (!input.matches("\\d")) {
			// [^\\d] : any character except digits
			input = input.replaceAll("[^\\d]", "");
		}
		return input;
	}

	@Override
	protected Long parseInput(String input) {
		
		return new Long(input);
	}
	
}
