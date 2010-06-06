/******************************************************************************
 * Copyright (c) 2006, 2007 Borland Software Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Richard Gronback (Borland) - initial API and implementation 
 *    Cherie Revells (IBM)
 ****************************************************************************/
package webml.diagram.part;

import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.util.ObjectAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateViewAndOptionallyElementCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import webml.DocTopic;
import webml.WebmlPackage;
import webml.diagram.edit.parts.AreaEditPart;
import webml.diagram.providers.WebmlElementTypes;

public class CreateAreaDocTopicAction implements IObjectActionDelegate {

	public final static String ID = "org.eclipse.gmf.webml.diagram.AreaInsertTopic";

	private AreaEditPart selectedElement;

	public void run(IAction action) { 
		createAreaDocTopic("New Doc Topic");
	}
	
	private void createAreaDocTopic(String name) {
		// create new DocTopic
		CompoundCommand cc = new CompoundCommand("Add new Topic");
		ObjectAdapter endAdapter = new ObjectAdapter();
		endAdapter.setObject(WebmlElementTypes.DocTopic_3003);
		CreateViewAndOptionallyElementCommand createTopicCmd =
		new CreateViewAndOptionallyElementCommand(endAdapter, selectedElement, new Point(10, 10), selectedElement.getDiagramPreferencesHint());
		cc.add(new ICommandProxy(createTopicCmd));
		selectedElement.getDiagramEditDomain().getDiagramCommandStack().execute(cc);
		
		// set its Name to name
		Collection results = DiagramCommandStack.getReturnValues(cc);
		for (Object res: results) {
			if (res instanceof IAdaptable) {
				IAdaptable adapter = (IAdaptable) res;
				View view = (View) adapter.getAdapter(View.class);
				if (view != null) {
					DocTopic newTopic = (DocTopic)view.getElement();
					SetRequest reqSet = new SetRequest(selectedElement.getEditingDomain(),
							newTopic, WebmlPackage.eINSTANCE.getDocTopic_Name(),
							name);
					SetValueCommand operation = new SetValueCommand(reqSet);
					selectedElement.getDiagramEditDomain().getDiagramCommandStack().execute(new 
							ICommandProxy(operation));
				}
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		selectedElement = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.getFirstElement() instanceof AreaEditPart) {
				selectedElement = (AreaEditPart) structuredSelection.getFirstElement();
			}
		}
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
