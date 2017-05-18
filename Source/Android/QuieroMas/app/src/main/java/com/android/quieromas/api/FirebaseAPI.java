package com.android.quieromas.api;

import com.android.quieromas.model.Bebe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by lucas on 18/5/17.
 */

public class FirebaseAPI {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public void writeNewBaby(Bebe bebe){
        String userId = mAuth.getCurrentUser().getUid();
        mDatabase.child("bebe").child(userId).setValue(bebe);
    }
}
