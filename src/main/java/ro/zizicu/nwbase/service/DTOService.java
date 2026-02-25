package ro.zizicu.nwbase.service;

import ro.zizicu.nwbase.entity.IdentityOwner;

import java.io.Serializable;
import java.util.List;

// TODO what is the role of this class

public interface DTOService<DTO extends IdentityOwner<ID>,
                            ID extends Serializable>
                                {
    DTO load(ID id);
    List<DTO> loadAll();
    void delete(ID id);
}
