package org.piva.codelab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            return ResponseEntity.ok(authentication.getPrincipal().toString());
        }
        else {
            return ResponseEntity.ok("Not an OAuth2User");
        }
    }
}
