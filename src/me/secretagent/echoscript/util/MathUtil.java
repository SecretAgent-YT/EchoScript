package me.secretagent.echoscript.util;

import org.mariuszgromada.math.mxparser.Expression;

import me.secretagent.echoscript.syntax.ScriptVariable;

public class MathUtil {
	
	public static Double parseEquation(String equation) {
		String string = equation;
		for (String str : ScriptVariable.vars.keySet()) {
			if (string.contains(str)) {
				string = string.replace(str, ScriptVariable.vars.get(str).toString());
			}
		}
		Expression solver = new Expression(string);
		return solver.calculate();
	}

}
