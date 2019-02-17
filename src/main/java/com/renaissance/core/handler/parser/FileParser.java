package com.renaissance.core.handler.parser;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Uploaded file parser interface
 * 
 * @author Wilson
 */
public interface FileParser {

	/**
	 * 
	 * @param extension
	 * @return
	 */
	public boolean isSupported(String extension);

	/**
	 * 
	 * @param inputStream
	 * @return
	 */
	public List<Map<String, Object>> parse(InputStream inputStream, List<String> columnProperties);
	
}
