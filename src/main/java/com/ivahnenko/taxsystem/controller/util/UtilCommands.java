package com.ivahnenko.taxsystem.controller.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class UtilCommands {

	/**
	 * Method for validation of more than one input.
	 */
	public static List<String> getValidationExceptionList(Map<String, String> userParams, String entityName) {
		
		return userParams.entrySet().stream()
				.filter(pair -> !InputValidator.validate(pair.getKey(), pair.getValue(), entityName))
				.map(Map.Entry::getKey).collect(Collectors.toList());
	}
	
	/**
	 * Method for getting values from parsed json file.
	 */
	public static Map<String, String> getParamValuesFromJsonInput(HttpServletRequest request, List<String> entityParamNames) throws
					IOException, ServletException {
		
		Map<String, String> parsedValuesMapping = new HashMap<>();
		
			
			Gson gson = new Gson();
			JsonObject json = gson.fromJson(parseJsonInput(request), JsonObject.class);
			
			entityParamNames.forEach(param -> parsedValuesMapping.put(param, json.get(param).getAsString()));
			return parsedValuesMapping; 
	}
	
	/**
	 * Method for parsing json file.
	 */
	private static String parseJsonInput(HttpServletRequest request) throws IOException, ServletException {

		int bufferSize = 1024;
		char[] buffer = new char[bufferSize];
		StringBuilder parsedJsonInput = new StringBuilder();

		for (Part part : request.getParts()) {

			try (Reader in = new InputStreamReader(part.getInputStream(), "UTF-8")) {
				for (;;) {
					int rsz = in.read(buffer, 0, buffer.length);
					if (rsz < 0)
						break;
					parsedJsonInput.append(buffer, 0, rsz);
				}
			}
		}
		return parsedJsonInput.toString();
	}
}
