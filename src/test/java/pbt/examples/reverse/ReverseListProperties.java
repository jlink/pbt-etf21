package pbt.examples.reverse;

import java.util.*;

import org.assertj.core.api.*;

import net.jqwik.api.*;

class ReverseListProperties {

	private <T> List<T> reverse(List<T> original) {
		List<T> clone = new ArrayList<>(original);
		// Should produce failing properties:
		// List<T> clone = new ArrayList<>(new HashSet<>(original));
		Collections.reverse(clone);
		return clone;
	}

	@Property
	boolean theSizeRemainsTheSame(@ForAll List<Integer> original) {
		List<Integer> reversed = reverse(original);
		return original.size() == reversed.size();
	}

	@Property
	void allElementsStay(@ForAll List<Integer> original) {
		List<Integer> reversed = reverse(original);
		Assertions.assertThat(original).allMatch(element -> reversed.contains(element));
	}

	@Property
	boolean reverseMakesFirstElementLast(@ForAll List<Integer> original) {
		Assume.that(original.size() >= 1);
		Integer lastReversed = reverse(original).get(original.size() - 1);
		return original.get(0).equals(lastReversed);
	}

	@Property
	boolean reverseTwiceIsOriginal(@ForAll List<Integer> original) {
		return reverse(reverse(original)).equals(original);
	}

	// Using type variables in properties - wildcards also work

	@Property
	//@Report(Reporting.GENERATED)
	<T> boolean reverseWithTypeVariable(@ForAll List<T> original) {
		return reverse(reverse(original)).equals(original);
	}

}
