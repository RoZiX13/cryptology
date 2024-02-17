package ciphers.simple;

import ciphers.simple.Cipher;

import java.util.Arrays;

public class CipherOfTheTabularRoutePermutation implements Cipher<String, String> {

    private int lengthOfTheTabularRoutePermutation;
    public CipherOfTheTabularRoutePermutation(int length) {
        this.lengthOfTheTabularRoutePermutation = length;
    }

    public int getLengthOfTheTabularRoutePermutation() {
        return lengthOfTheTabularRoutePermutation;
    }

    public void setLengthOfTheTabularRoutePermutation(int lengthOfTheTabularRoutePermutation) {
        this.lengthOfTheTabularRoutePermutation = lengthOfTheTabularRoutePermutation;
    }

    @Override
    public String decrypt(String string) {
        char[] charsArray = string.toLowerCase().toCharArray();

        StringBuilder stringBuilder = new StringBuilder();
        int width =  string.length() / lengthOfTheTabularRoutePermutation;
        for (int i = 0; i < charsArray.length * width; i += width) {
            stringBuilder.append(charsArray[i % charsArray.length + i / charsArray.length]);
        }
        return stringBuilder.toString();
    }

    @Override
    public String encrypt(String string) {
        char[] charsArray = Arrays.copyOf(string.toLowerCase().toCharArray(),
                string.length() + (lengthOfTheTabularRoutePermutation - string.length() % lengthOfTheTabularRoutePermutation));
        for (int i = string.length(); i < charsArray.length; i++) {
            charsArray[i] = '-';
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charsArray.length * lengthOfTheTabularRoutePermutation ; i += lengthOfTheTabularRoutePermutation) {
            stringBuilder.append(charsArray[i % charsArray.length + i / charsArray.length]);
        }
        return stringBuilder.toString();
    }
}
