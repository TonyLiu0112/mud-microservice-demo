package com.tony.demo.microservice.mud;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class JunitTest2 {

    private int port = 9998;

    @SuppressWarnings({"rawtypes", "unchecked"})
//	@Test
    public void getJwtTokenByTrustedClient() throws IOException {
        ResponseEntity<String> response = new TestRestTemplate("acme", "acmesecret")
                .postForEntity("http://localhost:" + port + "/oauth/token" +
                        "?client_id=acme&grant_type=client_credentials", null, String.class);
        String responseText = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        HashMap jwtMap = new ObjectMapper().readValue(responseText, HashMap.class);

        assertEquals("bearer", jwtMap.get("token_type"));
        assertEquals("read write", jwtMap.get("scope"));
        assertTrue(jwtMap.containsKey("access_token"));
        assertTrue(jwtMap.containsKey("expires_in"));
        assertTrue(jwtMap.containsKey("jti"));
        String accessToken = (String) jwtMap.get("access_token");

        Jwt jwtToken = JwtHelper.decode(accessToken);

        String claims = jwtToken.getClaims();

        HashMap claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
        assertEquals("spring-boot-application", ((List<String>) claimsMap.get("aud")).get(0));
        assertEquals("trusted-app", claimsMap.get("client_id"));
        assertEquals("read", ((List<String>) claimsMap.get("scope")).get(0));
        assertEquals("write", ((List<String>) claimsMap.get("scope")).get(1));
        List<String> authorities = (List<String>) claimsMap.get("authorities");
        assertEquals(1, authorities.size());
        assertEquals("ROLE_TRUSTED_CLIENT", authorities.get(0));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test(expected = ResourceAccessException.class)
    public void accessWithUnknownClientID() throws JsonParseException, JsonMappingException, IOException {
        ResponseEntity<String> response = new TestRestTemplate("trusted-app", "secrets").postForEntity("http://localhost:" + port + "/oauth/token?client_id=trusted-app&grant_type=client_credentials", null, String.class);
    }

    @Test
    public void accessProtectedResourceByJwtToken() throws JsonParseException, JsonMappingException, IOException {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        ResponseEntity<String> response = new TestRestTemplate()
                .exchange("http://localhost:7000/demo/tokens", HttpMethod.GET, new HttpEntity<>(null, headers), String.class);

        System.out.println(response.getBody());

    }

    public String getAccessToken() throws IOException {
        ResponseEntity<String> response = new TestRestTemplate("acme", "acmesecret")
                .postForEntity("http://localhost:" + port + "/oauth/token" +
                        "?client_id=acme&grant_type=client_credentials", null, String.class);
        String responseText = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        HashMap jwtMap = new ObjectMapper().readValue(responseText, HashMap.class);

        return (String) jwtMap.get("access_token");
    }
}
