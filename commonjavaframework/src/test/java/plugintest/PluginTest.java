package plugintest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileSystems;

import org.junit.Test;

import pluginframework.PluginLoader;

public class PluginTest {

	@Test
	public void test() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
		PluginLoader<TestInterface> loader = new PluginLoader<> ();
		loader.registerPlugin(FileSystems.getDefault().getPath("/home/Roland/Schreibtisch", "TestPlugin.jar"));
		TestInterface clazz = loader.getPlugin("TestPlugin");
		int value = clazz.getTestValue();
		assertEquals(5, value);
	}

}
