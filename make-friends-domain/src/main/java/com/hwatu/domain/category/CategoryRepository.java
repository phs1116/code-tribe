package com.hwatu.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by hwatu on 2017. 3. 6..
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, QueryDslPredicateExecutor<Category> {
	Category findByCategoryId(Long categoryId);
}
