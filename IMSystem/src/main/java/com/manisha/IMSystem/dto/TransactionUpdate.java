package com.manisha.IMSystem.dto;
import com.manisha.IMSystem.enums.TransactionStatus;
public class TransactionUpdate {

    private TransactionStatus status;

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
