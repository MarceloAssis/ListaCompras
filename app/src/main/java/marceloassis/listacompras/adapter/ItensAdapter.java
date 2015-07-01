package marceloassis.listacompras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import marceloassis.listacompras.R;
import marceloassis.listacompras.to.TOItem;

/**
 * Created by Marcelo on 30/06/2015.
 */
public abstract class ItensAdapter extends BaseAdapter {

    private List<TOItem> itens;
    private LayoutInflater inflater;

    public ItensAdapter(Context context, List<TOItem> itens){
        this.inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.itens = itens;
    }

    public void novosDados(List<TOItem> itens){

        this.itens = itens;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {

        View v = inflater.inflate(R.layout.item_lista, null);
        ((TextView)(v.findViewById(R.id.txtItem)))
                .setText(itens.get(position).item);

        ((TextView)(v.findViewById(R.id.txtValorItem)))
                .setText(itens.get(position).valor.toString());

        ((ImageButton)(v.findViewById(R.id.btnEditar))).setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edita(itens.get(position));
                    }
                });

        ((ImageButton)(v.findViewById(R.id.btnExcluir)))
                .setOnClickListener
                        (new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleta(itens.get(position));
                            }

                        });
        return v;

    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    public abstract void edita(TOItem item);
    public abstract void deleta(TOItem item);
}
