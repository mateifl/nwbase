package ro.zizicu.nwbase.controller;

import java.io.Serializable;
import java.net.URI;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.service.CrudService;


@Slf4j
@Data
@RequiredArgsConstructor
public abstract class CrudOperationsController<E extends IdentityOwner<ID>,
										S extends CrudService<E, ID>,
										ID extends Serializable> {

	private final S service;

	protected S getService() { return service; }

	@GetMapping
	public ResponseEntity<Iterable<E>> loadAll() {
		log.debug("loadAll");
		return ResponseEntity.ok(service.loadAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<E> load(@PathVariable ID id) {
		log.debug("load {}", id);
		return ResponseEntity.ok(service.load(id)); // EntityNotFoundException handled globally
	}

	@PostMapping
	public ResponseEntity<E> create(@RequestBody E entity) {
		log.debug("create {}", entity);
		E created = service.create(entity);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(created.getId()).toUri();
		return ResponseEntity.created(location).body(created);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable ID id) {
		log.debug("delete {}", id);
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}

