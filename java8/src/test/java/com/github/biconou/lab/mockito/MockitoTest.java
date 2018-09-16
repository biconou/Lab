package com.github.biconou.lab.mockito;

import org.junit.Assert;
import org.junit.Test;
import static  org.mockito.Mockito.*;

public class MockitoTest {

    @Test
    public void firstMockitoTest() {

        Additioner mockedAdditioner = mock(Additioner.class);
        when(mockedAdditioner.add(5, 2)).thenReturn(7);

        SuperCalculator superCalculator = new SuperCalculator(mockedAdditioner);

        int result = superCalculator.compute(5, 2);
        Assert.assertEquals(14, result);
    }
}
