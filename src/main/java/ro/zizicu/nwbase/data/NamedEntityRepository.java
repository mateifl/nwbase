package ro.zizicu.nwbase.data;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ro.zizicu.nwbase.entity.NamedIdentityOwner;

public interface NamedEntityRepository<Entity extends NamedIdentityOwner<ID>, ID extends Serializable>
	extends CrudRepository<Entity, ID>
{
	List<Entity> findByName(String name);
	
}
