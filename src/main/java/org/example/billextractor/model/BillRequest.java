package org.example.billextractor.model;
import jakarta.validation.constraints.NotBlank;

public class BillRequest {
    @NotBlank(message = "Document URL is required")
    private String document;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

}
