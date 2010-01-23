package org.spbu.pldoctoolkit.graph.diagram.productline.edit.commands;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.spbu.pldoctoolkit.graph.diagram.productline.part.DrlModelDiagramEditorPlugin;

public class NewFinalInfProductWizard extends Wizard implements INewWizard {
//	private URL PRODUCT_LINE_TEMPLATE_URL = DrlPublisherPlugin.getURL("templates/ProductLine.xml");
//	private URL DOCUMENTATION_CORE_TEMPLATE_URL = DrlPublisherPlugin.getURL("templates/DocumentationCore.xml");
	private URL PRODUCT_DOCUMENTATION_TEMPLATE_URL = DrlModelDiagramEditorPlugin.getURL("templates/ProductDocumentation.xml");
	
	private IWorkbench workbench;
	private IStructuredSelection selection;
	private NewFinalInfProductWizardPage page;

	@Override
	public void addPages() {
		page = new NewFinalInfProductWizardPage("NewFinalInfProductWizardPage", selection);
		page.setTitle("Create a new FinalInfProduct file");
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		final IFile file = page.getFile();
		if (file == null)
			return false;
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					createFile(file, monitor);
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

	private void createFile(final IFile file, IProgressMonitor monitor) throws IOException, CoreException {
		InputStream is = new InputStream() {
			@Override
			public int read() throws IOException {
				return -1;
			}
		};
		try {
			monitor.beginTask("Creating file...", 1);
//			if (NewFinalInfProductWizardPage.PRODUCT_LINE.equals(page.getType())) {
//				is = PRODUCT_LINE_TEMPLATE_URL.openStream();
//			} else if (NewFinalInfProductWizardPage.DOCUMENTATION_CORE.equals(page.getType())) {
//				is = DOCUMENTATION_CORE_TEMPLATE_URL.openStream();
//			} else if (NewFinalInfProductWizardPage.PRODUCT_DOCUMENTATION.equals(page.getType())) {
//				is = PRODUCT_DOCUMENTATION_TEMPLATE_URL.openStream();
//			}
			is = PRODUCT_DOCUMENTATION_TEMPLATE_URL.openStream();
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

	public static void runWizard(Shell shell, Wizard wizard, String settingsKey) {
		IDialogSettings pluginDialogSettings = DrlModelDiagramEditorPlugin
				.getInstance().getDialogSettings();
		IDialogSettings wizardDialogSettings = pluginDialogSettings
				.getSection(settingsKey);
		if (wizardDialogSettings == null) {
			wizardDialogSettings = pluginDialogSettings
					.addNewSection(settingsKey);
		}
		wizard.setDialogSettings(wizardDialogSettings);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();
		dialog.getShell().setSize(Math.max(500, dialog.getShell().getSize().x),
				500);
		dialog.open();
	}

	public static void runWizard() {
		runWizard(null, new NewFinalInfProductWizard(), "NewFinalInfProductWizard");
	}
}
