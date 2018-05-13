package com.cresol.buzzapplication.model;

/**
 * Created by Nitesh on 9/16/2016.
 */
public class MenuTypeModel {
    public Integer getMenuID() {
        return MenuID;
    }

    public void setMenuID(Integer menuID) {
        MenuID = menuID;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer MenuID;
    public String MenuName;
    public Integer Status;

}
