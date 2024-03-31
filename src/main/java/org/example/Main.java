package org.example;

import java.util.*;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Main {

    private static final Map<Character, Character> customAlphabet = new HashMap<>();

    static {
        customAlphabet.put('e', '~');
        customAlphabet.put('t', '!');
        customAlphabet.put('a', '@');
        customAlphabet.put('o', '#');
        customAlphabet.put('i', '$');
        customAlphabet.put('n', '%');
        customAlphabet.put('s', '^');
        customAlphabet.put('r', '&');
        customAlphabet.put('h', '*');
    }

    //Most Used Characters in English
    //E, T, A, O, I, N, S, R, H, and L
    //~!@#$%^&*
    public static String englishToCustomAlphabet(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toLowerCase().toCharArray()) {
            if (customAlphabet.containsKey(c)) {
                result.append(customAlphabet.get(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String customAlphabetToEnglish(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            boolean found = false;
            for (Map.Entry<Character, Character> entry : customAlphabet.entrySet()) {
                if (entry.getValue() == c) {
                    result.append(entry.getKey());
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String calculateSHA256(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String encrypt(String plaintext, int shift) {
        StringBuilder encryptedText = new StringBuilder();
        for (char character : plaintext.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int originalAlphabetPosition = character - base;
                int newAlphabetPosition = (originalAlphabetPosition + shift) % 26;
                char newCharacter = (char) (base + newAlphabetPosition);
                encryptedText.append(newCharacter);
            } else {
                encryptedText.append(character);
            }
        }
        return encryptedText.toString();
    }

    public static void bruteForce(String text) {
        for (int shift = 0; shift < 26; shift++) {
            System.out.println("Shift " + shift + ": " + encrypt(text, shift));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String plaintext = scanner.nextLine();

        System.out.println("");
        String customAlphabetText = englishToCustomAlphabet(plaintext);
        System.out.println("Custom alphabet text: " + customAlphabetText);

        System.out.println("");
        try {
            String sha256Hash = calculateSHA256(customAlphabetText);
            System.out.println("SHA-256 Hash: " + sha256Hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not found.");
        }

        System.out.println("");
        String encryptedText = encrypt(customAlphabetText, 5);
        System.out.println("Encrypted Text With 5 Character Shift: " + encryptedText);

        System.out.println("");
        System.out.println("--- Brute Force Caesar Cipher ---");
        bruteForce(customAlphabetText);

        scanner.close();
    }
}
