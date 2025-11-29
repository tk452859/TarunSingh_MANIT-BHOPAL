package org.example.billextractor.controller;

import jakarta.validation.Valid;
import org.example.billextractor.model.BillRequest;
import org.example.billextractor.model.BillResponse;
import org.example.billextractor.service.AIBillExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api") // ← ADD THIS LINE
public class BillExtractionController {
    @GetMapping("/")
    public String root() {
        return "Bill Extractor API - Use /api/extract-bill-data";
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Enhanced Bill Extraction API is running");
    }



    @Autowired
    private AIBillExtractionService billExtractionService;

    @PostMapping("/extract-bill-data")
    public ResponseEntity<BillResponse> extractBillData(
            @Valid @RequestBody BillRequest request) {

        try {
            BillResponse response = billExtractionService.extractBillData(request.getDocument());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            BillResponse errorResponse = new BillResponse();
            errorResponse.setIs_success(false);
            errorResponse.setToken_usage(Map.of("total_tokens", 0, "input_tokens", 0, "output_tokens", 0));
            return ResponseEntity.ok(errorResponse);
        }
    }




}
