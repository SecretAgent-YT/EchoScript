package me.secretagent.echoscript.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import me.secretagent.echoscript.classes.ClassManager;
import me.secretagent.echoscript.classes.ScriptClass;
import me.secretagent.echoscript.syntax.ScriptFunctions;
import me.secretagent.echoscript.syntax.ScriptMethod;
import me.secretagent.echoscript.syntax.ScriptString;
import me.secretagent.echoscript.syntax.ScriptVariable;
import me.secretagent.echoscript.util.FileUtil;
import me.secretagent.echoscript.util.MathUtil;

public class ScriptImportParser {
	
	private final File script;
	private final File folder;
	private final String name;
	private final ArrayList<String> lines = new ArrayList<>();
	private final ArrayList<Integer> methodlines = new ArrayList<>();
	private boolean ended = false;
	
	public ScriptImportParser(File file, String name) {
		script = file;
		folder = file.getParentFile();
		this.name = name + ".";
	}
	
	public ScriptImportParser(String file, String name) {
		script = new File(file);
		folder = script.getParentFile();
		this.name = name + ".";
	}
	
	public void run() throws FileNotFoundException {
		System.out.println("Importing " + script.getName() + "...");
		if (!FileUtil.getExtension(script.getName()).equals("es")) {
			System.out.println("Error: File is not EchoScript");
			return;
		} else if (!script.exists()) {
			System.out.println("Error: File does not exist!");
			return;
		}
		Scanner scanner = new Scanner(script);
		int i = 0;
		while (scanner.hasNext()) {
			lines.add(scanner.nextLine());
		}
		for (String line : lines) {
			onLine(line, i);
			i++;
		}
		System.out.println("Imported " + script.getName());
	}
	
	private void onLine(String s, int line) {
		if (methodlines.contains(line) || s.equals("")) return;
		if (s.startsWith("var")) {
			String[] strings = s.split(" ");
			for (ScriptClass clazz : ClassManager.classes) {
				String methodstr = s.substring(s.indexOf("=") + 1);
				methodstr = methodstr.replace(clazz.getName() + ":", "");
				methodstr = methodstr.replace(" ", "");
				for (ScriptMethod method : clazz.getMethods()) {
					if (methodstr.startsWith(method.getName())) {
						if (methodstr.startsWith(method.getName())) {
							String argsstr = methodstr.replace(method.getName(), "");
							if (argsstr.endsWith(")") && argsstr.startsWith("(")) {
								argsstr = argsstr.replace("(", "").replace(")", "");
							} else {
								System.out.println("Syntax Error: Method is not used correctly at line " + line);
								return;
							}
							Object args[] = argsstr.split(", ");
							for (int i = 0; i < args.length; ++i) {
								Object obj = args[i];
								if (obj instanceof String) {
									String string = (String) obj;
									if (ScriptVariable.vars.containsKey(string)) {
										args[i] = ScriptVariable.vars.get(string).toString();
									} else if (ScriptString.isString(string)) {
										args[i] = ScriptString.parse(string);
									} else {
										try {
											args[i] = String.valueOf(Double.parseDouble(string));
										} catch(Exception e) {
											System.out.println("Syntax Error: Method is not used correctly at line " + line);
											return;
										}
									}
								}
							}
							try {
								ScriptVariable.vars.put(name + strings[1], method.onInvoked(args));
								return;
							} catch (Exception e) {
								System.out.println("Error: Something happened at line " + line);
								return;
							}
						} 
					}
				}
			}
			try {
				String ss = s.substring(s.indexOf("'"), s.lastIndexOf("'") + 1);
				ScriptVariable.vars.put(name + strings[1], ScriptString.parse(ss));
				return;
			} catch (Exception e) {
				
			}
			String equation = s.substring(s.indexOf("=") + 1);
			ScriptVariable.vars.put(name + strings[1], MathUtil.parseEquation(equation));
			return;
		} else if (s.startsWith("func;") && s.endsWith("{")) {
			String funcname = s.replace("func;", "").replace("{", "");
			ArrayList<String> methods = new ArrayList<>();
			int i = 0;
			for (String liness : lines) {
				if (liness.equals("}") && i > line) {
					methodlines.add(i);
					ScriptFunctions.functions.put(name + funcname, methods);
					i++;
					return;
				}
				if (i > line) {
					methodlines.add(i);
					methods.add(liness);
				}
				i++;
			}
			return;
		} else if (s.startsWith("func:")) {
			String str = s.replace("func:", "");
			if (ScriptFunctions.functions.containsKey(str)) {
				ArrayList<String> methods = ScriptFunctions.functions.get(str);
				for (int i = 0; i < methods.size(); ++i) {
					onLine(methods.get(i), i);
				}
			} else {
				System.out.println("Syntax Error: Function " + str + " does not exist at line " + line);
			}
			return;
		} else if (s.startsWith("import")) {
			String name = s.split(" ")[1];
			if (!name.endsWith(".es")) name += ".es";
			System.out.println("import name: " + name);
			File importfile = new File(folder, name);
			if (!new File(folder, name).exists()) {
				System.out.println("Error: File does not exist");
				return;
			} else {
				System.out.println("File does exist");
				return;
			}
		}
		for (ScriptClass clazz : ClassManager.classes) {
			if (s.startsWith(clazz.getName())) {
				String methodstr;
				try {
					methodstr = s.split(clazz.getName() + ":")[1];
				} catch (Exception e) {
					System.out.println("Syntax Error: Use : for seperating class and method");
					return;
				}
				for (ScriptMethod method : clazz.getMethods()) {
					if (methodstr.startsWith(method.getName())) {
						String argsstr = methodstr.replace(method.getName(), "");
						if (argsstr.endsWith(")") && argsstr.startsWith("(")) {
							argsstr = argsstr.replace("(", "").replace(")", "");
						} else {
							System.out.println("Syntax Error: Method is not used correctly at line " + line);
							return;
						}
						Object args[] = argsstr.split(", ");
						for (int i = 0; i < args.length; ++i) {
							Object obj = args[i];
							if (obj instanceof String) {
								String string = (String) obj;
								if (ScriptVariable.vars.containsKey(string)) {
									args[i] = ScriptVariable.vars.get(string).toString();
								} else if (ScriptString.isString(string)) {
									args[i] = ScriptString.parse(string);
								} else {
									try {
										args[i] = String.valueOf(Double.parseDouble(string));
									} catch(Exception e) {
										System.out.println("Syntax Error: Method is not used correctly at line " + line);
										return;
									}
								}
							}
						}
						try {
							method.onInvoked(args);
							return;
						} catch (Exception e) {
							System.out.println("Error: Something happened at line " + line);
							return;
						}
					} 
				}
				System.out.println("Syntax Error: No such method defined in class " + clazz.getName() + " at line " + line);
				return;
			} 
		}
		System.out.println("Syntax Error: No such class defined at line " + line);
	}	
	
}
