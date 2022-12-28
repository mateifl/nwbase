package ro.zizicu.nwbase.transaction.support;

import javax.persistence.EntityManager;

public interface TransactionStep {
	void setEntityManager(EntityManager entityManager);
    void execute();
    Boolean lastStep();
    String getServiceName();
    void setServiceName(String serviceName);
}

