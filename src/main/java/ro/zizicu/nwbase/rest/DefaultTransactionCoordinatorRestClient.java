package ro.zizicu.nwbase.rest;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.zizicu.nwbase.transaction.TransactionMessage;
import ro.zizicu.nwbase.transaction.TransactionStatus;
import ro.zizicu.nwbase.transaction.TransactionStatusMessage;

@Slf4j
@AllArgsConstructor
public class DefaultTransactionCoordinatorRestClient implements TransactionCoordinatorRestClient {

    private final Environment environment;
    private final RestTemplate restTemplate;
    
    @Override
    public void sendTransactionStepStatus(Long transactionId, TransactionStatus status) {
    	
        String transactionCoordinatorUrl = environment.getProperty("transactionCoordinator.url");
        log.debug("send transaction step info to {} for transaction id {}", transactionCoordinatorUrl  , transactionId);
        TransactionMessage transactionMessage = TransactionMessage.builder()
        		.isLastStep(false)
        		.transactionId(transactionId)
        		.isSuccessful(true)
        		.serviceName("product")
        		.build();
        HttpEntity<TransactionMessage> updatedEntity = new HttpEntity<TransactionMessage>(transactionMessage);
        restTemplate.postForObject(transactionCoordinatorUrl + "/",
                updatedEntity,
                TransactionStatusMessage.class);
    }

    @Override
    public TransactionStatusMessage getDistributedTransactionStatus(Long transactionId) {
        String transactionCoordinatorUrl = environment.getProperty("transactionCoordinator.url");
        TransactionStatusMessage message = restTemplate.getForObject( transactionCoordinatorUrl + "/" + transactionId, TransactionStatusMessage.class );
        return message;
    }

}
