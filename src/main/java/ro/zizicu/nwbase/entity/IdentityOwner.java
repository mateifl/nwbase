package ro.zizicu.nwbase.entity;

public interface IdentityOwner<ID> {
	String getEntityName();
	ID getId();
	void setId(ID id);
}
