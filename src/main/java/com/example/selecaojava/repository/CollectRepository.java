package com.example.selecaojava.repository;

import com.example.selecaojava.model.Collect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectRepository extends PagingAndSortingRepository<Collect, Long> {

    @Query("SELECT AVG(col.salePrice) FROM Collect col WHERE col.county.name = ?1")
    Double getAvgSalePriceByCountyName(@Param("name") String name);

    Page<Collect> findAllByBanner_Name(String name, Pageable pageable);

    @Query(value = "SELECT * FROM collect c WHERE c.date >= ?1 AND c.date <= ?1", nativeQuery = true)
    Page<Collect> findAllByDate(@Param("date") String date, Pageable pageable);

}
