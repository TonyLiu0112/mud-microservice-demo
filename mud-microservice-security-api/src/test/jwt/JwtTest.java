import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import java.util.UUID;

import static org.springframework.security.jwt.codec.Codecs.utf8Encode;

public class JwtTest {

    static final String TEST_CLAIMS = "{\"user\":\"muhaha\"}";

    final static MacSigner hmac = new MacSigner(utf8Encode("123"));

    public static void main(String[] args) {

        System.out.println(UUID.randomUUID());

        String myToken = JwtHelper.encode(TEST_CLAIMS, hmac).getEncoded();
        System.out.println(myToken);

        Jwt jwt = JwtHelper.decodeAndVerify(myToken, hmac);
        System.out.println(jwt.getClaims());
    }
}
