package org.spbu.plweb.diagram.part.addProduct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.spbu.plweb.Area;
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.Element;
import org.spbu.plweb.Group;
import org.spbu.plweb.GroupType;
import org.spbu.plweb.Page;
import org.spbu.plweb.Root;
import org.spbu.plweb.SiteView;
import org.spbu.plweb.SourceRefElement;
import org.spbu.plweb.TargetRefElement;
import org.spbu.plweb.diagram.part.PlwebDiagramEditorPlugin;
import org.spbu.plweb.diagram.util.CollectionsUtils;
import org.spbu.plweb.diagram.util.DiagramContainer;
import org.spbu.plweb.diagram.util.SourceRefElementContainer;
import org.spbu.plweb.diagram.util.projects.ProjectOperationException;
import org.spbu.plweb.diagram.util.projects.ProjectWebRatio;

/**
 * Wizard page that allows to select elements from model.
 */
public class VariativitySelectionPage extends WizardPage {

	protected EObject selectedModelElement;

	private CheckboxTreeViewer modelViewer;

	private Text text;

	private DiagramRoot diagramRoot = null;

	private DiagramRoot productDiagramRoot = null;

	private String productName = null;

	private List<String> checkedPages = null;

	public VariativitySelectionPage(String pageName) {
		super(pageName);
	}

	public EObject getModelElement() {
		return selectedModelElement;
	}

	public void setModelElement(EObject modelElement) {
		selectedModelElement = modelElement;
		if (modelViewer != null) {
			if (selectedModelElement != null) {
				modelViewer.setInput(selectedModelElement.eResource());
				modelViewer.setSelection(new StructuredSelection(
						selectedModelElement));
			} else {
				modelViewer.setInput(null);
			}
			setPageComplete(validatePage());
		}
	}

	public void setDiagram(DiagramRoot diagramRoot) {
		this.diagramRoot = diagramRoot;
	}

	public void setProductDiagram(DiagramRoot diagramRoot) {
		productDiagramRoot = diagramRoot;
	}

	public void setProductName(String name) {
		productName = name;
	}

	public String getText() {
		return text.getText();
	}

	public Object[] getCheckedElements() {
		return modelViewer.getCheckedElements();
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite plate = new Composite(parent, SWT.NONE);
		plate.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		plate.setLayout(layout);
		setControl(plate);

		Label labelEnterTitle = new Label(plate, SWT.NONE);
		labelEnterTitle.setText("Enter title");
		labelEnterTitle.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_BEGINNING));

		text = new Text(plate, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if (productName != null) {
			text.setText(productName);
			text.setEditable(false);
		} else {
			text.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					setPageComplete(validatePage());
				}
			});
		}

		Label label = new Label(plate, SWT.NONE);
		label.setText("Select resources to add:");
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

		if (productDiagramRoot != null) {
			getCheckedPages();
		}

		modelViewer = new CheckboxTreeViewer(plate, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.heightHint = 300;
		layoutData.widthHint = 300;

		modelViewer.getTree().setLayoutData(layoutData);
		modelViewer.setContentProvider(new AdapterFactoryContentProvider(
				PlwebDiagramEditorPlugin.getInstance()
						.getItemProvidersAdapterFactory()));
		modelViewer.setLabelProvider(new AdapterFactoryLabelProvider(
				PlwebDiagramEditorPlugin.getInstance()
						.getItemProvidersAdapterFactory()) {
			@Override
			public String getText(Object object) {
				if (object != null && object instanceof Element) {
					Element element = (Element) object;
					String className = null;
					String title = element.getTitle();
					if (object instanceof Root) {
						className = "Root";
					} else if (object instanceof SiteView) {
						className = "SiteView";
					} else if (object instanceof Area) {
						className = "Area";
					} else if (object instanceof Group) {
						className = "Group";
						title = ((Group) element).getType().toString();
					} else if (object instanceof Page) {
						className = "Page";
					} else if (object instanceof org.spbu.plweb.Node) {
						className = "Node";
					}
					if (element instanceof TargetRefElement
							&& ((TargetRefElement) element).isOptional()) {
						return "[ " + className + " '" + title + "' ]";
					}
					return className + " '" + title + "'";
				}
				return super.getText(object);
			}
		});
		modelViewer.setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isGrayed(Object element) {
				if (element instanceof TargetRefElement) {
					TargetRefElement targetElement = (TargetRefElement) element;
					if (targetElement.isOptional()) {
						return false;
					}
					SourceRefElement parent = targetElement.getParent();
					if (parent instanceof Group) {
						return false;
					}
				}
				return true;
			}

			@Override
			public boolean isChecked(Object element) {
				if (checkedPages != null) {
					if (element instanceof SourceRefElement) {
						SourceRefElementContainer container = new SourceRefElementContainer(
								(SourceRefElement) element);
						List<Page> pages = container.getPageElements();
						for (Page page : pages) {
							if (checkedPages.contains(page.getSource())) {
								return true;
							}
						}
						return false;
					} else if (element instanceof Page) {
						if (!checkedPages.contains(((Page) element).getSource())) {
							return false;
						}
					}
					return true;
				} else {
					if (element instanceof TargetRefElement) {
						TargetRefElement targetElement = (TargetRefElement) element;
						SourceRefElement parent = targetElement.getParent();
						if (!modelViewer.getChecked(parent)) {
							return false;
						} else if (parent instanceof Group) {
							Group parentGroup = (Group) parent;
							if (parentGroup.getType().equals(GroupType.XOR)) {
								TargetRefElement firstElement = parentGroup
										.getClass_().get(0);
								if (!firstElement.equals(element)) {
									return false;
								}
							}
						}
					}
					return true;
				}
			}
		});
		if (selectedModelElement != null) {
			modelViewer.setInput(selectedModelElement.eResource());
			modelViewer.setSelection(new StructuredSelection(
					selectedModelElement));
		}
		modelViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				EObject element = (EObject) event.getElement();
				if (element instanceof TargetRefElement) {
					SourceRefElement parent = ((TargetRefElement) element).getParent();
					if (parent instanceof TargetRefElement && !modelViewer.getChecked(parent)) {
						modelViewer.setChecked(element, false);
					} else if (parent instanceof Group) {
						int checked = 0;
						for(TargetRefElement targetElement : parent.getClass_()) {
							if (modelViewer.getChecked(targetElement)) {
								checked++;
							}
						}
						if (((Group) parent).getType().equals(GroupType.XOR)) {
							if (!event.getChecked()) {
								modelViewer.setChecked(element, checked == 0);
							} else {
								modelViewer.setSubtreeChecked(parent, false);
								modelViewer.setChecked(parent, true);
								modelViewer.setSubtreeChecked(element, true);
								correctSubtree(element);
							}
						} else {
							if (event.getChecked() || checked == 0) {
								modelViewer.setSubtreeChecked(element, true);
								correctSubtree(element);
							} else {
								modelViewer.setSubtreeChecked(element, false);
							}
						}
					} else if (element instanceof Group
							&& event.getChecked()
							&& ((Group) element).getType().equals(GroupType.XOR)) {
						EList<TargetRefElement> children = ((Group) element).getClass_();
						if (children.size() > 0) {
							Element child = ((Group) element).getClass_().get(0);
							modelViewer.setSubtreeChecked(child, true);
							correctSubtree(child);
						}
					} else if (!((TargetRefElement) element).isOptional()) {
						if (modelViewer.getChecked(element)) {
							modelViewer.setSubtreeChecked(element, true);
							correctSubtree(element);
						} else {
							modelViewer.setChecked(element, true);
						}
					} else {
						modelViewer.setSubtreeChecked(element, event.getChecked());
						if (event.getChecked()) {
							correctSubtree(element);
						}
					}
				} else {
					modelViewer.setChecked(element, true);
				}
				setPageComplete(validatePage());
			}
			
			public void correctSubtree(EObject element) {
				if (!(element instanceof SourceRefElement)) {
					return;
				}
				
				if (element instanceof Group
						&& ((Group) element).getType().equals(GroupType.XOR)) {
					EList<TargetRefElement> children = ((Group) element).getClass_();
					if (children.size() > 0) {
						Element child = ((Group) element).getClass_().get(0);
						modelViewer.setSubtreeChecked(element, false);
						modelViewer.setChecked(element, true);
						modelViewer.setSubtreeChecked(child, true);
						correctSubtree(child);
					}
				} else {
					SourceRefElement source = (SourceRefElement) element;
					for (TargetRefElement child : source.getClass_()) {
						correctSubtree(child);
					}
				}
			}
		});
		modelViewer.expandAll();
		if (productDiagramRoot != null) {
			setPageComplete(validatePage());
		}
	}

	protected boolean validatePage() {
		if (text.getText().equals("")) {
			setErrorMessage("Product name cannot be empty");
			return false;
		}

		List<String> checkedPages = new ArrayList<String>();
		for (Object element : modelViewer.getCheckedElements()) {
			if (element instanceof Page) {
				checkedPages.add(((Page) element).getSource());
			}
		}
		System.out.println(checkedPages);

		try {
			ProjectWebRatio projectWr = new ProjectWebRatio(
					diagramRoot.getProjectPath());
			Map<String, String> missingPages = projectWr
					.getMissingPages(checkedPages);
			if (!missingPages.isEmpty()) {
				String reqPage = (String) missingPages.keySet().toArray()[0];
				String byPage = missingPages.get(reqPage);
				setMessage("Page '" + reqPage + "' is required by '" + byPage
						+ "' page", IMessageProvider.WARNING);
			} else {
				setMessage(null);
			}
		} catch (ProjectOperationException e) {
			// do nothing
		}

		if (productDiagramRoot != null) {
			DiagramContainer container = new DiagramContainer(diagramRoot);
			if (!container.isValid(checkedPages)) {
				String errorMessage = "Wrong diagram selection";
				List<Element> invalidElements = container.getInvalidElements();
				if (!invalidElements.isEmpty()) {
					Element element = invalidElements.get(0);
					if (element.getTitle() != null) {
						errorMessage += ": node '" + element.getTitle()
								+ "' is invalid";
					} else {
						errorMessage += ": some nodes are invalid";
					}
				}
				setErrorMessage(errorMessage);
				return false;
			}
		}
		setErrorMessage(null);
		return true;
	}

	private List<String> getCheckedPages() {
		if (checkedPages == null) {
			if (productDiagramRoot != null) {
				checkedPages = new ArrayList<String>();
				DiagramContainer container = new DiagramContainer(
						productDiagramRoot);
				for (Page page : container.getPageElements()) {
					checkedPages.add(page.getSource());
				}
			}
		}
		return checkedPages;
	}
}
