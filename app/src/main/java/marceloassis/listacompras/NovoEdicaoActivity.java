package marceloassis.listacompras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import marceloassis.listacompras.to.TOItem;
import marceloassis.listacompras.util.SharedPreferencesHelper;


public class NovoEdicaoActivity extends Activity {

        private EditText edtItem;
        private EditText edtValor;
        private TOItem item;
        private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_edicao);

        edtItem = (EditText) findViewById(R.id.edtItem);
        edtValor = (EditText) findViewById(R.id.edtValor);

        Intent intent = getIntent();

        item = (TOItem)intent.getSerializableExtra("item");
        usuario = SharedPreferencesHelper.read(NovoEdicaoActivity.this, "user-preferences",
                "user", null);

        if (item != null){
            edtItem.setText(item.item);
            edtValor.setText(item.valor.toString());
        }
    }

    public void salvar(View v){
        DataBaseHelper db = new DataBaseHelper(this);
        db.abrir();

        if (item != null){
            db.atualizaItem(item.id,
                    usuario,
                    edtItem.getText().toString(),
                    edtValor.getText().toString()
            );

        }else {
            db.insereItem(usuario,
                    edtItem.getText().toString(),
                    edtValor.getText().toString()
            );
        }
        db.fechar();
        setResult(ListaComprasActivity.REQUEST_SALVOU);
        finish();
    }

}
