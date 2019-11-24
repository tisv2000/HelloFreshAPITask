package com.hellofresh.challenge.data;

import org.apache.commons.text.RandomStringGenerator;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

class RandomUtils {

    private static RandomStringGenerator getStringGenerator() {
        return new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();
    }

    private static RandomStringGenerator getStringGeneratorLettersOnly() {
        return new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .filteredBy(LETTERS)
                .build();
    }

    private static RandomStringGenerator getStringGeneratorDigitsOnly() {
        return new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .filteredBy(DIGITS)
                .build();
    }

    private static String generateRandomString(int length) {
        return getStringGenerator().generate(length);
    }

    static String generateRandomStringLettersOnly(int length) {
        return getStringGeneratorLettersOnly().generate(length);
    }

    static String generateRandomStringDigitsOnly(int length) {
        return getStringGeneratorDigitsOnly().generate(length);
    }

    static String generateRandomEmail() {
        return RandomUtils.generateRandomString(5) + "@test.hf";
    }
}
