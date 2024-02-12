package ro.zizicu.nwbase.service.converter;

import ro.zizicu.nwbase.entity.IdentityOwner;

import java.io.Serializable;

public interface DtoConverter <Entity extends IdentityOwner<ID>,
                               DTO extends IdentityOwner<ID>,
                               ID extends Serializable> {

    DTO convertToDTO(Entity e);
    Entity convertToEntity(DTO d);

}
