package org.spbu.plweb.diagram.part.setVariativity;

import java.awt.MenuBar;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import org.eclipse.swt.widgets.Text;
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.Element;
import org.spbu.plweb.Group;
import org.spbu.plweb.GroupType;
import org.spbu.plweb.Page;
import org.spbu.plweb.Root;
import org.spbu.plweb.SiteView;
import org.spbu.plweb.SourceRefElement;
import org.spbu.plweb.TargetRefElement;
import org.spbu.plweb.diagram.edit.parts.AreaTitleEditPart;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorPlugin;
import org.spbu.plweb.diagram.util.DiagramContainer;
import org.spbu.plweb.diagram.util.SourceRefElementContainer;
import org.spbu.plweb.diagram.util.projects.parts.Area;
import org.spbu.plweb.impl.AreaImpl;
import org.spbu.plweb.impl.GroupImpl;
import org.spbu.plweb.impl.RootImpl;
import org.spbu.plweb.impl.SiteViewImpl;

/**
 * @author Violectra
 * 
 */
public class SetVariativityPage extends WizardPage {

	private CheckboxTableViewer modelViewer;

	private CheckboxTableViewer typeOfGroupViewer;

	private Text text;

	private String groupName = null;

	private DiagramRoot productDiagramRoot = null;

	private Button orButton;

	private Button xorButton;

	private Button andButton;

	protected SetVariativityPage(String pageName) {
		super(pageName);
	}

	public void setProductDiagram(DiagramRoot diagramRoot) {
		productDiagramRoot = diagramRoot;
	}

	public Object[] getCheckedElements() {
		return modelViewer.getCheckedElements();
	}

	public String getGroupType() {
		if (orButton.getSelection())
			return "OR";
		else if (xorButton.getSelection())
			return "XOR";
		else if (andButton.getSelection())
			return "AND";
		return null;
	}

	public String getGroupName() {
		return groupName;
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite plate = new Composite(parent, SWT.NONE);
		plate.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		plate.setLayout(layout);
		setControl(plate);

		Label labelEnterTitle = new Label(plate, SWT.NONE);
		labelEnterTitle.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_BEGINNING));

		Object selectedElement = getSelectedElement().getNotationView()
				.getElement();
		labelEnterTitle.setText("Set variativity for "
				+ getElementName(selectedElement));

		Composite panel = new Composite(plate, SWT.BORDER);
		panel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		panel.setLayout(new GridLayout(2, false));

		modelViewer = CheckboxTableViewer.newCheckList(panel, SWT.LEFT
				| SWT.BORDER);
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		// layoutData.heightHint = 300;
		// layoutData.widthHint = 300;
		// CheckboxTableViewer.
		modelViewer.getControl().setLayoutData(layoutData);
		
		modelViewer.setContentProvider(new AdapterFactoryContentProvider(
				PlwebDiagramEditorPlugin.getInstance()
				.getItemProvidersAdapterFactory()));

		modelViewer.setInput(selectedElement);

		modelViewer.setLabelProvider(new AdapterFactoryLabelProvider(
				PlwebDiagramEditorPlugin.getInstance()
						.getItemProvidersAdapterFactory()));

		// ((TableLayout) tree.getLayout()).addColumnData(new
		// ColumnPixelData(18, false, true));

		TreeViewer viewer = createViewer(panel);
		viewer.getControl().setLayoutData(createFillBothGridData(1));
		viewer.setContentProvider(new AdapterFactoryContentProvider(
				PlwebDiagramEditorPlugin.getInstance()
						.getItemProvidersAdapterFactory()));
		viewer.setInput(selectedElement);

		viewer.setLabelProvider(new AdapterFactoryLabelProvider(
				PlwebDiagramEditorPlugin.getInstance()
						.getItemProvidersAdapterFactory()));
		// viewer.set

		Label nameOfNewGroupEnterTitle = new Label(plate, SWT.NONE);
		nameOfNewGroupEnterTitle.setText("Enter title of new group");
		nameOfNewGroupEnterTitle.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_BEGINNING));

		text = new Text(plate, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					groupName = text.getText();
//					setPageComplete(validatePage());
				}
			});

		Label typeOfNewGroupTitle = new Label(plate, SWT.NONE);
		typeOfNewGroupTitle.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_BEGINNING));
		typeOfNewGroupTitle.setText("Choose type of new group");

		GridData radioGridData = new GridData(GridData.FILL_HORIZONTAL);

		orButton = createRadioButton(plate, "OR", radioGridData, true);

		xorButton = createRadioButton(plate, "XOR", radioGridData, false);

		andButton = createRadioButton(plate, "AND", radioGridData, false);

	}

	protected GridData createFillBothGridData(int span) {
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.grabExcessVerticalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = span;
		return data;
	}

	private TableViewer TableViewer(Composite panel, int none) {
		// TODO Auto-generated method stub
		return null;
	}

	private Button createRadioButton(Composite plate, String type,
			GridData radioGridData, boolean selected) {
		Button btn = new Button(plate, SWT.RADIO);
		btn.setText(type);

		btn.setLayoutData(radioGridData);
		btn.setSelection(selected);

		return btn;
	}

	private String getElementName(Object obj) {
		if (obj != null && obj instanceof Element) {
			Element element = (Element) obj;
			String className = null;
			String title = element.getTitle();
			if (obj instanceof RootImpl) {
				className = "Root";
			} else if (obj instanceof SiteViewImpl) {
				className = "Site View";
			} else if (obj instanceof AreaImpl) {
				className = "Area";
			}
			return className + " '" + title + "'";
		}
		return "Errorr"; // here should be an error handling
	}

	private ShapeNodeEditPart getSelectedElement() {
		IWizardContainer container = this.getContainer();
		// SetVariativityWizard wizard = (SetVariativityWizard)container;
		// return wizard.getInvoker();
		SetVariativityWizardDialog wizardDialog = (SetVariativityWizardDialog) container;
		return wizardDialog.selectedElement;
	}

	protected TreeViewer createViewer(Composite parent) {
		final Tree tree = new Tree(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		TableLayout layout = new TableLayout() {

			private boolean firstTime = true;

			public void layout(Composite c, boolean flush) {
				super.layout(c, flush);
				if (!firstTime) {
					return;
				}
				int cawidth = c.getClientArea().width;

				// XXX: Layout is being called with an invalid value the first
				// time
				// it is being called on Linux. This method resets the
				// Layout to null so we make sure we run it only when
				// the value is OK.
				if (cawidth <= 1) {
					return;
				}

				TreeColumn elementColumn = ((Tree) c).getColumn(0);
				int vsbWidth = tree.getVerticalBar().getSize().x + 9; // 9 is
																		// magic
																		// since
																		// vsbw
																		// is
																		// not
																		// enough
				int width = elementColumn.getWidth() - vsbWidth;
				if (width < 0) {
					width = 0;
				}
				elementColumn.setWidth(width);
				firstTime = false;
			}
		};
		tree.setLayout(layout);
		tree.setHeaderVisible(true);
		// tree.setLinesVisible(true);

		TreeColumn elementColumn = new TreeColumn(tree, SWT.LEFT);
		elementColumn.setText("123");
		elementColumn.setResizable(true);
		layout.addColumnData(new ColumnWeightData(1, 32, true));

		addResolutionColumn(tree);
		addResolutionColumn(tree);

		return new TreeViewer(tree);
	}

	protected TreeColumn addResolutionColumn(Tree tree) {
		TreeColumn column = new TreeColumn(tree, SWT.CENTER);
		// column.setText(resolution.getName());
		column.setResizable(true);
		((TableLayout) tree.getLayout()).addColumnData(new ColumnPixelData(18,
				false, true));
		return column;
	}

}

class OptionalityLabelProvider implements ILabelProvider {

	public Image getImage(Object arg0) {
		return null;
	}

	public String getText(Object arg0) {
		return "";
	}

	public void addListener(ILabelProviderListener arg0) {
		// Throw it away
	}

	public void dispose() {
		// Nothing to dispose
	}

	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	public void removeListener(ILabelProviderListener arg0) {
		// Ignore
	}
}
