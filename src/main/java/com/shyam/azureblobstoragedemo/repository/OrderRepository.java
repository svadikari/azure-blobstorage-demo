package com.shyam.azureblobstoragedemo.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudAppendBlob;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.shyam.azureblobstoragedemo.exception.NotFoundException;
import com.shyam.azureblobstoragedemo.model.AuditEvent;
import com.shyam.azureblobstoragedemo.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SVadikari on 4/12/19
 */
@Repository
@Slf4j
public class OrderRepository {

    @Autowired
    private CloudBlobContainer blobContainer;

    public void saveOrder(AuditEvent auditEvent) {
        log.info("@@@@ Saving Order#::: " + auditEvent.getOrderId());
        try {
            CloudBlockBlob blob = blobContainer.getBlockBlobReference(auditEvent.getOrderId());
            ObjectMapper mapper = new ObjectMapper();
              /*List<Order> orders;
            log.info("@@@@ Saving Order#::: " + blob.exists());
            if(blob.exists()){
                orders = mapper.readValue(blob.downloadText(), new TypeReference<List<Order>>(){});
                orders.add(order);
                log.info("@@@@ Saving Order#::: " + orders.size());
                blob.uploadText(mapper.writeValueAsString(orders));
            }else{
                orders = new ArrayList<>();
                orders.add(order);
                blob.uploadText(mapper.writeValueAsString(orders));
            }*/
            CloudAppendBlob cloudAppendBlob = blobContainer.getAppendBlobReference(auditEvent.getOrderId());
            if(!cloudAppendBlob.exists()){
                cloudAppendBlob.createOrReplace();
                cloudAppendBlob.appendText(mapper.writeValueAsString(auditEvent));
            }else {
                cloudAppendBlob.appendText("," + mapper.writeValueAsString(auditEvent));
            }
        } catch (StorageException | URISyntaxException | IOException exp) {
            exp.printStackTrace();
        }
    }
    public  List<AuditEvent> getAuditOrderDetails(final String orderId) {
        log.info("@@@@ orderId:: " + orderId);
        List<AuditEvent> orders = null;
        CloudBlockBlob blob = null;
        try {
            blob  = blobContainer.getBlockBlobReference(orderId);
            ObjectMapper mapper = new ObjectMapper();
            orders = mapper.readValue("["+blob.downloadText()+"]", new TypeReference<List<AuditEvent>>(){});
        } catch(StorageException sExp) {
            throw new NotFoundException(sExp.getHttpStatusCode(), sExp.getMessage());
        } catch(URISyntaxException | IOException exp) {
            exp.printStackTrace();
        }
        return orders;
    }

    public Boolean deleteOrderAuditDetails(final String orderId) {
        Boolean deletedAudit = Boolean.FALSE;
        try {
            deletedAudit = blobContainer.getBlockBlobReference(orderId).deleteIfExists();
        } catch(StorageException sExp) {
            throw new NotFoundException(sExp.getHttpStatusCode(), sExp.getMessage());
        } catch(URISyntaxException exp) {
            exp.printStackTrace();
        }
        return deletedAudit;
    }
}