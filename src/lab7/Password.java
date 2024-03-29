package lab7;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;


public class Password {
    private static final int LENGTH = 20;
    private static final double NUMBER_PROB = 0.25;
    private static final double THIRD_SYMB_PROB = 0.4;

    private static final String CONSONANTS = "bcdfghkmnprstvz";
    private static final String VOWELS = "aeiouy";

    private String value;

    Password() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while (sb.length() < LENGTH) {
            sb.append( CONSONANTS.charAt( random.nextInt(CONSONANTS.length()) ) );
            sb.append( VOWELS.charAt( random.nextInt(VOWELS.length()) ) );

            if (random.nextDouble() <= THIRD_SYMB_PROB) {
                sb.append(CONSONANTS.toUpperCase().charAt(random.nextInt(CONSONANTS.length())));
            }else {
                sb.append(random.nextInt(10));
            }
        }

        value = sb.toString();
    }

    Password(String value) {
        this.value = value;
    }

    String get() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    String getHashCode() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = digest.digest(value.getBytes());
            String hash = new BigInteger(1, bytes).toString(16);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < (32 - hash.length()); i++) {
                sb.append('0');
            }
            return sb.toString() + hash;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Password) {
            return getHashCode().equals(((Password) obj).getHashCode());
        } else {
            return false;
        }
    }
}
