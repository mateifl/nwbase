package ro.zizicu.nwbase.transaction.support;

import javax.persistence.EntityManager;

public abstract class AbstractTransactionStep implements TransactionStep{

    protected EntityManager entityManager;
    protected Boolean lastStep;
    protected String serviceName;
    
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Boolean lastStep() {
        return lastStep;
    }

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

    
}
