/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.spbu.pldoctoolkit.graph.validation;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link org.spbu.pldoctoolkit.graph.GenericDocumentPart}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface GenericDocumentPartValidator {
	boolean validate();

	boolean validateId(String value);
	boolean validateName(String value);
	boolean validateInfElemRefs(EList value);
	boolean validateGroups(EList value);
}
