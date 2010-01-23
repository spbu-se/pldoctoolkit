package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElemRefGroup;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.command.diagram.DrlElementCreateRelationshipCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 */
public class InfElemRef2TypeLinkCreateCommand extends DrlElementCreateRelationshipCommand {

	/**
	 * @generated
	 */
	private GenericDocumentPart myContainer;

	/**
	 * @generated
	 */
	private InfElemRefGroup mySource;
	
	private CreateRelationshipRequest request;
	
	/**
	 * @generated NOT
	 */
	public InfElemRef2TypeLinkCreateCommand(CreateRelationshipRequest req, GenericDocumentPart container) {
		super(req);
		super.setElementToEdit(container);
		
		request = req;
		myContainer = container;
		mySource = (InfElemRefGroup) req.getSource();
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
	 * @generated NOT
	 */
	public InfElement getTarget() {
		return (InfElement) request.getTarget();
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
	 * @generated NOT
	 */
	protected EObject doDefaultElementCreation() {
		InfElemRef newElement = (InfElemRef) super.doDefaultElementCreation();
		if (newElement != null) {
			newElement.setInfelem(getTarget()); //HAND myTarget -> getTarget()
			newElement.setGroup(mySource);
			DrlModelElementTypes.Initializers.InfElemRef_3003.init(newElement);
		}
		return newElement;
	}
	
	@Override
	protected boolean doCanExecute() {
		return getSource() != null;
	}
	
}
