package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.spbu.pldoctoolkit.graph.DrlPackage;
import org.spbu.pldoctoolkit.graph.GenericDocumentPart;
import org.spbu.pldoctoolkit.graph.InfElemRef;
import org.spbu.pldoctoolkit.graph.InfElement;
import org.spbu.pldoctoolkit.graph.command.diagram.DrlElementCreateRelationshipCommand;
import org.spbu.pldoctoolkit.graph.diagram.infproduct.providers.DrlModelElementTypes;

/**
 * @generated
 * 
 * Target is taken as request.getTarget and may not exist at the time of the command
 * creation.
 * 
 * Source must exist.
 * 
 */
public class InfElemRefTypeLinkCreateCommand extends DrlElementCreateRelationshipCommand {

	/**
	 * @generated
	 */
	private GenericDocumentPart mySource;

	private CreateRelationshipRequest request;
	
	/**
	 * @generated NOT
	 */
	public InfElemRefTypeLinkCreateCommand(CreateRelationshipRequest req) {
		super(req);
		super.setElementToEdit(req.getSource());
		
		request = req;
		mySource = (GenericDocumentPart) req.getSource();
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
			DrlModelElementTypes.Initializers.InfElemRef_3001.init(newElement);
		}
		return newElement;
	}

	@Override
	protected boolean doCanExecute() {
		return getSource() != null;
	}

}
