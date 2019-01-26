package com.example.selecaojava.repository;

import com.example.selecaojava.model.Collect;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectRepository extends PagingAndSortingRepository<Collect, Long> {
}
