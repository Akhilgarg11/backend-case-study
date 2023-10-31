package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.ProductDetails;

public interface ProductRepository extends JpaRepository<ProductDetails, Integer> {

	@Query("SELECT p FROM ProductDetails p WHERE p.category = :category")
	List<ProductDetails> getProductsByCategory(@Param("category") String category);

	@Query("SELECT p FROM ProductDetails p WHERE lower(p.category) LIKE lower(concat('%', :str, '%')) "
			+ "OR lower(p.brand) LIKE lower(concat('%', :str, '%'))"
			+ "OR lower(p.price) LIKE lower(concat('%', :str, '%'))"
			+ "OR lower(p.details) LIKE lower(concat('%', :str, '%'))"
			+ "OR lower(p.name) LIKE lower(concat('%', :str, '%'))"
			)
	List<ProductDetails> getProductsByString(@Param("str") String str);
	
	@Query("SELECT p FROM ProductDetails p WHERE p.category IN :category AND p.brand IN :brand AND CAST(p.price as INTEGER) BETWEEN :min AND :max")
	List<ProductDetails> getFilteredProducts(@Param("min") int minPrice, @Param("max") int maxPrice, 
	        @Param("brand") List<String> brand, @Param("category") List<String> category);
	
	
//	@Query("SELECT MAX(p.price) FROM ProductDetails p")
//	int findMax();
	
	@Query("SELECT DISTINCT p.brand FROM ProductDetails p ")
	List<String> getAllBrands();
	
	@Query("SELECT DISTINCT p.category FROM ProductDetails p ")
	List<String> getAllCategory();

}