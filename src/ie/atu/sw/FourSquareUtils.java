package ie.atu.sw;

public class FourSquareUtils {
    public String cleanPlaintext(String plaintext) {
        plaintext = plaintext.toUpperCase();
        plaintext = plaintext.replace("J", "I");
        // replace everything that is not A-Z. Reference:
        // https://www.geeksforgeeks.org/java/remove-all-non-alphabetical-characters-of-a-string-in-java/
        plaintext = plaintext.replaceAll("[^A-Z]", "");
        return plaintext;
    }

    public String[] createBigrams(String plaintext) {

        if (plaintext.length() % 2 != 0) {
            plaintext = plaintext + "X";
        }

        String[] bigrams = new String[plaintext.length() / 2];
        int counter = 0;
        for (int i = 0; i < plaintext.length(); i += 2) {
            bigrams[counter] = "" + plaintext.charAt(i) + plaintext.charAt(i + 1);
            counter++;
        }
        
        return bigrams;
    }

}
