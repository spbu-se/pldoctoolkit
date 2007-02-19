package org.spbu.pldoctoolkit.wizards;

import java.net.URL;

import org.spbu.pldoctoolkit.Activator;

public class NewFamilyWizard extends NewDocumentationElementWizard {
	@Override
	protected NewDocumentationElementWizardPage createPage() {
		URL templateURL = Activator.getDefault().getBundle().getEntry("/templates/family.xml");
		NewDocumentationElementWizardPage page = new NewDocumentationElementWizardPage("newFamily", selection, templateURL, "Family");
		page.setTitle("Family");
		page.setDescription("Create a new Documentation Family");
		return page;
	}
}
