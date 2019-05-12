package com.shyam.azureblobstoragedemo.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudAppendBlob;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlobDirectory;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import com.shyam.azureblobstoragedemo.exception.NotFoundException;
import com.shyam.azureblobstoragedemo.model.AuditEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author SVadikari on 4/12/19
 */
@Repository
@Slf4j
public class OrderRepository {

    @Autowired
    private CloudBlobContainer cloudBlobContainer;

    public void saveOrder(AuditEvent auditEvent) {
        log.info("@@@@ Saving Order#::: " + auditEvent.getOrderId());
        saveBlockBlobsWithDirectoryStructure(auditEvent);
    }

    private void saveAppendBlob(AuditEvent auditEvent) {
        try {
            CloudAppendBlob cloudAppendBlob = cloudBlobContainer.getAppendBlobReference(auditEvent.getOrderId());
            ObjectMapper mapper = new ObjectMapper();
            if (!cloudAppendBlob.exists()) {
                cloudAppendBlob.createOrReplace();
                cloudAppendBlob.appendText(mapper.writeValueAsString(auditEvent));
            } else {
                cloudAppendBlob.appendText("," + mapper.writeValueAsString(auditEvent));
            }
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBlockBlobsWithDirectoryStructure(AuditEvent auditEvent) {
        CloudBlobDirectory cloudBlobDirectory = null;
        try {
            cloudBlobDirectory = cloudBlobContainer.getDirectoryReference(auditEvent.getOrderId());
            CloudBlockBlob blob = cloudBlobDirectory.getBlockBlobReference(auditEvent.getEventName() + "_" + auditEvent
                    .getVersionId());
            ObjectMapper mapper = new ObjectMapper();
            blob.uploadText(mapper.writeValueAsString(auditEvent));
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBlockBlob(AuditEvent auditEvent) {
        try {
            CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(auditEvent.getOrderId());
            ObjectMapper mapper = new ObjectMapper();
            blob.uploadText(mapper.writeValueAsString(auditEvent));
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<AuditEvent> getAuditOrderDetails(final String orderId) {
        log.info("@@@@ orderId:: " + orderId);
        ObjectMapper mapper = new ObjectMapper();
        List<AuditEvent> orders = new ArrayList<>();
        try {
            CloudBlobDirectory cloudBlobDirectory = cloudBlobContainer
                    .getDirectoryReference(orderId);
            Iterator<ListBlobItem> cloudBlockBlobs = cloudBlobDirectory.listBlobs().iterator();
            if (!cloudBlockBlobs.hasNext()) {
                throw new NotFoundException(404, "Change log not found for orderId:" + orderId);
            }
            while (cloudBlockBlobs.hasNext()) {
                try {
                    CloudBlockBlob cloudBlockBlob = ((CloudBlockBlob) cloudBlockBlobs.next());
                    orders.add(mapper.readValue(cloudBlockBlob.downloadText(),
                            new TypeReference<AuditEvent>() {
                            }));
                } catch (IOException | StorageException e) {
                    e.printStackTrace();
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Boolean deleteOrderAuditDetails(final String orderId) {
        Boolean deletedAudit = Boolean.FALSE;
        try {
            deletedAudit = cloudBlobContainer.getBlockBlobReference(orderId).deleteIfExists();
            BlobRequestOptions options = new BlobRequestOptions();
            cloudBlobContainer.listBlobs(null, false, null, options, null);
            Iterable<ListBlobItem> blobItem = cloudBlobContainer.listBlobs();
            blobItem.forEach(listBlobItem -> {
                try {
                    CloudAppendBlob b = (CloudAppendBlob) listBlobItem;
                    Date date = b.getProperties().getLastModified();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.DATE, -8);
                    if (cal.getTime().after(date)) {
                        System.out.println("Name [" + b.getName() + "] date [" + date + "]    [" + cal.getTime() + "]");
                        b.deleteIfExists();
                    }
                } catch (StorageException e) {
                    e.printStackTrace();
                }
            });
        } catch (StorageException sExp) {
            throw new NotFoundException(sExp.getHttpStatusCode(), sExp.getMessage());
        } catch (URISyntaxException exp) {
            exp.printStackTrace();
        }
        return deletedAudit;
    }
}