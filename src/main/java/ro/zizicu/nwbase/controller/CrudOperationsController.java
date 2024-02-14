package ro.zizicu.nwbase.controller;

import java.io.Serializable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.exceptions.EntityNotFoundException;
import ro.zizicu.nwbase.service.CrudService;


@Slf4j
@Data
public abstract class CrudOperationsController<Entity extends IdentityOwner<ID>,
										ID extends Serializable> {

	private final CrudService<Entity, ID> service;


	public CrudOperationsController(CrudService<Entity, ID> service) {
		this.service = service;
	}

	@GetMapping(value = "/")
	public ResponseEntity<?> loadAll()
	{
		log.debug("load all entities");
		Iterable<Entity> entities = service.loadAll();
		return ResponseEntity.ok(entities);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> load(@PathVariable ID id) {
		try {
			log.debug("load entity " + id);
			return ResponseEntity.ok(service.load(id));
		}
		catch(EntityNotFoundException e) {
			String errorMessage = "entity not found, id:" + id;
			log.error(errorMessage);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}
	
	@DeleteMapping(value="/")
	public ResponseEntity<?> delete(@RequestBody Entity entity) {
		try {
			log.debug("delete entity " + entity.getId());
			service.delete(entity);
			return ResponseEntity.ok("deleted");
		}
		catch (Exception e) {
			String errorMessage = e.getMessage();
			log.error(errorMessage, e);
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}

	@PostMapping(value = "/")
	public ResponseEntity<?> create(@RequestBody Entity entity) {
		try {
			log.debug("create entity " + entity);
			Entity e = service.create(entity);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Location", getLocation() + e.getId());
			return ResponseEntity.ok().headers(responseHeaders).body(e);
		}
		catch (Exception e) {
			String errorMessage = e.getMessage();
			log.error(errorMessage, e);
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}

	protected abstract String getLocation();
}

