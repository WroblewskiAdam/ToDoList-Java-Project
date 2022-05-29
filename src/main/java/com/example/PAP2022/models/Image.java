package com.example.PAP2022.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Column(
            nullable = false,
            length = 200000
    )
    private String image;

    public Image(String name, String type, String image) {
        this.name = name;
        this.type = type;
        this.image = image;
    }
}