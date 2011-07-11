package org.spbu.plweb.diagram.util.projects.partsPLWeb;

public abstract class IsOptionalAware extends TitleAware{
	private final boolean optional;

	public IsOptionalAware(final boolean optional, final String title) {
		super(title);
		this.optional = optional;
	}

	private boolean getOptional() {
		return optional;
	}
}
