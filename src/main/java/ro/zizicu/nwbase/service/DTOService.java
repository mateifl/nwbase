package ro.zizicu.nwbase.service;

import org.springframework.data.repository.CrudRepository;
import ro.zizicu.nwbase.entity.IdentityOwner;
import ro.zizicu.nwbase.service.converter.DtoConverter;

import java.io.Serializable;
import java.util.List;

public interface DTOService<DTO extends IdentityOwner<ID>,
                            ID extends Serializable>
                                {
    DTO load(ID id);
    List<DTO> loadAll();
    void delete(ID id);
}
