package com.hse.miemfinance.model.dto;

import com.hse.miemfinance.model.entity.FileEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileResponseDTO {

	private String filename;

	private Long fileId;

	public FileResponseDTO(FileEntity entity) {
		this.filename = entity.getName();
		this.fileId = entity.getId();
	}

}
