package org.spbu.pldoctoolkit.refactor;

import org.spbu.pldoctoolkit.parser.DRLLang.Element;

public interface IForwardIterator {
	Element next();
	boolean hasNext();
}
