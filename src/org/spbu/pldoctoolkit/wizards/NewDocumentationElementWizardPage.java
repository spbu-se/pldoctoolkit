package org.spbu.pldoctoolkit.wizards;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class NewDocumentationElementWizardPage extends WizardNewFileCreationPage {
	private final URL templateURL;
	private final String newFileLabel;
	
	public NewDocumentationElementWizardPage(String pageName, IStructuredSelection selection, URL templateURL, String newFileLabel) {
		super(pageName, selection);
		this.templateURL = templateURL;
		this.newFileLabel = newFileLabel;
	}

	@Override
	protected InputStream getInitialContents() {
		try {
			return templateURL.openStream();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	protected String getNewFileLabel() {
		return newFileLabel;
	}
}
