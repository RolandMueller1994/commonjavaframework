package statemachine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class wrappes the {@link String} current state, the {@link String} next
 * state, the {@link ActionInterface} and the {@link TransitionInterface}.
 * 
 * @author roland
 *
 */
public class Transition {

	String curState;
	String nextState;
	ActionInterface joinAction;
	ActionInterface exitAction;
	TransitionInterface transition;

	/**
	 * Creates a new transition with the given values.
	 * 
	 * @param curState
	 *            The active state.
	 * @param joinAction
	 *            The action to execute when the state is joined. Can be
	 *            {@code null} if no action shall be executed.
	 * @param exitAction
	 *            The action to execute when the state is exited. Can be
	 *            {@code null} if no action shall be executed.
	 * @param nextState
	 *            The state to set if the the transition is fulfilled.
	 * @param transition
	 *            The condition to change to the next state.
	 */
	public Transition(@Nonnull String curState, @Nullable ActionInterface joinAction,
			@Nullable ActionInterface exitAction, @Nonnull String nextState, @Nonnull TransitionInterface transition) {
		this.curState = curState;
		this.joinAction = joinAction;
		this.nextState = nextState;
		this.exitAction = exitAction;
		this.transition = transition;
	}

	/**
	 * 
	 * @return The current state.
	 */
	public String getCurState() {
		return curState;
	}

	/**
	 * 
	 * @return The next state to set.
	 */
	public String getNextState() {
		return nextState;
	}

	/**
	 * 
	 * @return The join action.
	 */
	public ActionInterface getJoinAction() {
		return joinAction;
	}

	/**
	 * 
	 * @return The exit action.
	 */
	public ActionInterface getExitAction() {
		return exitAction;
	}

	/**
	 * 
	 * @return The transition which has to be fulfilled to change to the next
	 *         state.
	 */
	public TransitionInterface getTransition() {
		return transition;
	}

}
