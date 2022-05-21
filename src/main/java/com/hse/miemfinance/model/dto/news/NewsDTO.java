package com.hse.miemfinance.model.dto.news;

import com.hse.miemfinance.model.dto.DataDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentDTO;
import com.hse.miemfinance.model.entity.InstrumentNews;
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

	private String link;

	private String sentiment;

	private String publishedDate;

	private Set<InstrumentDTO> instruments;

	public NewsDTO(News entity) {
		super(String.valueOf(entity.getId()));
		this.header = entity.getHeader();
		this.shortText = entity.getShortText();
		this.fullText = entity.getFullText();
		this.sentiment = entity.getSentiment();
		this.source = entity.getSource();
		this.link = entity.getLink();
		this.publishedDate = generatePublishedDate(entity.getPublishedDate());
		this.instruments = entity.getFinancialInstruments().stream()
				.map(InstrumentNews::getFinancialInstrument)
				.map(InstrumentDTO::new)
				.collect(Collectors.toSet());
	}

	private String generatePublishedDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
		return date.format(formatter);
	}

}
