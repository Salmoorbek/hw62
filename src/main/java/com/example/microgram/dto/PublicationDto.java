package com.example.microgram.dto;

import com.example.microgram.entity.Publication;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

public class PublicationDto {
    private String img;
    private String description;
    private LocalDateTime publicationTime;
    private int userId;

    public static PublicationDto from(Publication publication){
        return builder()
                .img(publication.getImg())
                .userId(publication.getUserID())
                .description(publication.getDescription())
                .publicationTime(publication.getTimeOfPublication())
                .build();
    }
}
