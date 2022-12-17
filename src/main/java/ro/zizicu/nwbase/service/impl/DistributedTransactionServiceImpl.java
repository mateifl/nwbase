package ro.zizicu.nwbase.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.zizicu.nwbase.service.DistributedTransactionService;
import ro.zizicu.nwbase.transaction.DistributedTransactionStatus;
import ro.zizicu.nwbase.transaction.support.DefaultTransactionStepExecutor;
import ro.zizicu.nwbase.transaction.support.TransactionStep;

@RequiredArgsConstructor
@Slf4j
public class DistributedTransactionServiceImpl implements DistributedTransactionService {

	/** This is a state full class, therefore it cannot be a singleton. */
	private final DefaultTransactionStepExecutor transactionStepExecutor;
	
	@Override
	public void executeTransactionStep(TransactionStep transactionStep, Long transactionId) {
		log.debug("Execute transaction step");	
		transactionStepExecutor.executeOnDatabase(transactionStep, transactionId);
		DistributedTransactionStatus status = transactionStepExecutor.checkTransactionStatus(transactionId);
		if(status == DistributedTransactionStatus.READY_TO_COMMIT)
			transactionStepExecutor.commit(transactionId);
		else
			transactionStepExecutor.rollback(transactionId);
	}

}
