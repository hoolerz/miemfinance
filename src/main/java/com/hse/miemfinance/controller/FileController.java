package com.hse.miemfinance.controller;

import static com.hse.miemfinance.model.enums.Dictionaries.ExceptionMessages.WRONG_REQUEST;

import com.hse.miemfinance.model.dto.FileResponseDTO;
import com.hse.miemfinance.model.exception.BusinessException;
import com.hse.miemfinance.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@GetMapping(value = "/api/file")
	public HttpEntity<byte[]> getFile(@RequestParam("fileId") Long fileId) {
		return fileService.downloadFile(fileId);
	}

	@PostMapping(value = "/api/user/file")
	public ResponseEntity<FileResponseDTO> uploadFile(@RequestParam(value = "file") MultipartFile file) {
		if (file == null ) {
			throw new BusinessException().withMessage(WRONG_REQUEST);
		}
		return ResponseEntity.ok().body(fileService.uploadFile(file));
	}

	@DeleteMapping(value = "/api/user/file")
	public ResponseEntity<Object> deleteFile(@RequestParam("fileId") Long fileId) {
		fileService.deleteFile(fileId);
		return ResponseEntity.ok().body(null);
	}

}
