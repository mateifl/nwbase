package ro.zizicu.nwbase.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.exceptions.EntityNotFoundException;
import ro.zizicu.nwbase.service.CrudService;

@Slf4j
@RequiredArgsConstructor
public abstract class CrudServiceImpl<Entity extends IdentityOwner<ID>,
							  ID extends Serializable> 
	implements CrudService<Entity, ID>

{
	@Autowired
	private final CrudRepository<Entity, ID> repository;

	protected CrudRepository<Entity, ID> getRepository() {
		return repository;
	}

	@Override
	public void delete(ID id) {
		Entity entity = repository.findById(id).orElse(null);
		if(log.isInfoEnabled()) {
            assert entity != null;
            log.info("delete: {} id {}", entity.getClass().getName(), entity.getId());
        }
		repository.delete(entity);
	}

	@Override
	public Entity create(Entity entity) {
		if(log.isInfoEnabled()) log.info("create {} id {}", entity.getClass().getName(), entity.getId());
		return transform(repository.save(entity));
	}

	@Override
	public List<Entity> loadAll() {
		if(log.isInfoEnabled()) log.info("load all entities");
		List<Entity> entities = new ArrayList<>();

		for(Entity e : repository.findAll())
			entities.add(e);

		return entities.stream().map(this::transform).toList();
	}

	@Override
	public Entity load(ID id) {
		if(log.isInfoEnabled()) log.info("load entity with id {}", id);
		Optional<Entity> entity = repository.findById(id);
		if(entity.isEmpty()) {
			log.info("entity with id {} not found", id);
			throw new EntityNotFoundException();
		}
		return transform(entity.get());
	}

	protected abstract Entity transform(Entity e);

}
