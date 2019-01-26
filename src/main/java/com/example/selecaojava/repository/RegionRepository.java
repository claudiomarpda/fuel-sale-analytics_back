package com.example.selecaojava.repository;

import com.example.selecaojava.model.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<Region, Integer> {

    Optional<Region> findByCode(String code);

}
