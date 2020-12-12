package ro.zizicu.nwbase.entity;

public interface NamedIdentityOwner<ID> extends IdentityOwner<ID> {
	String getName();
	void setName(String name);
}
