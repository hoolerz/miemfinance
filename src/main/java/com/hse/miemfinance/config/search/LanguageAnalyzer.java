package com.hse.miemfinance.config.search;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.stereotype.Component;

@Component("miemAnalyzer")
public class LanguageAnalyzer implements LuceneAnalysisConfigurer {
	@Override
	public void configure(LuceneAnalysisConfigurationContext context) {
		context.analyzer("english").custom()
				.tokenizer("standard")
				.tokenFilter("lowercase")
				.tokenFilter("snowballPorter")
				.param("language","English")
				.tokenFilter("asciiFolding");

		context.analyzer("russian").custom()
				.tokenizer("standard")
				.tokenFilter("lowercase")
				.tokenFilter("snowballPorter")
				.param("language", "Russian")
				.tokenFilter("asciiFolding");
	}
}
