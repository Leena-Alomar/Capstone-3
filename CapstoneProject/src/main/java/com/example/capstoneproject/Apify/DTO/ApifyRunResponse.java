package com.example.capstoneproject.Apify.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ApifyRunResponse {

    private String id;               // run id
    private String defaultDatasetId; // dataset id
    private String status;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDefaultDatasetId() { return defaultDatasetId; }
    public void setDefaultDatasetId(String defaultDatasetId) {
        this.defaultDatasetId = defaultDatasetId;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
