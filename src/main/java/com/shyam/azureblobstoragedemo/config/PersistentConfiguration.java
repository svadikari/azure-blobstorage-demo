package com.shyam.azureblobstoragedemo.config;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

/**
 * @author SVadikari on 4/12/19
 */
@Configuration
@Slf4j
public class PersistentConfiguration {

    @Value("${azure.storage.account-name}")
    private String storageAccountName;

    @Value("${azure.storage.account-key}")
    private String storageAccountKey;

    @Value("${azure.storage.container-name}")
    private String storageContainerName;

    /*@Bean
    public ContainerURL loadContainer() {
        ContainerURL containerURL = null;
        try {
            log.info("storage name: " + storageAccountName + "::::Key: " + storageAccountKey);
            SharedKeyCredentials creds = new SharedKeyCredentials(storageAccountName, storageAccountKey);
            final ServiceURL serviceURL = new ServiceURL(new URL("https://" + storageAccountName + ".blob.core.windows.net"), StorageURL.createPipeline(creds, new PipelineOptions()));
            containerURL = serviceURL.createContainerURL(storageContainerName);
        } catch (InvalidKeyException | MalformedURLException e) {
            e.printStackTrace();
        }
        return containerURL;
    }*/

    @Bean
    public CloudBlobContainer getContainer() {
        CloudBlobContainer container = null;
        try {
            String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName="
                    + storageAccountName + ";AccountKey=" + storageAccountKey;
            CloudStorageAccount storageAccount = null;
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(storageContainerName);
            container.createIfNotExists();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }
        return container;
    }

}
