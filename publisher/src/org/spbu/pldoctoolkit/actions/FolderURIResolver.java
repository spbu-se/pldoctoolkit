package org.spbu.pldoctoolkit.actions;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.StandardURIResolver;
import net.sf.saxon.trans.XPathException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

public class FolderURIResolver extends StandardURIResolver {
	private static final long serialVersionUID = -7919352677909462305L;
	private static final String DRLRESOLVE_PREFIX = "drlresolve://";
	private IContainer folder;
	
	public FolderURIResolver(Configuration config) {
		super(config);
	}

	public void setFolder(IContainer folder) {
		this.folder = folder;
	}
	
	@Override
	public Source resolve(String href, String base) throws XPathException {
		if (folder != null && href.startsWith(DRLRESOLVE_PREFIX)) {
			return new StreamSource(getResource(href).getLocationURI().toString());
		}
		return super.resolve(href, base);
	}
	
	public IResource getResource(String href) {
		if (href.startsWith(DRLRESOLVE_PREFIX)) {
			String name = href.substring(DRLRESOLVE_PREFIX.length());
			return folder.getFile(new Path(name + ".xml"));
		}
		try {
			IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new URI(href));
			if (files.length > 0)
				return files[0];
		} catch (URISyntaxException e) {
		}
		return null;	
	}
}
