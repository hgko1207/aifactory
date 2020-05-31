package ins.core.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptUtil {

    public static void main(String[] args) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String encStr = bcryptPasswordEncoder.encode("123456");
        System.out.println(encStr);
    }
}
