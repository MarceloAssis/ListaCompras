package marceloassis.listacompras.to;

import java.io.Serializable;

/**
 * Created by Marcelo on 30/06/2015.
 */
public class TOItem implements Serializable{
    private static final long serialVersionUID = 1633833011084400384L;

    public int id;
    public String usuario;
    public String item;
    public Double valor;

}
