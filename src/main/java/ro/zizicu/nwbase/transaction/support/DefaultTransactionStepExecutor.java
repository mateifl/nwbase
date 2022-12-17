package ro.zizicu.nwbase.transaction.support;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.zizicu.nwbase.rest.TransactionCoordinatorRestClient;
import ro.zizicu.nwbase.transaction.DistributedTransactionStatus;

@RequiredArgsConstructor
@Data
@Slf4j
public class DefaultTransactionStepExecutor  {

    private final PlatformTransactionManager transactionManager;
    private final TransactionCoordinatorRestClient restClient;
    private TransactionStatus transactionStatus;

    public void executeOnDatabase(TransactionStep transactionStep, Long transactionId) {
        log.debug("executing transaction {}", transactionId);
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setTimeout(3);
        transactionStatus = getTransactionManager().getTransaction(definition);
        transactionStep.execute();
        restClient.sendTransactionStepStatus(transactionStep.lastStep(), Boolean.TRUE,  transactionId, DistributedTransactionStatus.READY_TO_COMMIT);
        log.debug("transaction step {} executed", transactionId);
    }

    public void commit(Long transactionId) {
        log.debug("committing transaction {}", transactionId);
        transactionManager.commit(transactionStatus);
        log.debug("transaction {} committed", transactionId);
    }

    public void rollback(Long transactionId) {
        log.debug("rollback transaction {}", transactionId);
        transactionManager.rollback(transactionStatus);
        log.debug("transaction {} rolled back", transactionId);
    }

	public DistributedTransactionStatus checkTransactionStatus(Long transactionId) {
		int counter = 0;
		try {
			while (true) {
				Thread.sleep(10);
				if(counter % 100 == 0)
					log.debug("checking transaction status {}", transactionId);
				DistributedTransactionStatus transactionStatus = restClient.getDistributedTransactionStatus(transactionId).getStatus();
				
				if(transactionStatus == DistributedTransactionStatus.READY_TO_COMMIT || transactionStatus == DistributedTransactionStatus.ROLLEDBACK) {
					return transactionStatus;
				}
				counter += 1;
				if (counter == 1000)
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
