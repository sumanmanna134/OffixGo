package com.offlix.offlixstore.service.product;

import com.offlix.offlixstore.constant.AppConstant;
import com.offlix.offlixstore.execption.ProductNotFoundException;
import com.offlix.offlixstore.model.Category;
import com.offlix.offlixstore.model.Product;
import com.offlix.offlixstore.repository.CreateCategoryRepository;
import com.offlix.offlixstore.repository.ProductRepository;
import com.offlix.offlixstore.request.AddProductRequest;
import com.offlix.offlixstore.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CreateCategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest productRequest) {
        Category category = Optional.ofNullable(categoryRepository.findByName(productRequest.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory  = new Category(productRequest.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

        productRequest.setCategory(category);
        return productRepository.save(createProduct(productRequest, category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(AppConstant.PRODUCT_NOT_FOUND));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, ()->
        {
            throw new ProductNotFoundException(AppConstant.PRODUCT_NOT_FOUND);
        });

    }

    @Override
    public Product updateProduct(UpdateProductRequest request, long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException(AppConstant.PRODUCT_NOT_FOUND));


    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request){

               existingProduct.setName(request.getName());
               existingProduct.setBrand(request.getBrand());
               existingProduct.setDescription(request.getDescription());
               existingProduct.setPrice(request.getPrice());
               existingProduct.setInventory(request.getInventory());

               Category category = categoryRepository.findByName(request.getCategory().getName());
               existingProduct.setCategory(category);
               return existingProduct;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
