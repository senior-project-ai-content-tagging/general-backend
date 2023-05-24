package me.ponlawat.domain.category.internal;

import io.quarkus.panache.common.Sort;
import me.ponlawat.domain.category.Category;
import me.ponlawat.domain.category.CategoryRepository;
import me.ponlawat.domain.category.CategoryService;
import me.ponlawat.domain.category.dto.CategoryEditRequest;
import me.ponlawat.domain.category.exception.CategoryNotFoundException;
import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.dto.UserEditRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoryServiceImpl implements CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategory() {
        return categoryRepository.listAll(Sort.by("id"));
    }

    @Override
    public Category getCategory(long id) {
        Optional<Category> optionalCategory = categoryRepository.findByIdOptional(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(id);
        }

        return optionalCategory.get();
    }

    @Override
    @Transactional
    public void removeCategory(long id) {
        Optional<Category> optionalCategory = categoryRepository.findByIdOptional(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(id);
        }

        categoryRepository.delete(optionalCategory.get());
    }

    @Override
    @Transactional
    public Category updateCategory(long id, CategoryEditRequest categoryEditRequest) {
        Optional<Category> optionalCategory = categoryRepository.findByIdOptional(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(id);
        }

        Category category = optionalCategory.get();
        category.setName(categoryEditRequest.getName());
        category.setStatus(categoryEditRequest.getStatus());

        categoryRepository.persist(category);

        return category;
    }
}
