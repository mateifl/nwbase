package ro.zizicu.nwbase.transaction;

public enum TransactionStatus {

    UNCOMMITED("UNCOMMITED"),
    READY_TO_COMMIT("READY_TO_COMMIT"),
    COMMITED("COMMITED"),
    ROLLEDBACK("ROLLEDBACK");

    private String status;

    private TransactionStatus(String code) {
        this.status = code;
    }

    public String getStatus() {
        return status;
    }

}
