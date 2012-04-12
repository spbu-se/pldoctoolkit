package org.spbu.pldoctoolkit.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.dialogs.HandleDIffWithAnotherInfElemDialog;
import org.spbu.pldoctoolkit.dialogs.InsertIntoDictionaryDialog;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.HandleDIffWithAnotherInfElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

public class HandleDIffWithAnotherInfElemAction extends Action implements
		IValidateDRLSelection {
	
	IEditorPart editor;
	TextEditor te;
	IProject project;
	HandleDIffWithAnotherInfElem refact = new HandleDIffWithAnotherInfElem();
	ProjectContent projectContent;
	FileEditorInput editorInput;

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

		CompareConfiguration cc = new CompareConfiguration();
		// buffered merge mode: don't ask for confirmation
		// when switching between modified resources
		cc.setProperty(CompareEditor.CONFIRM_SAVE_PROPERTY, new Boolean(false));

		// uncomment following line to have separate outline view
		// cc.setProperty(CompareConfiguration.USE_OUTLINE_VIEW, new Boolean(true));

		MyCompareInput input = new MyCompareInput(cc, refact.getFirstInfElementToCompare().getTextRepresentation(), refact.getSecondInfElementToCompare().getTextRepresentation());

		/*Differencer differencer = new Differencer();
		Object obj = differencer.findDifferences(false,null,null,null,refact.getFirstInfElementToCompare().getTextRepresentation(), refact.getSecondInfElementToCompare().getTextRepresentation());
		System.out.println(obj);*/	
		CompareUI.openCompareEditor(input);
//		CompareUI.openCompareDialog(input);
//		DrlTextEditor editor = new DrlTextEditor();
//		CompareUI.reuseCompareEditor(input, editor);
		/*
		 * DRLDocument DRLdoc =
		 * projectContent.DRLDocs.get(editorInput.getFile()); IDocument doc =
		 * te.getDocumentProvider().getDocument(editorInput); ISelection sel =
		 * te.getSelectionProvider().getSelection(); TextSelection ts =
		 * (TextSelection) sel; try { int line1 = ts.getStartLine(); int column1
		 * = ts.getOffset() - doc.getLineOffset(line1); int line2 =
		 * ts.getEndLine(); int column2 = ts.getOffset() + ts.getLength() -
		 * doc.getLineOffset(line2); PositionInText startPos = new
		 * PositionInText(line1 + 1, column1 + 1); PositionInText endPos = new
		 * PositionInText(line2 + 1, column2 + 1);
		 * 
		 * if(startPos.compare(endPos) != 0) return;
		 * 
		 * refact.setParams(projectContent, DRLdoc, editor, startPos);
		 * refact.perform();
		 * 
		 * projectContent.saveAll(); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
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
		
	}
	
}
