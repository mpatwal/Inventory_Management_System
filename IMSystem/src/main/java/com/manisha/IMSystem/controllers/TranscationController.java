package com.manisha.IMSystem.controllers;

import com.manisha.IMSystem.dto.CategoryDTO;
import com.manisha.IMSystem.dto.Response;
import com.manisha.IMSystem.dto.TransactionRequest;
import com.manisha.IMSystem.enums.TransactionStatus;
import com.manisha.IMSystem.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TranscationController {

    private final TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<Response> purchaseInventory(@RequestBody @Valid TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.purchase(transactionRequest));
    }

    @PostMapping("/sell")
    public ResponseEntity<Response> sell(@RequestBody @Valid TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.sell(transactionRequest));
    }

    @PostMapping("/return")
    public ResponseEntity<Response> returnToSupplier(@RequestBody @Valid TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.returnToSupplier(transactionRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<Response>getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(required = false) String filter)
    {
        System.out.println("search :" +filter);
        return ResponseEntity.ok(transactionService.getAllTransactions(page,size,filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response>getTransactionById(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/by_month_and_year")
    public ResponseEntity<Response>getTransactionsByMonthAndYear(@RequestParam int month,@RequestParam int year){
        return ResponseEntity.ok(transactionService.getTransactionsByMonthAndYear(month,year));
    }

    @PutMapping("/update/{transactionId}")
    public ResponseEntity<Response>updateTransactionStatus(
            @PathVariable Long transactionId,
            @RequestBody TransactionStatus status){

        System.out.println("I hit update transaction");
        return ResponseEntity.ok(transactionService.updateTransactionStatus(transactionId,status));
    }

}
