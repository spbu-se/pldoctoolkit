package org.eclipse.xslt.conversion.changelog.model;

public class DocChange extends Model {
	protected InterfaceChange parent;
	
	public DocChange() {
		super();
		this.checked = false;
	}
	
	public DocChange(String text, boolean checked) {
		super(text);
		this.checked = checked;
	}
	
	public InterfaceChange getParent() {
		return parent;
	}
}