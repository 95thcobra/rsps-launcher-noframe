package launcher.update;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;

import launcher.Configuration;
import launcher.core.Checksum;
import launcher.core.Client;

public class Downloader extends Observable implements Runnable {

	private static final int MAX_BUFFER_SIZE = 1024;
	public static final int DOWNLOADING = 0;
	public static final int COMPLETE = 2;
	public static final int ERROR = 4;

	private URL url;
	private int size;
	private int downloaded;
	private int status;

	public Downloader(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		size = -1;
		downloaded = 0;
		status = DOWNLOADING;
	}

	public float getProgress() {
		return ((float) downloaded / size) * 100;
	}

	private void error() {
		status = ERROR;
		stateChanged();
	}

	public void download() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		RandomAccessFile file = null;
		InputStream stream = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");
			connection.connect();

			if (connection.getResponseCode() / 100 != 2) {
				error();
			}

			int contentLength = connection.getContentLength();

			if (contentLength < 1) {
				error();
			}

			if (size == -1) {
				size = contentLength;
				stateChanged();
			}

			file = new RandomAccessFile(Configuration.CLIENT_LOCATION, "rw");
			file.seek(downloaded);

			stream = connection.getInputStream();

			int lastNum = 0;

			while (status == DOWNLOADING) {

				byte buffer[];

				if (size - downloaded > MAX_BUFFER_SIZE) {
					buffer = new byte[MAX_BUFFER_SIZE];
				} else {
					buffer = new byte[size - downloaded];
				}

				int read = stream.read(buffer);

				if (read == -1)
					break;

				int progress = (int) getProgress();

				if (progress > lastNum) {
					lastNum = progress;
				}

				file.write(buffer, 0, read);
				downloaded += read;
				stateChanged();
			}

			Checksum.writeUpdateToFileClient();

			if (status == DOWNLOADING) {
				status = COMPLETE;
				stateChanged();
				Client.runClient();
			}
		} catch (Exception e) {
			e.printStackTrace();
			error();
		} finally {
			if (file != null)
				try {
					file.close();
				} catch (Exception e) {
				}
			if (stream != null)
				try {
					stream.close();
				} catch (Exception e) {
				}
		}
	}

	private void stateChanged() {
		setChanged();
		notifyObservers();
	}
}