package com.chatRoom.bo.custom.impl;

import com.chatRoom.bo.custom.UserBO;
import com.chatRoom.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    @Override
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteUser(String userName) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public UserDTO searchUser(String userName) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        return null;
    }
}
