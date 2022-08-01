package cucumber.app.constants;

import java.io.File;

public abstract class FilePaths {

	/**
	 * This is Base path for all the page locators
	 */
	public static final String PROPERTIES_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "WebObjects" + File.separator;

	
	public static final String DATA_FILE_PATH = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "testData" + File.separator
			+ "assets" + File.separator + "SynoQA.MP4";

	
	
	
}