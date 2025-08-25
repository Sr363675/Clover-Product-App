package com.example.Product_Clover_API.config;

import com.example.Product_Clover_API.entity.Owner;
import com.example.Product_Clover_API.repository.OwnerRepository;
import com.example.Product_Clover_API.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private  JWTService jwtService;
    private OwnerRepository ownerRepository;

    public JWTFilter(JWTService jwtService, OwnerRepository ownerRepository) {
        this.jwtService = jwtService;
        this.ownerRepository = ownerRepository;
    }

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            String jwttoken = token.substring(7, token.length());

            //System.out.println(token);
            //System.out.println(jwttoken);


            String ownername = jwtService.getOwnername(jwttoken);
            //System.out.println(ownername);
            Optional<Owner> opOwner = ownerRepository.findByOwnername(ownername);
            if (opOwner.isPresent()) {
                Owner owner = opOwner.get();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(owner, null,
                        Collections.singleton(new SimpleGrantedAuthority(owner.getRole())));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
        filterChain.doFilter(request, response);

    }

}