import ciphers.charsRepresentation.CharRepresentation;
import ciphers.charsRepresentation.CharRepresentationMasonsCipher;
import ciphers.charsRepresentation.CharsRepresentationArray;
import ciphers.simple.*;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String stringRu = "розендаль";
        String stringEn = "rozendal";
        Cipher<String, String> cipher;

        // Шифр Цезаря
        cipher = new CaesarCipher(5);
        String encryptedString = cipher.encrypt(stringEn);
        System.out.println("Шифр Цезаря");
        System.out.println("Шифртекст: " + encryptedString);
        System.out.println("Исходный текст: " + cipher.decrypt(encryptedString) + "\n");

        // Шифр Виженера
        cipher = new VigenerCipher("vector");
        encryptedString = cipher.encrypt(stringEn);
        System.out.println("Шифр Виженера");
        System.out.println("Шифртекст: " + encryptedString);
        System.out.println("Исходный текст: " + cipher.decrypt(encryptedString) + "\n");

        // Шифр масонов
        MasonsCipher randomCipher = new MasonsCipher();
        CharRepresentationMasonsCipher[] charRepresentationMasonsCipherCharsRepresentationArray = randomCipher.encrypt(stringEn).getArrayOfCharsRepresentation();
        System.out.println("Шифртекст: ");
        for (CharRepresentationMasonsCipher charRepresentationMasonsCipher:
             charRepresentationMasonsCipherCharsRepresentationArray) {
            System.out.println(charRepresentationMasonsCipher.getStringRepresentation());
        }
        System.out.println("Исходный текст: " + cipher.decrypt(encryptedString) + "\n");

        // Шифр Плейфера
        cipher = new PlayfairBigramCipher("song");
        encryptedString = cipher.encrypt(stringEn);
        System.out.println("Шифр Плейфера");
        System.out.println("Шифртекст: " + encryptedString);
        System.out.println("Исходный текст: " + cipher.decrypt(encryptedString) + "\n");

        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        for (int i:
             new int[]{5, 6, 7, 1, 3, 4, 0, 2}) {
            set.add(i);
        }

        // Шифр простой одинарной перестановки
        cipher = new CipherOfASimpleSinglePermutation(set);
        encryptedString = cipher.encrypt(stringEn);
        System.out.println("Шифр простой одинарной перестановки");
        System.out.println("Шифртекст: " + encryptedString);
        System.out.println("Исходный текст: " + cipher.decrypt(encryptedString) + "\n");

        // Шифр табличной маршрутной перестановки
        cipher = new CipherOfTheTabularRoutePermutation(5);
        encryptedString = cipher.encrypt(stringEn);
        System.out.println("Шифр табличной маршрутной перестановки");
        System.out.println("Шифртекст: " + encryptedString);
        System.out.println("Исходный текст: " + cipher.decrypt(encryptedString) + "\n");

        // Аффинный шифр
        cipher = new AffineCipher(3, 2);
        encryptedString = cipher.encrypt(stringEn);
        System.out.println("Аффинный шифр");
        System.out.println("Шифртекст: " + encryptedString);
        System.out.println("Исходный текст: " + cipher.decrypt(encryptedString) + "\n");

        // Шифр Хилла
        cipher = new HillCipher("транспонирование", 4);
        encryptedString = cipher.encrypt(stringRu);
        System.out.println("Шифр Хилла");
        System.out.println("Шифртекст: " + encryptedString);
        System.out.println("Исходный текст: " + cipher.decrypt(encryptedString) + "\n");

        //  Шифр Вернама
        cipher = new VernamCipher("VernamCipher");
        encryptedString = cipher.encrypt(stringEn);
        System.out.println(" Шифр Вернама");
        System.out.println("Шифртекст: " + encryptedString);
        System.out.println("Исходный текст: " + cipher.decrypt(encryptedString) + "\n");
    }
}