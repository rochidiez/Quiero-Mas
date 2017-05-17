package com.android.quieromas.validator;

import android.text.TextUtils;
import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

/**
 * Created by lucas on 17/5/17.
 */

public class RepeatPasswordValidator extends Validator {

    EditText password;

    public RepeatPasswordValidator(EditText password) {
        super("Las contraseñas no son iguales");
        this.password = password;
    }

    public boolean isValid(EditText repeat) {
        return TextUtils.equals(password.getText(), repeat.getText());
    }

}
