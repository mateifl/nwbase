package ro.zizicu.nwbase.service;

import ro.zizicu.nwbase.entity.IdentityOwner;

import java.io.Serializable;
import java.util.List;

public interface DTOService<DTO extends IdentityOwner<ID>,
                            ID extends Serializable>
                                {
    DTO load(ID id);
    List<DTO> loadAll();
    void delete(ID id);
}
