package statemachinetest;

import static org.junit.Assert.*;

import org.junit.Test;

import statemachine.ActionInterface;
import statemachine.StateMachine;
import statemachine.Transition;
import statemachine.TransitionInterface;

public class StateMachineTest {

	private int count = 0;
	
	@Test
	public void test() {
		
		Transition[] transitions = {new Transition("first", new ActionInterface() {

			@Override
			public void executeAction() {
				count++;
				
			}
			
		}, null, "second", new TransitionInterface() {

			@Override
			public boolean isComplied() {
				// TODO Auto-generated method stub
				return true;
			}
			
		}), new Transition("second", new ActionInterface() {

			@Override
			public void executeAction() {
				count++;
				
			}
			
		}, null, "third", new TransitionInterface() {

			@Override
			public boolean isComplied() {
				// TODO Auto-generated method stub
				return true;
			}
			
		}), new Transition("third", new ActionInterface() {

			@Override
			public void executeAction() {
				count++;
				
			}
			
		}, null, null, new TransitionInterface() {

			@Override
			public boolean isComplied() {
				// TODO Auto-generated method stub
				return true;
			}
			
		})};
		
		StateMachine stateMachine = new StateMachine(transitions);
		
		stateMachine.execute();
		stateMachine.execute();
		
		System.out.println(count);
		
		assertEquals(count, 3);
	}

}
