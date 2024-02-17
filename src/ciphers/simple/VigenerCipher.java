package ciphers.simple;

import ciphers.simple.Cipher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VigenerCipher implements Cipher<String, String> {

    private static final char[] vectorVigener = createVectorVigener();
    private static final Map<Character, List<Character>> mapFromVigenerMatrix = new HashMap<>();
    private char[] key;
    static{
        List<Character> characterList;

        for(char key:
            vectorVigener){
            characterList = new ArrayList<>();
            for (int i = 0; i < vectorVigener.length; i++) {
                characterList.add(vectorVigener[(key + i - 'a') % vectorVigener.length]);
            }
            mapFromVigenerMatrix.put(key, characterList);
        }
    }
    public VigenerCipher(String key) {
        this.key = key.toLowerCase().toCharArray();
    }
    public VigenerCipher(char[] key) {
        this.key = key;
    }

    public char[] getKey() {
        return key;
    }

    public void setKey(char[] key) {
        this.key = key;
    }
    @Override
    public String encrypt(String string) {
        string = string.toLowerCase();
        StringBuilder encryptedString = new StringBuilder();
        int counter = 0;
        for(char c:
                string.toCharArray()){

            encryptedString.append(mapFromVigenerMatrix.get(key[counter]).get(getIndexCharacterInVectorVigener(c)));
            counter++;
            counter %= key.length;
        }
        return encryptedString.toString();
    }
    @Override
    public String decrypt(String string) {
        string = string.toLowerCase();
        StringBuilder decryptedString = new StringBuilder();
        int counter = 0;
        for(char c:
                string.toCharArray()){

            decryptedString.append((char)
                    (mapFromVigenerMatrix.get(key[counter]).indexOf(c) + 'a')
            );
            counter++;
            counter %= key.length;
        }
        return decryptedString.toString();
    }

    private static char[] createVectorVigener(){
        char[] vector = new char['z' - 'a' + 1];
        int counter = 0;
        for (int i = 'a'; i < 'z' + 1; i++) {
//            if(i == 'j' || i == 'v' || i == 'w') continue;
            vector[counter++] = (char) i;
        }
//        vector[vector.length - 1] = 'w';
        return vector;
    }

    public static int getIndexCharacterInVectorVigener(char c){
        int index = 0;
        while (vectorVigener[index] != c){
            index++;
        }
        return index;
    }
}
