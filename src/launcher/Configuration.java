package launcher;

import java.io.File;

public class Configuration {
	/**
	 * Update paths
	 */
	public static final String CLIENT_VERSION_URL = "http://edgeville.org/client/clientversion.txt";
	public static final String LOCAL_CLIENT_VERSION_PATH = System.getProperty("user.home") + File.separator + "clientversion.txt";
	
	/**
	 * Client
	 */
	public static final String CLIENT_URL = "http://edgeville.org/client/EdgevilleClient.jar";
	public static final String CLIENT_SAVE_NAME = ".edgeville.jar";
	public static final String CLIENT_LOCATION = System.getProperty("user.home") + File.separator + CLIENT_SAVE_NAME;
}
