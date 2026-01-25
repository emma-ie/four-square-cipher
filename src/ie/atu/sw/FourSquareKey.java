package ie.atu.sw;

import java.util.Random;

public class FourSquareKey {
    // The alphabet used for keys (I and J are combined)
    private String alphabetNoJ = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    private String key1; // Top-right quadrant key
    private String key2; // Bottom-left quadrant key

    // Constructor for randomly generated keys
    public FourSquareKey() {
        // Generates two random keys using Fisher-Yates shuffle
        this.key1 = generateRandomKey();
        this.key2 = generateRandomKey();
    }

    // Constructor when the user supplies one or two keys
    public FourSquareKey(String userKey1, String userKey2) {
        // Clean and prepare user-provided keys (remove duplicates, capitalise, fill
        // missing letters)
        this.key1 = prepareKey(userKey1 + alphabetNoJ); // Append alphabet to ensure all letters present
        this.key2 = prepareKey(userKey2 + alphabetNoJ);
    }

    // Fisher-Yates Algorithm
    // Code taken from:
    // https://www.geeksforgeeks.org/java/java-program-to-shuffle-characters-in-a-string-without-using-shuffle/
    private String generateRandomKey() {
        char[] chars = alphabetNoJ.toCharArray();
        Random random = new Random();

        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        // Return shuffled array as a string
        return new String(chars);
    }

    // Prepares a key provided by the user
    private String prepareKey(String keyString) {
        // Convert to uppercase and replace J with I
        keyString = keyString.toUpperCase();
        keyString = keyString.replace("J", "I");

        StringBuilder cleanKey = new StringBuilder();
        char[] key = keyString.toCharArray(); // Convert string to char array for processing
        boolean seen;

        // Loop through each character in the key string
        for (int i = 0; i < key.length; i++) {
            // Stop once the key has 25 unique letters
            if (cleanKey.length() >= 25) {
                break;
            }
            seen = false; // Reset flag for each character

            // Check if the character has already been added to the clean key
            for (int j = 0; j < cleanKey.length(); j++) {
                if (key[i] == cleanKey.charAt(j)) {
                    seen = true; // Character already exists, so break
                    break;
                }
            }

            // If character has not been seen before, append it to the clean key
            if (seen == false) {
                cleanKey.append(key[i]);
            }
        }

        // Return the cleaned, unique, uppercase key as a string
        return cleanKey.toString();
    }

    // Returns both keys as an array for use by the FourSquareCipher class
    public String[] getKeys() {
        return new String[] { key1, key2 };
    }
}
