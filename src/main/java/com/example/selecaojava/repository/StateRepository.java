package com.example.selecaojava.repository;

import com.example.selecaojava.model.State;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends PagingAndSortingRepository<State, Long> {

    Optional<State> findByUfCode(int ufCode);

}
