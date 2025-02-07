package com.hinova.pdfmerge.service;

import com.hinova.pdfmerge.dto.PdfMergeResponseDTO;
import com.hinova.pdfmerge.model.PdfMergeHistory;
import com.hinova.pdfmerge.repository.PdfMergeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PdfMergerService {

    private final PdfMergeHistoryRepository pdfMergeHistoryRepository;
    private final KafkaProducerService kafkaProducerService;

    /***public PdfMergeResponseDTO mergePdfs(List<MultipartFile> files, String outputFileName) {

     // Salva a solicitação no banco de dados com status "PENDENTE"
     PdfMergeHistory history = new PdfMergeHistory();
     history.setName(outputFileName);
     history.setStatus("PENDENTE");
     history.setCreatedAt(LocalDateTime.now());
     PdfMergeHistory savedHistory = pdfMergeHistoryRepository.save(history);

     // Publica a mensagem no Kafka
     kafkaProducerService.sendMessage("pdf-merger-topic", String.valueOf(savedHistory.getId()));

     // Retorna uma resposta imediata
     return new PdfMergeResponseDTO(
     savedHistory.getId(),
     outputFileName,
     "Solicitação recebida. Processando...",
     LocalDateTime.now()
     );
     }*/

    public PdfMergeResponseDTO mergePdfs(List<MultipartFile> files, String outputFileName) {
        String uniqueFileName = outputFileName + "_" + UUID.randomUUID() + ".pdf";
        try {
            byte[] mergedPdfBytes = mergePdfFiles(files);
            String fileLink = saveMergedPdf(mergedPdfBytes, uniqueFileName);

            PdfMergeHistory history = new PdfMergeHistory();
            history.setName(uniqueFileName);
            history.setLink(fileLink);
            history.setCreatedAt(LocalDateTime.now());

            PdfMergeHistory savedHistory = pdfMergeHistoryRepository.save(history);

            return new PdfMergeResponseDTO(savedHistory.getId(), savedHistory.getName(), savedHistory.getLink(), savedHistory.getCreatedAt());
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao mesclar PDFs", ex);
        }
    }

    public List<PdfMergeResponseDTO> getAllMergeHistory() {
        return pdfMergeHistoryRepository.findAll().stream().map(history -> new PdfMergeResponseDTO(history.getId(), history.getName(), history.getLink(), history.getCreatedAt())).collect(Collectors.toList());
    }

    public PdfMergeResponseDTO getMergeHistoryById(Long id) {
        PdfMergeHistory history = pdfMergeHistoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Histórico não encontrado"));
        return new PdfMergeResponseDTO(history.getId(), history.getName(), history.getLink(), history.getCreatedAt());
    }

    public byte[] mergePdfFiles(List<MultipartFile> files) throws IOException {
        PDFMergerUtility merger = new PDFMergerUtility();

        for (MultipartFile file : files) {
            InputStream inputStream = file.getInputStream();
            merger.addSource(inputStream);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        merger.setDestinationStream(outputStream);

        merger.mergeDocuments(null);

        return outputStream.toByteArray();
    }

    private String saveMergedPdf(byte[] pdfBytes, String fileName) {
        String fileLink = "http://example.com/pdfs/" + fileName;

        //SEND TO AWS

        return fileLink;
    }
}