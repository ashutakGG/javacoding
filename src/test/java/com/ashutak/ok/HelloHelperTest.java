package com.ashutak.ok;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelloHelperTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sayHello() throws Exception {
        new HelloHelper().sayHello();
        Assert.assertEquals(1, 2);
    }

}