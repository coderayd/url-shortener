package com.work.urlshortener.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlResponseDto {

    private Long id;
    private String url;
    private String code;
}
