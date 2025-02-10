package com.hinova.pdfmerge.controller;

import com.hinova.pdfmerge.dto.PdfMergeResponseDTO;
import com.hinova.pdfmerge.service.PdfMergerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfMergerController {

    private final PdfMergerService pdfMergerService;

    @PostMapping("/merge")
    public ResponseEntity<PdfMergeResponseDTO> mergePdfs(@RequestParam("files") List<MultipartFile> files,
                                                         @RequestParam("outputFileName") String outputFileName) {
        PdfMergeResponseDTO response = pdfMergerService.mergePdfs(files, outputFileName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<PdfMergeResponseDTO>> getAllMergeHistory() {
        List<PdfMergeResponseDTO> history = pdfMergerService.getAllMergeHistory();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<PdfMergeResponseDTO> getMergeHistoryById(@PathVariable Long id) {
        PdfMergeResponseDTO history = pdfMergerService.getMergeHistoryById(id);
        return ResponseEntity.ok(history);
    }
}
