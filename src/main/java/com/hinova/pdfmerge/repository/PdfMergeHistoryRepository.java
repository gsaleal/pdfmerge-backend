package com.hinova.pdfmerge.repository;

import com.hinova.pdfmerge.model.PdfMergeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfMergeHistoryRepository extends JpaRepository<PdfMergeHistory, Long> {
}