package me.secretagent.echoscript.syntax;

public class ScriptString {

	public static String parse(String raw) {
		return raw.replace("'", "");
	}
	
	public static boolean isString(String raw) {
		return raw.startsWith("'") && raw.endsWith("'");
	}
	
}
