package com.chatRoom.bo;

import com.chatRoom.bo.custom.impl.UserBOImpl;

public class BOFactory {

    private BOFactory() {
    }

    private static BOFactory boFactory;

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        USER
    }

    public SuperBO getBO(BOTypes boTypes) {
        if (boTypes == BOTypes.USER) {
            return new UserBOImpl();
        }
        return null;
    }
}
