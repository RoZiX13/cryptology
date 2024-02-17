package ciphers.charsRepresentation;

import java.util.Arrays;

public class CharsRepresentationArray <O extends PrintCharRepresentation>{
    private O[] arrayOfCharsRepresentation;

    public CharsRepresentationArray(O[] arrayOfCharsRepresentation) {
        this.arrayOfCharsRepresentation = arrayOfCharsRepresentation;
    }
    public O[] getArrayOfCharsRepresentation() {
        return arrayOfCharsRepresentation;
    }

    public void setArrayOfCharsRepresentation(O[] arrayOfCharsRepresentation) {
        this.arrayOfCharsRepresentation = arrayOfCharsRepresentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharsRepresentationArray<?> that = (CharsRepresentationArray<?>) o;
        return Arrays.equals(arrayOfCharsRepresentation, that.arrayOfCharsRepresentation);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arrayOfCharsRepresentation);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(O o:
            arrayOfCharsRepresentation){
            stringBuilder.append(o.getStringRepresentation());
        }
        return stringBuilder.toString();
    }
}
