package marceloassis.listacompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import marceloassis.listacompras.to.TOUsuario;

/**
 * Created by Marcelo on 28/06/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 3;
    static final String NOME_BANCO = "listacompras.db";
    static final String TAB_USUARIOS = "usuarios";
    static final String COLUNA_ID = "id";
    static final String COLUNA_NOME = "nome";
    static final String COLUNA_EMAIL = "email";
    static final String COLUNA_LOGIN = "login";
    static final String COLUNA_PASS = "pass";

    static final String TAB_LISTACOMPRAS = "itens";
    static final String COLUNA_LISTA_ID = "id";
    static final String COLUNA_USUARIO = "usuario";
    static final String COLUNA_ITEM = "item";
    static final String COLUNA_VALOR = "valor";

    final Context context;
    MeuOpenHelper openHelper;
    SQLiteDatabase db;

    private static final String TABLE_CREATE_USUARIOS = "create table " + TAB_USUARIOS + " (" + COLUNA_ID +
           " integer primary key not null  , "+
           COLUNA_NOME + " text not null, " + COLUNA_EMAIL + " text not null, " +
            COLUNA_LOGIN + " text not null, " + COLUNA_PASS + " text not null);";

    private static final String TABLE_CREATE_LISTACOMPRAS = "create table " + TAB_LISTACOMPRAS + " ( " + COLUNA_LISTA_ID +
            " integer primary key not null  , "+
            COLUNA_USUARIO + " text not null, " +
            COLUNA_ITEM + " text not null, " +
            COLUNA_VALOR + " decimal not null );";


    public DataBaseHelper(Context context){
        super(context, NOME_BANCO, null, DATABASE_VERSION);
        this.context = context;
        openHelper = new MeuOpenHelper(context);
    }

    public DataBaseHelper abrir() throws SQLException {
        db = openHelper.getWritableDatabase();
        return this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
        db.execSQL(TABLE_CREATE_USUARIOS);
        db.execSQL(TABLE_CREATE_LISTACOMPRAS);
        } catch (SQLException e) {
            e.printStackTrace();

    }
            this.db = db;
    }

    private class MeuOpenHelper extends SQLiteOpenHelper {
        MeuOpenHelper(Context context) {
            super(context, NOME_BANCO, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(TABLE_CREATE_LISTACOMPRAS);
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TAB_LISTACOMPRAS);
            onCreate(db);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS " + TAB_USUARIOS;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + TAB_LISTACOMPRAS;
        db.execSQL(query);

        this.onCreate(db);
    }

    public  int buscaProxNumItem(){

        String query = "select * from " + TAB_LISTACOMPRAS;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        return  count + 1;
    }
    public void insertUsuario(TOUsuario u){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from " + TAB_USUARIOS;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUNA_ID, count);
        values.put(COLUNA_NOME, u.getNome());
        values.put(COLUNA_EMAIL, u.getEmail());
        values.put(COLUNA_LOGIN, u.getLogin());
        values.put(COLUNA_PASS, u.getPass());

        db.insert(TAB_USUARIOS, null, values);
        db.close();

    }

    public String retornaUsuario(String pass){
        db = this.getReadableDatabase();
        String query = "select " + COLUNA_LOGIN + ", " + COLUNA_PASS + " from " +
                TAB_USUARIOS;

        Cursor cursor = db.rawQuery(query, null);

        String a,b;

        b = "";
         if (cursor.moveToFirst()){
            do{
                a = cursor.getString(0);

                if (a.equals(pass)){
                    b = cursor.getString(1);
                    break;
                }

            }
            while(cursor.moveToNext());
        }

        return  b;
    }

    public void fechar() {
        openHelper.close();
    }
    public long insereItem(String usuario, String item, String valor) {

       int prox_id = buscaProxNumItem();

        ContentValues campos = new ContentValues();
        campos.put(COLUNA_LISTA_ID, prox_id);
        campos.put(COLUNA_USUARIO, usuario);
        campos.put(COLUNA_ITEM, item);
        campos.put(COLUNA_VALOR, valor);


        return db.insert(TAB_LISTACOMPRAS, null, campos);
    }

    public boolean apagaItem(long id) {
        return db.delete(TAB_LISTACOMPRAS, COLUNA_LISTA_ID + "=" + id, null) > 0;
    }

    public Cursor retornaTodosItens(String usuario) {

  /*      db = this.getReadableDatabase();
        String query = "select *  from " +
                TAB_LISTACOMPRAS +
                " where " + COLUNA_USUARIO + "=" +  usuario ;

        Cursor cursor = db.rawQuery(query, null);
        int num = cursor.getCount();
        return  cursor;
*/
        return db.query(TAB_LISTACOMPRAS, new String[] {
                        COLUNA_LISTA_ID,
                        COLUNA_USUARIO,
                        COLUNA_ITEM,
                        COLUNA_VALOR},
                null, null, null, null, null);

    }

    public boolean atualizaItem(long id, String usuario,
                                String item,
                                  String valor) {
        ContentValues args = new ContentValues();
        args.put(COLUNA_LISTA_ID, id);
        args.put(COLUNA_USUARIO, usuario);
        args.put(COLUNA_ITEM, item);
        args.put(COLUNA_VALOR, valor);

        return db.update(TAB_LISTACOMPRAS, args, COLUNA_LISTA_ID + "=" + id, null) > 0;
    }
}
