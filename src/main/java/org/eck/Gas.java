package org.eck;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gas {
    private Object me;

    private Map<String, Method> getters = new HashMap<String, Method>();
    private Map<String, Method> setters = new HashMap<String, Method>();
    private List<String> fields = new ArrayList<String>();

    private void setMe(Object me) {
        this.me = me;
    }

    public Object getMe() {
        return me;
    }

    private void addGetter(String field, Method method) {
        getters.put(field, method);
    }

    private void addField(String field) {
        fields.add(field);
    }

    private void addSetter(String field, Method method) {
        setters.put(field, method);
    }

    public void set(String field, Object value) {
        Method method = setters.get(field);
        if (method == null) {
            throw new IllegalArgumentException("No setter found for: " + field);
        }
        try {
            method.invoke(me, value);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Object get(String field) {
        Method method = getters.get(field);
        if (method == null) {
            throw new IllegalArgumentException("No getter found for: " + field);
        }
        try {
            return method.invoke(me);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> fields() {
        return fields;
    }

    @SafeVarargs
    public static Gas gas(Object object, Class<? extends Annotation> ... filters) {
        Gas gas = new Gas();
        gas.setMe(object);

        Class<? extends Object> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            boolean skip = true;

            if(filters == null || filters.length == 0) {
                skip = false;
            } else {
                for(Class<? extends Annotation> annotationClass : filters) {
                    if(field.isAnnotationPresent(annotationClass)) {
                        skip = false;
                    }
                }
            }

            if(skip) {
                continue;
            }

            String name = field.getName();
            String _name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());

            String getterName = "get" + _name;
            String setterName = "set" + _name;

            Class<?> type = field.getType();
            try {
                Method getter = clazz.getMethod(getterName);
                gas.addGetter(name, getter);
            } catch (NoSuchMethodException | SecurityException e) {
                System.out.println("There is no getter for " + name + " field");
            }

            try {
                Method setter = clazz.getMethod(setterName, type);
                gas.addSetter(name, setter);
            } catch (NoSuchMethodException | SecurityException e) {
                System.out.println("There is no setter for " + name + " field");
            }

            gas.addField(name);
        }

        return gas;
    }
}
