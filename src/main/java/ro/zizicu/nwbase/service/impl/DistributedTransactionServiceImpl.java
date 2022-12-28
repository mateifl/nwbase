package ro.zizicu.nwbase.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.zizicu.nwbase.rest.TransactionCoordinatorRestClient;
import ro.zizicu.nwbase.service.DistributedTransactionService;
import ro.zizicu.nwbase.transaction.DistributedTransactionStatus;
import ro.zizicu.nwbase.transaction.support.DefaultTransactionStepExecutor;
import ro.zizicu.nwbase.transaction.support.TransactionStep;

@RequiredArgsConstructor
@Slf4j
public class DistributedTransactionServiceImpl implements DistributedTransactionService {

	private final EntityManagerFactory entityManagerFactory;
    private final TransactionCoordinatorRestClient restClient;

	
	@Override
	public void executeTransactionStep(TransactionStep transactionStep, Long transactionId) {
		log.debug("Execute transaction step");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		DefaultTransactionStepExecutor transactionStepExecutor = new DefaultTransactionStepExecutor(entityManager, restClient);
		transactionStepExecutor.executeOnDatabase(transactionStep, transactionId);

		new Thread( new Runnable() {
			@Override
			public void run() {
				DistributedTransactionStatus status = transactionStepExecutor.checkTransactionStatus(transactionId);
				if(status == DistributedTransactionStatus.READY_TO_COMMIT)
					transactionStepExecutor.commit(transactionId);
				else
					transactionStepExecutor.rollback(transactionId);
				
			}
		}).start();
		
	}

}


