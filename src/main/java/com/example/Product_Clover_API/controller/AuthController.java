package com.example.Product_Clover_API.controller;

import com.example.Product_Clover_API.entity.Owner;
import com.example.Product_Clover_API.payload.OwnerDto;
import com.example.Product_Clover_API.repository.OwnerRepository;
import com.example.Product_Clover_API.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final OwnerRepository ownerRepository;

    public AuthController(AuthService authService, OwnerRepository ownerRepository) {
        this.authService = authService;
        this.ownerRepository = ownerRepository;
    }

    @PostMapping("/owner/sign-up")
    public ResponseEntity<String> createOwner(@RequestBody Owner owner) {
        String result = authService.registerOwner(owner);

        if (result.equals("Owner Created")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/owner/login")
    public ResponseEntity<String> verifyLogin(@RequestBody OwnerDto ownerDto) {
        String jwtToken = authService.authenticate(ownerDto);
        if (jwtToken != null) {
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

}
