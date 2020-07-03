package ro.zizicu.nwbase.service;

import java.io.Serializable;

import ro.zizicu.nwbase.entity.NamedIdentityOwner;

public interface FindByNameService<ID extends Serializable,
									Entity extends NamedIdentityOwner<ID>> {
	
	Entity findByName(String name);
	
}
