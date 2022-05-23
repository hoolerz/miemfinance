package com.hse.miemfinance.model.dto.filters;

import com.hse.miemfinance.model.entity.DictionaryItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterDTO {

	private String key;

	private String value;

	public FilterDTO(DictionaryItem filter) {
		this.key = filter.getValue();
		this.value = filter.getText();
	}

}
