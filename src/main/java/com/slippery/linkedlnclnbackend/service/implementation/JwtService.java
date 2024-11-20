package com.slippery.linkedlnclnbackend.service.implementation;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final String SECRETSTRING ="210cc6288f81b4f3503eefded2346682a2b711304701da632f398b5685cde95862cd60d4a5035356e34b7461a757376abd4750a4ee4a20d8e5c8905b9dbde18e4116500f1f3cc8fa9845177c6bc4a762d1bb8cea2b62075ccc6b3d13e5105f4ac57ec6fe0d8b408838a44e523f9de10b8ccf7de90257798110433ed85551c6bc811f7044d0d5c0b2fe589ce19406d866bd5ea600410e9276b5cc4f7430782d79a96cbef4f8b60074cc11427bfbcadb6373fc041d62862e6ef596f21f214a66f06185c9cd32d148f345ae49b2873fb40567d16b361fdff84d09b50ca9fcd0159b885a95c8210880d4df87fdb3b5971de9c13f8b5822ff5b398a5c0797f1d5a3db";
    private final Long EXPIRATIONTIME =86400000L;

    private SecretKey generateKey(){
        byte[] keyBytes = Base64.getDecoder().decode(SECRETSTRING);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateJwtToken(String username){
        Map<String,Object> claims =new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date( System.currentTimeMillis()+EXPIRATIONTIME))
                .and()
                .signWith(generateKey())
                .compact();
    }



}
