package com.mz.fuel_sale_analytics_back.util;

import com.mz.fuel_sale_analytics_back.model.Collect;
import com.mz.fuel_sale_analytics_back.repository.BannerRepository;
import com.mz.fuel_sale_analytics_back.repository.CountyRepository;
import com.mz.fuel_sale_analytics_back.repository.ProductRepository;
import com.mz.fuel_sale_analytics_back.repository.RegionRepository;
import com.mz.fuel_sale_analytics_back.util.file.CollectsReaderUtil;
import com.mz.fuel_sale_analytics_back.util.file.ReadFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionsReaderUtilTest {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private CountyRepository countyRepository;

    private List<Collect> collects;

    @Before
    public void setUp() throws IOException {
        final String fileAsString = ReadFile.readFileAsString("src/test/resources/coletas.csv");
        collects = new CollectsReaderUtil(regionRepository, productRepository, bannerRepository, countyRepository).readCsv(fileAsString);
    }

    @Test
    public void readFileReturnsWrongNumberOfElements() {
        assertNotEquals(100, collects.size());
    }

    @Test
    public void readFileReturnsRightNumberOfElements() {
        assertEquals(1000, collects.size());
    }

}
