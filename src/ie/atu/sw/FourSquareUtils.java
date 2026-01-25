package ie.atu.sw;

public class FourSquareUtils {
    // Cleans and standardises input text for use in the four-square cipher
    // Converts to uppercase, replaces 'J' with 'I', and removes non-alphabetic
    // characters
    public String cleanPlaintext(String plaintext) {
        // Convert all letters to uppercase
        plaintext = plaintext.toUpperCase();

        // Replace all occurrences of 'J' with 'I' to match the 5x5 cipher convention
        plaintext = plaintext.replace("J", "I");

        // Remove any character that is not A-Z (e.g. numbers, punctuation, spaces)
        // Reference:
        // https://www.geeksforgeeks.org/java/remove-all-non-alphabetical-characters-of-a-string-in-java/
        plaintext = plaintext.replaceAll("[^A-Z]", "");

        // Return the cleaned string for encryption/decryption
        return plaintext;
    }

    // Splits cleaned plaintext into bigrams (pairs of letters) for the four-square
    // cipher
    // Ensures that the text length is even by appending 'X' if needed
    public String[] createBigrams(String plaintext) {

        // If text has odd length, append 'X' to make it even
        if (plaintext.length() % 2 != 0) {
            plaintext = plaintext + "X";
        }

        // Create an array to hold all bigrams
        String[] bigrams = new String[plaintext.length() / 2];
        int counter = 0;

        // Iterate over the string two characters at a time and store each pair as a
        // bigram
        for (int i = 0; i < plaintext.length(); i += 2) {
            bigrams[counter] = "" + plaintext.charAt(i) + plaintext.charAt(i + 1);
            counter++;
        }

        // Return the array of bigrams for use in encryption/decryption
        return bigrams;
    }
}
