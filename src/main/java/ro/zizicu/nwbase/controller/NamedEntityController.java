package ro.zizicu.nwbase.controller;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ro.zizicu.nwbase.entity.NamedIdentityOwner;
import ro.zizicu.nwbase.service.NamedService;

public class NamedEntityController<Entity extends NamedIdentityOwner<ID>, 
									ID extends Serializable> 
			  extends BasicOperationsController<Entity, ID> {

	private static Logger logger = LoggerFactory.getLogger(NamedEntityController.class);
	
	@Autowired
	private NamedService<Entity, ID> namedService;
	
	@GetMapping(value = "name/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name) {
		logger.debug("find: " + name);
		try {
			return ResponseEntity.ok(namedService.loadByName(name));
		}
		catch (Exception e) {
			String errorMessage = e.getMessage();
			logger.error(errorMessage, e);
			return ResponseEntity.badRequest().body(errorMessage);
		}
		
	}
}
