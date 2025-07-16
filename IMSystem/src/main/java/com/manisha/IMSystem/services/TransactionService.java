package com.manisha.IMSystem.services;

import com.manisha.IMSystem.dto.Response;
import com.manisha.IMSystem.dto.TransactionRequest;
import com.manisha.IMSystem.enums.TransactionStatus;

public interface TransactionService {
    Response purchase(TransactionRequest transactionRequest);
    Response sell(TransactionRequest transactionRequest);
    Response returnToSupplier(TransactionRequest transactionRequest);
    Response getAllTransactions(int page,int size,String filter);
    Response getTransactionById(Long id);
    Response getTransactionsByMonthAndYear(int month, int year);
    Response updateTransactionStatus(Long TransactionId, TransactionStatus status);
}
