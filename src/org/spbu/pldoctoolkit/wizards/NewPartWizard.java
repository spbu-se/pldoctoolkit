package org.spbu.pldoctoolkit.wizards;

import java.net.URL;

import org.spbu.pldoctoolkit.Activator;

public class NewPartWizard extends NewDocumentationElementWizard {
	@Override
	protected NewDocumentationElementWizardPage createPage() {
		URL templateURL = Activator.getDefault().getBundle().getEntry("/templates/part.xml");
		NewDocumentationElementWizardPage page = new NewDocumentationElementWizardPage("newPart", selection, templateURL, "Part");
		page.setTitle("Part");
		page.setDescription("Create a new Part");
		return page;
	}
}
