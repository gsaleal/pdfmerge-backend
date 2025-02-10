package com.hinova.pdfmerge.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hinova.pdfmerge.dto.MergeRequestDTO;
import com.hinova.pdfmerge.service.PdfMergerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PdfMergeConsumer {

    private final PdfMergerService pdfMergeService;

    @KafkaListener(topics = "pdf-merge-topic", groupId = "pdf-merge-group")
    public void consume(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        MergeRequestDTO request = objectMapper.readValue(message, MergeRequestDTO.class);
        pdfMergeService.mergePdfFiles(request);
    }
}
