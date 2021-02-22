package pbt.exercise2;

import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.*;

class AddressProperties {

	//@Property
	@Label("Address instances are valid")
	void addressesAreValid(@ForAll Address anAddress) {
		assertThat(anAddress.city()).isNotEmpty();
		assertThat(anAddress.street()).isNotEmpty();
		if (anAddress.zipCode().isPresent()) {
			isValidGermanZipCode(anAddress.zipCode().get());
		}
	}

	private void isValidGermanZipCode(@ForAll String germanZipcode) {
		assertThat(germanZipcode.chars()).allMatch(c -> c >= '0' && c <= '9');
		assertThat(germanZipcode).doesNotStartWith("00");
	}
}
