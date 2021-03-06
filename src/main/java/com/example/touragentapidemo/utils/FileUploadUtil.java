package com.example.touragentapidemo.utils;

import com.example.touragentapidemo.appconfig.FireBaseProperties;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * This class is for creating connection between Firebase and my application.
 */
@Component
public class FileUploadUtil {
    final
    FireBaseProperties fireBaseProperties;
    private static String TEMP_URL = "";

    public FileUploadUtil(FireBaseProperties fireBaseProperties) {
        this.fireBaseProperties = fireBaseProperties;
    }

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {

            ClassPathResource serviceAccount = new ClassPathResource(fireBaseProperties.getJson());

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(fireBaseProperties.getBucketName())
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String uploadFile(File file, String fileName) throws IOException {

        BlobId blobId = BlobId.of(fireBaseProperties.getBucketName(), fileName);
        String type = Files.probeContentType(file.toPath());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(type).build();

        Path path = Paths.get(fireBaseProperties.getJsonPath());
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(path.toAbsolutePath().toString()));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return String.format(fireBaseProperties.getImageUrl(), URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

//    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
//        File tempFile = new File(fileName);
//        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
//            fos.write(multipartFile.getBytes());
//        }
//        return tempFile;
//    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String upload(File file) {

        try {
            String fileName = file.getName();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.
            TEMP_URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            return TEMP_URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "500 Unsuccessfully Uploaded!";
        }

    }

    public void delete(String url) {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(url);
        blob.delete();
    }


}
