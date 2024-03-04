package ie.atu.sw;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * This class contains methods to process the paths to files and directories
 * using GIU
 */
public class GUIPathProcessor {

	/**
     * Creates a graphical window for selecting the path to a file or directory.
     *
     * @return Returns the absolute path to the selected file or directory,
     * 		   or null if wasn't selected by the user.
     */
	public static String openFileOrDirectory() {
		System.out.println("Opening the window...");
		System.out.println("If nothing happens, please check windows in the background.");

		JFileChooser fileChooser = new JFileChooser();
		// The parent catalog to be displayed in the dialog window
		fileChooser.setCurrentDirectory(new File("./"));

		// Sets the data selection mode
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Opens a dialog box. 0 - selected, 1 - not selected
		int result = fileChooser.showOpenDialog(null);

		if (result == 0) {
			// Gets the value of the selected path
			File selectedPath = fileChooser.getSelectedFile();
			return selectedPath.getAbsolutePath();
		} else {
			System.out.println("Path selection canceled by user");
		}
		return null;
	}

	/**
     * Creates a graphical window for selecting the path to a directory only.
     * Works similar to the openFileOrDirectory() method.
     *
     * @return Returns the absolute path to the selected directory,
     * 		   or null if wasn't selected by the user.
     */
	public static String openDirectory() {
		System.out.println("Opening the window...");
		System.out.println("If nothing happens, please check windows in the background.");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("./"));

		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = fileChooser.showOpenDialog(null);

		if (result == 0) {
			File selectedPath = fileChooser.getSelectedFile();
			return selectedPath.getAbsolutePath();
		} else {
			System.out.println("Path selection canceled by user");
		}
		return null;
	}

	/**
     * Creates a graphical window for selecting the path to the file only.
     * Works similar to the openFileOrDirectory() method.
     *
     * @return Returns the absolute path to the selected file,
     * 		   or null if wasn't selected by the user.
     */
	public static String openFile() {
		System.out.println("Opening the window...");
		System.out.println("If nothing happens, please check windows in the background.");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("./"));

		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int result = fileChooser.showOpenDialog(null);

		if (result == 0) {
			File selectedPath = fileChooser.getSelectedFile();
			return selectedPath.getAbsolutePath();
		} else {
			System.out.println("Path selection canceled by user");
		}
		return null;
	}
}
