package com.example.selecaojava.repository;

import com.example.selecaojava.model.County;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountyRepository extends PagingAndSortingRepository<County, Long> {
}
