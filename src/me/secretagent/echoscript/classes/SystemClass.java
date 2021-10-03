package me.secretagent.echoscript.classes;

import java.util.ArrayList;
import java.util.Calendar;

import me.secretagent.echoscript.syntax.ScriptMethod;
import me.secretagent.echoscript.syntax.ScriptString;

public class SystemClass implements ScriptClass {

	@Override
	public String getName() {
		return "system";
	}
	
	@Override
	public ArrayList<ScriptMethod> getMethods() {
		ArrayList<ScriptMethod> list = new ArrayList<>();
		list.add(new PrintMethod());
		list.add(new ExitMethod());
		list.add(new TimeMethod());
		return list;
	}

	private class PrintMethod implements ScriptMethod {

		@Override
		public String getName() {
			return "print";
		}

		@Override
		public Object onInvoked(Object[] args) {
			System.out.println(args[0]);
			return null;
		}
		
	}
	
	private class ExitMethod implements ScriptMethod {

		@Override
		public String getName() {
			return "exit";
		}

		@Override
		public Object onInvoked(Object args[]) {
			System.exit(Integer.parseInt((String) args[0]));
			return null;
		}
		
	}
	
	private class TimeMethod implements ScriptMethod {

		@Override
		public String getName() {
			return "time";
		}

		@Override
		public Object onInvoked(Object[] args) {
			return Calendar.getInstance().getTime();
		}
		
	}
	
}
