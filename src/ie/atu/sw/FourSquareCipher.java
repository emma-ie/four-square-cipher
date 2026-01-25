package ie.atu.sw;

public class FourSquareCipher {

    // 10x10 matrix representing the four-square cipher squares
    // Top-left and bottom-right are plaintext squares (A-Z)
    // Top-right and bottom-left will hold the encryption keys
    private char[][] fourSquare = new char[][] {
            { 'A', 'B', 'C', 'D', 'E', '#', '#', '#', '#', '#' },
            { 'F', 'G', 'H', 'I', 'K', '#', '#', '#', '#', '#' },
            { 'L', 'M', 'N', 'O', 'P', '#', '#', '#', '#', '#' },
            { 'Q', 'R', 'S', 'T', 'U', '#', '#', '#', '#', '#' },
            { 'V', 'W', 'X', 'Y', 'Z', '#', '#', '#', '#', '#' },
            { '#', '#', '#', '#', '#', 'A', 'B', 'C', 'D', 'E' },
            { '#', '#', '#', '#', '#', 'F', 'G', 'H', 'I', 'K' },
            { '#', '#', '#', '#', '#', 'L', 'M', 'N', 'O', 'P' },
            { '#', '#', '#', '#', '#', 'Q', 'R', 'S', 'T', 'U' },
            { '#', '#', '#', '#', '#', 'V', 'W', 'X', 'Y', 'Z' },
    };

    // Row and column boundaries for each quadrant
    private int topLeftRowStart = 0;
    private int topLeftRowEnd = 4;
    private int topLeftColStart = 0;
    private int topLeftColEnd = 4;

    private int topRightRowStart = 0;
    private int topRightRowEnd = 4;
    private int topRightColStart = 5;
    private int topRightColEnd = 9;

    private int botLeftRowStart = 5;
    private int botLeftRowEnd = 9;
    private int botLeftColStart = 0;
    private int botLeftColEnd = 4;

    private int botRightRowStart = 5;
    private int botRightRowEnd = 9;
    private int botRightColStart = 5;
    private int botRightColEnd = 9;

    // Constructor takes two keys (for top-right and bottom-left quadrants)
    public FourSquareCipher(String[] keys) {
        generateFourSquare(keys[0], keys[1]);
    }

    // Populate the top-right and bottom-left quadrants using the provided keys
    private void generateFourSquare(String key1, String key2) {
        fillFourSquareSection(key1, this.topRightRowStart, this.topRightRowEnd, this.topRightColStart,
                this.topRightColEnd);
        fillFourSquareSection(key2, this.botLeftRowStart, this.botLeftRowEnd, this.botLeftColStart, this.botLeftColEnd);
    }

    // Fill a specified quadrant of the four-square with letters from a key
    private void fillFourSquareSection(String key, int rowStart, int rowEnd, int colStart, int colEnd) {
        int position = 0;
        // Nested loops iterate over rows and columns of the quadrant
        for (int row = rowStart; row <= rowEnd; row++) {
            for (int col = colStart; col <= colEnd; col++) {
                // Assign character from key at current position
                fourSquare[row][col] = key.charAt(position);
                position++;
            }
        }
    }

    // Print the entire four-square matrix (used for debugging)
    private void printFourSquare() {
        for (int i = 0; i < fourSquare.length; i++) {
            for (int j = 0; j < fourSquare[i].length; j++) {
                System.out.print(fourSquare[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Encrypts plaintext using the four-square cipher
    public String encrypt(String plaintext) {
        FourSquareUtils fsu = new FourSquareUtils();

        // Clean and prepare the plaintext (convert to uppercase, remove non-letters,
        // split into bigrams)
        plaintext = fsu.cleanPlaintext(plaintext);
        String[] bigrams = fsu.createBigrams(plaintext);

        // Create a StringBuilder for efficient string concatenation inside loops
        StringBuilder cipherText = new StringBuilder();
        int[] coords1 = new int[2];
        int[] coords2 = new int[2];

        // Loop through each bigram
        for (int i = 0; i < bigrams.length; i++) {
            char char1 = bigrams[i].charAt(0); // First letter
            char char2 = bigrams[i].charAt(1); // Second letter

            // Find coordinates of first letter in top-left plaintext quadrant
            coords1 = findCoords(char1, topLeftRowStart, topLeftRowEnd, topLeftColStart, topLeftColEnd);
            // Find coordinates of second letter in bottom-right plaintext quadrant
            coords2 = findCoords(char2, botRightRowStart, botRightRowEnd, botRightColStart, botRightColEnd);

            // Combine row from first char with column from second char (four-square
            // encryption)
            cipherText.append(fourSquare[coords1[0]][coords2[1]]);
            cipherText.append(fourSquare[coords2[0]][coords1[1]]);
        }

        // Return encrypted text as a String
        return cipherText.toString();
    }

    // Find the row and column of a character within a specified quadrant of the
    // four-square
    private int[] findCoords(char c, int rowStart, int rowEnd, int colStart, int colEnd) {
        int[] coords = new int[2];
        // Nested loops searches each cell in the quadrant
        for (int row = rowStart; row <= rowEnd; row++) {
            for (int col = colStart; col <= colEnd; col++) {
                if (fourSquare[row][col] == c) {
                    coords[0] = row;
                    coords[1] = col;
                    break; // Stop searching once found
                }
            }
        }

        return coords;
    }

    // Decrypt a cipherText using the four-square cipher
    public String decrypt(String cipherText) {
        FourSquareUtils fsu = new FourSquareUtils();

        // Split ciphertext into bigrams
        String[] bigrams = fsu.createBigrams(cipherText);

        // Use StringBuilder for efficient string concatenation inside loops
        StringBuilder plaintext = new StringBuilder();
        int[] coords1 = new int[2];
        int[] coords2 = new int[2];

        // Loop through each bigram
        for (int i = 0; i < bigrams.length; i++) {
            char char1 = bigrams[i].charAt(0);
            char char2 = bigrams[i].charAt(1);

            // Find coordinates of first letter in top-right encryption quadrant
            coords1 = findCoords(char1, topRightRowStart, topRightRowEnd, topRightColStart, topRightColEnd);
            // Find coordinates of second letter in bottom-left encryption quadrant
            coords2 = findCoords(char2, botLeftRowStart, botLeftRowEnd, botLeftColStart, botLeftColEnd);

            // Combine row from first char with column from second char (four-square
            // decryption)
            plaintext.append(fourSquare[coords1[0]][coords2[1]]);
            plaintext.append(fourSquare[coords2[0]][coords1[1]]);
        }

        // Return decrypted text as a String
        return plaintext.toString();
    }
}
