package org.spbu.pldoctoolkit.wizards;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.ui.INewWizard;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;

public class NewFamilyWizard extends NewWizard implements INewWizard {
	private static final String TEMPLATE = "templates/family.xml";
	
	@Override
	public NewWizardPage createPage() {
		NewWizardPage page = new NewWizardPage("newFamily", "Family name:", selection);
		page.setTitle("New Family");
		page.setDescription("Create a new Documentation Family");
		return page;
	}

	@Override
	public InputStream openContentStream() throws IOException {
		return DrlPublisherPlugin.getDefault().getBundle().getResource(TEMPLATE).openStream();
	}
}
