package org.spbu.plweb.diagram.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CollectionsUtils {

	private CollectionsUtils() {

	}

	public static <T> List<T> newArrayList() {
		return new ArrayList<T>();
	}

	public static <T> List<T> newLinkedList() {
		return new LinkedList<T>();
	}

	public static <T> List<T> newList() {
		return newArrayList();
	}

	public static <T> List<T> join(final List<T> a, final List<T> b) {
		final List<T> result = newList();
		result.addAll(a);
		result.addAll(b);
		return result;
	}

	public static <T> List<T> join(final List<T> list, final T element) {
		return join(list, Collections.singletonList(element));
	}

}
