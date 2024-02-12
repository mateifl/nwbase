package ro.zizicu.nwbase.service;

import ro.zizicu.nwbase.entity.IdentityOwner;

public interface DtoEntityConverter<DTO extends IdentityOwner<ID>, Entity extends IdentityOwner<ID>, ID > {

    DTO toDto(Entity e);

    Entity toEntity(DTO e);
}
