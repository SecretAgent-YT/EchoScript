package me.secretagent.echoscript.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import me.secretagent.echoscript.classes.ClassManager;

public class FileUtil {
	
	public static String getExtension(String fileName) {
		int i = fileName.lastIndexOf(".");
		return fileName.substring(i + 1);
	}
	
}
