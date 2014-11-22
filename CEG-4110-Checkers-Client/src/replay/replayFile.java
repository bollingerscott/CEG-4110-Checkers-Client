package replay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * helper methods to define creating and writing files to be read as replays.
 * A replay is a list of board states. This is done to make it easiest to 
 * save and load replays.
 */
public class replayFile {

	public static final String fileExtension = "CKRSREPLAY";

	public static void writeFile(String fileName, List<byte[][]> states)
			throws FileNotFoundException {
		
		File file = new File(fileName);
		PrintWriter pw = new PrintWriter(file);

		pw.println(states.size()); // very first thing in the file is how many
									// states to read in

		for (byte[][] state : states) {
			for (byte[] row : state) {
				for (byte tile : row) {
					pw.print(tile);
				}
				pw.println();
			}
		}
		pw.flush();
		pw.close();
	}

	public static List<byte[][]> readFile(File file)
			throws FileNotFoundException {
		ArrayList<byte[][]> list = new ArrayList<byte[][]>();

		Scanner scanner = new Scanner(file);
		
		int totalStates = scanner.nextInt();
		for (int k = 0; k < totalStates; k++) {

			// the board is always 8X8
			byte[][] state = new byte[8][8];
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					state[i][j] = scanner.nextByte();
				}
			}
			list.add(state);
		}
		scanner.close();
		return list;
	}
}
