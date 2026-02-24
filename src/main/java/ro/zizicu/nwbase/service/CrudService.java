package ro.zizicu.nwbase.service;

import ro.zizicu.nwbase.entity.IdentityOwner;

import java.io.Serializable;
import java.util.List;

/**
 * Role: expose a CRUD REST API for entities that do not need a data transfer object.
 * @param <Entity>
 * @param <ID>
 */
public interface CrudService<Entity extends IdentityOwner<ID>, ID extends Serializable> {
	Entity load(ID id);
	List<Entity> loadAll();
	Entity create(Entity entity);
	void delete(ID id);
}
