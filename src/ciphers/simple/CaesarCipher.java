package ciphers.simple;

import java.util.Objects;

public class CaesarCipher implements Cipher<String, String> {
    private static final int start = 'a';
    private static final int end = 'z';

    private static final int alphabetLength = end - start + 1;
    private int offset;

    public CaesarCipher(int offset) {
        this.offset = offset;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String encrypt(String string) {
        string = string.toLowerCase();
        StringBuilder encryptedString = new StringBuilder();

        for(int c:
            string.toCharArray()){
            encryptedString.append((char)(((c - start + offset)%(alphabetLength)) + start));
        }
        return encryptedString.toString();
    }
    @Override
    public String decrypt(String string) {
        string = string.toLowerCase();
        StringBuilder encryptedString = new StringBuilder();
        for(int c:
                string.toCharArray()){
            encryptedString.append((char)(((c - end - offset)%(alphabetLength)) + end));
        }
        return encryptedString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaesarCipher that = (CaesarCipher) o;
        return offset == that.offset;
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset);
    }
}
