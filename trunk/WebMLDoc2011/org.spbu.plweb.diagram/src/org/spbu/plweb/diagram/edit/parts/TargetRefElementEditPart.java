package org.spbu.plweb.diagram.edit.parts;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.notation.View;

import org.spbu.plweb.TargetRefElement;

public abstract class TargetRefElementEditPart extends ShapeNodeEditPart {

	public TargetRefElementEditPart(View view) {
		super(view);
	}

	protected void handleNotificationEvent(Notification notification) {
		super.handleNotificationEvent(notification);

		EObject element = ((View) getModel()).getElement();
		if (element instanceof TargetRefElement) {
			TargetRefElement refElement = (TargetRefElement) ((View) getModel())
					.getElement();
			List connections = this.getTargetConnections();
			if (connections.size() > 0) {
				SourceRefElementClassEditPart editPart = 
					(SourceRefElementClassEditPart) connections.get(0);
				editPart.getPrimaryShape().setOptionalLink(refElement.isOptional());
			}
		}
	}
}
