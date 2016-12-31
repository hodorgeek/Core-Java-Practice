package com.hodor.corejava.jdbc.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author sham
 * 
 */
public class PropertyLoaderUtils {

	private static final Logger LOGGER = Logger.getLogger(PropertyLoaderUtils.class.getName());

	/**
	 * readFromFile() is used to read the data of the given file and return it
	 * 
	 * @param filePath
	 *            the path of the file to be read
	 * @return the data in the string format read from the given file.
	 * @throws FileNotFoundException
	 */
	public static String readFromFile(final String filePath) throws FileNotFoundException {
		StringBuilder data = new StringBuilder();
		final Scanner scanner = new Scanner(filePath);
		while (scanner.hasNext()) {
			data = data.append(scanner.nextLine());
		}
		scanner.close();
		LOGGER.log(Level.INFO, "The data has been read from the file sucessfully");
		return data.toString().trim();
	}

	/**
	 * readPropertyFromFile() is used to get the Properties object
	 * 
	 * @param configFile
	 *            the configuration file name to be load
	 * @return the Properties object
	 * @throws IOException
	 *             if file not found and property does not loaded successfully
	 */
	public static Properties readPropertyFromFile(final String configFile) throws IOException {
		final Properties properties = new Properties();
		final InputStream inputStream = new FileInputStream(configFile);
		properties.load(inputStream);
		LOGGER.log(Level.INFO, "The property has been locaded sucessfully");
		return properties;
	}

}