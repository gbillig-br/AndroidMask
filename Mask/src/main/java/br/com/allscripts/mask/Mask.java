package br.com.allscripts.mask;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import br.com.allscripts.mask.utils.Formatter;

public class Mask extends Formatter implements TextWatcher {

    private EditText Field;
    private String CurrentText;

    public Mask(EditText Field, String Mask) {
        super(Mask);
        this.Field = Field;
    }

    public void ChangeMask(String Mask) {
        this.mask = Mask;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence Sequence, int i, int i1, int i2) {
        if (!Sequence.toString().equals(CurrentText)) {
            CurrentText = Format(Sequence.toString());
            Field.setText(CurrentText);
            if (Field instanceof EditText) {
                EditText LocalField = this.Field;
                LocalField.setSelection(CurrentText.length());
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
