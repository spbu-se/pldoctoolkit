package org.spbu.pldoctoolkit.wizards;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.ui.INewWizard;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;

public class NewPartWizard extends NewWizard implements INewWizard {
	private static final String TEMPLATE = "templates/part.xml";
	
	@Override
	public NewWizardPage createPage() {
		NewWizardPage page = new NewWizardPage("newPart", "Part name:", selection);
		page.setTitle("New Part");
		page.setDescription("Create a new Document Part");
		return page;
	}

	@Override
	public InputStream openContentStream() throws IOException {
		return DrlPublisherPlugin.getDefault().getBundle().getResource(TEMPLATE).openStream();
	}
}
