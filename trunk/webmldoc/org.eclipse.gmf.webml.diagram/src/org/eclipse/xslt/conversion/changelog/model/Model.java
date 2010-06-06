package org.eclipse.xslt.conversion.changelog.model;
public abstract class Model {
	protected String name;
	protected int index;
	protected boolean checked;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
		
	public Model(String text) {
		this.name = text;
	}
	
	public Model() {
		this.name = "New Section";
	}	

	public void setChecked(boolean isChecked) {
		checked = isChecked;
	}

	public boolean getChecked() {
		return checked;
	}
}
