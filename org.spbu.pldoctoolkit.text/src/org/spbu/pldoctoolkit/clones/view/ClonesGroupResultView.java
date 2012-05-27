package org.spbu.pldoctoolkit.clones.view;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.fop.layoutmgr.inline.ICLayoutManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.spbu.pldoctoolkit.actions.ExtractAsNewInfElem;
import org.spbu.pldoctoolkit.clones.*;
import org.spbu.pldoctoolkit.dialogs.SelectIntoInfElemDialog;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.CreateNest;
import org.spbu.pldoctoolkit.refactor.SelectIntoInfElem;

public class ClonesGroupResultView extends ViewPart {

	private IEditorPart editor;
	private TreeViewer treeViewer;
	private Action doubleClickAction;
	private LangElem infElem;

	@Override
	public void createPartControl(Composite parent) {

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		parent.setLayout(layout);

		GridData layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		// text.setLayoutData(layoutData);

		// Create the tree viewer as a child of the composite parent
		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(new ClonesGroupContentProvider());
		treeViewer.setLabelProvider(new ClonesGroupLabelProvider());

		treeViewer.setUseHashlookup(true);
		// treeViewer.getTableTree().getTable().setHeaderVisible(true);
		// treeViewer.getTableTree().getTable().setLinesVisible(true);

		// layout the tree viewer below the text field
		layoutData = new GridData();
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;
		treeViewer.getControl().setLayoutData(layoutData);

		doubleClickAction = createDoubleCLickAction();
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});

		createPopupMenu();
	}

	private void createPopupMenu() {
		MenuManager menuMgr = new MenuManager();
		Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		menuMgr.addMenuListener(new MyMenuListener());
		menuMgr.setRemoveAllWhenShown(true);
		treeViewer.getControl().setMenu(menu);
	}

	private final class MyMenuListener implements IMenuListener {
		@Override
		public void menuAboutToShow(IMenuManager manager) {
			ISelection iSelection = treeViewer.getSelection();
			if (!iSelection.isEmpty()
					&& iSelection instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection) iSelection;
				if (selection.getFirstElement() instanceof IClonesGroup) {
					manager.add(new ExtractAsNewInfElem((IClonesGroup) selection
									.getFirstElement(), editor));
				}
			}
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void setContent(List<IClonesGroup> clonesGroups, IEditorPart editor) {
		this.editor = editor;
		treeViewer.setInput(clonesGroups);
		treeViewer.refresh(clonesGroups, true);
		treeViewer.expandAll();
	}

	private Action createDoubleCLickAction() {

		return new Action() {
			public void run() {
				ISelection selection = treeViewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				if (obj instanceof IClonesGroup) {
					IClonesGroup clonesGroup = (IClonesGroup) obj;
					if (treeViewer.getExpandedState(clonesGroup)) {
						treeViewer.collapseToLevel(clonesGroup,
								TreeViewer.ALL_LEVELS);
					} else {
						treeViewer.expandToLevel(clonesGroup, 1);
					}
				} else if (obj instanceof ICloneInst) {
					ICloneInst cloneInst = (ICloneInst) obj;
					try {
						String path = cloneInst.getPath();
						IFile file = ResourcesPlugin.getWorkspace().getRoot()
								.getFile(new Path(path));
						IMarker marker = file.createMarker(IMarker.TEXT);
						marker.setAttribute(IMarker.CHAR_START, cloneInst
								.getAbsoluteStartPosition());
						marker.setAttribute(IMarker.CHAR_END, cloneInst
								.getAbsoluteEndPosition());
//						marker.setAttribute(IMarker.CHAR_START, 364);
//						marker.setAttribute(IMarker.CHAR_END, 363+1);

						IDE.openEditor(getSite().getPage(), marker);
						marker.delete();
					} catch (PartInitException e) {
						e.printStackTrace();
					} catch (CoreException e) {
						e.printStackTrace();
					}
				} else {
					// treeViewer.refresh(root, false);
					throw new IllegalStateException();
				}
			}
		};
	}

	public void clear() {
		List<IClonesGroup> emptyList = Collections.emptyList();
		this.setContent(emptyList , editor);
	}

	// private void createToolbar() {
	// Object addBookAction;
	// getViewSite().
	// // IAction addBookAction = new Action(){
	// // public void run() {
	// // System.out.println("Actiooooooon!");
	// // }
	// // };
	// toolbarManager.add(addBookAction );
	// // toolbarManager.add(removeAction);
	// }

	// private void createToolbar() {
	// IToolBarManager toolbarManager = getViewSite().getActionBars()
	// .getToolBarManager();
	// getViewSite().registerContextMenu(menuManager, selectionProvider);
	// IAction action = new Action(){
	// public void run() {
	// System.out.println("Actiooooooon!");
	// }
	// };
	// toolbarManager.add(action );
	// }

}