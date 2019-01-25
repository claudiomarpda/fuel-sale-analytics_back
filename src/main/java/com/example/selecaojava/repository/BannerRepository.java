package com.example.selecaojava.repository;

import com.example.selecaojava.model.Banner;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends PagingAndSortingRepository<Banner, Integer> {
}
