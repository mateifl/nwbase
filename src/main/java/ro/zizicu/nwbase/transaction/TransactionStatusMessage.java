package ro.zizicu.nwbase.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatusMessage {

    private Long transactionId;
    private TransactionStatus status;


}
