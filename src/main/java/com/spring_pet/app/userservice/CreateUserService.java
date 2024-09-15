package com.spring_pet.app.userservice;

public interface CreateUserService<T, E> {

    public T createUser(E userData);
}
