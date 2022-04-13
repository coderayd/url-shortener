package com.work.urlshortener.service;

import com.work.urlshortener.converter.ShortUrlDtoConverter;
import com.work.urlshortener.dto.request.ShortUrlRequestDto;
import com.work.urlshortener.dto.response.ShortUrlResponseDto;
import com.work.urlshortener.exception.CodeAlreadyExistsException;
import com.work.urlshortener.exception.UrlNotFoundException;
import com.work.urlshortener.model.ShortUrl;
import com.work.urlshortener.repository.ShortUrlRepository;
import com.work.urlshortener.util.RandomStringGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@Slf4j
public class ShortUrlService {

    private final ShortUrlDtoConverter shortUrlDtoConverter;
    private final ShortUrlRepository shortUrlRepository;
    private final RandomStringGenerator randomStringGenerator;

    public ShortUrlService(ShortUrlDtoConverter shortUrlDtoConverter, ShortUrlRepository shortUrlRepository, RandomStringGenerator randomStringGenerator) {
        this.shortUrlDtoConverter = shortUrlDtoConverter;
        this.shortUrlRepository = shortUrlRepository;
        this.randomStringGenerator = randomStringGenerator;
    }

    public ResponseEntity<List<ShortUrlResponseDto>> getAllShortUrl() {

        List<ShortUrl> shortUrlDtoList = shortUrlRepository.findAll();
        return ResponseEntity.ok(shortUrlDtoConverter.entityToDtoList(shortUrlDtoList));
    }

    public ResponseEntity<ShortUrlResponseDto> getShortUrlByCode(String code) {

        ShortUrl shortUrl = shortUrlRepository.findShortUrlByCode(code)
                .orElseThrow(() -> new UrlNotFoundException("Short Url Not Found"));

        return ResponseEntity.ok(shortUrlDtoConverter.entityToDto(shortUrl));
    }

    public ResponseEntity<ShortUrlResponseDto> handlerRedirect(String code) throws URISyntaxException {

        ShortUrl shortUrl = shortUrlRepository.findShortUrlByCode(code)
                .orElseThrow(() -> new UrlNotFoundException("Short Url Not Found"));

        URI uri = new URI(shortUrl.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(httpHeaders).build();
    }

    public ResponseEntity<ShortUrlResponseDto> createShortUrl(ShortUrlRequestDto shortUrlDto) {

        if ( shortUrlDto.getCode() == null || shortUrlDto.getCode().isEmpty()) {
            shortUrlDto.setCode(generateCode());
        } else if(shortUrlRepository.existsByCode(shortUrlDto.getCode())) {
            throw new CodeAlreadyExistsException("Code Already Exists!");
        }

        shortUrlDto.setCode(shortUrlDto.getCode().toUpperCase());
        ShortUrl shortUrl = shortUrlRepository.save(shortUrlDtoConverter.requestDtoToEntity(shortUrlDto));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}")
                .buildAndExpand(shortUrl.getCode()).toUri();

        return ResponseEntity.created(location).body(shortUrlDtoConverter.entityToDto(shortUrl));
    }

    private String generateCode() {

        String code;
        do {
            code = randomStringGenerator.generateRandomCode();
        }
        while (shortUrlRepository.existsByCode(code));

        return code;
    }


}
