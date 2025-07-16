package com.manisha.IMSystem.services.impl;

import com.manisha.IMSystem.dto.Response;
import com.manisha.IMSystem.dto.TransactionDTO;
import com.manisha.IMSystem.dto.TransactionRequest;
import com.manisha.IMSystem.enums.TransactionStatus;
import com.manisha.IMSystem.enums.TransactionType;
import com.manisha.IMSystem.exceptions.NameValueRequiredException;
import com.manisha.IMSystem.exceptions.NotFoundException;
import com.manisha.IMSystem.models.Product;
import com.manisha.IMSystem.models.Supplier;
import com.manisha.IMSystem.models.Transaction;
import com.manisha.IMSystem.models.User;
import com.manisha.IMSystem.repositories.ProductRepository;
import com.manisha.IMSystem.repositories.SupplierRepository;
import com.manisha.IMSystem.repositories.TransactionRepository;
import com.manisha.IMSystem.services.TransactionService;
import com.manisha.IMSystem.services.UserService;
import com.manisha.IMSystem.specification.TransactionFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private  final SupplierRepository supplierRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Response purchase(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();

        if(supplierId ==null) throw new NameValueRequiredException("Supplier id is required");

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new NotFoundException("Product Not Found"));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(()-> new NotFoundException("Supplier Not Found"));

        User user = userService.getCurrentLoggedInUser();

        //update and save stock quantity
        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);

        //create a transaction
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.PURCHASE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .supplier(supplier)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .description(transactionRequest.getDescription())
                .note(transactionRequest.getNote())
                .build();

        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Purchase made successfully")
                .build();
    }

    @Override
    public Response sell(TransactionRequest transactionRequest) {

        Long productId = transactionRequest.getProductId();
        Integer quantity = transactionRequest.getQuantity();

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new NotFoundException("Product Not Found"));

        User user = userService.getCurrentLoggedInUser();

        //update and save stock quantity
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        //create a transaction
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.SALE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .description(transactionRequest.getDescription())
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .note(transactionRequest.getNote())
                .build();

        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Product sale male successfully")
                .build();


    }

    @Override
    public Response returnToSupplier(TransactionRequest transactionRequest) {

        Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();

        if(supplierId ==null) throw new NameValueRequiredException("Supplier id is required");

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new NotFoundException("Product Not Found"));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(()-> new NotFoundException("Supplier Not Found"));

        User user = userService.getCurrentLoggedInUser();

        //update and save stock quantity
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        //create a transaction
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.RETURN_TO_SUPPLIER)
                .status(TransactionStatus.PROCESSING)
                .product(product)
                .user(user)
                .totalProducts(quantity)
                .totalPrice(BigDecimal.ZERO)
                .description(transactionRequest.getDescription())
                .note(transactionRequest.getNote())
                .build();

        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Products return is in progress")
                .build();



    }

    @Override
    public Response getAllTransactions(int page, int size, String filter) {

        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"id"));
        Specification<Transaction> spec = TransactionFilter.byFilter(filter);
        Page<Transaction> transactionPage = transactionRepository.findAll(spec, pageable);
        List<TransactionDTO> transactionDTOS = modelMapper.map(transactionPage
                .getContent(),new TypeToken<List<TransactionDTO>>(){}.getType());

        transactionDTOS.forEach(transactionDTO -> {
            transactionDTO.setUser(null);
            transactionDTO.setProduct(null);
            transactionDTO.setSupplier(null);
        });

        return Response.builder()
                .status(200)
                .message("Success")
                .transactions(transactionDTOS)
                .totalElements(transactionPage.getTotalElements())
                .totalPages(transactionPage.getTotalPages())
                .build();
    }

    @Override
    public Response getTransactionById(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Transaction Not Found"));

        TransactionDTO transactionDTO = modelMapper.map(transaction,TransactionDTO.class);
        transactionDTO.setUser(null);
        return Response.builder()
                .status(200)
                .message("Success")
                .transaction(transactionDTO)
                .build();

    }

    @Override
    public Response getTransactionsByMonthAndYear(int month, int year) {

        List<Transaction> transactions = transactionRepository.findAll(TransactionFilter.byMonthAndYear(month,year));

        List<TransactionDTO>transactionDTOS = modelMapper.map(transactions, new TypeToken<List<TransactionDTO>>(){}
                .getType());

        transactionDTOS.forEach(transactionDTO -> {
            transactionDTO.setUser(null);
            transactionDTO.setProduct(null);
            transactionDTO.setSupplier(null);
        });

        return Response.builder()
                .status(200)
                .message("Success")
                .transactions(transactionDTOS)
                .build();

    }

    @Override
    public Response updateTransactionStatus(Long TransactionId, TransactionStatus status) {

        Transaction existingtransaction = transactionRepository.findById(TransactionId)
                .orElseThrow(()-> new NotFoundException("Transaction Not Found"));

        existingtransaction.setStatus(status);
        existingtransaction.setUpdatedAt(LocalDateTime.now());

        transactionRepository.save(existingtransaction);

        return Response.builder()
                .status(200)
                .message("Transaction status updated successfully")
                .build();

    }
}
