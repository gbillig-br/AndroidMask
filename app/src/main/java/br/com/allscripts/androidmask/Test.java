package br.com.allscripts.androidmask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import br.com.allscripts.mask.Mask;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        EditText phone = findViewById(R.id.phone);
        EditText cpf = findViewById(R.id.cpf);
        EditText cnpj = findViewById(R.id.cnpj);
        EditText carplate = findViewById(R.id.carplate);

        phone.addTextChangedListener(new Mask(phone, "(00) 000.000.000"));
        cpf.addTextChangedListener(new Mask(cpf, "000.000.000-00"));
        cnpj.addTextChangedListener(new Mask(cnpj, "00.000.000/0000-00"));
        carplate.addTextChangedListener(new Mask(carplate, "UUU-0000"));
    }
}
