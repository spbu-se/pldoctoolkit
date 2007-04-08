package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.InfElement;

import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class InfElemRef2TypeLinkCreateCommand extends CreateRelationshipCommand {

	/**
	 * @generated
	 */
	private GenericDocumentPart myContainer;

	/**
	 * @generated
	 */
	private InfElemRefGroup mySource;

	/**
	 * @generated
	 */
	private InfElement myTarget;

	/**
	 * @generated
	 */
	public InfElemRef2TypeLinkCreateCommand(CreateRelationshipRequest req,
			GenericDocumentPart container, InfElemRefGroup source,
			InfElement target) {
		super(req);
		super.setElementToEdit(container);
		myContainer = container;
		mySource = source;
		myTarget = target;
	}

	/**
	 * @generated
	 */
	public GenericDocumentPart getContainer() {
		return myContainer;
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
			newElement.setGroup(mySource);
			DrlModelElementTypes.Initializers.InfElemRef_3003.init(newElement);
		}
		return newElement;
	}

}
