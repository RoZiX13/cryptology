package ciphers.simple;

import ciphers.simple.Cipher;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class PlayfairBigramCipher implements Cipher<String, String> {
    private char[] vectorPlayfair;
    public PlayfairBigramCipher(char[] vectorPlayfair) {
        this.vectorPlayfair = vectorPlayfair;
    }
    public PlayfairBigramCipher(String key) {
        this.vectorPlayfair = createSetForPlayfairBigramCipherFromKey(key);
    }
    public char[] getVectorPlayfair() {
        return vectorPlayfair;
    }
    public void setVectorPlayfair(char[] vectorPlayfair) {
        this.vectorPlayfair = vectorPlayfair;
    }
    public static char[] createSetForPlayfairBigramCipherFromKey(String key){
        Set<Character> characterSet = new LinkedHashSet<>();
        for (char c:
             key.toLowerCase().toCharArray()) {
            characterSet.add(c);
        }

        for (int i = 'a'; i < 'z' + 1; i++) {
            char c = (char) i;
            if(c == 'j'){
                continue;
            }
            characterSet.add(c);
        }
        Iterator<Character> characterIterator = characterSet.iterator();
        char[] chars = new char[characterSet.size()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = characterIterator.next();
        }
        return chars;
    }
    @Override
    public String decrypt(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        if(string.length() % 2 != 0){
            throw new IllegalArgumentException();
        }
        char[] chars = string.toCharArray();
        for (int i = 1; i < chars.length; i+=2) {
            int indexCharacter0 = getIndexInVectorPlayfair(chars[i-1]);
            int indexCharacter1 = getIndexInVectorPlayfair(chars[i]);
            if(indexCharacter0 / 5 == indexCharacter1 / 5){
                stringBuilder
                        .append(vectorPlayfair[((indexCharacter0 - 1) % 5 + (indexCharacter0 / 5) * 5) % 25])
                        .append(vectorPlayfair[((indexCharacter1 - 1) % 5 + (indexCharacter1 / 5) * 5) % 25]);
            }else if(indexCharacter0 % 5 == indexCharacter1 % 5){
                stringBuilder
                        .append(vectorPlayfair[(indexCharacter0 % 5 + (indexCharacter0 / 5 - 1) * 5) % 25])
                        .append(vectorPlayfair[(indexCharacter1 % 5 + (indexCharacter1 / 5 - 1) * 5) % 25]);
            }else {
                stringBuilder
                        .append(vectorPlayfair[indexCharacter0 % 5 + ((indexCharacter1) / 5) * 5])
                        .append(vectorPlayfair[indexCharacter1 % 5 + ((indexCharacter0) / 5) * 5]);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String encrypt(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        if(string.length() % 2 != 0){
            throw new IllegalArgumentException();
        }
        char[] chars = string.toLowerCase().toCharArray();
        for (int i = 1; i < chars.length; i+=2) {
            int indexCharacter0 = getIndexInVectorPlayfair(chars[i-1]);
            int indexCharacter1 = getIndexInVectorPlayfair(chars[i]);
            if(indexCharacter0 / 5 == indexCharacter1 / 5){
                stringBuilder
                        .append(vectorPlayfair[((indexCharacter0 + 1) % 5 + (indexCharacter0 / 5) * 5) % 25])
                        .append(vectorPlayfair[((indexCharacter1 + 1) % 5 + (indexCharacter1 / 5) * 5) % 25]);
            }else if(indexCharacter0 % 5 == indexCharacter1 % 5){
                stringBuilder
                        .append(vectorPlayfair[(indexCharacter0 % 5 + (indexCharacter0 / 5 + 1) * 5) % 25])
                        .append(vectorPlayfair[(indexCharacter1 % 5 + (indexCharacter1 / 5 + 1) * 5) % 25]);
            }else {
                stringBuilder
                        .append(vectorPlayfair[indexCharacter0 % 5 + ((indexCharacter1) / 5) * 5])
                        .append(vectorPlayfair[indexCharacter1 % 5 + ((indexCharacter0) / 5) * 5]);
            }
        }
        return stringBuilder.toString();
    }

    public int getIndexInVectorPlayfair(char c){
        int index = 0;
        if(c == 'j'){
            c = 'i';
        }
        while (c != vectorPlayfair[index]){
            index++;
        }
        return index;
    }
}
