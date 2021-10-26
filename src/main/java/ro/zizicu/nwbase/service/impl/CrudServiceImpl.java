package ro.zizicu.nwbase.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.exceptions.EntityNotFoundException;
import ro.zizicu.nwbase.service.CrudService;


public class CrudServiceImpl<Entity extends IdentityOwner<ID>, 
							  ID extends Serializable> 
	implements CrudService<Entity, ID>

{
	private static Logger logger = LoggerFactory.getLogger(CrudServiceImpl.class);
	
	@Autowired
	protected CrudRepository<Entity, ID> repository;

	@Override
	public void delete(Entity entity) {
		if(logger.isInfoEnabled()) logger.info("delete: " + entity.getClass().getName() + " id " + entity.getId());
		repository.delete(entity);
	}
	
	@Override
	public Entity create(Entity entity) {
		if(logger.isInfoEnabled()) logger.info("create: " + entity.getClass().getName() + " id " + entity.getId());
		return repository.save(entity);
	}
	
	@Override
	public List<Entity> loadAll() {
		if(logger.isInfoEnabled()) logger.info("load all entities");
		List<Entity> entities = new ArrayList<>();
		
		for(Entity e : repository.findAll())
			entities.add(e);
		
		return entities;
	}
	
	@Override
	public Entity load(ID id) {
		if(logger.isInfoEnabled()) logger.info("load entity with id " + id);
		Optional<Entity> entity = repository.findById(id);
		if(entity.isEmpty()) {
			logger.info("entity with id {} not found", id);
			throw new EntityNotFoundException();
		}
		return entity.get();
	}


	@Override
	public Entity update(Entity entity) {
		if(logger.isInfoEnabled()) logger.info("update: " + entity.getClass().getName() + " id " + entity.getId());
		return repository.save(entity);
	}
}
