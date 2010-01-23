package org.spbu.pldoctoolkit.refactor;

import com.sun.org.apache.bcel.internal.generic.FSTORE;

public class Couple<T1, T2> {
	public T1 fst;
	public T2 snd;
	
	public Couple(T1 fst, T2 snd) {
		this.fst = fst;
		this.snd = snd;
	}	
}
