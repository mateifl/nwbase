package ro.zizicu.nwbase.controller;

import java.io.Serializable;


import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ro.zizicu.nwbase.entity.NamedIdentityOwner;
import ro.zizicu.nwbase.service.NamedService;

@Slf4j
public abstract class NamedEntityController<Entity extends NamedIdentityOwner<ID>,
									ID extends Serializable> 
			  extends CrudOperationsController<Entity, ID> {


	private final NamedService<Entity, ID> namedService;

	public NamedEntityController(NamedService<Entity, ID> namedService) {
		super(namedService);
		this.namedService = namedService;
	}

	@GetMapping(value = "name/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name) {
		log.debug("find: " + name);
		try {
			return ResponseEntity.ok(namedService.loadByName(name));
		}
		catch (Exception e) {
			String errorMessage = e.getMessage();
			log.error(errorMessage, e);
			return ResponseEntity.badRequest().body(errorMessage);
		}
		
	}
}
