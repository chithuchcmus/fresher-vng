package fresher.RockPaperScissors.Helper;

import fresher.RockPaperScissors.Model.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Repository;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Repository
public class TokenService {
    private final String SECRET_KEY = "fresher";
    private final long timeExpiration = 5 * 60 * 60 * 1000;
    public  String createJWT(User user)
    {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //time start exp
        long miliNow = System.currentTimeMillis();
        Date now = new Date(miliNow);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //Claims info
        JwtBuilder builder = Jwts.builder()
                .setId(user.getUserId().toString())
                .claim("username",user.getUsername())
                .signWith(signatureAlgorithm, signingKey);

        //set expiration time
        long expirationTime = miliNow + timeExpiration;
        Date exp = new Date(expirationTime);
        builder.setExpiration(exp);
        return builder.compact();
    }

    public Claims decodeToken(String token)
    {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e)
        {
            return null;
        }

    }


}
