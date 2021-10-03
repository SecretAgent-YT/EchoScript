package me.secretagent.echoscript.classes;

import java.util.ArrayList;

import me.secretagent.echoscript.syntax.ScriptMethod;
import me.secretagent.echoscript.syntax.ScriptString;
import me.secretagent.echoscript.syntax.ScriptVariable;

public class ClassManager {

	public static ArrayList<ScriptClass> classes = new ArrayList<ScriptClass>();
	
	public static void register(ScriptClass clazz) {
		classes.add(clazz);
	}
	
	public static void unregister(ScriptClass clazz) {
		classes.remove(clazz);
	}
	
}
