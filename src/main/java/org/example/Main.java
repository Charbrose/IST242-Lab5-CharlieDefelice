// Project: Lab5
// Purpose Details: This program converts text to a custom alphabet, SHA-256 hash, encrypts using the CaesarCipher, and does a brute force decryption
// Course: IST 242
// Author: Charlie Defelice
// Date Developed: 3/31/24
// Last Date Changed: 3/31/34
// Rev: 3.6

package org.example;

import java.util.*;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class Main
{

    //custom alphabet mapping
    private static final Map<Character, Character> customAlphabet = new HashMap<>();

    static
    {
        //assign symbols to the most frequently used English letters
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

    //convert English to custom alphabet
    public static String englishToCustomAlphabet(String text)
    {
        StringBuilder result = new StringBuilder();
        for (char c : text.toLowerCase().toCharArray())
        {
            if (customAlphabet.containsKey(c))
            {
                result.append(customAlphabet.get(c));
            }

            else
            {
                result.append(c);
            }
        }
        return result.toString();
    }

    //convert custom alphabet to English
    public static String customAlphabetToEnglish(String text)
    {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray())
        {
            boolean found = false;
            for (Map.Entry<Character, Character> entry : customAlphabet.entrySet())
            {
                if (entry.getValue() == c)
                {
                    result.append(entry.getKey());
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                result.append(c);
            }
        }
        return result.toString();
    }

    //SHA256 hash of the given string
    public static String calculateSHA256(String data) throws NoSuchAlgorithmException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash)
        {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    //encrypts the text using CaesarCipher with the given shift value
    public static String encrypt(String plaintext, int shift)
    {
        StringBuilder encryptedText = new StringBuilder();
        for (char character : plaintext.toCharArray())
        {
            if (Character.isLetter(character))
            {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int originalAlphabetPosition = character - base;
                int newAlphabetPosition = (originalAlphabetPosition + shift) % 26;
                char newCharacter = (char) (base + newAlphabetPosition);
                encryptedText.append(newCharacter);
            }

            else
            {
                encryptedText.append(character);
            }
        }
        return encryptedText.toString();
    }

    //brute force decryption for the CaesarCipher
    public static void bruteForce(String text)
    {
        //goes over all shift values
        for (int shift = 0; shift < 26; shift++)
        {
            //decrypts the text using the current shift value and prints the result
            System.out.println("Shift " + shift + ": " + encrypt(text, shift));
        }
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        //prompts the user for a plaintext input
        System.out.print("Enter plaintext: ");
        String plaintext = scanner.nextLine();

        System.out.println("");

        //converts plaintext to the new custom alphabet text
        String customAlphabetText = englishToCustomAlphabet(plaintext);
        System.out.println("Custom alphabet text: " + customAlphabetText);

        System.out.println("");

        try
        {
            //SHA256 hash of the custom alphabet text
            String sha256Hash = calculateSHA256(customAlphabetText);
            System.out.println("SHA-256 Hash: " + sha256Hash);
        }

        catch (NoSuchAlgorithmException e)
        {
            //handles SHA256 algorithm in case it's not available
            System.err.println("SHA-256 algorithm not found.");
        }

        System.out.println("");

        //encrypts the custom alphabet text using CaesarCipher with a shift
        String encryptedText = encrypt(customAlphabetText, 5);
        System.out.println("Encrypted Text With 5 Character Shift: " + encryptedText);

        System.out.println("");

        //brute force decryption for CaesarCipher
        System.out.println("--- Brute Force Caesar Cipher ---");
        bruteForce(customAlphabetText);

        scanner.close();
    }
}
