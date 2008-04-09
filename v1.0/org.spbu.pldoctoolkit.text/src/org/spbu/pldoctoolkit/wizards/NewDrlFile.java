package org.spbu.pldoctoolkit.wizards;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.ProjectContentCreator;
import org.spbu.pldoctoolkit.refactor.StartUpRefactoring;
import org.spbu.pldoctoolkit.registry.ProjectRegistry;
import org.xml.sax.helpers.AttributesImpl;

public class NewDrlFile extends Wizard implements INewWizard {
	private URL PRODUCT_LINE_TEMPLATE_URL = DrlPublisherPlugin.getURL("templates/ProductLine.xml");
	private URL DOCUMENTATION_CORE_TEMPLATE_URL = DrlPublisherPlugin.getURL("templates/DocumentationCore.xml");
	private URL PRODUCT_DOCUMENTATION_TEMPLATE_URL = DrlPublisherPlugin.getURL("templates/ProductDocumentation.xml");
	
	private IWorkbench workbench;
	private IStructuredSelection selection;
	private NewDrlFilePage page;
	
	static {
		if (PLDocToolkitPlugin.getRegistryIndex().getProjectContentCreator() == null)
			PLDocToolkitPlugin.getRegistryIndex().setProjectContentCreator(new ProjectContentCreator());
	}

	@Override
	public void addPages() {
		page = new NewDrlFilePage("NewDrlFilePage", selection);
		page.setTitle("Create a new DRL file");
		addPage(page);
	/*	
		NewDrlFilePage page1 = new NewDrlFilePage("NewDrlFilePage22222", selection);
		page1.setTitle("Create a new DRL file2222222");
		addPage(page1);*/		
	}

	@Override
	public boolean performFinish() {
		if (page.getSourceType())
			return createFromExistingFile();
		else
			return createNewFile();
	}
	
	private boolean createNewFile() {
		final IFile file = page.getFile();
		if (file == null)
			return false;
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					createFile(file, monitor, page.getType());
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
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable target = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", target.getMessage());
			return false;
		}
		return true;
	}

	private boolean createFromExistingFile() {
		final IFile docCoreFile = page.getDocCoreFile();
		final IFile productDocFile = page.getProductDocFile();
		
		IContainer temp;
	
		if (docCoreFile == null)
			return false;
		if (productDocFile == null)
			return false;
		
		String contName = page.getContainerName();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		temp = (IContainer) root.findMember(new Path(contName));
		while (! (temp instanceof IProject)) {
			temp = temp.getParent();
			if (temp == null){
				System.out.print("Ther is no project");
				return false;
			}				
		}
		
		final IContainer container = temp;
		final File file = new File(page.getFileName());
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					
					ProjectRegistry prReg = PLDocToolkitPlugin.getRegistryIndex().getRegistry(container.getName());
					ProjectContent prContent = (ProjectContent)prReg.getProjectContent();
					
										
					//FileInputStream fis = new FileInputStream(file);
					
					BufferedReader in = new BufferedReader(new FileReader(file));
					String res = "";
					String tempStr = in.readLine();
					while (tempStr != null) {
						res += tempStr + '\n';
						tempStr = in.readLine();
					}
					
					createFile(docCoreFile, monitor, NewDrlFilePage.DOCUMENTATION_CORE);
					createFile(productDocFile, monitor, NewDrlFilePage.PRODUCT_DOCUMENTATION);
					
					prContent.add(docCoreFile);				
					prContent.add(productDocFile);
					
					DRLDocument coreDoc = prContent.DRLDocs.get(docCoreFile);
					StartUpRefactoring.addInfoToCore(coreDoc, res);
					
					DRLDocument productDoc = prContent.DRLDocs.get(productDocFile);
					StartUpRefactoring.addInfoToProduct(productDoc);				
					
					prContent.saveAll();
					
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
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable target = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", target.getMessage());
			return false;
		}
		return true;
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
				is = PRODUCT_LINE_TEMPLATE_URL.openStream();
			} else if (NewDrlFilePage.DOCUMENTATION_CORE.equals(type)) {
				is = DOCUMENTATION_CORE_TEMPLATE_URL.openStream();
			} else if (NewDrlFilePage.PRODUCT_DOCUMENTATION.equals(type)) {
				is = PRODUCT_DOCUMENTATION_TEMPLATE_URL.openStream();
			}
			if (file.exists()) {
				file.setContents(is, true, true, monitor);
			} else {
				file.create(is, true, monitor);
			}
			file.setCharset("utf-8", monitor);
			monitor.worked(1);
			getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
					try {
						IDE.openEditor(page, file, true);
					} catch (PartInitException e) {
					}
				}
			});
		} finally {
			is.close();
		}
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}

}
