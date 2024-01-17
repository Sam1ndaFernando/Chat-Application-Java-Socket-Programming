package com.chatRoom.dao.custom.impl;

import com.chatRoom.dao.custom.UserDAO;
import com.chatRoom.entity.User;
import com.chatRoom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDAO {
    @Override
    public boolean add(User dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO user(userName, password) VALUES (?,?)");

    }

    @Override
    public boolean update(User dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE user SET  password = ?, status = ? WHERE userName = ?", dto.getPassword(), dto.getStatus(), dto.getUserName());

    }

    @Override
    public boolean delete(String userName) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM user WHERE userName = ?", userName);
    }

    @Override
    public User search(String userName) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE userName = ?", userName);
        if (resultSet.next()) {
            return new User(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
        }
        return null;

    }

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user");
        while (resultSet.next()) {
            users.add(new User(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
        }
        return users;
    }
}
