package plweb.diagram.util;

import java.util.ArrayList;
import java.util.List;

import plweb.Element;
import plweb.Page;
import plweb.SourceRefElement;
import plweb.TargetRefElement;

public class SourceRefElementContainer {
	
	SourceRefElement sourceRefElement = null;
	
	public SourceRefElementContainer(SourceRefElement sourceRefElement) {
		this.sourceRefElement = sourceRefElement;
	}
	
	public List<Element> getElements() {
		List<Element> result = new ArrayList<Element>();
		for (Element element : getAllChildren(sourceRefElement)) {
			result.add(element);
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
