package ro.zizicu.nwbase.controller;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.exceptions.EntityNotFoundException;
import ro.zizicu.nwbase.service.CrudService;


public class BasicOperationsController<Entity extends IdentityOwner<ID>, 
										ID extends Serializable,
										Service extends CrudService<Entity, ID>> {
	
	private static Logger logger = LoggerFactory.getLogger(BasicOperationsController.class);
	
	@Autowired
	protected Service service;

	@GetMapping(value = "/")
	public ResponseEntity<?> loadAll()
	{
		logger.debug("load all entities");
		Iterable<Entity> entities = service.loadAll();
		return ResponseEntity.ok(entities);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> load(@PathVariable ID id) {
		try {
			logger.debug("load entity " + id);
			return ResponseEntity.ok(service.load(id));
		}
		catch(EntityNotFoundException e) {
			String errorMessage = "entity not found, id:" + id;
			logger.error(errorMessage);
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}
	
	@DeleteMapping(value="/")
	public ResponseEntity<?> delete(@RequestBody Entity entity) {
		try {
			logger.debug("delete entity " + entity.getId());
			service.delete(entity);
			return ResponseEntity.ok("deleted");
		}
		catch (Exception e) {
			String errorMessage = e.getMessage();
			logger.error(errorMessage, e);
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}

	@PostMapping(value = "/")
	public ResponseEntity<?> create(@RequestBody Entity entity) {
		try {
			logger.debug("create entity " + entity);
			Entity e = service.create(entity);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Location", "categories/" + e.getId());
			return ResponseEntity.ok().headers(responseHeaders).body(e);
		}
		catch (Exception e) {
			String errorMessage = e.getMessage();
			logger.error(errorMessage, e);
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}
}

