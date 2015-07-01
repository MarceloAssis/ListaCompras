package marceloassis.listacompras;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import marceloassis.listacompras.adapter.ItensAdapter;
import marceloassis.listacompras.to.TOItem;
import marceloassis.listacompras.util.SharedPreferencesHelper;

public class ListaComprasActivity extends ListActivity  {

    private DataBaseHelper db;
    private List<TOItem> itens = new ArrayList<TOItem>();
    private ItensAdapter itensAdapter;
    public static final int REQUEST_EDICAO = 0;
    public static final int REQUEST_SALVOU = 1;
    private String usuario;

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_EDICAO) {
            if (resultCode == REQUEST_SALVOU) {
                lerDados();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras);

        usuario = SharedPreferencesHelper.read(ListaComprasActivity.this, "user-preferences",
                "user", null);
        db = new DataBaseHelper(this);
        lerDados();
    }

    public void lerDados(){
        db.abrir();
        itens.clear();

        Cursor cursor = db.retornaTodosItens(usuario);

//        int num = cursor.getCount();

        if (cursor.moveToFirst()){
            do {

                TOItem a = new TOItem();
                a.id = cursor.getInt
                        (cursor.getColumnIndex(DataBaseHelper.COLUNA_LISTA_ID));
                a.usuario = cursor.getString
                        (cursor.getColumnIndex(DataBaseHelper.COLUNA_USUARIO));
                a.item = cursor.getString
                        (cursor.getColumnIndex(DataBaseHelper.COLUNA_ITEM));
                a.valor = cursor.getDouble
                        (cursor.getColumnIndex(DataBaseHelper.COLUNA_VALOR));

                if (a.usuario.equals(usuario))
                   itens.add(a);
            } while (cursor.moveToNext());
        }
        if (itens.size() > 0){
            if (itensAdapter == null){

                itensAdapter = new ItensAdapter(this, itens){
                    @Override
                    public void edita(TOItem item) {
                        Intent intent = new Intent(getApplicationContext(),
                                NovoEdicaoActivity.class);
                        intent.putExtra("item", item);

                        startActivityForResult(intent, REQUEST_EDICAO);
                    }

                    @Override
                    public void deleta(TOItem item) {
                        db.abrir();
                        db.apagaItem(item.id);
                        db.fechar();
                        lerDados();
                    }
                };
                setListAdapter(itensAdapter);
            } else {
                itensAdapter.novosDados(itens);
            }
            db.fechar();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_compras, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == R.id.menu_add){
            Intent intent = new Intent(this, NovoEdicaoActivity.class);

            startActivityForResult(intent, REQUEST_EDICAO);
            return true;
        }else {
            return super.onMenuItemSelected(featureId, item);
        }

    }


}
