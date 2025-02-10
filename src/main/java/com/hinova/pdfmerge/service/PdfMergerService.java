package com.hinova.pdfmerge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hinova.pdfmerge.dto.MergeRequestDTO;
import com.hinova.pdfmerge.dto.PdfMergeResponseDTO;
import com.hinova.pdfmerge.model.PdfMergeHistory;
import com.hinova.pdfmerge.repository.PdfMergeHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PdfMergerService {

    private final PdfMergeHistoryRepository pdfMergeHistoryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PdfMergeResponseDTO mergePdfs(List<MultipartFile> files, String outputFileName) {

        PdfMergeHistory history = new PdfMergeHistory();
        history.setName(outputFileName);
        history.setCreatedAt(LocalDateTime.now());
        history.setStatus("PENDING");
        PdfMergeHistory savedHistory = pdfMergeHistoryRepository.save(history);

        try {
            List<String> encodedPdfs = encodePdfs(files);
            MergeRequestDTO request = new MergeRequestDTO(savedHistory.getId(), outputFileName, encodedPdfs);
            String message = new ObjectMapper().writeValueAsString(request);
            new Thread(() -> {
                kafkaTemplate.send("pdf-merge-topic", message);
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new PdfMergeResponseDTO(savedHistory.getId(), savedHistory.getName(), savedHistory.getLink(), savedHistory.getCreatedAt(), savedHistory.getStatus());
    }

    private List<String> encodePdfs(List<MultipartFile> files) throws IOException {
        List<String> encodedPdfs = new ArrayList<>();
        for (MultipartFile file : files) {
            encodedPdfs.add(Base64.getEncoder().encodeToString(file.getBytes()));
        }
        return encodedPdfs;
    }

    public List<PdfMergeResponseDTO> getAllMergeHistory() {
        return pdfMergeHistoryRepository.findAll().stream().map(history -> new PdfMergeResponseDTO(history.getId(), history.getName(), history.getLink(), history.getCreatedAt(), history.getStatus())).collect(Collectors.toList());
    }

    public PdfMergeResponseDTO getMergeHistoryById(Long id) {
        PdfMergeHistory history = pdfMergeHistoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Histórico não encontrado"));
        return new PdfMergeResponseDTO(history.getId(), history.getName(), history.getLink(), history.getCreatedAt(), history.getStatus());
    }

    public void mergePdfFiles(MergeRequestDTO request) throws IOException {
        List<String> pdfFiles = request.getPdfFiles();

        byte[] pdfMerge = mergePdfs(pdfFiles);

        String link = this.saveMergedPdf(pdfMerge, request.getName());
        pdfMergeHistoryRepository.findById(request.getId()).map(history -> {
            history.setStatus("COMPLETED");
            history.setLink(link);
            return pdfMergeHistoryRepository.save(history);
        }).orElseThrow(() -> new EntityNotFoundException("History not found with id:" + request.getId()));
    }

    private String saveMergedPdf(byte[] pdfBytes, String fileName) {

        //SEND TO AWS

        return "http://example.com/pdfs/" + fileName;
    }

    public byte[] mergePdfs(List<String> pdfFiles) throws IOException {
        // Cria o PDFMergerUtility para mesclar os PDFs
        PDFMergerUtility mergerUtility = new PDFMergerUtility();

        // Adiciona cada PDF codificado em base64 ao utility
        for (String base64Pdf : pdfFiles) {
            byte[] pdfBytes = Base64.getDecoder().decode(base64Pdf);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pdfBytes);

            // Adiciona o InputStream ao merger
            mergerUtility.addSource(byteArrayInputStream);
        }

        // Criar um ByteArrayOutputStream para capturar o PDF mesclado
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Define o destino para o ByteArrayOutputStream
        mergerUtility.setDestinationStream(byteArrayOutputStream);

        // Mescla os PDFs
        mergerUtility.mergeDocuments(null);

        return byteArrayOutputStream.toByteArray();
    }
}