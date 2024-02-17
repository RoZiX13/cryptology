package ciphers.simple;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class CipherOfASimpleSinglePermutation implements Cipher<String, String> {
    private Set<Integer> setPermutation;

    public CipherOfASimpleSinglePermutation(LinkedHashSet<Integer> setPermutation) {
        this.setPermutation = setPermutation;
    }
    
    public CipherOfASimpleSinglePermutation(int length) {
        this.setPermutation = createRandomSetPermutation(length);
    }

    public Set<Integer> getSetPermutation() {
        return setPermutation;
    }

    public void setSetPermutation(LinkedHashSet<Integer> setPermutation) {
        this.setPermutation = setPermutation;
    }

    public Set<Integer> createRandomSetPermutation(int length){
        Random random = new Random();
        Set<Integer> set = new LinkedHashSet<>();
        while (set.size() < length){
            set.add(random.nextInt(length));
        }
        return set;
    }

    @Override
    public String decrypt(String string) {
        if(string.length() != setPermutation.size()){
            throw new IllegalArgumentException();
        }
        char[] chars = string.toLowerCase().toCharArray();
        char[] decryptChars = new char[string.length()];
        int counter = 0;
        for (int index:
                setPermutation){
            decryptChars[index] = chars[counter++];
        }
        return String.copyValueOf(decryptChars);
    }
    @Override
    public String encrypt(String string) {
        if(string.length() != setPermutation.size()){
            throw new IllegalArgumentException();
        }
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = string.toLowerCase().toCharArray();
        for (int index:
                setPermutation){
            stringBuilder.append(chars[index]);
        }
        return stringBuilder.toString();
    }
}
