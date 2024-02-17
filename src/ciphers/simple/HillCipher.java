package ciphers.simple;

import ciphers.simple.Cipher;

import java.util.concurrent.atomic.AtomicInteger;

public class HillCipher implements Cipher<String, String> {
    private final static char[] alphabet;
    private char[][] matrixKey;
    private int[][] inverseMatrixKey;

    static {
        char[] chars = new char[37];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (i + 'а');
        }

        chars[36] = '?';
        chars[35] = ' ';
        chars[34] = ',';
        chars[33] = '.';
        chars[32] = '!';
        alphabet = chars;
    }
    public HillCipher(char[][] matrixKey) {
        this.matrixKey = matrixKey;
        this.inverseMatrixKey = createInverseMatrixKey(matrixKey);
    }
    public HillCipher(String key, int matrixLength) {
        this.matrixKey = createHillMatrix(key, matrixLength);
        this.inverseMatrixKey = createInverseMatrixKey(this.matrixKey);
    }

    public char[][] getMatrixKey() {
        return matrixKey;
    }

    public void setMatrixKeyAndCreateNewInverseMatrixKey(char[][] matrixKey) {
        this.matrixKey = matrixKey;
        this.inverseMatrixKey = createInverseMatrixKey(matrixKey);
    }

    public int[][] getInverseMatrixKey() {
        return inverseMatrixKey;
    }

    public static char[][] createHillMatrix(String key, int length){
        char[] chars = key.toLowerCase().toCharArray();
        char[][] matrix = new char[length][length];
        int index = 0;
        while (index < length * length){
            matrix[index / length][index % length] = chars[index % chars.length];
            index++;
        }
        return matrix;
    }

    public static int[][] createInverseMatrixKey(char[][] matrixKey){
        int[][] integerMatrixFormCharacterMatrixByAlphabet = convertToIntegerMatrixFormCharacterMatrixByAlphabet(matrixKey);
        int inverseDet = createInverseDet(integerMatrixFormCharacterMatrixByAlphabet);
        int [][] transpositionMatrixOfAlgebraicAdditions = transposition(modMatrix(createMatrixOfAlgebraicAdditions(integerMatrixFormCharacterMatrixByAlphabet), alphabet.length));

        for (int i = 0; i < transpositionMatrixOfAlgebraicAdditions.length; i++) {
            for (int j = 0; j < transpositionMatrixOfAlgebraicAdditions[0].length; j++) {
                int calc = inverseDet *  transpositionMatrixOfAlgebraicAdditions[i][j] % alphabet.length;
                transpositionMatrixOfAlgebraicAdditions[i][j] = calc < 0 ? (alphabet.length + calc) : (calc);
            }
        }
        return transpositionMatrixOfAlgebraicAdditions;
    }

    private static int[][] createMatrixOfAlgebraicAdditions(int[][] matrixKey){
        int[][] matrixOfAlgebraicAdditions = new int[matrixKey.length][matrixKey[0].length];
        for (int i = 0; i < matrixOfAlgebraicAdditions.length; i++) {
            for (int j = 0; j < matrixOfAlgebraicAdditions[0].length; j++) {
                matrixOfAlgebraicAdditions[i][j] = -(int) Math.pow(-1, i+j) * detMatrix(newDetMatrix(matrixKey, i, j));
            }
        }
        return matrixOfAlgebraicAdditions;
    }
    private static int detMatrix(int[][] matrix){

        if(matrix.length == 2){
            return matrix[1][0] * matrix[0][1] - matrix[0][0] * matrix[1][1];
        }
        int detMatrix = 0;
        for (int i = 0; i < matrix.length; i++) {
            if(matrix[i][0] == 0) continue;
            detMatrix += (int) Math.pow(-1, 2 + i) * matrix[i][0] * detMatrix(newDetMatrix(matrix, i, 0));
        }
        return -detMatrix;
    }
    private static int[][] newDetMatrix(int[][] matrix, int i, int j){
        int[][] newMatrix = new int[matrix.length - 1][matrix[0].length - 1];
        int counter = 0;
        for (int k = 0; k < matrix.length; k++) {
            if(k == i) continue;
            for (int l = 0; l < matrix[0].length; l++) {
                if(l == j) continue;
                newMatrix[counter / newMatrix.length][counter % newMatrix[0].length] = matrix[k][l];
                counter++;
            }
        }
        return newMatrix;
    }
    private static int createInverseDet(int[][] matrix){
        AtomicInteger x = new AtomicInteger();
        AtomicInteger y = new AtomicInteger();
        int detMatrix = detMatrix(matrix);
        extendedGcd(detMatrix, alphabet.length, x, y);
        boolean isDeterminantsGreaterThanZero = detMatrix > 0;
        boolean isXGreaterThanZero = x.get() > 0;
        if(isDeterminantsGreaterThanZero){
            if(isXGreaterThanZero){
                return x.get();
            }
            return alphabet.length + x.get();
        }
        if(isXGreaterThanZero){
            return x.get();
        }
        return -x.get();
    }
    private static int extendedGcd(int a, int b, AtomicInteger x, AtomicInteger y) {
        if (a == 0)
        {
            x.set(0);
            y.set(1);
            return b;
        }

        AtomicInteger _x = new AtomicInteger(), _y = new AtomicInteger();
        int gcd = extendedGcd(b % a, a, _x, _y);

        x.set(_y.get() - (b/a) * _x.get());
        y.set(_x.get());

        return gcd;
    }
    private static int[][] modMatrix(int[][] matrix, int mod){
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[0].length; j++) {
                newMatrix[i][j] = matrix[i][j] % mod;
            }
        }
        return newMatrix;
    }

    private static int[][] transposition(int[][] matrix){
        int[][] transpositionMatrix = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < transpositionMatrix.length; i++) {
            for (int j = 0; j < transpositionMatrix[0].length; j++) {
                transpositionMatrix[i][j] = matrix[j][i];
            }
        }
        return transpositionMatrix;
    }
    private static int[][] convertToIntegerMatrixFormCharacterMatrixByAlphabet(char[][] charsMatrix){
        int[][] matrix = new int[charsMatrix.length][charsMatrix[0].length];
        for (int i = 0; i < charsMatrix.length; i++) {
            for (int j = 0; j < charsMatrix[0].length; j++) {
                matrix[i][j] = getIndexCharFromAlphabet(charsMatrix[i][j]);
            }
        }
        return matrix;
    }
    private static int getIndexCharFromAlphabet(char c){
        int index = 0;
        while (alphabet[index] != c){
            index++;
        }
        return index;
    }
    @Override
    public String decrypt(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = splitTheLineByLength(string.toLowerCase(), matrixKey.length);
        for (String s : strings) {
            char[][] charsMatrix = new char[1][];
            charsMatrix[0] = s.toCharArray();
            int[][] intsMatrix = multiplicationMatrix(inverseMatrixKey,
                    transposition(convertToIntegerMatrixFormCharacterMatrixByAlphabet(charsMatrix)));
            for (int[] index :
                    intsMatrix) {
                stringBuilder.append(alphabet[index[0] % alphabet.length]);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String encrypt(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = splitTheLineByLength(string.toLowerCase(), matrixKey.length);
        int[][] matrixKeyByAlphabet = convertToIntegerMatrixFormCharacterMatrixByAlphabet(matrixKey);
        for (String s : strings) {
            char[][] charsMatrix = new char[1][];
            charsMatrix[0] = s.toCharArray();
            int[][] intsMatrix = multiplicationMatrix(matrixKeyByAlphabet,
                    transposition(convertToIntegerMatrixFormCharacterMatrixByAlphabet(charsMatrix)));
            for (int[] index :
                    intsMatrix) {
                stringBuilder.append(alphabet[index[0] % alphabet.length]);
            }
        }
        return stringBuilder.toString();
    }

    private String[] splitTheLineByLength(String string, int length){
        int mod = string.length() % length;
        int stringArraySize = string.length() / length +
                (mod == 0 ? (0) : (1));
        String[] strings = new String[stringArraySize];
        for (int i = 0; i < strings.length; i++) {
            int beginIndex = i * length;
            if(beginIndex + length > string.length()) continue;
            strings[i] = string.substring(beginIndex, beginIndex + length);

        }

        if(mod != 0){
            int len = string.length();
            strings[strings.length - 1] = string.substring(len - mod, len) + ".".repeat(length - mod);
        }
        return strings;
    }
    private int[][] multiplicationMatrix(int[][] matrix0, int[][] matrix1){
        if(matrix0[0].length != matrix1.length){
            throw new IllegalArgumentException();
        }
        int[][] multiplicationMatrix = new int[matrix0.length][matrix1[0].length];
            for (int i = 0; i < multiplicationMatrix.length; i++) {
                for (int j = 0; j < multiplicationMatrix[0].length; j++) {
                    for (int k = 0; k < matrix0[0].length; k++) {
                        multiplicationMatrix[i][j] = multiplicationMatrix[i][j] + matrix0[i][k] * matrix1[k][j];
                    }
                }
            }
        return multiplicationMatrix;
    }

}
