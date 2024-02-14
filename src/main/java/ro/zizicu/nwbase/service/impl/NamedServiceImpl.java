package ro.zizicu.nwbase.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ro.zizicu.nwbase.data.NamedEntityRepository;
import ro.zizicu.nwbase.entity.NamedIdentityOwner;
import ro.zizicu.nwbase.service.NamedService;


/**
 *
 * @param <Entity>
 * @param <ID>
 */
public class NamedServiceImpl<Entity extends NamedIdentityOwner<ID>,
								   ID extends Serializable> 
	extends CrudServiceImpl<Entity, ID>
	implements NamedService<Entity, ID>
{
	@Autowired
	private NamedEntityRepository<Entity, ID> namedRepository;
	
	@Override
	public List<Entity> loadByName(String name) {
		return namedRepository.findByName(name);
	}
}
