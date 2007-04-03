package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElement;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class InfElemRefTypeLinkCreateCommand extends CreateRelationshipCommand {

	/**
	 * @generated
	 */
	private GenericDocumentPart mySource;

	/**
	 * @generated
	 */
	private InfElement myTarget;

	/**
	 * @generated
	 */
	public InfElemRefTypeLinkCreateCommand(CreateRelationshipRequest req,
			GenericDocumentPart source, InfElement target) {
		super(req);
		super.setElementToEdit(source);
		mySource = source;
		myTarget = target;
	}

	/**
	 * @generated
	 */
	public EObject getSource() {
		return mySource;
	}

	/**
	 * @generated
	 */
	public EObject getTarget() {
		return myTarget;
	}

	/**
	 * @generated
	 */
	protected EClass getEClassToEdit() {
		return DrlPackage.eINSTANCE.getGenericDocumentPart();
	}

	/**
	 * @generated
	 */
	protected void setElementToEdit(EObject element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @generated
	 */
	protected EObject doDefaultElementCreation() {
		InfElemRef newElement = (InfElemRef) super.doDefaultElementCreation();
		if (newElement != null) {
			newElement.setInfelem(myTarget);
			DrlModelElementTypes.Initializers.InfElemRef_3001.init(newElement);
		}
		return newElement;
	}

}
