package com.hse.miemfinance.service;

import com.hse.miemfinance.repository.FileEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

	private final FileEntityRepository fileEntityRepository;

	//todo: yamikhaylov 18.03.22 - add MINio file storage
}
