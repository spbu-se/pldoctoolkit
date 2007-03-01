package org.spbu.pldoctoolkit.actions;

import org.eclipse.core.runtime.CoreException;
import org.spbu.pldoctoolkit.Activator;

public class PdfExportAction extends AbstractExportAction {
	public PdfExportAction() throws CoreException {
		super("Export to pdf", Activator.getImageDescriptor("icons/sample.gif"));
	}
}
