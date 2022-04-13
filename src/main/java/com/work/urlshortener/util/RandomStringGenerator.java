package com.work.urlshortener.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RandomStringGenerator {

    @Value("${short.url.code-length}")
    private int codeLength;

    public String generateRandomCode() {

        StringBuilder code = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        List<Character> letters = "abcdefghijklmnprstuvyzqw123456789"
                .toUpperCase()
                .chars()
                .mapToObj(x -> (char)x)
                .collect(Collectors.toList());

        Collections.shuffle(letters);

        for (int i = 0; i < codeLength; i++) {
            int index = secureRandom.nextInt(letters.size());
            code.append(letters.get(index));
        }

        return code.toString();
    }


}
