package com.work.urlshortener.dto.request;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlRequestDto {

    @NotBlank
    private String url;
    private String code;
}
