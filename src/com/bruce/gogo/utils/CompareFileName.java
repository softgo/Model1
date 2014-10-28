package com.bruce.gogo.utils;

import java.io.File;
import java.util.Comparator;

public class CompareFileName implements Comparator<File> {
	public int compare(File f1, File f2) {  
		return f1.getName().compareTo(f2.getName());
	}  
}
