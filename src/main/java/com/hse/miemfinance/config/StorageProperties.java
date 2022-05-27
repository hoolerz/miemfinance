package com.hse.miemfinance.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "miemfinance.storage")
public class StorageProperties {

	private String accessKey;

	private String secretKey;

	private String endpoint;

	private String bucket;

}
