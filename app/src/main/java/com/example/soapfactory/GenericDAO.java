package com.example.soapfactory;

import android.support.annotation.NonNull;

import java.io.Serializable;

public interface GenericDAO<T extends Serializable> {

    void create(@NonNull String id,@NonNull T entity);

    void update(@NonNull String id,@NonNull T entity);

    void delete(@NonNull String id);

    String newKey();
}
