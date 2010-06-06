package org.eclipse.xslt.conversion.changelog.model;

import java.util.ArrayList;
import java.util.List;

public class InterfaceChange extends Model {
	private final List<InterfaceChange> changes;
	private final List<DocChange> sections;
	private int counter;
	private String id;
	
	public InterfaceChange() {
		counter = 0;
		sections = new ArrayList<DocChange>();
		changes =  new ArrayList<InterfaceChange>();
	}
	
	public InterfaceChange(String text, String elem_id) {
		super(text);
		counter = 0;
		id = elem_id;
		sections = new ArrayList<DocChange>();
		changes =  new ArrayList<InterfaceChange>();
	}
	
	protected void addDocChange(DocChange section) {
		sections.add(section);
		section.parent = this;
		section.index = counter++;
	}
	
	protected void addInterfaceChange(InterfaceChange change) {
		changes.add(change);
		change.index = counter++;
	}
	
	public List<DocChange> getDocChanges() {
		return sections;
	}
	
	public List<InterfaceChange> getInterfaceChanges() {
		return changes;
	}
	
	public String getId() {
		return id;
	}
	
	public int size() {
		return getInterfaceChanges().size() + getDocChanges().size();
	}
	
	public void add(InterfaceChange toAdd) {
		this.addInterfaceChange(toAdd);
	}
	
	public void add(DocChange toAdd) {
		this.addDocChange(toAdd);
	}
}