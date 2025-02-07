package com.hinova.pdfmerge.kafka;

import com.hinova.pdfmerge.service.PdfMergerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final PdfMergerService pdfMergerService;

    public KafkaConsumerService(PdfMergerService pdfMergerService) {
        this.pdfMergerService = pdfMergerService;
    }

    @KafkaListener(topics = "pdf-merger-topic", groupId = "pdf-merger-group")
    public void consume(String requestId) {
        //pdfMergerService.processMergeRequest(requestId);
    }
}
