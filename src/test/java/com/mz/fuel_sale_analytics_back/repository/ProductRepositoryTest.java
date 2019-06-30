package com.mz.fuel_sale_analytics_back.repository;

import com.mz.fuel_sale_analytics_back.util.file.FileRecords;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void numberOfCountiesMatches() {
        assertEquals(FileRecords.PRODUCTS, productRepository.findAll().spliterator().getExactSizeIfKnown());
    }

}