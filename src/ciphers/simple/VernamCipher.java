package ciphers.simple;

import ciphers.simple.Cipher;

public class VernamCipher implements Cipher<String, String> {
    private String key;

    public VernamCipher(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String decrypt(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = string.toLowerCase().toCharArray();
        char[] key = this.key.toLowerCase().toCharArray();
        for(int i = 0; i < chars.length; i++){
            stringBuilder.append((char) (
                    ((chars[i % chars.length] - 'a') ^ (key[i % key.length]) - 'a') + 'a')
            );
        }
        return stringBuilder.toString();
    }

    @Override
    public String encrypt(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = string.toLowerCase().toCharArray();
        char[] key = this.key.toLowerCase().toCharArray();
        for(int i = 0; i < chars.length; i++){
            stringBuilder.append((char) (
                    ((chars[i % chars.length] - 'a') ^ (key[i % key.length]) - 'a') + 'a')
            );
        }
        return stringBuilder.toString();
    }
}
