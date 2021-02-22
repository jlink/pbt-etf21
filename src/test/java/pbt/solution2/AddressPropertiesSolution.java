package pbt.solution2;

import pbt.exercise2.*;

import net.jqwik.api.*;
import net.jqwik.api.statistics.*;

import static org.assertj.core.api.Assertions.*;

@Label("Addresses")
class AddressPropertiesSolution {

	@Property
	@Label("are valid")
	void addressesAreValid(@ForAll("validAddresses") Address anAddress) {
		assertThat(anAddress.city()).isNotEmpty();
		assertThat(anAddress.street()).isNotEmpty();
		if (anAddress.zipCode().isPresent()) {
			isValidGermanZipCode(anAddress.zipCode().get());
		}
	}

	@Property
	@Label("have enough variation")
	void addressesHaveEnoughVariation(@ForAll("validAddresses") Address anAddress) {
		Statistics.label("zip codes are present")
				  .collect(anAddress.zipCode().isPresent())
				  .coverage(checker -> {
					  checker.check(true).percentage(p -> p > 30);
					  checker.check(false).percentage(p -> p > 30);
				  });
	}

	@Provide
	Arbitrary<Address> validAddresses() {
		Arbitrary<Country> country = Arbitraries.of(Country.class);
		Arbitrary<String> city = Arbitraries.strings().alpha().ofMinLength(1).ofMaxLength(30);
		Arbitrary<String> street = Arbitraries.strings().alpha().ofMinLength(1).ofMaxLength(20);
		return Combinators.combine(country, city, germanZipCode(), street)
						  .as(Address::new);
	}

	private Arbitrary<String> germanZipCode() {
		return Arbitraries.strings()
						  .withCharRange('0', '9')
						  .ofLength(5)
						  .filter(z -> !z.startsWith("00"));
	}

	private void isValidGermanZipCode(@ForAll String germanZipcode) {
		assertThat(germanZipcode.chars()).allMatch(c -> c >= '0' && c <= '9');
		assertThat(germanZipcode).doesNotStartWith("00");
	}

}
