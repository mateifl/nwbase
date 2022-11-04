package ro.zizicu.nwbase.transaction;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMessage {
    private String serviceName;
    private Long transactionId;
    private Boolean isSuccessful;
    private Boolean isLastStep;
}
