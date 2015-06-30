# GAS
GAS - Getters and Setters. Simple library to get an instance getters and setters in java

## Maven

```xml
<repository>
  <id>erickzanardo-releases</id>
  <url>http://erickzanardo.github.com/maven/releases/</url>
</repository>
```

```xml
<dependency>
  <groupId>org.eck</groupId>
  <artifactId>gas</artifactId>
  <version>1.0.0</version>
</dependency
```

## Use it

Given a random java class, for example:

```java
package org.eck;

public class TestClass {
    @Bla
    private Long id;
    @Bla
    @Ble
    private String name;
    private String password;

    // Getters and setters ommited
}
```

You can access this class getters and setters by doing:

```java
        TestClass testClass = new TestClass();
        Gas gas = Gas.gas(testClass);

        List<String> fields = gas.fields();
        Assert.assertEquals(3, fields.size());

        gas.set("id", 2l);
        Assert.assertEquals(2l, gas.get("id"));

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
```
