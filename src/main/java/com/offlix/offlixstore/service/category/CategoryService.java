package com.offlix.offlixstore.service.category;

import com.offlix.offlixstore.constant.AppConstant;
import com.offlix.offlixstore.execption.AlreadyExistException;
import com.offlix.offlixstore.execption.CategoryNotFoundException;
import com.offlix.offlixstore.model.Category;
import com.offlix.offlixstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException(AppConstant.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(cat -> !categoryRepository.existsByName(cat.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()-> new AlreadyExistException(AppConstant.CATEGORY_ALREADY_EXIST));

    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        return Optional.ofNullable(getCategoryById(categoryId))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                }).orElseThrow(()-> new CategoryNotFoundException(AppConstant.CATEGORY_NOT_FOUND));
    }

    @Override
    public void deleteCategoryById(Long id) {
         categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,()->{
             throw new CategoryNotFoundException(AppConstant.CATEGORY_NOT_FOUND);
        });

    }
}
