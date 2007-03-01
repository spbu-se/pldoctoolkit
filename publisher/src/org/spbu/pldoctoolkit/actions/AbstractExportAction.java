package org.spbu.pldoctoolkit.actions;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;
import org.spbu.pldoctoolkit.Activator;

public abstract class AbstractExportAction extends Action {
	protected static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
	protected static final URL BASE_URL = Activator.getDefault().getBundle().getEntry("/");
	protected static final String DRL2DOCBOOK = getURL("xsl/drl/drl2docbook.xsl");
	
	protected final Transformer drlTransformer;
	
	protected static String getURL(String path) {
		try {
			return new URL(BASE_URL, path).toString();
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	protected IEditorPart editor;
	
	public AbstractExportAction(String text, ImageDescriptor image) throws CoreException {
		super(text, image);
		try {
			drlTransformer = TRANSFORMER_FACTORY.newTransformer(new StreamSource(DRL2DOCBOOK));
		} catch (TransformerConfigurationException e) {
			throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID, 0, "Unable to load drl2docbook transformation", e));
		}
	}
	
	public void setActiveEditor(IEditorPart editor) {
		this.editor = editor;
	}
}
