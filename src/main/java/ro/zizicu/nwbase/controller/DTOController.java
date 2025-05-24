package ro.zizicu.nwbase.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.exceptions.EntityNotFoundException;
import ro.zizicu.nwbase.service.DTOService;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public abstract class DTOController<DTO extends IdentityOwner<ID>, ID extends Serializable> {

    private final DTOService<DTO, ID> dtoService;

    @GetMapping(value = "/")
    public ResponseEntity<?> loadAll()
    {
        log.debug("load all entities");
        Iterable<DTO> entities = dtoService.loadAll();
        return ResponseEntity.ok(entities);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> load(@PathVariable ID id) {
        try {
            log.debug("load entity {}", id);
            return ResponseEntity.ok(dtoService.load(id));
        }
        catch(EntityNotFoundException e) {
            String errorMessage = "entity not found, id:" + id;
            log.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }


}
