package com.hse.miemfinance.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "miemfinance")
public class ApplicationProperties {

	private Boolean ssoEnabled;

	private String parserHost;

	private Boolean initializeApplication;

}
