package ie.atu.sw;

import static java.lang.System.out;
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Runner {
	// Main method manages the command-line menu, user input, and program flow
	public static void main(String[] args) throws Exception {
		// Print program header
		out.println("************************************************************");
		out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		out.println("*                                                          *");
		out.println("*       Encrypting Files with a Four Square Cipher         *");
		out.println("*                                                          *");
		out.println("************************************************************");

		// Boolean flag controls the main menu loop
		// When false, program exits
		boolean keepRunning = true;

		// Create a Scanner for reading user input from the console
		Scanner scanner = new Scanner(System.in);

		// Initalising variables to store user input and state
		String inputFile = null; // Local file path
		String inputURL = null; // URL input
		boolean isURL = false; // Flag to distinguish file vs URL
		String outputFile = "./out.txt"; // Default output file
		String[] keys = null; // Cipher keys

		// Main menu loop - repeatedly displays options until user quits
		while (keepRunning) {
			// Display menu options
			showOptions();

			// Read user input and parse to int
			// Using next() instead of nextLine() avoids extra newlines in Scanner
			int userChoice = Integer.parseInt(scanner.next());

			// Switch statement determines program behaviour based on user interaction
			switch (userChoice) {
				// (1) Specify Input Details
				case 1 -> {
					out.println("Please select: ");
					out.println("(1) Input a File");
					out.println("(2) Input a URL");
					out.println("(3) Back to Main Menu");
					int userInputChoice = Integer.parseInt(scanner.next());

					// Nested switch for secondary menu 
					switch (userInputChoice) {
						// (1) Input a file - user is prompted to specify which text file to input
						case 1 -> {
							out.print("Please specify the input text file: ");
							inputFile = scanner.next();
							// Confirms specified text file to user
							out.println("Input file is " + inputFile + '\n');
							isURL = false; // Input is local file
						}
						// (2) Input a URL - prompted to specify a URL
						case 2 -> {
							// Takes in user input and outputs confirmation of URL
							out.print(
									"Please specify a .txt file hosted on a URL to encrypt (e.g. https://www.gutenberg.org/cache/epub/77764/pg77764.txt): ");
							inputURL = scanner.next();
							out.println("Input URL is " + inputURL + '\n');
							// isURL becomes true so the program knows the input source is a URL rather than
							// a local file
							isURL = true;
						}
					}
				}
				// (2) Specify Output File (default: ./out.txt) - asks user to specify output
				// file
				case 2 -> {
					// Takes in user input and outputs confirmation of the output file
					out.print("Please specify the output file: ");
					outputFile = scanner.next();
					out.println("Outputting to: " + outputFile + '\n');
				}
				// (3) Enter Cipher Key - opens cipher key menu
				case 3 -> {
					// Cipher key menu selections
					out.println("Please select: ");
					out.println("(1) Input 1 Key to populate both encryption quadrants");
					out.println("(2) Input 2 Keys to populate each encryption quadrant");
					out.println("(3) Generate Random Key");
					out.println("(4) Hardcoded Key from Assignment Brief");
					out.println("(5) Back to Main Menu");
					int userKeyChoice = Integer.parseInt(scanner.next());

					// Initialises rawKey1 & 2 variables
					String rawKey1 = null;
					String rawKey2 = null;

					// Nested switch statement handles user selection of cipher key option
					switch (userKeyChoice) {
						case 1 -> {
							// User is asked to input a singular cipher key they would like to use
							out.println("Input 1 Key: ");
							String longKey = scanner.next();

							// The singular input key is split into two parts to populate both encryption
							// quadrants
							int longKeyLength = longKey.length();
							rawKey1 = longKey.substring(0, longKeyLength / 2);
							rawKey2 = longKey.substring(longKeyLength / 2, longKeyLength);

							// Confirmation of the keys
							out.println("Your keys are " + rawKey1 + " and " + rawKey2);
						}
						case 2 -> {
							// User is asked to input the 2 keys they would like to use
							out.println("Please input 2 keys.");
							out.println("Input first key: ");
							rawKey1 = scanner.next();
							out.println("Input second key: ");
							rawKey2 = scanner.next();

							// Confirmation of the keys
							out.println("Your keys are " + rawKey1 + " and " + rawKey2);
						}
						case 3 -> {
							// Random keys requested, leave rawKey1 & 2 null
							rawKey1 = null;
							rawKey2 = null;
						}
						case 4 -> {
							// Hardcoded keys from assignment brief
							rawKey1 = "ZGPTFOIHMUWDRCNYKEQAXVSBL";
							rawKey2 = "MFNBDCRHSAXYOGVITUEWLQZKP";

							// Confirmation of the keys
							out.println("Your keys are " + rawKey1 + " and " + rawKey2);
						}
						case 5 -> {
							// Breaks out of the cipher key menu and goes back to the main menu
							out.println("\nNavigating back to main menu...\n");
							break;
						}
						default -> {
							// If the user tries to input an invalid selection, an error is printed and the
							// user is sent back to the main menu
							out.print(ConsoleColour.RED_BOLD_BRIGHT);
							out.println("\n [ERROR] Invalid selection, please try again.\n");
							break;
						}
					}
					// Create FourSquareKey instance
					if (rawKey1 == null || rawKey2 == null) {
						// If null (random keys requested), generate random keys
						FourSquareKey fsk = new FourSquareKey();
						keys = fsk.getKeys();
					} else {
						// Otherwise, use user keys
						FourSquareKey fsk = new FourSquareKey(rawKey1, rawKey2);
						keys = fsk.getKeys();
					}
				}
				// (4) Encrypt - proceeds to encrypt input if possible
				case 4 -> {
					// Prints confirmation that encrypting is underway
					out.println("Encrypting..." + '\n');

					// Ensure input exists
					if (inputFile == null && inputURL == null) {
						out.print(ConsoleColour.RED_BOLD_BRIGHT);
						out.println(
								"\n[ERROR] No input file or url provided. Please provide one using option (1) in the menu.\n");
						break;
					}
					// Ensure keys exist - generate random keys if not
					if (keys == null) {
						out.print(ConsoleColour.RED_BOLD_BRIGHT);
						out.println("\n[WARNING] No key provided. Using a randomly generated key for encryption.");
						FourSquareKey fsc = new FourSquareKey();
						keys = fsc.getKeys();
					}

					// Create cipher instance
					FourSquareCipher fsc = new FourSquareCipher(keys);

					// Encrypt depending on input source
					if (!isURL) {
						// Local file encryption
						try {
							// Create a FileWriter to write output to the specified output file
							FileWriter fw = new FileWriter(new File(outputFile));

							// Create a BufferedReader to read the input text file line by line
							BufferedReader br = new BufferedReader(
									new InputStreamReader(new FileInputStream(new File(inputFile))));

							String line = null;

							// Read each line from the input file until the end is reached
							while ((line = br.readLine()) != null) {
								// Encrypt the line and write it to the output file
								fw.write(fsc.encrypt(line) + "\n");
							}

							// Close resources and ensure all data is written to the output file
							br.close();
							fw.flush();
							fw.close();

							// Handle any file input/output errors
						} catch (Exception e) {
							e.printStackTrace();
						}

						// If the input source is a URL
					} else {
						// URL encryption
						try {
							// Create a URL object using the user-specified URL
							URL connection = new URL(inputURL);

							// Create a new FileWriter and write to the specified output file
							FileWriter fw = new FileWriter(new File(outputFile));

							// Create a BufferedReader to read text content from the URL stream
							BufferedReader br = new BufferedReader(new InputStreamReader(connection.openStream()));

							String line;

							// Read each line from the URL until the end of the stream is reached
							while ((line = br.readLine()) != null) {
								// Encrypt the line and write it to the output file
								fw.write(fsc.encrypt(line) + "\n");
							}

							// Close resources and ensure all data is written to the output file
							br.close();
							fw.flush();
							fw.close();

							// Handle any URL or input/output errors
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				// (5) Decrypt - proceeds to decrypt input if possible
				case 5 -> {
					// Prints confirmation that decryption is underway
					out.println("Decrypting..." + '\n');

					// If there are no keys present, the user is warned and returned to the main
					// menu
					if (keys == null) {
						out.print(ConsoleColour.RED_BOLD_BRIGHT);
						out.println(
								"\n[WARNING] No key provided for decryption. Please provide one before decrypting.\n");
						break;
					}

					// New instance of FourSquareCipher is created
					FourSquareCipher fsc = new FourSquareCipher(keys);

					try {
						FileWriter fw = new FileWriter(new File(outputFile));
						BufferedReader br = new BufferedReader(
								new InputStreamReader(new FileInputStream(new File(inputFile))));
						String line = null;

						// Decrypts the text line by line until there are no lines left to decrypt
						while ((line = br.readLine()) != null) {
							fw.write(fsc.decrypt(line) + "\n");
						}

						// Close BufferedReader, flush and close FileWriter
						br.close();
						fw.flush();
						fw.close();

						// Error handling
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// (6) Print Key option
				case 6 -> {
					// If there are no keys, an error is printed
					if (keys == null) {
						out.print(ConsoleColour.RED_BOLD_BRIGHT);
						out.print("[ERROR] No keys. Please visit menu option (3) and try again." + '\n');
						// If there are keys, they are printed
					} else {
						out.println("Key = " + keys[0] + keys[1]);
					}
				}
				// (7) Export Key to File
				case 7 -> {
					// If there are no keys, an error is printed
					if (keys == null) {
						out.print(ConsoleColour.RED_BOLD_BRIGHT);
						out.print("[ERROR] No keys. Please visit menu option (3) and try again." + '\n');
						// Otherwise, FileWriter is used to write the keys to the file ./key.txt
					} else {
						FileWriter fw = new FileWriter(new File("./key.txt"));
						fw.write(keys[0] + keys[1]);

						// FileWriter is flushed and closed
						fw.flush();
						fw.close();
					}
				}
				// (8) Quit - this sets keepRunning to false and therefore stops showOptions()
				// from running, effectively quitting the program
				case 8 -> {
					keepRunning = false;
				}
				// If the user inputs an invalid selection, an error is printed
				default -> {
					out.print(ConsoleColour.RED_BOLD_BRIGHT);
					out.println("[ERROR] Invalid Selection");
				}
			}
		} // Program termination message and scanner closure 
		out.println("Quitting...");
		scanner.close();
	}

	// Displays the main menu
	public static void showOptions() {
		out.print(ConsoleColour.BLUE_BRIGHT);
		out.println("(1) Specify Input Details");
		out.println("(2) Specify Output File (default: ./out.txt)");
		out.println("(3) Enter Cipher Key");
		out.println("(4) Encrypt");
		out.println("(5) Decrypt");
		out.println("(6) Print Key");
		out.println("(7) Export Key to File");
		out.println("(8) Quit");
	}
}