package testplugin;

import plugintest.TestInterface;

public class TestPlugin implements TestInterface{

	public TestPlugin() {
		
	}
	
	@Override
	public int getTestValue() {
		
		return 5;
	}
	
	
}
