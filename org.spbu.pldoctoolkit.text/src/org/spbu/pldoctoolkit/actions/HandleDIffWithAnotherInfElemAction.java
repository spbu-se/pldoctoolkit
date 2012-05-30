package org.spbu.pldoctoolkit.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.compare.BufferedContent;
import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.internal.CompareEditor;
import org.eclipse.compare.internal.Utilities;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.window.Window;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.clones.CloneFinder;
import org.spbu.pldoctoolkit.clones.IClonesGroup;
import org.spbu.pldoctoolkit.clones.view.ClonesGroupResultView;
import org.spbu.pldoctoolkit.dialogs.HandleDIffWithAnotherInfElemDialog;
import org.spbu.pldoctoolkit.dialogs.InsertIntoDictionaryDialog;
import org.spbu.pldoctoolkit.dialogs.SelectIntoInfElemDialog;
import org.spbu.pldoctoolkit.diff.DiffMaker;
import org.spbu.pldoctoolkit.diff.IPairGroup;
import org.spbu.pldoctoolkit.diff.view.DiffGroupResultView;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.CreateNest;
import org.spbu.pldoctoolkit.refactor.HandleDIffWithAnotherInfElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.SelectIntoInfElem;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class HandleDIffWithAnotherInfElemAction extends Action implements
		IValidateDRLSelection {
	
	IEditorPart editor;
	TextEditor te;
	IProject project;
	HandleDIffWithAnotherInfElem refact = new HandleDIffWithAnotherInfElem();
	SelectIntoInfElem refactSelectIntoInfElem = new SelectIntoInfElem();
	CreateNest refactCreateNest = new CreateNest();
	ProjectContent projectContent;
	FileEditorInput editorInput;
	
	private DiffGroupResultView view;

	public static String refactName = "Handle DIff With Another Inf Element";

	public HandleDIffWithAnotherInfElemAction(IEditorPart editor)
			throws Exception {
		super(refactName);
		this.editor = editor;
		te = (TextEditor) editor;
		editorInput = (FileEditorInput) editor.getEditorInput();
		project = editorInput.getFile().getProject();
		projectContent = (ProjectContent) ((ProjectRegistryImpl) PLDocToolkitPlugin
				.getRegistry(project.getName())).projectContent;
		((DrlTextEditor) te).getMenuListener().addListener(this);
	}

	@Override
	public void validateSelection(IEditorPart part) {
		ISelection sel = te.getSelectionProvider().getSelection();
		TextSelection ts = (TextSelection) sel;

		IDocument doc = te.getDocumentProvider().getDocument(editorInput);
		DRLDocument DRLdoc = projectContent.DRLDocs.get(editorInput.getFile());
		try {
			int line1 = ts.getStartLine();
			int column1 = ts.getOffset() - doc.getLineOffset(line1);
			int line2 = ts.getEndLine();
			int column2 = ts.getOffset() + ts.getLength()
					- doc.getLineOffset(line2);
			PositionInText pos1 = new PositionInText(line1 + 1, column1 + 1);
			PositionInText pos2 = new PositionInText(line2 + 1, column2 + 1);

			if (pos1.compare(pos2) == 0) {
				setEnabled(false);
				return;
			}

			//refact.reset();
			//refact.setValidationPararams(projectContent, DRLdoc, pos1, pos2);

			refact.setValidationParams(DRLdoc, pos1, pos2);
			setEnabled(refact.validate());
		} catch (Exception e) {
			setEnabled(false);
			e.printStackTrace();
		}
	}

	public void run() {
		HandleDIffWithAnotherInfElemDialog dialog = new HandleDIffWithAnotherInfElemDialog(
				editor.getSite().getShell(), projectContent, refact);

		int res = dialog.open();

		if (res != Window.OK)
			return;

		try {
			showView();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		List<IPairGroup> pairs = getDiffPairGroups();
		System.out.println(pairs.size());
		view.setContent(pairs, editor);
		
		/*CompareConfiguration cc = new CompareConfiguration();
		// buffered merge mode: don't ask for confirmation
		// when switching between modified resources
		cc.setProperty(CompareEditor.CONFIRM_SAVE_PROPERTY, new Boolean(false));

		// uncomment following line to have separate outline view
		// cc.setProperty(CompareConfiguration.USE_OUTLINE_VIEW, new Boolean(true));

		MyCompareInput input = new MyCompareInput(cc, refact.getFirstInfElementToCompare().getTextRepresentation(), refact.getSecondInfElementToCompare().getTextRepresentation());

		Differencer differencer = new Differencer();
		Object obj = differencer.findDifferences(false,null,null,null,refact.getFirstInfElementToCompare().getTextRepresentation(), refact.getSecondInfElementToCompare().getTextRepresentation());
		System.out.println(obj);	
		//System.out.println("sdfhsdkfhsdkfsdfs");
		CompareUI.openCompareEditor(input);
		//TextEditor te;
		//te.getSd
		List<String> keys = Collections.list(CompareUI.getResourceBundle().getKeys());
		for (String key : keys){
			System.out.println("key " + key);
		}
		for (IExtension ext : CompareUI.getPlugin().getDescriptor().getExtensions()) {
			System.out.println("ext " + ext.getNamespaceIdentifier() + " " + ext.getNamespace() + " " + ext.getSimpleIdentifier() + " " + ext.getUniqueIdentifier() + " " + ext.getExtensionPointUniqueIdentifier() + " " + ext.getLabel() + " " + ext.getExtensionPointUniqueIdentifier());
			for (IConfigurationElement ce : ext.getConfigurationElements()) {
				for (String name : ce.getAttributeNames()) {
					System.out.println("name " + name);
				}
			}
		}
//		CompareUI.openCompareDialog(input);
//		DrlTextEditor editor = new DrlTextEditor();
//		CompareUI.reuseCompareEditor(input, editor);
		
		//выделяем в новый инф элемент
		SelectIntoInfElemDialog selectIntoInfElemDialog = refactSelectIntoInfElem.createSelectIntoInfElemDialog(editor, projectContent);  
		int result = selectIntoInfElemDialog.open();
		if (result != Window.OK)
			return;
		DRLDocument DRLdocOfFirstInfElem = projectContent.DRLDocs.get(editorInput.getFile());
		refactSelectIntoInfElem.setPararams(selectIntoInfElemDialog.getInfElemId(), selectIntoInfElemDialog.getInfElemName(),
				selectIntoInfElemDialog.getInfElemRefId(), projectContent, DRLdocOfFirstInfElem,refact.getStartPosition(),refact.getEndPosition());
		refactSelectIntoInfElem.perform();
		
		//создаём в местах дифов несты в новом инф элементе
		for (Element elem : refact.getDiffElemsToReplace()) {
			String nestId = CreateNest.getAppropriateNestId("diff", projectContent);
			refact.addNestId(nestId);			
			refactCreateNest.setValidationPararams(DRLdocOfFirstInfElem, elem.getStartPos());
			refactCreateNest.setPararams(nestId);			
			refactCreateNest.perform();
		}
		
		//заменяем часть второго инф. элемента ссылкой на новый инф элемент
		DRLDocument DRLdocOfSecondInfElem = refact.getSecondInfElementToCompare().getDRLDocument();
		refactSelectIntoInfElem.setPararams(selectIntoInfElemDialog.getInfElemId(), selectIntoInfElemDialog.getInfElemName(),
				selectIntoInfElemDialog.getInfElemRefId(), projectContent, DRLdocOfSecondInfElem,refact.getStartPosition(),refact.getEndPosition());
		//refactSelectIntoInfElem.performWithoutCreatingNewElement();
		
		//заменяем точки расширения в конечных информационных продуктах
		refact.replaceNests(projectContent, selectIntoInfElemDialog.getInfElemRefId());
		
		projectContent.saveAll();*/
	}
	
	private List<IPairGroup> getDiffPairGroups() {
		DiffMaker diffMaker = new DiffMaker();
		return diffMaker.makeDiff(refact.getFirstInfElementToCompare(), refact.getSecondInfElementToCompare());
	}

	private void showView() throws PartInitException {
		view = (DiffGroupResultView) getPage().showView(
				"org.spbu.pldoctoolkit.diff.view.DiffGroupResultView");
	}
	
	private IWorkbenchPage getPage() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return window.getActivePage();
	}
	
	final class MyCompareInput extends CompareEditorInput {
		
		private final String fLeft;
		private final String fRight;

		public MyCompareInput(CompareConfiguration configuration, String fleft, String fright) {
			super(configuration);
			configuration.setLeftLabel("firstInfElementToCompare");
			configuration.setRightLabel("secondInfElementToCompare");
			this.fLeft = fleft;
			this.fRight = fright;
		}

		@Override
		protected Object prepareInput(IProgressMonitor pm)
				throws InvocationTargetException, InterruptedException {
			// pm.beginTask(Utilities.getString("ResourceCompare.taskName"),
			// IProgressMonitor.UNKNOWN);

			/*String format = Utilities.getString("ResourceCompare.twoWay.title"); //$NON-NLS-1$
			String title = MessageFormat.format(format, new String[] {
					"firstInfElement", "secondInfElement" });
			setTitle(title);*/

			ITypedElement left = new MyTypedElem(fLeft);
			ITypedElement right = new MyTypedElem(fRight);

			return new DiffNode(left, right);
		}
		
		final class MyTypedElem extends BufferedContent implements ITypedElement {

			private final String string;

			public MyTypedElem(String string) {
				this.string = string;
			}

			@Override
			public String getName() {
				return "typedElemStringName";
			}

			@Override
			public Image getImage() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getType() {
				return ITypedElement.TEXT_TYPE;
			}

			@Override
			protected InputStream createStream() throws CoreException {
				return new ByteArrayInputStream(string.getBytes());
			}

		}
		@Override
		public void registerContextMenu(MenuManager menu, ISelectionProvider selectionProvider) {
			for (IContributionItem item : menu.getItems()) {
				System.out.println(item.toString());
			}
			super.registerContextMenu(menu, selectionProvider);
			System.out.println(selectionProvider.getClass()+" "+selectionProvider.getSelection());
			menu.add(new Action("trololo"){
				public void run(){
					System.out.println("Yahooooo!");
				}
			});
		}
		
	}
	
}
