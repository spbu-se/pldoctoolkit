package org.spbu.pldoctoolkit.clones.view;

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
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.spbu.pldoctoolkit.clones.*;

public class ClonesGroupResultView extends ViewPart {
	
	private TreeViewer treeViewer;
	private Action doubleClickAction;

	@Override
	public void createPartControl(Composite parent) {
		/*
		 * Create a grid layout object so the text and treeviewer are layed out
		 * the way I want.
		 */
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.verticalSpacing = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 2;
		parent.setLayout(layout);

		/*
		 * Create a "label" to display information in. I'm using a text field
		 * instead of a lable so you can copy-paste out of it.
		 */
		// text = new Text(parent, SWT.READ_ONLY | SWT.SINGLE | SWT.BORDER);
		// // layout the text field above the treeviewer
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
//		MenuManager menuManager = new MenuManager("Lololololol DocLine");
//		ISelectionProvider selectionProvider = new ISelectionProvider(){
//
//		getViewSite().registerContextMenu(menuManager, selectionProvider);
//		}		
		//treeViewer.expandAll();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	public void setContent(List<IClonesGroup> clonesGroups) {
		treeViewer.setInput(clonesGroups);
		treeViewer.expandAll();
		treeViewer.refresh(clonesGroups, true);
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
						treeViewer.collapseToLevel(clonesGroup, TreeViewer.ALL_LEVELS);
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
						marker.setAttribute(IMarker.CHAR_START, cloneInst.getAbsoluteStartPosition());
						marker.setAttribute(IMarker.CHAR_END, cloneInst.getAbsoluteEndPosition());

						IDE.openEditor(getSite().getPage(), marker);
						marker.delete();
					} catch (PartInitException e) {
						e.printStackTrace();
					} catch (CoreException e) {
						e.printStackTrace();
					}
				} else {
//					treeViewer.refresh(root, false);
					throw new IllegalStateException();
				}
			}
		};
	}
	
//	private void createToolbar() {
//		Object addBookAction;
//		getViewSite().
////		IAction addBookAction = new Action(){
////			public void run() {
////				System.out.println("Actiooooooon!");
////			}
////		};
//		toolbarManager.add(addBookAction );
//		// toolbarManager.add(removeAction);
//	}

//	private void createToolbar() {
//		IToolBarManager toolbarManager = getViewSite().getActionBars()
//				.getToolBarManager();
//		getViewSite().registerContextMenu(menuManager, selectionProvider);
//		IAction action = new Action(){
//			public void run() {
//			System.out.println("Actiooooooon!");
//		}
//		};
//		toolbarManager.add(action );
//	}
	
}