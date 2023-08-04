package com.example.demo.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private String secretKey;

    private long jwtExpiration;

    public JwtService(){
        Dotenv dotenv = Dotenv.load();
        this.secretKey = dotenv.get("jwt.secret-key");
        String jwtExpirationFromDotenv = dotenv.get("jwt.expiration");
        if(jwtExpirationFromDotenv != null){
            this.jwtExpiration = Long.parseLong(jwtExpirationFromDotenv);
        }
        else{
            throw new RuntimeException("dot env cant read the value of key jwt.expiration");
        }
    }


    public long getJwtExpiration(){
        return this.jwtExpiration;
    }

    public String extractUsername(String jwt){
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails){
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }

    public String buildToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return (Key) Keys.hmacShaKeyFor(keyBytes);
    }

}
