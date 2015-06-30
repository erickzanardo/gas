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

    @Test
    public void testGasAnnotationFilter() {
        TestClass testClass = new TestClass();
        Gas gas = Gas.gas(testClass, Bla.class);

        List<String> fields = gas.fields();
        Assert.assertEquals(2, fields.size());

        testClass = new TestClass();
        gas = Gas.gas(testClass, Ble.class);

        fields = gas.fields();
        Assert.assertEquals(1, fields.size());

        testClass = new TestClass();
        gas = Gas.gas(testClass, Ble.class, Bla.class);

        fields = gas.fields();
        Assert.assertEquals(2, fields.size());

    }
}
