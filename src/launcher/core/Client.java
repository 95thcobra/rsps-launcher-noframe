package launcher.core;

import launcher.Configuration;

public class Client {
	public static void runClient() {
		openClient();
	}

	private static void openClient() {
		try {
			Runtime.getRuntime().exec(new String[] { "java", "-jar", Configuration.CLIENT_LOCATION });
		} catch (Exception e) {
			System.out.println("Client failed to open.");
		}
	}
}
