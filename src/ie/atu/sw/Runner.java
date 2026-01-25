package ie.atu.sw;

import static java.lang.System.out;
import java.util.Scanner;
import java.io.*;
import java.net.*;

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
		String inputURL = null;
		boolean isURL = false;
		String outputFile = "./out.txt";
		String[] keys = null;

		while (keepRunning) {
			showOptions();

			int userChoice = Integer.parseInt(scanner.next());
			switch (userChoice) {
				case 1 -> {
					out.println("Please select: ");
					out.println("(1) Input a File");
					out.println("(2) Input a URL");
					out.println("(3) Back to Main Menu");
					int userInputChoice = Integer.parseInt(scanner.next());

					switch (userInputChoice) {
						case 1 -> {
							out.print("Please specify the text file to encrypt: ");
							inputFile = scanner.next();
							out.println("Input file is " + inputFile + '\n');
							isURL = false;
						}
						case 2 -> {
							out.print(
									"Please specify a .txt file hosted on a URL to encrypt (e.g. https://www.gutenberg.org/cache/epub/77764/pg77764.txt): ");
							inputURL = scanner.next();
							out.println("Input URL is " + inputURL + '\n');
							isURL = true;
						}
					}
				}
				case 2 -> {
					out.print("Please specify the output file: ");
					outputFile = scanner.next();
					out.println("Outputting to: " + outputFile + '\n');
				}
				case 3 -> {
					out.println("Please select: ");
					out.println("(1) Input 1 Key to fill both encryption quadrants");
					out.println("(2) Input 2 Keys to fill each encryption quadrant");
					out.println("(3) Generate Random Key");
					out.println("(4) Hardcoded Key from Assignment Brief");
					out.println("(5) Back to Main Menu");
					int userKeyChoice = Integer.parseInt(scanner.next());
					String rawKey1 = null;
					String rawKey2 = null;

					switch (userKeyChoice) {
						case 1 -> {
							out.println("Input 1 Key: ");
							String longKey = scanner.next();
							int longKeyLength = longKey.length();
							rawKey1 = longKey.substring(0, longKeyLength / 2);
							rawKey2 = longKey.substring(longKeyLength / 2, longKeyLength);
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
							rawKey1 = null;
							rawKey2 = null;
						}
						case 4 -> {
							rawKey1 = "ZGPTFOIHMUWDRCNYKEQAXVSBL";
							rawKey2 = "MFNBDCRHSAXYOGVITUEWLQZKP";
						}
						case 5 -> {
							out.println("\nNavigating back to main menu...\n");
							break;
						}
						default -> {
							out.println("\n [ERROR] Invalid selection, please try again.\n");
							break;
						}
					}
					if (rawKey1 == null || rawKey2 == null) {
						FourSquareKey fsk = new FourSquareKey();
						keys = fsk.getKeys();
					} else {
						FourSquareKey fsk = new FourSquareKey(rawKey1, rawKey2);
						keys = fsk.getKeys();
					}
				}
				case 4 -> {
					out.println("Encrypting..." + '\n');
					if (inputFile == null && inputURL == null) {
						out.println("\n[Error] No input file or url provided. Please provide one using option (1) in the menu.\n");
						break;
					}
					if (keys == null){
						out.println("\n[WARNING] No key provided. Using a randomly generated key for encryption.");
						FourSquareKey fsc = new FourSquareKey();
						keys = fsc.getKeys();
					}
					FourSquareCipher fsc = new FourSquareCipher(keys);
					if (!isURL) {
						try {
							FileWriter fw = new FileWriter(new File(outputFile));
							BufferedReader br = new BufferedReader(
									new InputStreamReader(new FileInputStream(new File(inputFile))));
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
					} else {
						try {
							URL connection = new URL(inputURL);
							FileWriter fw = new FileWriter(new File(outputFile));
							BufferedReader br = new BufferedReader(new InputStreamReader(connection.openStream()));
							String line;
							while ((line = br.readLine()) != null) {
								fw.write(fsc.encrypt(line) + "\n");
							}
							br.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				case 5 -> {
					out.println("Decrypting..." + '\n');
					if (keys == null){
						out.print(ConsoleColour.RED_BOLD_BRIGHT);
						out.println("\n[WARNING] No key provided for decryption. Please provide one before decrypting.\n");
						break;
					}
					FourSquareCipher fsc = new FourSquareCipher(keys);
					try {
						FileWriter fw = new FileWriter(new File(outputFile));
						BufferedReader br = new BufferedReader(
								new InputStreamReader(new FileInputStream(new File(inputFile))));
						String line = null;

						while ((line = br.readLine()) != null) {
							fw.write(fsc.decrypt(line) + "\n");
						}

						br.close();
						fw.flush();
						fw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				case 6 -> {
					if (keys == null) {
						out.print("No keys. Please visit menu option (3) and try again." + '\n');
					} else {
						out.println("Key = " + keys[0] + keys[1]);
					}

				}
				case 7 -> {
					if (keys == null) {
						out.print("No keys. Please visit menu option (3) and try again." + '\n');
					} else {
						FileWriter fw = new FileWriter(new File("./key.txt"));
						fw.write(keys[0] + keys[1]);
						fw.flush();
						fw.close();
					}
				}
				case 8 -> {
					keepRunning = false;
				}
				default -> out.println("[Error] Invalid Selection");
			}
		}
		out.println("Quitting...");
		scanner.close();
	}

	public static void showOptions() {
		out.print(ConsoleColour.PURPLE_BRIGHT);
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