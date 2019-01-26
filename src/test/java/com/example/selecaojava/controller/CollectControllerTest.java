package com.example.selecaojava.controller;

import com.example.selecaojava.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CollectControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void importFileSucceeds() throws Exception {
        byte[] data = FileUtil.getFileBytes("src/test/resources/coletas.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "coletas.csv", MediaType.ALL_VALUE, data);

        mvc.perform(multipart("/user/collections/files")
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .with(user("user")))
                .andExpect(status().isCreated());
    }

}
