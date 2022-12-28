package ro.zizicu.nwbase.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import lombok.extern.slf4j.Slf4j;
import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.exceptions.EntityNotFoundException;
import ro.zizicu.nwbase.service.CrudService;

@Slf4j
public class CrudServiceImpl<Entity extends IdentityOwner<ID>, 
							  ID extends Serializable> 
	implements CrudService<Entity, ID>

{

	@Autowired
	protected CrudRepository<Entity, ID> repository;

	@Override
	public void delete(Entity entity) {
		if(log.isInfoEnabled()) log.info("delete: {} id {}", entity.getClass().getName(), entity.getId());
		repository.delete(entity);
	}
	
	@Override
	public Entity create(Entity entity) {
		if(log.isInfoEnabled()) log.info("create: {} id {}", entity.getClass().getName(), entity.getId());
		return repository.save(entity);
	}
	
	@Override
	public List<Entity> loadAll() {
		if(log.isInfoEnabled()) log.info("load all entities");
		List<Entity> entities = new ArrayList<>();
		repository.findAll().forEach(entities::add);
		return entities;
	}
	
	@Override
	public Entity load(ID id) {
		if(log.isInfoEnabled()) log.info("load entity with id {}", id);
		Optional<Entity> entity = repository.findById(id);
		if(entity.isEmpty()) {
			log.info("entity with id {} not found", id);
			throw new EntityNotFoundException();
		}
		return entity.get();
	}


	@Override
	public Entity update(Entity entity) {
		if(log.isInfoEnabled()) log.info("update: {} id {}", entity.getClass().getName(), entity.getId());
		return repository.save(entity);
	}
}
