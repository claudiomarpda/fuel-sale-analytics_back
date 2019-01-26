package com.example.selecaojava.repository;

import com.example.selecaojava.model.Collect;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectRepository extends PagingAndSortingRepository<Collect, Long> {

    @Query("SELECT AVG(col.salePrice) from Collect col WHERE col.county.name = ?1")
    Double getAvgSalePriceByCountyName(@Param("name") String name);

}
