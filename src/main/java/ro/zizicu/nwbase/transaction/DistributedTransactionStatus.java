package ro.zizicu.nwbase.transaction;

public enum DistributedTransactionStatus {

    UNCOMMITED("UNCOMMITED"),
    READY_TO_COMMIT("READY_TO_COMMIT"),
    COMMITED("COMMITED"),
    ROLLEDBACK("ROLLEDBACK");

    private String status;

    private DistributedTransactionStatus(String code) {
        this.status = code;
    }

    public String getStatus() {
        return status;
    }

}
