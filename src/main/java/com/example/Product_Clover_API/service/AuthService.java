package com.example.Product_Clover_API.service;

import com.example.Product_Clover_API.entity.Owner;
import com.example.Product_Clover_API.payload.OwnerDto;
import com.example.Product_Clover_API.repository.OwnerRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final OwnerRepository ownerRepository;
    private final JWTService jwtService;

    public AuthService(OwnerRepository ownerRepository, JWTService jwtService) {
        this.ownerRepository = ownerRepository;
        this.jwtService = jwtService;
    }

    public String registerOwner(Owner owner) {
        // Check email
        Optional<Owner> opEmail = ownerRepository.findByEmail(owner.getEmail());
        if (opEmail.isPresent()) {
            return "Email id exists";
        }

        // Check ownername
        Optional<Owner> opOwnername = ownerRepository.findByOwnername(owner.getOwnername());
        if (opOwnername.isPresent()) {
            return "Ownername exists";
        }

        // Encrypt password
        String hashpw = BCrypt.hashpw(owner.getPassword(), BCrypt.gensalt(12));
        owner.setPassword(hashpw);
        owner.setRole("ROLE_OWNER");

        // Save owner
        ownerRepository.save(owner);

        return "Owner Created";
    }


    public String authenticate(OwnerDto ownerDto) {
        Optional<Owner> opOwner = ownerRepository.findByOwnername(ownerDto.getOwnername());

        if (opOwner.isPresent()) {
            Owner owner = opOwner.get();
            boolean status = BCrypt.checkpw(ownerDto.getPassword(), owner.getPassword());
            if (status) {
                return jwtService.generateToken(owner.getOwnername());
            }
        }
        return null;
    }

}
