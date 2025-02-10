package com.hinova.pdfmerge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MergeRequestDTO {
    private Long id;
    private String name;
    private List<String> pdfFiles;
}
