package com.example.agro.security.jwt;

import com.example.agro.security.CustomerDetails;
import com.example.agro.security.CustomerDetailsService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${agro.app.jwtSecretKey}")
    private String jwtSecretKey;

    @Value("${agro.app.jwtValidity}")
    private int jwtValidity;

    @Autowired
    CustomerDetailsService customerDetailsService;


    @PostConstruct
    protected void init() {
        jwtSecretKey = Base64.getEncoder().encodeToString(jwtSecretKey.getBytes());
    }

    public String generateJwtToken(Authentication authentication) {

        CustomerDetails customerPrincipal = (CustomerDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((customerPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtValidity*1000))
                .signWith(SignatureAlgorithm.ES256.HS512, jwtSecretKey)
                .compact();
    }

    public boolean validateToken(String token)  {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        UserDetails customerDetails = customerDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(customerDetails, "", customerDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
