package com.manisha.IMSystem.services.impl;

import com.manisha.IMSystem.dto.ProductDTO;
import com.manisha.IMSystem.dto.Response;
import com.manisha.IMSystem.exceptions.NotFoundException;
import com.manisha.IMSystem.models.Category;
import com.manisha.IMSystem.models.Product;
import com.manisha.IMSystem.repositories.CategoryRepository;
import com.manisha.IMSystem.repositories.ProductRepository;
import com.manisha.IMSystem.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    private static  final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/product-images/";

    @Override
    public Response saveProduct(ProductDTO productDTO, MultipartFile imageFile) {

        Category category =categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()-> new NotFoundException("Category Not Found"));

        //map oyr dto to product entity

        Product productToSave = Product.builder()

                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .stockQuantity(productDTO.getStockQuantity())
                .description(productDTO.getDescription())
                .category(category)
                .build();
        if(imageFile !=null && !imageFile.isEmpty()){
            log.info("Image file exists");
            String imagePath = saveImage(imageFile);
            productToSave.setImageUrl(imagePath);
        }

        //save the product entity
        productRepository.save(productToSave);

        return Response.builder()
                .status(200)
                .message("Product successfully saved")
                .build();

    }

    @Override
    public Response updateProduct(ProductDTO productDTO, MultipartFile imageFile) {

        //to check if product exists
        Product existingProduct = productRepository.findById(productDTO.getProductId())
                .orElseThrow(()->new NotFoundException("Product Not Found"));

        //check if image is associated with the product to update and upload
        if(imageFile != null && !imageFile.isEmpty()){
            String imagePath = saveImage(imageFile);
            existingProduct.setImageUrl(imagePath);
        }

        //check if category is to be changed for the products
        if(productDTO.getCategoryId() != null && productDTO.getCategoryId() > 0){
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(()-> new NotFoundException("Category Not found"));
            existingProduct.setCategory(category);
        }
        //check if product fields is to be changed and update
        if (productDTO.getName() != null && !productDTO.getName().isBlank()){
            existingProduct.setName(productDTO.getName());
        }

        if (productDTO.getSku() != null && !productDTO.getSku().isBlank()){
            existingProduct.setSku(productDTO.getSku());
        }

        if (productDTO.getDescription() != null && !productDTO.getDescription().isBlank()){
            existingProduct.setDescription(productDTO.getDescription());
        }

        if (productDTO.getPrice() != null && productDTO.getPrice().compareTo(BigDecimal.ZERO)>=0){
            existingProduct.setPrice(productDTO.getPrice());
        }

        if (productDTO.getStockQuantity() != null && productDTO.getStockQuantity()>=0){
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
        }

        //update the product
        productRepository.save(existingProduct);

        //build our response
        return Response.builder()
                .status(200)
                .message("Product successfully updated")
                .build();

    }

    @Override
    public Response getAllProducts() {

        List<Product> productList=productRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));

        List<ProductDTO> productDTOList = modelMapper.map(productList,new TypeToken<List<ProductDTO>>()
        {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .products(productDTOList)
                .build();

    }

    @Override
    public Response getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Product Not Found"));

        return Response.builder()
                .status(200)
                .message("success")
                .product(modelMapper.map(product,ProductDTO.class))
                .build();
    }

    @Override
    public Response deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Product Not Found"));

        productRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Product successfully deleted")
                .build();
    }

    @Override
    public Response searchProduct(String input) {

        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(input,input);

        if(products.isEmpty()){
            throw  new NotFoundException("Product not found");
        }

        List<ProductDTO> productDTOList = modelMapper.map(products,new TypeToken<List<ProductDTO>>()
        {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .products(productDTOList)
                .build();


    }


    private String saveImage(MultipartFile imageFile){

        //validate image and check if it is greater than 1GIG
        if(!imageFile.getContentType().startsWith("image/")|| imageFile.getSize()> 1024 *1024*1024){
            throw new IllegalArgumentException("Only files under 1GIG are allowed");
        }

        //create the directory if it doesn't exist

        File directory = new File(IMAGE_DIRECTORY);
        if(!directory.exists()){
            directory.mkdir();
            log.info("Directory was created");
        }
        //generate unique file name for the image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        //get the absolute path of the image
        String imagePath = IMAGE_DIRECTORY + uniqueFileName;

        try{
            File destinationFile = new File(imagePath);
            imageFile.transferTo(destinationFile); //here we are transferring the file to this folder
        }catch (Exception e){
            throw  new IllegalArgumentException("Error saving Image:" + e.getMessage());
        }
        return imagePath;
    }
}
