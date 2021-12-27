package com.tony966.javaspringdbfdemo.Filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class RegexFilter implements FilenameFilter {
	private Pattern pattern;

	public RegexFilter(String regex) {
		pattern = Pattern.compile(regex);
	}

	@Override
	public boolean accept(File dir, String name) {
		return pattern.matcher(name).matches();
	}
}
