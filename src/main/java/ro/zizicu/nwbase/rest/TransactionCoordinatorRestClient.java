package ro.zizicu.nwbase.rest;

import ro.zizicu.nwbase.transaction.DistributedTransactionStatus;
import ro.zizicu.nwbase.transaction.TransactionStatusMessage;

public interface TransactionCoordinatorRestClient {
    void sendTransactionStepStatus(Boolean lastStep, Boolean successful, Long transactionId, DistributedTransactionStatus status);

    TransactionStatusMessage getDistributedTransactionStatus(Long transactionId);
}
