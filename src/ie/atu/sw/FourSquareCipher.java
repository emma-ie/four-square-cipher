package ie.atu.sw;

import static java.lang.System.out;

public class FourSquareCipher {
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


    // foursquare quadrant coordinates
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

    public FourSquareCipher(String[] keys) {
        // Initialize object and foursquare using the provided keys
        generateFourSquare(keys[0], keys[1]);
    }

    private void generateFourSquare(String key1, String key2) {
        fillFourSquareSection(key1, this.topRightRowStart, this.topRightRowEnd, this.topRightColStart, this.topRightColEnd);
        fillFourSquareSection(key2, this.botLeftRowStart, this.botLeftRowEnd, this.botLeftColStart, this.botLeftColEnd);
    }

    private void fillFourSquareSection(String key, int rowStart, int rowEnd, int colStart, int colEnd) {
        int position = 0;
        for (int row = rowStart; row <= rowEnd; row++) {
            for (int col = colStart; col <= colEnd; col++) {
                fourSquare[row][col] = key.charAt(position);
                position++;
            }
        }
    }

    public void printFourSquare() {
        // used for debugging
        for (int i = 0; i < fourSquare.length; i++) {
            for (int j = 0; j < fourSquare[i].length; j++) {
                System.out.print(fourSquare[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String encrypt(String plaintext) {
        FourSquareUtils fsu = new FourSquareUtils();
        plaintext = fsu.cleanPlaintext(plaintext);
        String[] bigrams = fsu.createBigrams(plaintext);
        char char1;
        char char2;
        int[] coords1 = new int[2];
        int[] coords2 = new int[2];
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < bigrams.length; i++) {
            char1 = bigrams[i].charAt(0);
            char2 = bigrams[i].charAt(1);
            coords1 = findCoords(char1, topLeftRowStart, topLeftRowEnd, topLeftColStart, topLeftColEnd);
            coords2 = findCoords(char2, botRightRowStart, botRightRowEnd, botRightColStart, botRightColEnd);
            cipherText.append(fourSquare[coords1[0]][coords2[1]]);
            cipherText.append(fourSquare[coords2[0]][coords1[1]]);
        }
        return cipherText.toString();
    }

    public int[] findCoords(char c, int rowStart, int rowEnd, int colStart, int colEnd) {
        int[] coords = new int[2];
        for (int row = rowStart; row <= rowEnd; row++) {
            for (int col = colStart; col <= colEnd; col++) {
                if (fourSquare[row][col] == c) {
                    coords[0] = row;
                    coords[1] = col;
                    break;
                }
            }
        }
        return coords;
    }

    public String decrypt(String cipherText) {
        // decrypt file at file path provided
        FourSquareUtils fsu = new FourSquareUtils();
        String[] bigrams = fsu.createBigrams(cipherText);
        char char1;
        char char2;
        int[] coords1 = new int[2];
        int[] coords2 = new int[2];
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < bigrams.length; i++) {
            char1 = bigrams[i].charAt(0);
            char2 = bigrams[i].charAt(1);
            coords1 = findCoords(char1, topRightRowStart, topRightRowEnd, topRightColStart, topRightColEnd);
            coords2 = findCoords(char2, botLeftRowStart, botLeftRowEnd, botLeftColStart, botLeftColEnd);
            plaintext.append(fourSquare[coords1[0]][coords2[1]]);
            plaintext.append(fourSquare[coords2[0]][coords1[1]]);
        }
        return plaintext.toString();
    }
}
