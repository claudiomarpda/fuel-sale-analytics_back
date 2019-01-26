package com.example.selecaojava.util;

import com.example.selecaojava.model.Collect;
import com.example.selecaojava.repository.*;
import com.example.selecaojava.util.file.CollectsReaderUtil;
import com.example.selecaojava.util.file.ReadFile;
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
