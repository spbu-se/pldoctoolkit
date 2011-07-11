package org.spbu.plweb.diagram.part.tagDocTopics;

import java.util.List;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.spbu.plweb.DocTopic;
import org.spbu.plweb.Element;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorPlugin;
import org.spbu.plweb.impl.AreaImpl;
import org.spbu.plweb.impl.PageImpl;
import org.spbu.plweb.impl.SiteViewImpl;

public class TagDocTopicPage extends WizardPage{
	
	private CheckboxTableViewer listViewer;
	
	private List<String> lst = null;

	protected TagDocTopicPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite plate = new Composite(parent, SWT.NONE);
		plate.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		plate.setLayout(layout);
		setControl(parent);
		
		Label labelEnterTitle = new Label(plate, SWT.NONE);
		labelEnterTitle.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_BEGINNING));

		Object selectedElement = getSelectedElement().getNotationView()
				.getElement();
		labelEnterTitle.setText("Select topics to tag for "
				+ getElementName(selectedElement));
		
		Composite panel = new Composite(plate, SWT.BORDER);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout(2, false));
		
		lst = getListOfDocsElement();
		
		listViewer = CheckboxTableViewer.newCheckList(panel, SWT.LEFT
				| SWT.BORDER);
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		listViewer.getControl().setLayoutData(layoutData);
		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setInput(lst);
		listViewer.setLabelProvider(new AdapterFactoryLabelProvider(
				PlwebDiagramEditorPlugin.getInstance()
						.getItemProvidersAdapterFactory()) {
			@Override
			public String getText(Object object) {
				if (object != null && object instanceof DocTopic) {
					DocTopic topic = (DocTopic) object;
					return topic.getDocTopicName();
				}
				return super.getText(object);
			}
		});
		
	}
	
	private String getElementName(Object obj) {
		if (obj != null && obj instanceof Element) {
			Element element = (Element) obj;
			String className = null;
			String title = element.getTitle();
			if (obj instanceof SiteViewImpl) {
				className = "Site View";
			} else if (obj instanceof AreaImpl) {
				className = "Area";
			}else if (obj instanceof PageImpl){
				className = "Page";
			}
			return className + " '" + title + "'";
		}
		return "Errorr"; // here should be an error handling
	}
	
	private ShapeNodeEditPart getSelectedElement() {
		IWizardContainer container = this.getContainer();
		TagDocTopicWizardDialog wizardDialog = (TagDocTopicWizardDialog) container;
		return wizardDialog.getSelectedElement();
	}
	
	private List<String> getListOfDocsElement() {
		IWizardContainer container = this.getContainer();
		TagDocTopicWizardDialog wizardDialog = (TagDocTopicWizardDialog) container;
		return wizardDialog.getListOfTopics();
	}
	
	public Object[] getCheckedElements() {
		return listViewer.getCheckedElements();
	}

}
