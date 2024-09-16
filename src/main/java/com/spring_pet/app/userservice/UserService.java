package com.spring_pet.app.userservice;

import java.util.List;

public interface UserService<T> {

    public T createUser(T userData);

    public List<T> getUsers(int page, int limit);

    public T getUserByUserId(String userId);

    public T updateUser(String userId, T user);

    public void deleteUser(String userId);
}
