package ie.atu.sw;

import java.util.Random;

public class FourSquareKey {
    private String alphabetNoJ = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    private String key1;
    private String key2;

    // random key is requested by user
    public FourSquareKey() {
        this.key1 = generateRandomKey();
        this.key2 = generateRandomKey();
    }

    // user supplies key(s)
    public FourSquareKey(String userKey1, String userKey2) {
        this.key1 = prepareKey(userKey1 + alphabetNoJ);
        this.key2 = prepareKey(userKey2 + alphabetNoJ);
    }

    // Fisher-Yates Algorithm 
    // Code taken from https://www.geeksforgeeks.org/java/java-program-to-shuffle-characters-in-a-string-without-using-shuffle/
    private String generateRandomKey(){
        char[] chars = alphabetNoJ.toCharArray();
        Random random = new Random();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }

    private String prepareKey(String keyString){
        // sets key to uppercase and removes duplicate characters
        keyString = keyString.toUpperCase();
        keyString = keyString.replace("J", "I");
        StringBuilder cleanKey = new StringBuilder();
        char[] key = keyString.toCharArray();
        boolean seen;

        for (int i = 0; i < key.length; i++) {
            if (cleanKey.length() >= 25){
                break;
            }
            seen = false;

            for (int j = 0; j < cleanKey.length(); j++) {
                if (key[i] == cleanKey.charAt(j)) {
                    seen = true;
                    break;
                }
            }   

            if (seen == false) {
                cleanKey.append(key[i]);
            }
        }
        
        return cleanKey.toString();
    }

    public String[] getKeys() {
        return new String[] {key1, key2};
    }

}
