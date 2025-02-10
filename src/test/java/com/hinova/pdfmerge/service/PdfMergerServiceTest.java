package com.hinova.pdfmerge.service;

import com.hinova.pdfmerge.dto.PdfMergeResponseDTO;
import com.hinova.pdfmerge.model.PdfMergeHistory;
import com.hinova.pdfmerge.repository.PdfMergeHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PdfMergerServiceTest {

    @Mock
    private PdfMergeHistoryRepository pdfMergeHistoryRepository;


    @InjectMocks
    private PdfMergerService pdfService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMergePdfs_Success() throws IOException {
    }

    @Test
    void mergePdfs() {
    }

    @Test
    void getAllMergeHistory() {
    }

    @Test
    void getMergeHistoryById() {
    }

    @Test
    void mergePdfFiles() {
    }

}