package ro.zizicu.nwbase.transaction.support;

import javax.persistence.EntityManager;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.zizicu.nwbase.rest.TransactionCoordinatorRestClient;
import ro.zizicu.nwbase.transaction.DistributedTransactionStatus;

@RequiredArgsConstructor
@Data
@Slf4j
public class DefaultTransactionStepExecutor  {

	private final EntityManager entityManager;
    private final TransactionCoordinatorRestClient restClient;

    public void executeOnDatabase(TransactionStep transactionStep, Long transactionId) {
    	entityManager.getTransaction().begin();
        log.debug("executing transaction {} service {}", transactionId, transactionStep.getServiceName());
        transactionStep.setEntityManager(entityManager);
        transactionStep.execute();
        restClient.sendTransactionStepStatus(transactionStep.lastStep(), Boolean.TRUE,  transactionId, DistributedTransactionStatus.READY_TO_COMMIT, transactionStep.getServiceName());
        log.debug("transaction step {} executed", transactionId);
    }

    public void commit(Long transactionId) {
        log.debug("committing transaction {}", transactionId);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        log.debug("transaction {} committed", transactionId);
    }

    public void rollback(Long transactionId) {
        log.debug("rollback transaction {}", transactionId);
        entityManager.getTransaction().rollback();
        entityManager.close();
        log.debug("transaction {} rolled back", transactionId);
    }

	public DistributedTransactionStatus checkTransactionStatus(Long transactionId) {
		int counter = 0;
		try {
			while (true) {
				Thread.sleep(10);
				if(counter % 25 == 0)
					log.debug("checking transaction status {}", transactionId);
				DistributedTransactionStatus transactionStatus = restClient.getDistributedTransactionStatus(transactionId).getStatus();
				
				if(transactionStatus == DistributedTransactionStatus.READY_TO_COMMIT || transactionStatus == DistributedTransactionStatus.ROLLEDBACK) {
					return transactionStatus;
				}
				counter += 1;
				if (counter == 100)
				{
					log.debug("end polling, transaction rollback");
					return DistributedTransactionStatus.ROLLEDBACK;
				}
			}

		}
		catch(InterruptedException e) {
			log.error(e.getMessage());
			return DistributedTransactionStatus.ROLLEDBACK;
		}
		
	}
}
