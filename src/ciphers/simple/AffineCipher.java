package ciphers.simple;

import java.util.HashMap;
import java.util.Map;

public class AffineCipher implements Cipher<String, String> {
    private int a;
    private int b;

    private final static int lengthAlphabet = 'z' - 'a' + 1;
    private final static Map<Integer, Integer> mapOfMultiplicativePair;

    static {
        Map<Integer, Integer> mapForCreateMultiplicativePair = new HashMap<>();
        Map<Integer, Integer> mapForCreateAdditivePair = new HashMap<>();
        for (int i = 0; i < lengthAlphabet; i++) {
            for (int j = 0; j < lengthAlphabet; j++) {
                if((i*j)%lengthAlphabet == 1){
                    mapForCreateMultiplicativePair.put(i, j);
                }
                if((i+j)%lengthAlphabet == 0){
                    mapForCreateAdditivePair.put(i, j);
                }
            }
        }
        mapOfMultiplicativePair = mapForCreateMultiplicativePair;
    }
    public AffineCipher(int a, int b) {
        if(!mapOfMultiplicativePair.containsKey(a)){
            throw new IllegalArgumentException();
        }
        this.a = a;
        this.b = b;
    }
    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String decrypt(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int a = mapOfMultiplicativePair.get(this.a);
        for (int c:
                string.toLowerCase().toCharArray()) {
            int index = (((c - 'a') - b) * a) % lengthAlphabet;
            if(index >= 0){
                stringBuilder.append((char) ('a' + index));
            }else {
                stringBuilder.append((char) ('z' + index + 1));
            }

        }
        return stringBuilder.toString();
    }

    @Override
    public String encrypt(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int c:
             string.toLowerCase().toCharArray()) {
            stringBuilder.append((char) ('a' + (((c - 'a') * a + b) % lengthAlphabet)));
        }
        return stringBuilder.toString();
    }
}
