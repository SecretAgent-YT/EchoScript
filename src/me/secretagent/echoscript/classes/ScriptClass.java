package me.secretagent.echoscript.classes;

import java.util.ArrayList;

import me.secretagent.echoscript.syntax.ScriptMethod;

public interface ScriptClass {

	String getName();
	
	ArrayList<ScriptMethod> getMethods();
	
}
