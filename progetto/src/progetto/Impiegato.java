package progetto;

public class Impiegato {
    private String id;
    public TipoImpiegato tipoImpiegato;
    private String nome;
    private String cognome;

    public Impiegato(String id , TipoImpiegato tipoImpiegato , String nome , String cognome)
    {
        this.id = id;
        this.tipoImpiegato = tipoImpiegato;
        this.nome = nome;
        this. cognome = cognome;
    }
    public String getId()
    {
        return id;
    }
    public TipoImpiegato getTipoImpiegato()
    {
        return tipoImpiegato;
    }
}
