package org.spbu.pldoctoolkit.refactor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.SelectIntoInfElem;
import org.spbu.pldoctoolkit.wizards.NewDrlFile;
import org.spbu.pldoctoolkit.wizards.NewDrlFilePage;

public class SelectIntoInfProd {
	private String infProdId, infProdName, finalInfProdId, infElemId, 
		infElemName,infElemRefInText,infElemRefInProduct;
	
	public IPath projectPath;
	public String productFile, finalProductFile;
	
	public ProjectContent project;
	public PositionInDRL from, to;
	public PositionInText fromText, toText;
	public DRLDocument doc;
	
	private boolean isValide = false;
	private boolean wasValidation = false;
	
	private Element infElem = null;
	
	public SelectIntoInfProd() {	
	}
	
	public void setParams( String infProdId, String infProdName, String finalInfProdId, 
			String infElemId, String infElemName, String infElemRefInText, String infElemRefInProduct,
            ProjectContent project, DRLDocument doc,
            PositionInText fromText, PositionInText toText,
            IPath path,String ProductFile,String finalProductFile) {
		this.infProdId = infProdId;
		this.infProdName = infProdName;
		this.finalInfProdId = finalInfProdId;
		this.infElemId = infElemId;
		this.infElemName = infElemName;
		this.infElemRefInText = infElemRefInText;
		this.infElemRefInProduct = infElemRefInProduct;
		//
		this.projectPath = path;
		this.finalProductFile = finalProductFile;
		this.productFile = ProductFile;
		this.project = project;
		this.fromText = fromText;
		this.toText = toText;
		this.doc = doc;
	}
	
	public void setValidationParams( ProjectContent project, DRLDocument doc,
							 		   PositionInText fromText, PositionInText toText ) {		
		this.project = project;
		this.fromText = fromText;
		this.toText = toText;
		this.doc = doc;
	}
	
	public void reset() {
		wasValidation = false;
		isValide = false;
	}
	
	private void init() {
		// 1.
		from = doc.findByPosition(fromText);
		to = doc.findByPosition(toText);
		
		// 2.
		if (from.isInTag == true)
			return;
		
		UpwardIterator searchInfElemiterator;
		if (from.isInText)
			searchInfElemiterator = new UpwardIterator(from.elem);
		else if (from.next != null)
			searchInfElemiterator = new UpwardIterator(from.next);
		else
			return;
		
		infElem = searchInfElemiterator.next();
		while (infElem != null) {				
			if (infElem instanceof LangElem) {
				LangElem langElem = (LangElem)infElem; 
				if ( langElem.tag.equals("InfElement") )
					break;				
			}
			infElem = searchInfElemiterator.next();
		}
	}
	
	public boolean validate() {
		if (wasValidation)
			return isValide;
		
		init();
	
		isValide = true;
		
		// 1. 		
		if (from.parent != to.parent) 
			isValide = false;				
			
		// 2.		
		if (infElem == null) 
			isValide = false;
		
		// 3.
		if (from.isInTag == true || to.isInTag == true)
			isValide = false;
		
		wasValidation = true;
		return isValide;
	}

	public void perform() {
		validate();
		
		if (isValide){
			//1)SelectIntoInfElem
			SelectIntoInfElem elemRefact = new SelectIntoInfElem();
			elemRefact.setPararams(infElemId, infElemName, infElemRefInText, project, doc, fromText, toText);
			elemRefact.perform();
			project.saveAll();	
			//2)Create 2 files with core and product
			final Shell shell = DrlPublisherPlugin.getShell();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IContainer container = (IContainer) root.findMember(projectPath);
			
			final IFile docCoreFile = container.getFile(new Path(productFile));
			final IFile productDocFile = container.getFile(new Path(finalProductFile));
				
			if (docCoreFile == null)
				return;
			if (productDocFile == null)
				return;
			
			
			IRunnableWithProgress createFiles = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						
						createFile(docCoreFile, monitor, NewDrlFilePage.DOCUMENTATION_CORE);
						createFile(productDocFile, monitor, NewDrlFilePage.PRODUCT_DOCUMENTATION);
						
						project.add(docCoreFile);				
						project.add(productDocFile);
						
						DRLDocument coreDoc = project.DRLDocs.get(docCoreFile);
						StartUpRefactoring.addInfoToCore(coreDoc,
								infProdId, infProdName, infElemId, infElemName, infElemRefInProduct);
						
						DRLDocument productDoc = project.DRLDocs.get(productDocFile);
						StartUpRefactoring.addInfoToProduct(productDoc,
								finalInfProdId, infProdId, infElemRefInProduct);				
						
						project.saveAll();
						
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					} finally {
						monitor.done();
					}
				}
			};
			try {
				new ProgressMonitorDialog(shell).run(true, false, createFiles);
			} catch (InterruptedException e) {
				return;
			} catch (InvocationTargetException e) {
				Throwable target = e.getTargetException();
				MessageDialog.openError(shell, "Error", target.getMessage());
				return;
			}
		}
	}
	
	private void createFile(final IFile file, IProgressMonitor monitor, String type) throws IOException, CoreException {
		InputStream is = new InputStream() {
			@Override
			public int read() throws IOException {
				return -1;
			}
		};
		try {
			monitor.beginTask("Creating file...", 1);
			if (NewDrlFilePage.PRODUCT_LINE.equals(type)) {
				is = NewDrlFile.PRODUCT_LINE_TEMPLATE_URL.openStream();
			} else if (NewDrlFilePage.DOCUMENTATION_CORE.equals(type)) {
				is = NewDrlFile.DOCUMENTATION_CORE_TEMPLATE_URL.openStream();
			} else if (NewDrlFilePage.PRODUCT_DOCUMENTATION.equals(type)) {
				is = NewDrlFile.PRODUCT_DOCUMENTATION_TEMPLATE_URL.openStream();
			}
			if (file.exists()) {
				file.setContents(is, true, true, monitor);
			} else {
				file.create(is, true, monitor);
			}
			//file.setCharset("utf-8", monitor);
			monitor.worked(1);
			
			/*DrlPublisherPlugin.getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage page = 
						DrlPublisherPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						IDE.openEditor(page , file, true);
					} catch (PartInitException e) {
					}
				}
			});*/
		} finally {
			is.close();
		}
	}
}
