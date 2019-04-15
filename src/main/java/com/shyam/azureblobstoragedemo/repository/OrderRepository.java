package com.shyam.azureblobstoragedemo.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudAppendBlob;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.shyam.azureblobstoragedemo.exception.NotFoundException;
import com.shyam.azureblobstoragedemo.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author SVadikari on 4/12/19
 */
@Repository
@Slf4j
public class OrderRepository {

    @Autowired
    private CloudBlobContainer blobContainer;

    public void saveOrder(Order order) {
        log.info("@@@@ Saving Order#::: " + order.getOrderId());
        try {
            CloudBlockBlob blob = blobContainer.getBlockBlobReference(order.getOrderId());
            ObjectMapper mapper = new ObjectMapper();
            blob.uploadText(mapper.writeValueAsString(order));
            /*CloudAppendBlob cloudAppendBlob = blobContainer.getAppendBlobReference(order.getOrderId());
            cloudAppendBlob.createOrReplace();
            cloudAppendBlob.appendText(mapper.writeValueAsString(order));*/
        } catch (StorageException | URISyntaxException | IOException exp) {
            exp.printStackTrace();
        }
    }
    public Order getOrder(final String orderId) {
        log.info("@@@@ orderId:: " + orderId);
        Order order = null;
        CloudBlockBlob blob = null;
        try {
            blob = blobContainer.getBlockBlobReference(orderId);
            ObjectMapper mapper = new ObjectMapper();
            order = mapper.readValue(blob.downloadText(), Order.class);
        } catch(StorageException sExp) {
            throw new NotFoundException(sExp.getHttpStatusCode(), sExp.getMessage());
        } catch(URISyntaxException | IOException exp) {
            exp.printStackTrace();
        }
        return order;
    }
}
