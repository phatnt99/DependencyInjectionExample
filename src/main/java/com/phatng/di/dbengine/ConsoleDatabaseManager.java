package com.phatng.di.dbengine;

import com.phatng.di.annotation.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsoleDatabaseManager<T> {
    public List<T> query(Class<T> clazz) {
        try {
            return List.of(
                    clazz.newInstance(),
                    clazz.newInstance(),
                    clazz.newInstance()
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }
}
