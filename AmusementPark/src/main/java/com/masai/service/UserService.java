package com.masai.service;

import java.util.List;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.User;

public interface UserService {

	public User getUser(Integer userId) throws UserNotFoundException;

	public User createUser(User user) throws SomethingWentWrongException;

	public String updateUser(User user) throws UserNotFoundException, SomethingWentWrongException;

	public String deleteUser(Integer userId) throws UserNotFoundException;

	public List<User> getAllUsers() throws SomethingWentWrongException;

	public User findByEmail(String name) throws UserNotFoundException;

}
