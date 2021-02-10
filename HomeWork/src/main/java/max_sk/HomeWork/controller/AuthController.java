package max_sk.HomeWork.controller;

import lombok.RequiredArgsConstructor;
import max_sk.HomeWork.bean.JwtTokenUtil;
import max_sk.HomeWork.dto.JwtRequest;
import max_sk.HomeWork.dto.JwtResponse;
import max_sk.HomeWork.dto.UserDTO;
import max_sk.HomeWork.exceptions_handling.MarketError;
import max_sk.HomeWork.services.UserService;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    @PostMapping("/reg")
    public ResponseEntity<?> registrationUser(@RequestBody UserDTO userDTO){
        userDTO.setRegPassword(passwordEncoder.encode(userDTO.getRegPassword()));
        String pass = passwordEncoder.encode(userDTO.getRegPassword());
        try {
        userService.registrationUser(userDTO);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "username is busy"), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(userDTO);
    }
}
