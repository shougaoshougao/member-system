/**
 * 
 */
package com.renaissance.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renaissance.core.handler.parser.FileParser;
import com.renaissance.core.CoreConst;
import com.renaissance.core.utils.BeanUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wilson
 *
 */
public class FileParseHandler {

    private static final Logger logger = LoggerFactory.getLogger(FileParseHandler.class);

    @Autowired
	private List<FileParser> fileParsers;

	public <T> List<T> parse(InputStream inputStream, String fileName, Class<T> targetClass){
	    List<T> resultList = new ArrayList<>();
	    // read all fields of target class as a string list
		List<String> columnProperties = BeanUtils.getAllPropertyNames(targetClass);
		// find suitable parser
		FileParser fileParser = findSuitableParser(FilenameUtils.getExtension(fileName));
		// parse file to map list
		List<Map<String, Object>> lines = fileParser.parse(inputStream, columnProperties);
		// convert map list to object list
		ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
        for(Map<String, Object> line : lines) {
			T t = mapper.convertValue(line, targetClass);
			resultList.add(t);
		}
		return resultList;
	}

	/**
	 *
	 * @param extension
	 * @return
	 */
	private FileParser findSuitableParser(String extension) {

		for (FileParser fileParser : this.fileParsers) {
			if (fileParser.isSupported(extension)) {
				return fileParser;
			}
		}

		throw CoreConst.fileNotMatchException();
	}
}
