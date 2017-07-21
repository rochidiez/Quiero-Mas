package com.android.quieromas.model.api;

import java.util.List;

/**
 * Created by lucas on 21/7/17.
 */

public class ShareParams {

    List<String> emails;

    public ShareParams(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmail(List<String> email) {
        this.emails = emails;
    }
}
