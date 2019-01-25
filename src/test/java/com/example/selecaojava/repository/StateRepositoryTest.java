package com.example.selecaojava.repository;

import com.example.selecaojava.util.FileRecords;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StateRepositoryTest {

    @Autowired
    private StateRepository stateRepository;

    @Test
    public void numberOfStatesMatches() {
        assertEquals(FileRecords.STATES, stateRepository.findAll().spliterator().getExactSizeIfKnown());
    }

}
