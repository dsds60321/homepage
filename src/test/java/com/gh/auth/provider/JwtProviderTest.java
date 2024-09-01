package com.gh.auth.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

class JwtProviderTest {

    @Test
    void validToken() {
        String SECRET_KEY = "bf91149297f98761d4266feb805e1c6f3164bd01fffe0796c746b74041c0a6a4652dc4a8dc1f7ce32e56e5b950efbf818594fee07f76497c52e75c10ae7f8582";
        String toke ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkc2RzNjAxIiwidXNlci1pZCI6MSwidXNlci1lbWFpbCI6ImRzZHM2MzIxQGdtYWlsLmNvbSIsImlhdCI6MTcyNTE2NDU1NywiZXhwIjoxNzI1MTY4MTU3fQ.ga4WryF8bcwSK_n_XixTF_HUcpW2_5zHSCAzPxxs-4gcHe_pbZvluzCdLXvIdboUEWe_pW6HftxK-YdWOU_03w";
        Jws<Claims> claim = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(toke);
        System.out.println(claim.getBody());
    }

}