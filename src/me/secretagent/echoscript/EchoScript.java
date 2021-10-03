package me.secretagent.echoscript;

import me.secretagent.echoscript.classes.ClassManager;
import me.secretagent.echoscript.classes.SystemClass;
import me.secretagent.echoscript.parser.ScriptParser;

public class EchoScript {
	
	private static final EchoScript es = new EchoScript();
	
	public static EchoScript getScript() {
		return es;
	}
	
	public static void main(String args[]) throws Exception {
		getScript().start(args);
	}
	
	public void start(String args[]) throws Exception {
		String path = args[0];
		path = path.replace("\\", "/").replace("//", "/");
		ClassManager.register(new SystemClass());
		ScriptParser parser = new ScriptParser(args[0]);
		parser.run();
	}
	
}
