package org.spbu.pldoctoolkit.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class XMLDocumentProvider extends FileDocumentProvider {

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document instanceof IDocumentExtension3) {
			IDocumentExtension3 ext3 = (IDocumentExtension3) document;
			IDocumentPartitioner partitioner = new FastPartitioner(new XMLPartitionScanner(),
				new String[] {XMLPartitionScanner.XML_TAG, XMLPartitionScanner.XML_COMMENT });
			ext3.setDocumentPartitioner(DRLEditor.XML_PARTITIONING, partitioner);
			partitioner.connect(document);
		}
		return document;
	}
}