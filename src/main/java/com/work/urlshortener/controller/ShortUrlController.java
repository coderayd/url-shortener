package com.work.urlshortener.controller;

import com.work.urlshortener.dto.request.ShortUrlRequestDto;
import com.work.urlshortener.dto.response.ShortUrlResponseDto;
import com.work.urlshortener.service.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/url")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShortUrlResponseDto>> getAllShortUrl(){

        return shortUrlService.getAllShortUrl();
    }

    @GetMapping("/show/{code}")
    public ResponseEntity<ShortUrlResponseDto> getShortUrlByCode(@Valid @NotEmpty @PathVariable String code){

        return shortUrlService.getShortUrlByCode(code);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> handlerRedirect(@Valid @NotEmpty @PathVariable String code) throws URISyntaxException {

        return shortUrlService.handlerRedirect(code);
    }

    @PostMapping
    public ResponseEntity<ShortUrlResponseDto> createShortUrl(@Valid @RequestBody ShortUrlRequestDto shortUrlRequestDto){

        return shortUrlService.createShortUrl(shortUrlRequestDto);
    }


}
