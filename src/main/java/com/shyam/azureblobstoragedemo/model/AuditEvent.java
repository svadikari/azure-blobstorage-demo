package com.shyam.azureblobstoragedemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * AuditEvent
 */

@Data
public class AuditEvent {

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("versionId")
    private Integer versionId;

    @JsonProperty("eventName")
    @Size(max = 100, message = "eventName should be within 100 character length")
    private String eventName;

    @JsonProperty("details")
    @Valid
    private List<Attribute> details;

    @JsonProperty("notes")
    @Size(max = 2000, message = "notes should be within 2000 character length")
    private String notes;

    @JsonProperty("createdBy")
    @Size(max = 50)
    private String createdBy;

    @JsonProperty("createdOn")
    @Size(max = 40, message = "createdOn shoud be ISO data format yyyy-MM-ddTHH:mm:ss.SSSZ")
    private String createdOn;

    @JsonProperty("publishedTime")
    @Size(max = 40, message = "publishedTime shoud be ISO data format yyyy-MM-ddTHH:mm:ss.SSSZ")
    private String publishedTime;

}


