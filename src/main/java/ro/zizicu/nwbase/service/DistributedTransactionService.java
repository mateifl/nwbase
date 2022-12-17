package ro.zizicu.nwbase.service;

import ro.zizicu.nwbase.transaction.support.TransactionStep;

public interface DistributedTransactionService {
	
	void executeTransactionStep(TransactionStep transactionStep, Long transactionId);
	
}
