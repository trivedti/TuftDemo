package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import cucumber.app.constants.FilePaths;
import cucumber.app.enums.FileNames;



public class PropertyFileOperations {

	private Properties property = new Properties();
	public WebDriver driver;
	public String locator;

	public PropertyFileOperations(WebDriver driver)
	{
		this.driver = driver;
	}
	public PropertyFileOperations(FileNames filename) {
		InputStream input;
		try {
			input = new FileInputStream(new File(FilePaths.PROPERTIES_FOLDER_PATH + filename + ".properties"));
			property.load(input);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final String getKey(String key) {
		this.locator = locator;
		return property.getProperty(key);
	}
	


}
