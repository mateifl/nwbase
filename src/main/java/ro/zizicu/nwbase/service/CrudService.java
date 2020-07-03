package ro.zizicu.nwbase.service;

import java.io.Serializable;
import java.util.List;

public interface CrudService<Entity, ID extends Serializable> {
	Entity load(ID id);
	List<Entity> loadAll();
	Entity create(Entity entity);
	Entity update(Entity entity);
	void delete(Entity entity);
}
