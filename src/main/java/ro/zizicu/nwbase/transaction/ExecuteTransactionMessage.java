package ro.zizicu.nwbase.transaction;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload for REST POST that will commit/rollback a transaction.
 * transactionResult = "COMMIT" or "ROLLBACK"
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteTransactionMessage {
    private String microservice;
    private Long transactionId;
    private String transactionResult;
}
