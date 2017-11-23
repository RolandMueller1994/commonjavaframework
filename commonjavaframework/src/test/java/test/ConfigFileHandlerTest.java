package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.common.io.Files;

import configfileframework.ConfigFileHandler;
import configfileframework.ConfigFileHandlerException;
import configfileframework.ConfigFileValueWrapper;
import resourceframework.GlobalResourceProvider;
import resourceframework.ResourceProviderException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfigFileHandlerTest {

	private ConfigFileHandler configHandler;

	@Test
	public void t01_write_file_test() throws ResourceProviderException, IOException, ConfigFileHandlerException {

		if (!GlobalResourceProvider.getInstance().checkRegistered("workDir")) {
			String current = new java.io.File(".").getCanonicalPath();
			GlobalResourceProvider.getInstance().registerResource("workDir", current);
		}
		
		File dir = new File(GlobalResourceProvider.getInstance().getResource("workDir") + File.separator + ".config");
		
		for(File file : dir.listFiles()) {
			if(file.getName().equals("testConfig.config") || file.getName().equals("testConfig.config2")) {
				file.delete();
			}
		}

		HashMap<String, ConfigFileValueWrapper> params = new HashMap<>();
		params.put("testParamBol", new ConfigFileValueWrapper(new Boolean(true), "This is a comment%crwith multiple lines"));
		params.put("testParamLong", new ConfigFileValueWrapper(new Long(10), null));
		params.put("testParamDouble", new ConfigFileValueWrapper(new Double(1.23), null));
		params.put("testParamString", new ConfigFileValueWrapper("TestString", null));
		configHandler = new ConfigFileHandler("testConfig", params);

		File tmp = new File(GlobalResourceProvider.getInstance().getResource("workDir") + File.separator + ".config"
				+ File.separator + "testConfig.config");
		File tmp2 = new File(GlobalResourceProvider.getInstance().getResource("workDir") + File.separator + ".config"
				+ File.separator + "testConfig.config2");
		Files.copy(tmp, tmp2);
		
		GlobalResourceProvider.getInstance().changeResource("testParamBol", new Boolean(false));
		GlobalResourceProvider.getInstance().changeResource("testParamLong", new Long(11));
	}

	@Test
	public void t02_read_file_test() throws ResourceProviderException, ConfigFileHandlerException {
		
		HashMap<String, ConfigFileValueWrapper> params = new HashMap<>();
		params.put("testParamBol2", new ConfigFileValueWrapper(new Boolean(false), null));
		params.put("testParamLong2", new ConfigFileValueWrapper(new Long(10), null));
		params.put("testParamDouble2", new ConfigFileValueWrapper(new Double(1.23), null));
		params.put("testParamString2", new ConfigFileValueWrapper("TestString", null));
		configHandler = new ConfigFileHandler("testConfig2", params);
		
		GlobalResourceProvider resProv = GlobalResourceProvider.getInstance();
		
		if(!resProv.getResource("testParamBol2").equals(new Boolean(true)) || !resProv.getResource("testParamLong2").equals(new Long(11))) {
			System.out.println(resProv.getResource("testParamBol2").getClass().toString());
			System.out.println(resProv.getResource("testParamBol2").toString());
			System.out.println(resProv.getResource("testParamLong2").getClass().toString());
			System.out.println(resProv.getResource("testParamLong2").toString());
			System.out.println(resProv.getResource("testParamDouble2").getClass().toString());
			System.out.println(resProv.getResource("testParamDouble2").toString());
			System.out.println(resProv.getResource("testParamString2").getClass().toString());
			System.out.println(resProv.getResource("testParamString2").toString());
			fail();
		}
			
	}
}
