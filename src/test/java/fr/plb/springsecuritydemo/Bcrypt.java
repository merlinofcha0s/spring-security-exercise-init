package fr.plb.springsecuritydemo;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Bcrypt {

    @Test
    public void testBcrypt() {
        String password = "M0nSuperP@asswordS3cured";
        for (int i = 10; i < 19; i++) {
            long startTime = System.currentTimeMillis();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(i);
            System.out.println(encoder.encode(password));
            System.out.println("Strength: "+i+" - ("+(System.currentTimeMillis() - startTime)+" ms)");
        }
    }
}
