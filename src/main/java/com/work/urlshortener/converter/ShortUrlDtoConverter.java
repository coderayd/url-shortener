package com.work.urlshortener.converter;

import com.work.urlshortener.dto.request.ShortUrlRequestDto;
import com.work.urlshortener.dto.response.ShortUrlResponseDto;
import com.work.urlshortener.model.ShortUrl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShortUrlDtoConverter {

    public ShortUrlResponseDto entityToDto(ShortUrl shortUrl) {

        if (shortUrl == null) {
            return new ShortUrlResponseDto();
        }

        return ShortUrlResponseDto.builder()
                .id(shortUrl.getId())
                .url(shortUrl.getUrl())
                .code(shortUrl.getCode())
                .build();
    }

    public List<ShortUrlResponseDto> entityToDtoList(List<ShortUrl> shortUrlList) {

        return shortUrlList.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public ShortUrl requestDtoToEntity(ShortUrlRequestDto shortUrlRequestDto) {

        if(shortUrlRequestDto == null){
            return new ShortUrl();
        }

        return ShortUrl.builder()
                .url(shortUrlRequestDto.getUrl())
                .code(shortUrlRequestDto.getCode())
                .build();
    }

}
