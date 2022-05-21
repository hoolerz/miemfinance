package com.hse.miemfinance.model.dto.news;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsListDTO {

	private List<NewsDTO> news;

}
