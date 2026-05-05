package com.keleybolos.api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestSenha {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hash = encoder.encode("123");

        System.out.println("SENHA CRIPTOGRAFADA:");
        System.out.println(hash);
    }
}