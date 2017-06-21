package statemachine;

/**
 * This interface is used to execute a action to the corresponding state in a
 * {@link StateMachine}
 * 
 * @author roland
 *
 */
public interface ActionInterface {

	/**
	 * Execute the action for one state.
	 */
	void executeAction();

}
