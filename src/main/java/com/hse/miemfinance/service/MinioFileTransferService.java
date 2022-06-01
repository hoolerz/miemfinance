package com.hse.miemfinance.service;

import com.amazonaws.util.IOUtils;
import com.hse.miemfinance.config.StorageProperties;
import com.hse.miemfinance.model.entity.FileEntity;
import com.hse.miemfinance.model.exception.BusinessException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinioFileTransferService {

	private final StorageProperties storageProperties;

	private MinioClient minioClient;

	public MinioFileTransferService(StorageProperties storageProperties) {
		this.storageProperties = storageProperties;
		this.minioClient = MinioClient.builder()
				.endpoint(storageProperties.getEndpoint())
				.credentials(storageProperties.getAccessKey(), storageProperties.getSecretKey())
				.build();
	}

	public byte[] downloadFile(String fileName) {
		return downloadFile(storageProperties.getBucket(), fileName);
	}

	@SneakyThrows
	public byte[] downloadFile(String bucketName, String fileName) {
		byte[] bytes;
		try (InputStream stream = minioClient.getObject(
				GetObjectArgs.builder()
						.bucket(bucketName)
						.object(fileName)
						.build())) {
			bytes = IOUtils.toByteArray(stream);
		}
		return bytes;
	}

	public String uploadFile(MultipartFile file, String fileName) {
		try {
			return uploadFileFromMultiPartFile(file, storageProperties.getBucket(), fileName);
		} catch (Exception e) {
			throw new BusinessException().withMessage("Faf");
		}
	}

	public String uploadFile(byte[] content, String fileName, String contentType) {
		try {
			return uploadFileFromBytes(content, fileName, contentType, storageProperties.getBucket());
		} catch (Exception e) {
			throw new BusinessException().withMessage("Faf");
		}
	}

	public void deleteFile(FileEntity entity) {
		deleteFile(storageProperties.getBucket(), entity);
	}

	@SneakyThrows
	private String uploadFileFromMultiPartFile(MultipartFile file, String bucketName, String fileName) {
		String fileType = file.getContentType();
		InputStream inputStream = file.getInputStream();
		minioClient.putObject(
				PutObjectArgs.builder()
						.bucket(bucketName)
						.object(fileName)
						.stream(inputStream, file.getSize(), -1)
						.contentType(fileType)
						.build());
		
		return fileName;
	}

	@SneakyThrows
	private String uploadFileFromBytes(byte[] content, String fileName, String contentType, String bucketName) {
		InputStream inputStream = new ByteArrayInputStream(content);
		minioClient.putObject(
				PutObjectArgs.builder()
						.bucket(bucketName)
						.object(fileName)
						.stream(inputStream, content.length, -1)
						.contentType(contentType)
						.build());

		return fileName;
	}

	@SneakyThrows
	private void deleteFile(String bucket, FileEntity entity) {
		String s3Filename = entity.getName();
		minioClient.removeObject(
				RemoveObjectArgs.builder()
						.bucket(bucket)
						.object(s3Filename)
						.build());
	}

}
