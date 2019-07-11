package com.vpu.mp;

import org.junit.Test;

import java.time.LocalDate;

public class TestLocalDate {

    @Test
    public void testLocalDate() {
        LocalDate now = LocalDate.now();
        System.out.println(now.toString().replaceAll("-",""));
    }
}
