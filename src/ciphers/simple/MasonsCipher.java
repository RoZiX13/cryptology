package ciphers.simple;


import ciphers.charsRepresentation.CharRepresentationMasonsCipher;
import ciphers.charsRepresentation.CharsRepresentationArray;
import ciphers.simple.Cipher;

import java.io.Serializable;
import java.util.*;

public class MasonsCipher implements Cipher<CharsRepresentationArray<CharRepresentationMasonsCipher>, String>, Serializable {
    private Map<Character, CharRepresentationMasonsCipher> charRepresentationMapForEncrypt;
    private Map<CharRepresentationMasonsCipher, Character> charRepresentationMapForDecrypt;

    public MasonsCipher(Map<Character, CharRepresentationMasonsCipher> charRepresentationMap) {
        this.charRepresentationMapForEncrypt = charRepresentationMap;
        this.charRepresentationMapForDecrypt = createMapWithCharRepresentationForEncodingFromMapCharRepresentationForEncrypt(charRepresentationMap);
    }

    public MasonsCipher() {
        charRepresentationMapForEncrypt = createRandomMapWithCharRepresentationForEncoding();
        charRepresentationMapForDecrypt = createMapWithCharRepresentationForEncodingFromMapCharRepresentationForEncrypt(charRepresentationMapForEncrypt);
    }

    public Map<Character, CharRepresentationMasonsCipher> getCharRepresentationMapForEncrypt() {
        return charRepresentationMapForEncrypt;
    }

    public void setCharRepresentationMapForEncrypt(Map<Character, CharRepresentationMasonsCipher> charRepresentationMapForEncrypt) {
        this.charRepresentationMapForEncrypt = charRepresentationMapForEncrypt;
    }

    public Map<CharRepresentationMasonsCipher, Character> getCharRepresentationMapForDecrypt() {
        return charRepresentationMapForDecrypt;
    }

    public void setCharRepresentationMapForDecrypt(Map<CharRepresentationMasonsCipher, Character> charRepresentationMapForDecrypt) {
        this.charRepresentationMapForDecrypt = charRepresentationMapForDecrypt;
    }

    public static Map<Character, CharRepresentationMasonsCipher> createRandomMapWithCharRepresentationForEncoding(){
        Map<Character, CharRepresentationMasonsCipher> randomMapWithCharRepresentation = new HashMap<>();
        Iterator<CharRepresentationMasonsCipher> iteratorOfCharRepresentation = createRandomSetOfCharRepresentation().iterator();
        Iterator<Character> characterIterator = createRandomCharacterSet().iterator();
        for (int i = 'a'; i < 'z' + 1; i++) {
            randomMapWithCharRepresentation.put(characterIterator.next(), iteratorOfCharRepresentation.next());
        }

        return randomMapWithCharRepresentation;
    }

    public static Set<CharRepresentationMasonsCipher> createRandomSetOfCharRepresentation(){
        Set<CharRepresentationMasonsCipher> randomSetOfCharRepresentationMasonsCipher = new HashSet<>();
        int indexBox = 0;
        int size;
        while ((size = randomSetOfCharRepresentationMasonsCipher.size()) < 'z' - 'a' + 1){
            indexBox %= 9;
            randomSetOfCharRepresentationMasonsCipher.add(createRandomnewCharRepresentation(indexBox));
            if(size < randomSetOfCharRepresentationMasonsCipher.size()){
                indexBox++;
            }
        }
        return randomSetOfCharRepresentationMasonsCipher;
    }

    private static CharRepresentationMasonsCipher createRandomnewCharRepresentation(int indexBox){
        CharRepresentationMasonsCipher charRepresentationMasonsCipher = new CharRepresentationMasonsCipher();
        boolean[] thereAreBoards = new boolean[4];
        switch (indexBox){
            case 0: {
                thereAreBoards[2] = true;
                thereAreBoards[3] = true;
                break;
            }
            case 1:{
                thereAreBoards[1] = true;
                thereAreBoards[2] = true;
                thereAreBoards[3] = true;
                break;
            }
            case 2:{
                thereAreBoards[1] = true;
                thereAreBoards[3] = true;
                break;
            }
            case 3:{
                thereAreBoards[0] = true;
                thereAreBoards[2] = true;
                thereAreBoards[3] = true;
                break;
            }
            case 4:{
                thereAreBoards[0] = true;
                thereAreBoards[1] = true;
                thereAreBoards[2] = true;
                thereAreBoards[3] = true;
                break;
            }
            case 5:{
                thereAreBoards[0] = true;
                thereAreBoards[1] = true;
                thereAreBoards[3] = true;
                break;
            }
            case 6:{
                thereAreBoards[0] = true;
                thereAreBoards[2] = true;
                break;
            }
            case 7:{
                thereAreBoards[0] = true;
                thereAreBoards[1] = true;
                thereAreBoards[2] = true;
                break;
            }
            case 8:{
                thereAreBoards[0] = true;
                thereAreBoards[1] = true;
                break;
            }
        }
        charRepresentationMasonsCipher.setThereAreBoards(thereAreBoards);
        return charRepresentationMasonsCipher;
    }

    private static Set<Character> createRandomCharacterSet(){
        Random random = new Random();
        Set<Character> characterSet = new LinkedHashSet<>();

        while (characterSet.size() < 'z' - 'a' + 1){
            characterSet.add((char)(random.nextInt(26) + 'a'));
        }
        return characterSet;
    }

    private Map<CharRepresentationMasonsCipher, Character> createMapWithCharRepresentationForEncodingFromMapCharRepresentationForEncrypt(Map<Character, CharRepresentationMasonsCipher> charRepresentationMapForEncrypt){
        Map<CharRepresentationMasonsCipher, Character> map = new HashMap<>();
        for(Map.Entry<Character, CharRepresentationMasonsCipher> entrySet:
                charRepresentationMapForEncrypt.entrySet()){
            map.put(entrySet.getValue(), entrySet.getKey());
        }
        return map;
    }
    @Override
    public CharsRepresentationArray<CharRepresentationMasonsCipher> encrypt(String string) {
        string = string.toLowerCase();
        char[] arrayChars = string.toCharArray();
        CharsRepresentationArray<CharRepresentationMasonsCipher> charsRepresentationArray = new CharsRepresentationArray<>(new CharRepresentationMasonsCipher[arrayChars.length]);
        for (int i = 0; i < arrayChars.length; i++) {
            charsRepresentationArray.getArrayOfCharsRepresentation()[i] = charRepresentationMapForEncrypt.get(arrayChars[i]);
        }
        return charsRepresentationArray;
    }
    @Override
    public String decrypt(CharsRepresentationArray<CharRepresentationMasonsCipher> string) {
        StringBuilder decryptedString = new StringBuilder();
        for(CharRepresentationMasonsCipher c:
                string.getArrayOfCharsRepresentation()){

            decryptedString.append(charRepresentationMapForDecrypt.get(c));

        }
        return decryptedString.toString();
    }

}