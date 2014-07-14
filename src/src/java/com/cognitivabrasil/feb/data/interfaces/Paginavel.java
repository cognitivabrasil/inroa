package com.cognitivabrasil.feb.data.interfaces;

import java.util.List;
import java.util.Set;

public interface Paginavel<T> extends Iterable<List<T>> {

	public int size();
}
