package lz78.commons;

public class CodePair {
    private final int index;
    private final char character;

    public CodePair(int index, char character) {
        this.index = index;
        this.character = character;
    }

    public int getIndex() {
        return index;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return "(" + index + "," + character + ")";
    }
}
