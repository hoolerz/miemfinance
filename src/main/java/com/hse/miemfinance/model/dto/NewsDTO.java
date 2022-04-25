package com.hse.miemfinance.model.dto;

import com.hse.miemfinance.model.entity.News;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsDTO extends DataDTO {

	private String header;

	private String shortText;

	private String fullText;

	private String source;

	private String sentiment;

	private String publishedDate;

	private Set<String> financialInstruments;

	public NewsDTO(News entity) {
		super(String.valueOf(entity.getId()));
		this.header = entity.getHeader();
		this.shortText = entity.getShortText();
		this.fullText = entity.getFullText();
		this.sentiment = entity.getSentiment();
		this.source = entity.getSource();
		this.publishedDate = generatePublishedDate(entity.getPublishedDate());
		this.financialInstruments = entity.getFinancialInstruments().stream()
				.map(financialInstrumentNews -> financialInstrumentNews.getFinancialInstrument().getTicker())
				.collect(Collectors.toSet());
	}

	private String generatePublishedDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
		return date.format(formatter);
	}

}
