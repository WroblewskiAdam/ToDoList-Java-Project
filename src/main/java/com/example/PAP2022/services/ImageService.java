package com.example.PAP2022.services;

import com.example.PAP2022.exceptions.ImageNotFoundException;
import com.example.PAP2022.models.Image;
import com.example.PAP2022.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Optional<Image> loadImageById(Long id) {
        return imageRepository.findById(id);
    }

    public Image getImage(Long id) throws ImageNotFoundException {
        if (loadImageById(id).isPresent()) {
            return loadImageById(id).get();
        } else {
            throw new ImageNotFoundException("Could not find image with ID " + id);
        }
    }

    public Image saveImage(MultipartFile file) throws IOException {
        String img = Base64.getEncoder().encodeToString(file.getBytes());
        return imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .image(img).build());
    }
}
