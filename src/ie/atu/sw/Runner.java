package ie.atu.sw;

import static java.lang.System.out;
import java.util.Scanner;
import java.io.*;

public class Runner {
	public static void main(String[] args) throws Exception {
		out.println("************************************************************");
		out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		out.println("*                                                          *");
		out.println("*       Encrypting Files with a Four Square Cipher         *");
		out.println("*                                                          *");
		out.println("************************************************************");

		boolean keepRunning = true;
		Scanner scanner = new Scanner(System.in);
		String inputFile = null;
		String outputFile = "./out.txt";
		// FileWriter out = new FileWriter(fileName);
		String[] keys = null;

		while (keepRunning) {
			showOptions();

			int userChoice = Integer.parseInt(scanner.next());
			switch (userChoice) {
				case 1 -> {
					out.print("Please specify the text file to encrypt: ");
					inputFile = scanner.next();
					out.println("Input file is " + inputFile + '\n');
				}
				case 2 -> {
					out.print("Please specify the output file: ");
					outputFile = scanner.next();
					out.println("Outputting to: " + outputFile + '\n');
				}
				case 3 -> {
					out.println("Please select: ");
					out.println("(1) Input 1 Key");
					out.println("(2) Input 2 Keys");
					out.println("(3) Generate Random Key");
					int userKeyChoice = Integer.parseInt(scanner.next());
					String rawKey1;
					String rawKey2;
					
					switch(userKeyChoice) {
						case 1 -> {
							out.println("Input 1 Key: ");
							String longKey = scanner.next();
							int longKeyLength = longKey.length();
							rawKey1 = longKey.substring(0, longKeyLength/2);
							rawKey2 = longKey.substring(longKeyLength/2, longKeyLength);
						}
						case 2 -> {
							out.println("Please input 2 keys.");
							out.println("Input first key: ");
							rawKey1 = scanner.next();
							out.println("Input second key: ");
							rawKey2 = scanner.next();
							out.println("Your keys are " + rawKey1 + " and " + rawKey2);
						}
						case 3 -> {
							rawKey1 = "ZGPTFOIHMUWDRCNYKEQAXVSBL";
							rawKey2 = "MFNBDCRHSAXYOGVITUEWLQZKP";
						}
						default -> {
							rawKey1 = "ZGPTFOIHMUWDRCNYKEQAXVSBL";
							rawKey2 = "MFNBDCRHSAXYOGVITUEWLQZKP";
						}
					}
					FourSquareKey fsk = new FourSquareKey(rawKey1, rawKey2);
					keys = fsk.getKeys();
				}
				case 4 -> {
					out.println("Encrypting..." + '\n');
					FourSquareCipher fsc = new FourSquareCipher(keys);
					try {
						FileWriter fw = new FileWriter(new File(outputFile));
						BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputFile))));
						String line = null;

						while ((line = br.readLine()) != null) {
							fw.write(fsc.encrypt(line) + "\n");
						}

						br.close();
						fw.flush();
						fw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				case 5 -> {
					out.println("Decrypting..." + '\n');
				}
				case 6 -> {
					keepRunning = false;
				}
				default -> out.println("[Error] Invalid Selection");
			}
		}
		out.println("Quitting...");
		scanner.close();
	}

	public static void showOptions() {
		out.println("(1) Specify Text File to Encrypt");
		out.println("(2) Specify Output File (default: ./out.txt)");
		out.println("(3) Enter Cipher Key");
		out.println("(4) Encrypt Text File");
		out.println("(5) Decrypt Text File");
		out.println("(?) Optional Extras...");
		out.println("(6) Quit");
	}
}

// // Output a menu of options and solicit text from the user
// out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
// out.print("Select Option [1-?]>");
// out.println();

// // You may want to include a progress meter in you assignment!
// out.print(ConsoleColour.YELLOW); // Change the colour of the console
// text
// int size = 100; // The size of the meter. 100 equates to 100%
// for (int i = 0; i < size; i++) { // The loop equates to a sequence of
// processing steps
// printProgress(i + 1, size); // After each (some) steps, update the progress
// meter
// Thread.sleep(10); // Slows things down so the animation is visible
// }

// }

/*
 * Terminal Progress Meter
 * -----------------------
 * You might find the progress meter below useful. The progress effect
 * works best if you call this method from inside a loop and do not call
 * out.println(....) until the progress meter is finished.
 * 
 * Please note the following carefully:
 * 
 * 1) The progress meter will NOT work in the Eclipse console, but will
 * work on Windows (DOS), Mac and Linux terminals.
 * 
 * 2) The meter works by using the line feed character "\r" to return to
 * the start of the current line and writes out the updated progress
 * over the existing information. If you output any text between
 * calling this method, i.e. out.println(....), then the next
 * call to the progress meter will output the status to the next line.
 * 
 * 3) If the variable size is greater than the terminal width, a new line
 * escape character "\n" will be automatically added and the meter won't
 * work properly.
 * 
 * 
 */
// public static void printProgress(int index, int total) {
// if (index > total)
// return; // Out of range
// int size = 50; // Must be less than console width
// char done = '█'; // Change to whatever you like.
// char todo = '░'; // Change to whatever you like.

// // Compute basic metrics for the meter
// int complete = (100 * index) / total;
// int completeLen = size * complete / 100;

// /*
// * A StringBuilder should be used for string concatenation inside a
// * loop. However, as the number of loop iterations is small, using
// * the "+" operator may be more efficient as the instructions can
// * be optimized by the compiler. Either way, the performance overhead
// * will be marginal.
// */
// StringBuilder sb = new StringBuilder();
// sb.append("[");
// for (int i = 0; i < size; i++) {
// sb.append((i < completeLen) ? done : todo);
// }

// /*
// * The line feed escape character "\r" returns the cursor to the
// * start of the current line. Calling print(...) overwrites the
// * existing line and creates the illusion of an animation.
// */
// out.print("\r" + sb + "] " + complete + "%");

// // Once the meter reaches its max, move to a new line.
// if (done == total)
// out.println("\n");
