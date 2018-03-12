package com.koverse.example.gcp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class GCPStorageReadWriteExample {
	public static void main(String... args) throws Exception {
	    GCPStorageReadWriteExample example = new GCPStorageReadWriteExample();
	    example.readFromBucket();
	 }
	
	public void createBucket(String bucketName) {
	    Storage storage = StorageOptions.getDefaultInstance().getService();
	    Bucket bucket = storage.create(BucketInfo.of(bucketName));
	    System.out.printf("Bucket %s created.%n", bucket.getName());
	}
	
	public void readFromBucket() throws FileNotFoundException, IOException {
		String projectId = "stable-hologram-197813";
		String acctJson = "src/main/resources/MyFirstProject-read-only.json";
		String bucketName = "koverse-test-bucket";
		
		Storage storage = StorageOptions.newBuilder()
	            .setProjectId(projectId)
	            .setCredentials(GoogleCredentials.fromStream(new FileInputStream(acctJson)))
	            .build()
	            .getService();
		Blob blob = storage.get(bucketName, "temp.txt");
		String fileContent = new String(blob.getContent());
		System.out.println(fileContent);
	}
	
}