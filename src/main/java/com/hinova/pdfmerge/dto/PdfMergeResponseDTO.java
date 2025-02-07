package com.hinova.pdfmerge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfMergeResponseDTO {
    private Long id;
    private String name;
    private String link;
    private LocalDateTime createdAt;
}
