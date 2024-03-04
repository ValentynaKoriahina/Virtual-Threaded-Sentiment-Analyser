package ie.atu.sw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The class contains basic operations methods responsible for working with
 * files and folders.
 */
public class FileAndDirectoryProcessor {
	/*
	 * Used as a monitor to ensure thread safety in class methods that perform
	 * operations that require synchronized access
	 * https://blogs.oracle.com/javamagazine/post/java-thread-synchronization-
	 * raceconditions-locks-conditions
	 */
	private final Object lock = new Object();

	/**
	 * Writes the content to a file. If the file does not exist, it will be created.
	 * 
	 * Time complexity O(n), where n - content size.
	 *
	 * @param content  Content to write to the file.
	 * @param fileName Path to the file to be written to.
	 */
	public static void writeFile(String content, String fileName) {
		try {
			File file = new File(fileName);
			File parentDirectory = file.getParentFile();

			// Create if does not exist
			if (parentDirectory != null && !parentDirectory.exists()) {
				parentDirectory.mkdirs();
			}

			FileWriter out = new FileWriter(file);
			out.write(content);

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Allows to add text content to a file. This method is synchronized to be
	 * thread safe. If the file does not exist, it will be created.
	 * 
	 * Time complexity O(n), where n - content size.
	 *
	 * @param content  The string to add to the file.
	 * @param fileName The path to the target file.
	 */
	public void appendFile(String content, String fileName) {
		synchronized (lock) {
			try {
				File file = new File(fileName);
				File parentDirectory = file.getParentFile();

				// Create if does not exist
				if (parentDirectory != null && !parentDirectory.exists()) {
					parentDirectory.mkdirs();
				}

				FileWriter out = new FileWriter(file, true); // true enables data appending mode
				out.write(content);
				out.write("\n\n");

				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks the existence of the specified path.
	 * 
	 * Time complexity O(1). Simple file system operation.
	 *
	 * @param path Path to check.
	 * @return Returns true if the path exists, false otherwise.
	 */
	public static Boolean checkPath(String path) {
		File file = new File(path);

		if (file.exists())
			return true;

		return false;
	}

	/**
	 * Returns the canonical path to the specified directory or file.
	 *
	 * Time complexity O(1). Simple file system operation.
	 * 
	 * @param path Path to the file or directory.
	 * @return Canonical path.
	 */
	public static String getCanonicalPath(String path) {
		File directory = new File(path);
		String canonical = null;
		try {
			canonical = directory.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return canonical;
	}

	/**
	 * Returns the name of a file at the specified path.
	 * 
	 * Time complexity O(1). Simple file system operation.
	 *
	 * @param path Path to the file.
	 * @return The name of the file.
	 */
	public static String getFileName(String path) {
		File directory = new File(path);
		String fileName = directory.getName();
		return fileName;
	}
}
