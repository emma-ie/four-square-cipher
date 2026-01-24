package ie.atu.sw;

public class FourSquareKey {
    private String alphabetNoJ = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    private String key1;
    private String key2;

    public FourSquareKey(String userKey1, String userKey2) {
        this.key1 = prepareKey(userKey1 + alphabetNoJ);
        this.key2 = prepareKey(userKey2 + alphabetNoJ);
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

        System.out.println(cleanKey);
        return cleanKey.toString();
    }

    public String[] getKeys() {
        return new String[] {key1, key2};
    }

}
