package com.example.PAP2022.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserEditRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private MultipartFile image;

    public AppUserEditRequest(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
