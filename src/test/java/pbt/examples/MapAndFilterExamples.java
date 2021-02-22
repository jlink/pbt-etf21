package pbt.examples;

import java.util.List;

import net.jqwik.api.*;

class MapAndFilterExamples {

	@Property
	boolean evenNumbersAreEven(@ForAll("even") int anInt) {
		return anInt % 2 == 0;
	}

	@Provide
	Arbitrary<Integer> even() {
		return Arbitraries.integers().filter(i -> i % 2 == 0);
	}

	@Provide
	Arbitrary<Integer> evenUpTo10000() {
		return Arbitraries.integers().between(1, 10000).filter(i -> i % 2 == 0);
	}

	@Provide
	Arbitrary<Integer> _evenUpTo10000() {
		return Arbitraries.integers().between(1, 5000).map(i -> i * 2);
	}

	@Property
	boolean allStringsHaveSameLength(@ForAll("equalSizedStrings") List<String> listOfStrings) {
		int min = listOfStrings.stream().mapToInt(String::length).min().getAsInt();
		int max = listOfStrings.stream().mapToInt(String::length).max().getAsInt();
		return min == max;
	}

	@Provide
	Arbitrary<List<String>> equalSizedStrings() {
		return Arbitraries.integers().between(1, 42)
						  .flatMap(length -> Arbitraries.strings().alpha().ofLength(length)
														.list().ofMinSize(1).ofMaxSize(10)
						  );
	}
}
