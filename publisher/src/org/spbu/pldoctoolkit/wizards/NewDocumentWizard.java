package org.spbu.pldoctoolkit.wizards;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.ui.INewWizard;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;

public class NewDocumentWizard extends NewWizard implements INewWizard {
	private static final String TEMPLATE = "templates/document.xml";
	
	@Override
	public NewWizardPage createPage() {
		NewWizardPage page = new NewWizardPage("newDocument", "Document name:", selection);
		page.setTitle("New Document");
		page.setDescription("Create a new Final Document");
		return page;
	}

	@Override
	public InputStream openContentStream() throws IOException {
		return DrlPublisherPlugin.getDefault().getBundle().getResource(TEMPLATE).openStream();
	}
}
