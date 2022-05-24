package com.example.PAP2022.repository;

import com.example.PAP2022.models.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HexFormat;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Test
    void shouldWork() {
        Image image1 = new Image(1L, "1111111111", "image/jpeg", HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));

        System.out.println(imageRepository.findById(1L).get());

        assertThat(image1).isEqualTo(imageRepository.findById(1L).get());
    }
}