package com.example.selecaojava.controller;

import com.example.selecaojava.model.*;
import com.example.selecaojava.repository.*;
import com.example.selecaojava.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CollectControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CountyRepository countyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BannerRepository bannerRepository;

    private Collect collect;

    private Region region;
    private County county;
    private County county2;
    private Product product;
    private Banner banner;

    @Before
    public void setUp() {
        region = regionRepository.findAll().iterator().next();
        List<County> counties = (List<County>) countyRepository.findAll();
        county = counties.get(0);
        county2 = counties.get(1);
        assertNotEquals(county.getName(), county2.getName());
        product = productRepository.findAll().iterator().next();
        banner = bannerRepository.findAll().iterator().next();

        collect = getCollect();
    }

    private Collect getCollect() {
        return new Collect(null, region, county, product, "AUTO POSTO", LocalDate.now(), 3.79, 3.99, "R$ / Litro", banner);
    }

    @Test
    public void importFileSucceeds() throws Exception {
        byte[] data = FileUtil.getFileBytes("src/test/resources/coletas.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "coletas.csv", MediaType.ALL_VALUE, data);

        mvc.perform(multipart("/user/collections/files")
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .with(user("user").roles(RoleName.USER)))
                .andExpect(status().isCreated());
    }

    private void makePost() throws Exception {
        String json = objectMapper.writeValueAsString(collect);
        System.out.println(json);
        mvc.perform(post("/user/collections")
                .with(user("user").roles(RoleName.USER))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void createSucceeds() throws Exception {
        makePost();
        assertTrue(collectRepository.findAll().iterator().hasNext());
    }

    @Test
    public void createTwoSucceeds() throws Exception {
        collectRepository.deleteAll();
        makePost();
        makePost();
        assertEquals(2, collectRepository.findAll().spliterator().getExactSizeIfKnown());
    }

    @Test
    public void findByIdFails() throws Exception {
        makePost();
        mvc.perform(get("/user/collections/{id}", -1)
                .with(user("user").roles(RoleName.USER)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void findByIdSucceeds() throws Exception {
        collect = collectRepository.save(collect);

        mvc.perform(get("/user/collections/" + collect.getId())
                .with(user("user").roles(RoleName.USER)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateSucceeds() throws Exception {
        double price = 1.99;
        collect = collectRepository.save(collect);
        collect.setSalePrice(price);

        String json = objectMapper.writeValueAsString(collect);

        mvc.perform(put("/user/collections/" + collect.getId())
                .with(user("user").roles(RoleName.USER))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salePrice", Matchers.is(price)));

        Collect c = collectRepository.findById(collect.getId()).orElseThrow(RuntimeException::new);
        assertEquals(Double.valueOf(1.99), c.getSalePrice());
    }

    @Test
    public void deleteByIdFails() throws Exception {
        makePost();
        mvc.perform(delete("/user/collections/" + 99)
                .with(user("user").roles(RoleName.USER)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteByIdSucceeds() throws Exception {
        collect = collectRepository.save(collect);

        mvc.perform(delete("/user/collections/" + collect.getId())
                .with(user("user").roles(RoleName.USER)))
                .andExpect(status().isOk());

        assertFalse(collectRepository.findById(collect.getId()).isPresent());
    }


    @Test
    public void go() throws Exception {
        collectRepository.deleteAll();
        assertFalse(collectRepository.findAll().iterator().hasNext());

        String name = county.getName();

        // Same county
        double p1 = 3.9874;
        collect = getCollect();
        collect.setSalePrice(p1);
        collectRepository.save(collect);

        // Same county
        double p2 = 3.79841;
        collect = getCollect();
        collect.setSalePrice(p2);
        collectRepository.save(collect);

        // Different county
        collect = getCollect();
        collect.setSalePrice(9.0);
        collect.setCounty(county2);
        collectRepository.save(collect);

        // 3 entities
        assertEquals(3, collectRepository.findAll().spliterator().getExactSizeIfKnown());

        Double avg = (p1 + p2) / 2.0;

        mvc.perform(get("/user/collections/avgSalePrice?county=" + name)
                .with(user("user").roles(RoleName.USER)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(avg)));
    }

}
