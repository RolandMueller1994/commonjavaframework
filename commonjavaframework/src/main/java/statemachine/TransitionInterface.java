package statemachine;

/**
 * This interface is used to create transistions for a {@link StateMachine}.
 * 
 * @author roland
 *
 */
public interface TransitionInterface {

	/**
	 * Calculates if the transition is complied or not.
	 * 
	 * @return {@code true} if the transition is complied, {@code false} if not.
	 */
	boolean isComplied();

}
