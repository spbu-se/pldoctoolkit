package org.spbu.pldoctoolkit.wizards;

import java.net.URL;

import org.spbu.pldoctoolkit.Activator;

public class NewDocumentWizard extends NewDocumentationElementWizard {
	@Override
	protected NewDocumentationElementWizardPage createPage() {
		URL templateURL = Activator.getDefault().getBundle().getEntry("/templates/document.xml");
		NewDocumentationElementWizardPage page = new NewDocumentationElementWizardPage("newDocument", selection, templateURL, "Document");
		page.setTitle("Document");
		page.setDescription("Create a new Final Document");
		return page;
	}
}
