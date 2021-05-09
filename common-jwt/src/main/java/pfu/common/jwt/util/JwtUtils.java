package pfu.common.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import pfu.common.jwt.property.ClaimSet;
import pfu.common.jwt.property.JwtProperties;

import java.util.Calendar;

public class JwtUtils {

    private final JwtProperties properties;
    public JwtUtils(JwtProperties properties) {
        this.properties = properties;
    }

    public String genToken(String secret, String account) {
        Calendar calendar = Calendar.getInstance();

        ClaimSet claimSet = properties.getClaim();

        JWTCreator.Builder builder = JWT.create()
                .withAudience(account)
                .withIssuedAt(calendar.getTime())
                .withNotBefore(calendar.getTime());

        if (null != claimSet.getSubject()) {
            builder.withSubject(claimSet.getSubject());
        }

        if (null != claimSet.getIssuer()) {
            builder.withIssuer(claimSet.getIssuer());
        }

        if (0 < claimSet.getExpiredInDays()) {
            calendar.add(Calendar.DAY_OF_MONTH, claimSet.getExpiredInDays());
            builder.withExpiresAt(calendar.getTime());
        }

        return builder.sign(Algorithm.HMAC256(secret));
    }

    public boolean verify(String secret, String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer getClaimInt(String token, String claimName) {
        Claim claim = JWT.decode(token).getClaim(claimName);
        return claim.isNull() ? null : claim.asInt();
    }

}
