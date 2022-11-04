package ro.zizicu.nwbase.service;

import java.io.Serializable;
import java.util.List;

import ro.zizicu.nwbase.entity.NamedIdentityOwner;

public interface NamedService<Entity extends NamedIdentityOwner<ID>, ID extends Serializable>
		extends CrudService<Entity, ID>
{
	List<Entity> loadByName(String name);
}
