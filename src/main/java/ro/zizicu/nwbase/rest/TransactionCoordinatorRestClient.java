package ro.zizicu.nwbase.rest;

import ro.zizicu.nwbase.transaction.TransactionStatus;
import ro.zizicu.nwbase.transaction.TransactionStatusMessage;

public interface TransactionCoordinatorRestClient {
    void sendTransactionStepStatus(Long transactionId, TransactionStatus status);

    TransactionStatusMessage getDistributedTransactionStatus(Long transactionId);
}
