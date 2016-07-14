package launcher.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import launcher.Configuration;

public class Checksum {

	public static String getLocalClientVersion() {
		String version = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(Configuration.LOCAL_CLIENT_VERSION_PATH));
			version = br.readLine();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("local client version: " + version);
		return version;
	}

	public static String getRemoteClientVersion() {
		String version = "";
		try {
			URL url = new URL(Configuration.CLIENT_VERSION_URL);
			URLConnection uc = url.openConnection();
			uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			//Scanner s = new Scanner(url.openStream());
			version = in.readLine();//s.nextLine();
			//s.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("remote client version: " + version);
		return version;
	}

	public static void writeCurrentClientVersion() {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(Configuration.LOCAL_CLIENT_VERSION_PATH));
			System.out.println("write client: " + getRemoteClientVersion());
			writer.write(getRemoteClientVersion());
			writer.close();
		} catch (Exception e) {
			System.out.println("failed writing client version");
		}
	}

	public static void writeUpdateToFileClient() {
		File file = new File(Configuration.LOCAL_CLIENT_VERSION_PATH);
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (Exception e) {
			}
		}
		writeCurrentClientVersion();
	}

	public static boolean handleTextClientUpdate() {
		File file = new File(Configuration.LOCAL_CLIENT_VERSION_PATH);
		if (!file.exists()) {
			return false;
		} else if (!getLocalClientVersion().equals(getRemoteClientVersion())) {
			return false;
		}
		return true;
	}

	public static boolean isClientUpToDate() {
		return handleTextClientUpdate();
	}
}
