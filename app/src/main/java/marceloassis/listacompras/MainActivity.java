package marceloassis.listacompras;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import marceloassis.listacompras.util.SharedPreferencesHelper;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    DataBaseHelper helper = new DataBaseHelper(this);
    public Button btnEntrar;
    public Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnEntrar.setOnClickListener(this);
        btnCadastrar.setOnClickListener(this);
    }

    public void onClick(View v){

        TextView txtUsuario = (TextView) findViewById(R.id.edtUsuario);
        TextView txtSenha = (TextView) findViewById(R.id.edtSenha);

        String usuario = txtUsuario.getText().toString();
        String senha = txtSenha.getText().toString();

        if (v.getId() == R.id.btnEntrar) {


            String password = helper.retornaUsuario(usuario);
            if(senha.equals(password)){

                SharedPreferencesHelper.write(MainActivity.this, "user-preferences", "user",
                            usuario);

                Intent i = new Intent(this, ListaComprasActivity.class);
                i.putExtra("valor", usuario);
                startActivity(i);
            } else {
                Toast.makeText(this, "Usuário e/ou senha inválidos",
                        Toast.LENGTH_SHORT).show();
            }
        }

        if (v.getId() == R.id.btnCadastrar){
            Intent i = new Intent(this, CadastrarUsuarioActivity.class);
            startActivity(i);
        }
    }

}
