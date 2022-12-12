package ro.zizicu.nwbase.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.zizicu.nwbase.rest.TransactionCoordinatorRestClient;
import ro.zizicu.nwbase.transaction.TransactionStatus;

@Slf4j
@RequiredArgsConstructor
public class DefaultTransactionService {

	private final TransactionCoordinatorRestClient restClient;
	
	private TransactionStatus checkTransactionStatus(Long transactionId) {
		int counter = 0;
		try {
			while (true) {
				Thread.sleep(10);
				if(counter % 100 == 0)
					log.debug("checking transaction status {}", transactionId);
				TransactionStatus transactionStatus = restClient.getDistributedTransactionStatus(transactionId).getStatus();
				
				if(transactionStatus == TransactionStatus.READY_TO_COMMIT || transactionStatus == TransactionStatus.ROLLEDBACK) {
					return transactionStatus;
				}
				counter += 1;
				if (counter == 1000)
				{
					log.debug("end polling, transaction rollback");
					return TransactionStatus.ROLLEDBACK;
				}
			}

		}
		catch(InterruptedException e) {
			log.error(e.getMessage());
			return TransactionStatus.ROLLEDBACK;
		}
		
	}
	
}
