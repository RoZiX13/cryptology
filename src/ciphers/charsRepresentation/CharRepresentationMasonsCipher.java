package ciphers.charsRepresentation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class CharRepresentationMasonsCipher implements PrintCharRepresentation, Serializable {
    private boolean[] thereAreBoards;
    private boolean[] thereArePoints ;

    public CharRepresentationMasonsCipher(boolean[] thereAreBoards, boolean[] thereArePoints) {
        this.thereAreBoards = thereAreBoards;
        this.thereArePoints = thereArePoints;
    }
    public CharRepresentationMasonsCipher() {
        thereAreBoards = createRandomArrayOfBoolean(4);
        thereArePoints = createRandomArrayOfBoolean(9);
    }

    public boolean[] getThereAreBoards() {
        return thereAreBoards;
    }

    public void setThereAreBoards(boolean[] thereAreBoards) {
        this.thereAreBoards = thereAreBoards;
    }

    public boolean[] getThereArePoints() {
        return thereArePoints;
    }

    public void setThereArePoints(boolean[] thereArePoints) {
        this.thereArePoints = thereArePoints;
    }

    private static boolean[] createRandomArrayOfBoolean(int length){
        Random random = new Random();
        boolean[] randomArrayOfBoolean = new boolean[length];
        for (int i = 0; i < length; i++) {
            randomArrayOfBoolean[i] = random.nextBoolean();
        }
        return randomArrayOfBoolean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharRepresentationMasonsCipher that = (CharRepresentationMasonsCipher) o;
        return Arrays.equals(thereAreBoards, that.thereAreBoards) && Arrays.equals(thereArePoints, that.thereArePoints);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(thereAreBoards);
        result = 31 * result + Arrays.hashCode(thereArePoints);
        return result;
    }

    private void addRepresentationStringTopOrBottomBoardsInStringBuilder(int index, StringBuilder stringBuilder){
        if(!(index == 0 || index == 3)){
            throw new IllegalArgumentException();
        }
        stringBuilder.append((thereAreBoards[index] ? (" - ") : ("   ")).repeat(5));
        stringBuilder.append("\n");
    }

    private void addVectorPointsInStringBuilder(int index, StringBuilder stringBuilder){
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(thereArePoints[index + i] ? (" * ") : ("   "));
        }
    }

    @Override
    public void print() {
        System.out.println(getStringRepresentation());
    }

    @Override
    public String getStringRepresentation(){
        StringBuilder stringBuilder = new StringBuilder();
        addRepresentationStringTopOrBottomBoardsInStringBuilder(0, stringBuilder);

        for (int i = 0; i < 3; i++) {
            stringBuilder.append(thereAreBoards[1] ? (" | ") : ("   "));

            addVectorPointsInStringBuilder(i * 3, stringBuilder);

            stringBuilder.append(thereAreBoards[2] ? (" | ") : ("   ")).append("\n");
        }

        addRepresentationStringTopOrBottomBoardsInStringBuilder(3, stringBuilder);
        return stringBuilder.toString();
    }
}
