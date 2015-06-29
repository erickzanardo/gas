package org.eck;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class GasTest {

    @Test
    public void testGas() {
        TestClass testClass = new TestClass();
        Gas gas = Gas.gas(testClass);

        List<String> fields = gas.fields();
        Assert.assertEquals(3, fields.size());

        gas.set("id", 2l);
        Assert.assertEquals(2l, gas.get("id"));
    }
}
