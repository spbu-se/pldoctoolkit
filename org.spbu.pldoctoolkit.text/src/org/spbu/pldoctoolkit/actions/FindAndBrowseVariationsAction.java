package org.spbu.pldoctoolkit.actions;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.clones.view.ClonesGroupResultView;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.CloneFinderUtil;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.VariationDetectAndBrowseUtil;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

//dluciv
public class FindAndBrowseVariationsAction  extends Action implements IValidateDRLSelection {
	private static String refactName = "Find and browse variations";
	private final FileEditorInput editorInput;
	private IEditorPart editor;
	private TextEditor te;
	private final IProject project;
	private final ProjectContent projectContent;
	private ClonesGroupResultView view;

	public FindAndBrowseVariationsAction(IEditorPart editor) throws Exception {
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
		// TODO Auto-generated method stub		
	}

	@Override
	public void run() {
		VariationDetectAndBrowseUtil.run(editor);
	}
}
