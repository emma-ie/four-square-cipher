package ie.atu.sw;

import static java.lang.System.out;

public class FourSquareCipher {
    private char[][] fourSquare = new char[][] {
                                                {'A', 'B', 'C', 'D', 'E', '#', '#', '#', '#', '#'},
                                                {'F', 'G', 'H', 'I', 'K', '#', '#', '#', '#', '#'},
                                                {'L', 'M', 'N', 'O', 'P', '#', '#', '#', '#', '#'},
                                                {'Q', 'R', 'S', 'T', 'U', '#', '#', '#', '#', '#'},
                                                {'V', 'W', 'X', 'Y', 'Z', '#', '#', '#', '#', '#'},
                                                {'#', '#', '#', '#', '#', 'A', 'B', 'C', 'D', 'E'},
                                                {'#', '#', '#', '#', '#', 'F', 'G', 'H', 'I', 'K'},
                                                {'#', '#', '#', '#', '#', 'L', 'M', 'N', 'O', 'P'},
                                                {'#', '#', '#', '#', '#', 'Q', 'R', 'S', 'T', 'U'},
                                                {'#', '#', '#', '#', '#', 'V', 'W', 'X', 'Y', 'Z'},                                            
    };

    private String key;
    private char[] alphabet ="ABCDEFGHIKLMNOPQRSTUVWXYZ".toCharArray();
    int match = 0;
    
    public FourSquareCipher(String key1, String key2) {
        // Initialize object and foursquare using the provided keys
        out.println("Received key: " + key1 + key2);
        String preparedKey1 = prepareKey(key1);
        String preparedKey2 = prepareKey(key2);
        generateFourSquare(preparedKey1, preparedKey2);
    }
    
    private String prepareKey(String key){
        char[] keyArray = key.toCharArray();
        out.print(keyArray);
        return key;
        // for (int i = 0; i <= alphabet.length; i++) {
        //     for (int j = 0; i <= keyArray.length; j++) {
        //         if (alphabet[i] == keyArray[j]) {
        //             match++;
        //             out.println("Matchy matchy" + match);
        //             break;
        //         }
        
    }

    private void generateFourSquare(String key1, String key2){

    }

    public void encrypt(String filePath){
    // encrypt file at file path provided
    }

    public void decrypt(String filePath){
    // decrypt file at file path provided
    }
}
