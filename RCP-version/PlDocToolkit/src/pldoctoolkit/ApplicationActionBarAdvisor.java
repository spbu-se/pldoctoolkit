package pldoctoolkit;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.ide.IDEActionFactory;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	//fileMenu
	private IWorkbenchAction newAction;
	private IWorkbenchAction closeAction;
	private IWorkbenchAction closeAllAction;
	private IWorkbenchAction saveAction;
	private IWorkbenchAction saveAsAction;
	private IWorkbenchAction saveAllAction;
	private IWorkbenchAction revertAction;
	private IWorkbenchAction renameAction;
	private IWorkbenchAction refreshAction;
	private IWorkbenchAction printAction;
	private IWorkbenchAction importAction;
	private IWorkbenchAction exportAction;
	private IWorkbenchAction propertiesAction;
	private IWorkbenchAction exitAction;
	
	//editMenu
	private IWorkbenchAction undoAction;
	private IWorkbenchAction redoAction;
	private IWorkbenchAction cutAction;
	private IWorkbenchAction copyAction;
	private IWorkbenchAction pasteAction;
	private IWorkbenchAction deleteAction;
	private IWorkbenchAction selectAllAction;
	private IWorkbenchAction findAndReplaceAction;
	
	//projectMenu
	private IWorkbenchAction openProjectAction;
	private IWorkbenchAction closeProjectAction;
	private IWorkbenchAction projectPropertiesAction;
	
	//windowMenu
	private IWorkbenchAction newWindowAction;
	private IWorkbenchAction newEditorAction;
	private MenuManager perspectiveMenu = new MenuManager("&Open Perspective");
    private MenuManager viewMenu = new MenuManager("Show &View");
	private IWorkbenchAction windowPreferencesAction;
	
	//helpMenu
	private IWorkbenchAction helpContentsAction;
	private IWorkbenchAction aboutAction;
	  
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	newAction = ActionFactory.NEW_WIZARD_DROP_DOWN.create(window);
    	closeAction = ActionFactory.CLOSE.create(window);
    	closeAllAction = ActionFactory.CLOSE_ALL.create(window);
    	saveAction = ActionFactory.SAVE.create(window);
    	saveAsAction = ActionFactory.SAVE_AS.create(window);
    	saveAllAction = ActionFactory.SAVE_ALL.create(window);
    	revertAction = ActionFactory.REVERT.create(window);
    	renameAction = ActionFactory.RENAME.create(window);
    	refreshAction = ActionFactory.REFRESH.create(window);
    	printAction = ActionFactory.PRINT.create(window);
    	importAction = ActionFactory.IMPORT.create(window);
    	exportAction = ActionFactory.EXPORT.create(window);
    	propertiesAction = ActionFactory.PROPERTIES.create(window);
    	exitAction = ActionFactory.QUIT.create(window);
    	
    	undoAction = ActionFactory.UNDO.create(window);
    	redoAction = ActionFactory.REDO.create(window);
    	cutAction = ActionFactory.CUT.create(window);
    	copyAction = ActionFactory.COPY.create(window);
    	pasteAction = ActionFactory.PASTE.create(window);
    	deleteAction = ActionFactory.DELETE.create(window);
    	selectAllAction = ActionFactory.SELECT_ALL.create(window);
    	findAndReplaceAction = ActionFactory.FIND.create(window);
    	
    	openProjectAction = IDEActionFactory.OPEN_PROJECT.create(window);
    	closeProjectAction = IDEActionFactory.CLOSE_PROJECT.create(window);
    	projectPropertiesAction = IDEActionFactory.OPEN_PROJECT_PROPERTIES.create(window);
    	
    	
    	newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
    	newEditorAction = ActionFactory.NEW_EDITOR.create(window);
    	perspectiveMenu.add(ContributionItemFactory.PERSPECTIVES_SHORTLIST.create(window));
	    viewMenu.add(ContributionItemFactory.VIEWS_SHORTLIST.create(window));
	    windowPreferencesAction = ActionFactory.PREFERENCES.create(window);
	    
	    helpContentsAction = ActionFactory.HELP_CONTENTS.create(window);
	    aboutAction = ActionFactory.ABOUT.create(window);
        /*
        register(aboutAction);
*/
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    	MenuManager fileMenu = new MenuManager("&File",IWorkbenchActionConstants.M_FILE);
    	MenuManager editMenu = new MenuManager("&Edit",IWorkbenchActionConstants.M_EDIT);
    	MenuManager projectMenu = new MenuManager("&Project",IWorkbenchActionConstants.M_PROJECT);
    	MenuManager windowMenu = new MenuManager("&Window",IWorkbenchActionConstants.M_WINDOW);
    	MenuManager helpMenu = new MenuManager("&Help",IWorkbenchActionConstants.M_HELP);

    	  /* for (int i=0; i<menuBar.getItems().length; i++){
    	       IContributionItem item=menuBar.getItems()[i];
    	       if (item.getId().equals("org.eclipse.search")){
    	           item.setVisible(false);
    	       }
    	   }*/
    	
    	menuBar.add(fileMenu);
    	menuBar.add(editMenu);
    	menuBar.add(projectMenu);
    	menuBar.add(windowMenu);
    	menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
    	menuBar.add(helpMenu);
    	
    	
    	
    	
    	fileMenu.add(newAction);
    	fileMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
    	fileMenu.add(new Separator());
    	fileMenu.add(closeAction);
    	fileMenu.add(closeAllAction);
    	fileMenu.add(new Separator());
    	fileMenu.add(saveAction);
    	fileMenu.add(saveAsAction);
    	fileMenu.add(saveAllAction);
    	fileMenu.add(revertAction);
    	fileMenu.add(new Separator());
    	fileMenu.add(renameAction);
    	fileMenu.add(refreshAction);
    	fileMenu.add(new Separator());
    	fileMenu.add(printAction);
    	fileMenu.add(new Separator());
    	fileMenu.add(importAction);
    	fileMenu.add(exportAction);
    	fileMenu.add(new Separator());
    	fileMenu.add(propertiesAction);
    	fileMenu.add(new Separator());
    	fileMenu.add(exitAction);
    	
    	editMenu.add(undoAction);
    	editMenu.add(redoAction);
    	editMenu.add(new Separator());
    	editMenu.add(cutAction);
    	editMenu.add(copyAction);
    	editMenu.add(pasteAction);
    	editMenu.add(new Separator());
    	editMenu.add(deleteAction);
    	editMenu.add(selectAllAction);
    	editMenu.add(new Separator());
    	editMenu.add(findAndReplaceAction);
    	
    	projectMenu.add(openProjectAction);
    	projectMenu.add(closeProjectAction);
    	projectMenu.add(projectPropertiesAction);
    	
    	windowMenu.add(newWindowAction);
    	windowMenu.add(newEditorAction);
        windowMenu.add(new Separator());
    	windowMenu.add(perspectiveMenu);
        windowMenu.add(viewMenu);
        windowMenu.add(new Separator());
        windowMenu.add(windowPreferencesAction);
        
        
        helpMenu.add(helpContentsAction);
        helpMenu.add(aboutAction);
    }
    
}
