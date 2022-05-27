package com.hse.miemfinance.model.enums;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Dictionaries {

	@UtilityClass
	public static class ItemTypes {
		public static final String COUNTRY = "COUNTRY";
		public static final String QUICK_FILTER = "QUICK_FILTER";
		public static final String TAG = "TAG";
	}

	@UtilityClass
	public static class Countries {
		public static final String RUSSIA = "RU";
		public static final String USA = "US";
		public static final String GREAT_BRITAIN = "GB";
		public static final String FRANCE = "FR";
		public static final String GERMANY = "DE";
	}

	@UtilityClass
	public static class QuickFilters {
		public static final String RUSSIAN = "RUSSIAN";
		public static final String FOREIGN = "FOREIGN";
		public static final String OIL = "OIL";
		public static final String BANKS = "BANKING";
		public static final String BLUE_CHIPS = "BLUE_CHIPS";
	}

	@UtilityClass
	public static class Tags {
		public static final String RUSSIAN = "RUSSIAN";
		public static final String FOREIGN = "FOREIGN";
		public static final String OIL = "OIL";
		public static final String BANKS = "BANKING";
		public static final String BLUE_CHIPS = "BLUE_CHIPS";
		public static final String FINANCIAL = "FINANCIAL";
		public static final String TECHNOLOGY = "TECHNOLOGY";
		public static final String RETAIL = "RETAIL";
		public static final String CONSUMER = "CONSUMER";
		public static final String SEMICONDUCTORS = "SEMICONDUCTORS";
		public static final String INDUSTRIAL = "INDUSTRIAL";
	}

	@UtilityClass
	public static class Currencies {
		public static final String RUB = "₽";
		public static final String USD = "$";
		public static final String EURO = "€";
		public static final String GBP = "£";
	}

	@UtilityClass
	public static class ExceptionMessages {
		public static final String NOT_FOUND = "Specified entity not found";
		public static final String ALREADY_EXISTS = "Specified entity already exists";
		public static final String WRONG_REQUEST = "Wrong request";
	}

}
