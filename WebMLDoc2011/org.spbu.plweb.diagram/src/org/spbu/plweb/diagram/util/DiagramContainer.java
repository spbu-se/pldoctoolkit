package org.spbu.plweb.diagram.util;

import java.util.ArrayList;
import java.util.List;

import org.spbu.plweb.Area;
import org.spbu.plweb.DiagramRoot;
import org.spbu.plweb.Element;
import org.spbu.plweb.Group;
import org.spbu.plweb.GroupType;
import org.spbu.plweb.Node;
import org.spbu.plweb.Page;
import org.spbu.plweb.Root;
import org.spbu.plweb.SourceRefElement;
import org.spbu.plweb.TargetRefElement;

public class DiagramContainer {
	
	private DiagramRoot diagramRoot = null;
	
	private List<Element> invalidElements = null;
	
	public DiagramContainer(DiagramRoot diagramRoot) {
		this.diagramRoot = diagramRoot;
	}
	
	public List<Element> getElements() {
		List<Element> result = new ArrayList<Element>();
		Root root = diagramRoot.getRoot();
		if (root != null) {
			result.add(root);
			for (Element element : getAllChildren(root)) {
				result.add(element);
			}
		}
		return result;
	}
	
	public List<SourceRefElement> getSourceElements() {
		List<SourceRefElement> result = new ArrayList<SourceRefElement>();
		for (Element element : getElements()) {
			if (element instanceof SourceRefElement) {
				result.add((SourceRefElement) element);
			}
		}
		return result;
	}
	
	public List<TargetRefElement> getTargetElements() {
		List<TargetRefElement> result = new ArrayList<TargetRefElement>();
		for (Element element : getElements()) {
			if (element instanceof TargetRefElement) {
				result.add((TargetRefElement) element);
			}
		}
		return result;
	}
	
	public List<Page> getPageElements() {
		List<Page> result = new ArrayList<Page>();
		for (Element element : getElements()) {
			if (element instanceof Page) {
				result.add((Page) element);
			}
		}
		return result;
	}
	
	public boolean isValid(List<String> pages) {
		Root root = diagramRoot.getRoot();
		invalidElements = new ArrayList<Element>();
		for (TargetRefElement element : root.getClass_()) {
			if (!validate(element, pages, false)) {
				return false;
			}
		}
		return true;
	}
	
	public List<Element> getInvalidElements() {
		return invalidElements;
	}
	
	private boolean validate(TargetRefElement element, List<String> pages, boolean optional) {
		if (element instanceof Page) {
			if (!optional && !element.isOptional() && !pages.contains(((Page) element).getSource())) {
				invalidElements.add(element);
				return false;
			}
		} else if (element instanceof Node) {
			if (!element.isOptional() && !optional && !hasSelectedElements((SourceRefElement) element, pages)) {
				invalidElements.add(element);
				return false;
			}
			for ( TargetRefElement child : ((SourceRefElement) element).getClass_()) {
				if (!validate(child, pages, element.isOptional() || optional)) {
					return false;
				}
			}
		} else if (element instanceof Group) {
			if ((!element.isOptional() && !optional) || hasSelectedElements((SourceRefElement) element, pages)) {
				int checked = 0;
				for ( TargetRefElement child : ((SourceRefElement) element).getClass_()) {
					if (child instanceof SourceRefElement
							&& !hasSelectedElements((SourceRefElement) child, pages)) {
						continue;
					} else if ((child instanceof Page)
							&& !pages.contains(((Page) child).getSource())) {
						continue;
					}
					checked++;
				}
				if (((Group) element).getType().equals(GroupType.XOR)) {
					if (checked != 1) {
						invalidElements.add(element);
						return false;
					}
				} else {
					if (checked == 0) {
						invalidElements.add(element);
						return false;
					}
				}
			}
			for ( TargetRefElement child : ((SourceRefElement) element).getClass_()) {
				if (!validate(child, pages, true)) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean hasSelectedElements(SourceRefElement element, List<String> pages) {
		SourceRefElementContainer container = new SourceRefElementContainer(element);
		for (Page page : container.getPageElements()) {
			if (pages.contains(page.getSource())) {
				return true;
			}
		}
		return false;
	}
	
	private List<Element> getAllChildren(SourceRefElement parent) {
		List<Element> result = new ArrayList<Element>();
		for (TargetRefElement element : parent.getClass_()) {
			result.add(element);
			if (element instanceof SourceRefElement) {
				for (Element childElement : getAllChildren((SourceRefElement) element)) {
					result.add(childElement);
				}
			}
		}
		return result;
	}
}
