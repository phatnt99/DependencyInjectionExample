package com.phatng.di.container;


import com.phatng.di.annotation.Component;
import com.phatng.di.annotation.Inject;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.util.*;

public class DIContainer {

    private final Map<Class<?>, Object> components = new HashMap<>();

    public DIContainer() {
        // scan all classes in the classpath for @Component annotation
        List<Object> highLevelComponents = new ArrayList<>();
        for (Class<?> clazz : getAllClasses()) {
            if (clazz.isAnnotationPresent(Component.class)) {
                // create an instance of the component and store it in the map
                Object component = createComponent(clazz);
                components.put(clazz, component);
                if (isHighLevelComponent(clazz)) {
                    highLevelComponents.add(component);
                }
            }
        }

        // inject dependencies into all high level components
        for (Object highLevelComponent : highLevelComponents) {
            injectDependencies(highLevelComponent);
        }
    }

    public <T> T getInstance(Class<T> type) {
        if (!components.containsKey(type)) {
            throw new RuntimeException("No instance found for type " + type.getName());
        }
        return type.cast(components.get(type));
    }

    private Set<Class> getAllClasses() {
        Reflections reflections = new Reflections("com.phatng.di", new SubTypesScanner(false));
        return new HashSet<>(reflections.getSubTypesOf(Object.class));
    }

    private boolean isHighLevelComponent(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                return true;
            }
        }
        return false;
    }

    private Object createComponent(Class<?> clazz) {
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            System.out.println("Cannot instantiate class " + clazz.getName());
            instance = null;
        }
        return instance;
    }

    private void injectDependencies(Object component) {
        Field[] fields = component.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> type = field.getType();
                Object dependency = getInstance(type);
                field.setAccessible(true);
                try {
                    field.set(component, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error injecting dependency of type " + type.getName() + " into field " + field.getName() + " of component " + component.getClass().getName(), e);
                }
            }
        }
    }

}