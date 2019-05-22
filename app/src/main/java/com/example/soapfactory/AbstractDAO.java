package com.example.soapfactory;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractDAO<T extends Serializable> implements GenericDAO<T> {

    private DatabaseReference db;
    private String reference;
    private Type currentClass;

    public AbstractDAO(String tableReference){
        db = FirebaseDatabase.getInstance().getReference();
        reference = tableReference;
        Type sooper = getClass().getGenericSuperclass();
        currentClass = ((ParameterizedType)sooper).getActualTypeArguments()[ 0 ];
    }

    @Override
    public void create(@NonNull String id,@NonNull T entity) {
        db.child(reference).child(id).setValue(entity);
    }

    @Override
    public void update(@NonNull String id,@NonNull T entity) {
        db.child(reference).child(id).setValue(entity);
    }

    @Override
    public void delete(@NonNull String id) {
        db.child(reference).child(id).removeValue();
    }

    @Override
    public String newKey() {
        return db.child(reference).push().getKey();
    }
}
