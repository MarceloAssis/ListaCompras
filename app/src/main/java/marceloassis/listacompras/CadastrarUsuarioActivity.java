package marceloassis.listacompras;

import android.database.SQLException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import marceloassis.listacompras.to.TOUsuario;


public class CadastrarUsuarioActivity extends ActionBarActivity implements View.OnClickListener{

    public Button btnSalvar;
    DataBaseHelper helper = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(this);

    }

    public void onClick(View v){
        TextView txtNome = (TextView) findViewById(R.id.txtNome);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        TextView txtLogin = (TextView) findViewById(R.id.txtUsuarioCad);
        TextView txtSenha = (TextView) findViewById(R.id.txtSenhaCad);
        TextView txtConfirmaSenha = (TextView) findViewById(R.id.txtConfirmaSenha);

        if(!txtSenha.getText().toString().equals(txtConfirmaSenha.getText().toString())){
            Toast pass = Toast.makeText(CadastrarUsuarioActivity.this, "Senha não confirmada! 9w ",
                    Toast.LENGTH_SHORT);
            pass.show();

            return;
        }
        TOUsuario u = new TOUsuario();
        u.setNome(txtNome.getText().toString());
        u.setEmail(txtEmail.getText().toString());
        u.setLogin(txtLogin.getText().toString());
        u.setPass(txtSenha.getText().toString());

        try {
            helper.insertUsuario(u);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Usuário cadastrado com Sucesso!",
                Toast.LENGTH_SHORT).show();
    }


}
