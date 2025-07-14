package com.manisha.IMSystem.repositories;

import com.manisha.IMSystem.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category,Long> {

}
