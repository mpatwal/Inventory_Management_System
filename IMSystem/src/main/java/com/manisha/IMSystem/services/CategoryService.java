package com.manisha.IMSystem.services;

import com.manisha.IMSystem.dto.CategoryDTO;
import com.manisha.IMSystem.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDTO categoryDTO);
    Response getAllCategories();
    Response getCategoryById(Long id);
    Response updateCategory(Long id,CategoryDTO categoryDTO);
    Response deleteCategory(Long id);
}
