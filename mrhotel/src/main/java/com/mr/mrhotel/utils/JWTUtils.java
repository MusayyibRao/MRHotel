package com.mr.mrhotel.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
@Component
public class JWTUtils {

    private static final long EXPIRATION_TIME = 1000 * 60 * 24 * 7; //7DAYS

    private final SecretKey key;

    public JWTUtils(){

        String secretString="4F6A74EFA8E34445D11ABBE9F8F8A9467C69A32CFEC37E6A1A767F8CEB";
        byte[] keyBytes= Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.key=new SecretKeySpec(keyBytes,"HmacSHA256");

    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .compact();
    }

    public String extractUsername(String token){

        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsTFunction){

        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isValidToken(String token,UserDetails userDetails){

        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));

    }

    public boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }
}
