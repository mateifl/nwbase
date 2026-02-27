package ro.zizicu.nwbase.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStockRequest {
    @NotNull
    private Integer id;
    @NotNull
    private Integer unitsOnOrder;
}
