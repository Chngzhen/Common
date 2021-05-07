package pfu.common.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import pfu.common.jwt.property.Claim;
import pfu.common.jwt.property.JwtProperties;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private final JwtProperties properties;
    public JwtUtils(JwtProperties properties) {
        this.properties = properties;
    }

    public String genToken(String secret, Map<String, Object> claims) {
        LocalDate today = LocalDate.now();

        Claim claim = properties.getClaim();

        JWTCreator.Builder builder = JWT.create()
                .withIssuer("")
                .withIssuedAt(new Date())
                .withSubject("")
                .withNotBefore(new Date());
        if (0 < claim.getExpiredInDays()) {
            LocalDate expiredAt = today.plusDays(claim.getExpiredInDays());
            builder.withExpiresAt(Date.from(expiredAt.toS))
        }

        return builder.sign(Algorithm.HMAC256(secret));
    }

}
