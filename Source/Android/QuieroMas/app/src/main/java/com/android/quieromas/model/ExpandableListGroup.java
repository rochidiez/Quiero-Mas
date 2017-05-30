package com.android.quieromas.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 24/5/17.
 */

public class ExpandableListGroup {

    public String string;
    public final List<String> children = new ArrayList<String>();

    public ExpandableListGroup(String string) {
        this.string = string;
    }
}
