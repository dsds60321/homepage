package com.gh.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TempMailRequestDTO {

    private String email;
    private String csrf;
}
