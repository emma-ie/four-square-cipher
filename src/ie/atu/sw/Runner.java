package ie.atu.sw;

import java.util.Scanner;
//import java.io.*;

public class Runner {
	public static void main(String[] args) throws Exception {
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*       Encrypting Files with a Four Square Cipher         *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		
		Scanner scanner = new Scanner(System.in);
		String inputFile = null;
		String outputFile = "./out.txt";
		// FileWriter out = new FileWriter(fileName);
		int userChoice;
		String userCipher; 

		do {
			// System.out.println(ConsoleColour.WHITE);
			System.out.println("(1) Specify Text File to Encrypt");
			System.out.println("(2) Specify Output File (default: ./out.txt)");
			System.out.println("(3) Enter Cipher Key");
			System.out.println("(4) Encrypt Text File");
			System.out.println("(5) Decrypt Text File");
			System.out.println("(?) Optional Extras...");
			System.out.println("(6) Quit");
			userChoice = scanner.nextInt();

			switch (userChoice) {
				case 1 -> {
					System.out.print("Please specify the text file to encrypt: ");
					inputFile = scanner.next();
					System.out.println("Input file is " + inputFile + '\n');
					break;
				}
				case 2 -> {
					System.out.print("Please specify the output file: ");
					outputFile = scanner.next();
					System.out.println("Outputting to: " + outputFile + '\n');
					break;
				}
				case 3 -> {
					System.out.print("Enter cipher keyword or leave blank to randomly generate: ");
					scanner.nextLine();
					userCipher = scanner.nextLine();
					if (userCipher.trim().isEmpty()) {
						System.out.print("Generating random cipher" + '\n');
					}
					else {
						System.out.print("Cipher key inputted: " + userCipher + '\n');
					}
					System.out.print('\n');
				}
				case 4 -> {
					System.out.println("Encrypting..." + '\n');

				}
				case 5 -> {
					System.out.println("Surprise !" + '\n');
				}
				case 6 -> {
					System.out.println("Quitting...");
					break;
				}
			}
		} while (userChoice != 6);
		scanner.close();
	}

	// // Output a menu of options and solicit text from the user
	// System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
	// System.out.print("Select Option [1-?]>");
	// System.out.println();

	// // You may want to include a progress meter in you assignment!
	// System.out.print(ConsoleColour.YELLOW); // Change the colour of the console
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
	 * System.out.println(....) until the progress meter is finished.
	 * 
	 * Please note the following carefully:
	 * 
	 * 1) The progress meter will NOT work in the Eclipse console, but will
	 * work on Windows (DOS), Mac and Linux terminals.
	 * 
	 * 2) The meter works by using the line feed character "\r" to return to
	 * the start of the current line and writes out the updated progress
	 * over the existing information. If you output any text between
	 * calling this method, i.e. System.out.println(....), then the next
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
	// System.out.print("\r" + sb + "] " + complete + "%");

	// // Once the meter reaches its max, move to a new line.
	// if (done == total)
	// System.out.println("\n");
}