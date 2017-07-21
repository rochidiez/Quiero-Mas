package com.android.quieromas.model.api;

import java.util.List;

/**
 * Created by lucas on 21/7/17.
 */

public class ShoppingListParams {

    String email;

    List<String> list;

    public ShoppingListParams(String email, List<String> list) {
        this.email = email;
        this.list = list;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
