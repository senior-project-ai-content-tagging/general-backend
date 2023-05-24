package me.ponlawat.domain.category;

import me.ponlawat.domain.category.dto.CategoryEditRequest;
import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.dto.UserEditRequest;

import java.util.List;

public interface CategoryService {
    List<Category> listCategory();
    Category getCategory(long id);
    void removeCategory(long id);
    Category updateCategory(long id, CategoryEditRequest categoryEditRequest);
}
