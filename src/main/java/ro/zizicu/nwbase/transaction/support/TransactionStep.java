package ro.zizicu.nwbase.transaction.support;

public interface TransactionStep {
    void execute();
    Boolean lastStep();
}

