package com.camunda.entity;

import com.camunda.model.TranscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image_transcription")
public class ImageTranscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean hasImage;
    private Integer imageCount;
    private String body;
    @Enumerated(EnumType.STRING)
    private TranscriptionStatus status;
    private String correlationKey;
}
