package pbt.exercise2;

import java.util.*;

public class Address {

	private final Country country;
	private final String city;
	private final String zipcode;
	private final String street;

	public Address(Country country, String city, String germanZipcode, String street) {
		if (country == null) {
			throw new IllegalArgumentException();
		}
		if (city == null || city.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		if (!isGermanZipcodeOrNull(germanZipcode)) {
			throw new IllegalArgumentException();
		}
		if (street == null || street.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.country = country;
		this.city = city;
		this.zipcode = germanZipcode;
		this.street = street;
	}

	private boolean isGermanZipcodeOrNull(String zipcode) {
		if (zipcode == null) {
			return true;
		}
		return zipcode.length() == 5
					   && zipcode.chars()
								 .allMatch(c -> c >= '0' && c <= '9');
	}

	public String city() {
		return city;
	}

	public Optional<String> zipCode() {
		return Optional.ofNullable(zipcode);
	}

	public String street() {
		return street;
	}

	@Override
	public String toString() {
		String zipcodeDescription = zipCode().map(z -> z + " ").orElse("");
		String cityDescription = zipcodeDescription + city;
		return String.format("%s, %s, %s", cityDescription, street, country.name());
	}
}
