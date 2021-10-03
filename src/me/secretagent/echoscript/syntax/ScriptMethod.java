package me.secretagent.echoscript.syntax;

public interface ScriptMethod {

	String getName();
	
	Object onInvoked(Object[] args);
	
}
