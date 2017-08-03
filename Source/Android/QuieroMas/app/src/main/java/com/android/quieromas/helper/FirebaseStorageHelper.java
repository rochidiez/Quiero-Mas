package com.android.quieromas.helper;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

/**
 * Created by lucas on 3/8/17.
 */

public class FirebaseStorageHelper {

    FirebaseStorage mStorage;
    FirebaseAuth mAuth;

    public static final String BABIES = "Bebes";

    public FirebaseStorageHelper(){
        mStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public StorageReference getProfilePictureStorageReference(){
        return mStorage.getReference().child(BABIES).child(mAuth.getCurrentUser().getUid());
    }


}
