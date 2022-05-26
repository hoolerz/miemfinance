package com.hse.miemfinance.config.search;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "miemfinance.search")
public class SearchProperties {

	private Boolean isEnabled;

	private Boolean initialIndexingEnabled;

}
