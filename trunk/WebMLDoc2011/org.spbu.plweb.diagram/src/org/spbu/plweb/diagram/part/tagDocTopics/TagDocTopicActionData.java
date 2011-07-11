package org.spbu.plweb.diagram.part.tagDocTopics;

import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.ui.IWorkbenchPart;

public class TagDocTopicActionData {
	public ShapeNodeEditPart selectedElement;
	public List<ShapeNodeEditPart> listOfSelectedElements;
	public List<String> listDocs;
	public IWorkbenchPart targetPart;

	public TagDocTopicActionData() {
	}
}