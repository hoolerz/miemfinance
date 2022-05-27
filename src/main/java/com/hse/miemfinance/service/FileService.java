package com.hse.miemfinance.service;

import static com.hse.miemfinance.model.enums.Dictionaries.ExceptionMessages.NOT_FOUND;

import com.hse.miemfinance.model.dto.FileResponseDTO;
import com.hse.miemfinance.model.entity.FileEntity;
import com.hse.miemfinance.model.entity.user.User;
import com.hse.miemfinance.model.entity.user.UserAttachment;
import com.hse.miemfinance.model.exception.BusinessException;
import com.hse.miemfinance.repository.FileEntityRepository;
import com.hse.miemfinance.repository.UserAttachmentRepository;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

	private final FileEntityRepository fileEntityRepository;

	private final UserAttachmentRepository userAttachmentRepository;

	private final MinioFileTransferService minioFileTransferService;

	private final UserService userService;

	public HttpEntity<byte[]> downloadFile(Long fileId) {
		FileEntity fileEntity = fileEntityRepository.findById(fileId).orElse(null);
		if (fileEntity == null) {
			throw new BusinessException().withMessage(NOT_FOUND);
		}
		byte[] content = minioFileTransferService.downloadFile(fileEntity.getName());
		return buildHttpEntity(content, fileEntity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public FileResponseDTO uploadFile(MultipartFile file) {
		FileEntity entity = createFileEntity(file);
		User user = userService.getCurrentUser();
		String fileName = entity.getName().split("/")[1];
		boolean existsUserAttachment = userAttachmentRepository.findAllByUser(user)
				.stream().anyMatch(userAttachment -> {
			FileEntity fileEntity = userAttachment.getEntity();
			return fileEntity.getName().split("/")[1].equals(fileName);
		});
		if (!existsUserAttachment) {
			userAttachmentRepository.deleteAllByUser(user);
			UserAttachment userAttachment = new UserAttachment();
			userAttachment.setUser(user);
			userAttachment.setEntity(entity);
			userAttachmentRepository.save(userAttachment);
		}
		return new FileResponseDTO(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteFile(Long fileId) {
		FileEntity file = fileEntityRepository.findById(fileId).orElse(null);
		User user = userService.getCurrentUser();
		if (file == null) {
			throw new BusinessException().withMessage(NOT_FOUND);
		}
		if (userAttachmentRepository.existsByUserAndAndEntity(user, file)) {
			userAttachmentRepository.deleteByUserAndEntity(user, file);
			deleteFileEntity(file);
		}
	}

	private FileEntity createFileEntity(MultipartFile file) {
		FileEntity entity = new FileEntity();
		entity.setContentType(file.getContentType());
		entity.setName(file.getOriginalFilename());
		fileEntityRepository.save(entity);
		String s3FileName = String.format("users/%d/%s", entity.getId(), file.getOriginalFilename());
		entity.setName(minioFileTransferService.uploadFile(file, s3FileName));
		return entity;
	}

	private void deleteFileEntity(FileEntity entity) {
		minioFileTransferService.deleteFile(entity);
		fileEntityRepository.delete(entity);
	}

	private HttpEntity<byte[]> buildHttpEntity(byte[] content, FileEntity fileEntity) {
		String fileName = fileEntity.getName();
		String[] splitFileName = fileName.split("/");
		fileName = splitFileName[splitFileName.length - 1];
		HttpHeaders header = new HttpHeaders();
		header.set(
				HttpHeaders.CONTENT_DISPOSITION,
				ContentDisposition.builder("attachment")
						.filename(fileName, StandardCharsets.UTF_8)
						.build()
						.toString()
		);
		header.setContentType(getMediaType(fileEntity.getContentType()));
		header.setContentLength(content.length);
		return new HttpEntity<>(content, header);
	}

	private MediaType getMediaType(final String type) {
		try {
			return MediaType.parseMediaType(type);
		} catch (InvalidMediaTypeException e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}


	//todo: yamikhaylov 18.03.22 - add MINio file storage
}
