package util;

import java.io.*;
import java.util.Properties;

public class PropertyFileOperation {
	
	private Properties prop;
	
	public PropertyFileOperation(String filePath) throws IOException{
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		prop = new Properties();
		prop.load(inputStream);
	}	
	
	public String propReadValue(String key) {
		String value = prop.getProperty(key);
		if(value == null)
			throw new NullPointerException("No such property available in property file");
		else
			return value;
	}
}