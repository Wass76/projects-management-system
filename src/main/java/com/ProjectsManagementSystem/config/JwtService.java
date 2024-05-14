package com.ProjectsManagementSystem.config;

import com.ProjectsManagementSystem.exception.ApiInvalidTokenException;
import com.ProjectsManagementSystem.user.Token;
import com.ProjectsManagementSystem.user.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

@Service

public class JwtService {

    private static final String SECRET_KEY = "7402bb3c24c35f15d1a7f1422078d9c1a4d9ebf1a276ff01ac84e6407625532e";
    private final TokenRepository tokenRepository;
    private long AccessTokenExpiration=86400000; //a day
    private long RefreshTokenExpiration=604800000; //7 days

    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>() ,userDetails,AccessTokenExpiration);
    }
    public String generateRefreshToken(UserDetails userDetails){
        return generateToken(new HashMap<>() ,userDetails,RefreshTokenExpiration);
    }

    public String generateToken(
            Map<String ,Object> extraClaims,
            UserDetails userDetails,
            Long expiration
    ){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expiration)) // 1000*60*24
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = extractUsername(token);
//        Token token1 = tokenRepository.findByToken(token).orElse(null);
        boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(token)
//                && token1 != null
                ;
        //            throw new ApiInvalidTokenException("token is invalid");
        return isValid;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token , Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (ExpiredJwtException ex)
        {
            Optional<Token> find_token= tokenRepository.findByToken(token);
            var the_Token=find_token.get();
            the_Token.setIsExpired(true);
            tokenRepository.save(the_Token);
            throw ex;
        }
    }



    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
