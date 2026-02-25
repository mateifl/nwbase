package ro.zizicu.nwbase.controller;

import java.io.Serializable;
import java.util.List;


import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ro.zizicu.nwbase.entity.NamedIdentityOwner;
import ro.zizicu.nwbase.service.NamedService;

@Slf4j
public abstract class NamedEntityController<Entity extends NamedIdentityOwner<ID>,
									S extends NamedService<Entity, ID>,
									ID extends Serializable> 
			  extends CrudOperationsController<Entity, NamedService<Entity, ID>, ID> {


	public NamedEntityController(S service) {
		super(service);
	}

	@GetMapping(value = "/name/{name}")
	public ResponseEntity<List<Entity>> findByName(@PathVariable String name) {
        log.debug("find by name: {}", name);
		return ResponseEntity.ok(getService().loadByName(name));
	}
}
