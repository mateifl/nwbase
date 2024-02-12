package ro.zizicu.nwbase.service.impl;

import org.springframework.data.repository.CrudRepository;
import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.service.converter.DtoConverter;
import ro.zizicu.nwbase.service.DTOService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Role: handles CRUD operations for entities with a DTO
 * @param <Repository>
 * @param <Converter>
 * @param <Entity>
 * @param <DTO>
 * @param <ID>
 */

public class DefaultLoaderService<Repository extends CrudRepository<Entity, ID>,
                                    Converter extends DtoConverter<Entity, DTO, ID>,
                                    Entity extends IdentityOwner<ID>,
                                    DTO extends IdentityOwner<ID>,
                                    ID extends Serializable>
            implements DTOService<DTO, ID>
{

    private Repository repository;
    private Converter converter;

    @Override
    public DTO load(ID id) {
        Entity e = repository.findById(id).orElseThrow();
        return converter.convertToDTO(e);
    }

    @Override
    public List<DTO> loadAll() {
        List<DTO> l = new ArrayList<>();
        for(Entity e : repository.findAll())
            l.add(converter.convertToDTO(e));
        return l;
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
}
