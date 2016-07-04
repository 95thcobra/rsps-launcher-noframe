package launcher;

import java.io.File;

import launcher.core.Checksum;
import launcher.core.Client;
import launcher.update.ClientDownloader;

public class Launcher {
	public static void main(String[] args) {
		File client = new File(Configuration.CLIENT_LOCATION);
		if (!client.exists() || !Checksum.isClientUpToDate()) {
			new ClientDownloader().update();
			return;
		}
		Client.runClient();
	}
}
