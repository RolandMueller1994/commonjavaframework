package statemachine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * A default statemachine for common jobs.
 * 
 * @author roland
 *
 */
public class StateMachine {

	private HashMap<String, List<Transition>> transitionMap = new HashMap<>();

	private Transition curState;

	/**
	 * Creates a new statemachine with the given transitions.
	 * 
	 * @param transitions
	 *            The {@link Transition}s who define this statemachine. The
	 *            first element has to define the initial state.
	 */
	public StateMachine(@Nonnull Transition... transitions) {

		for (Transition transition : Arrays.asList(transitions)) {

			// Check if the state of the state of the actual transition element
			// has already been mapped. If not insert a new list.
			if (!transitionMap.containsKey(transition.getCurState())) {
				transitionMap.put(transition.getCurState(), new LinkedList<>());
			}
			transitionMap.get(transition.getCurState()).add(transition);
		}

		// Set the initial state to the first element in the given transition
		// array.
		curState = transitions[0];

		// Execute the join action of the initial state.
		if (curState.getJoinAction() != null) {
			curState.getJoinAction().executeAction();
		}
	}

	/**
	 * Checks if the current state has to be changed. Must be called if a
	 * transition can be true.
	 */
	public void execute() {
		List<Transition> possibleTransitions = transitionMap.get(curState.getCurState());

		// Get the possible transitions for the current state.
		for (Transition transition : possibleTransitions) {
			// Check all transitions.
			if (transition.getTransition().isComplied()) {
				if (curState.getExitAction() != null) {
					curState.getExitAction().executeAction();
				}

				curState = transition;

				if (curState.getJoinAction() != null) {
					curState.getJoinAction().executeAction();
				}
				break;
			}
		}
	}
}
