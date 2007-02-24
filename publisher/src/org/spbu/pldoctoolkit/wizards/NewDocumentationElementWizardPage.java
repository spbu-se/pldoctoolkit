package org.spbu.pldoctoolkit.wizards;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.internal.ide.misc.ResourceAndContainerGroup;

public class NewDocumentationElementWizardPage extends WizardPage implements Listener {
	private URL templateURL;
	private String nameLabel;
	private String title;
	private String description;
	private String initialName;
	
	private ResourceAndContainerGroup resourceGroup;
	
	private IFile file;
	
	private IStructuredSelection selection;
	
	public NewDocumentationElementWizardPage(String pageName, IStructuredSelection selection) {
		super("newDocumentationElementPage");
		this.selection = selection;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setNameLabel(String nameLabel) {
		this.nameLabel = nameLabel;
	}

	public void setTemplateURL(URL templateURL) {
		this.templateURL = templateURL;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setInitialName(String initialName) {
		this.initialName = initialName;
	}

	protected boolean validatePage() {
		return false;
	}
	
	public void handleEvent(Event event) {
		
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		
	}
	
	protected InputStream getInitialContents() {
		try {
			return templateURL.openStream();
		} catch (IOException e) {
			return null;
		}
	}
}
