package progetto;

import java.util.ArrayList;

public class Lavoro {
    private String nome;
    private ArrayList<Impiegato> partecipanti;
    public TipoLavoro tipoLavoro;
    private int durata;
    private int importo;

    public Lavoro(String nome , ArrayList<Impiegato> partecipanti , TipoLavoro tipoLavoro , int durata , int importo)
    {
        this.nome = nome;
        this.partecipanti = partecipanti;
        this.tipoLavoro = tipoLavoro;
        this.durata = durata;
        this.importo = importo;
    }
    public String getNome()
    {
        return nome;
    }
    public ArrayList<Impiegato> getPartecipanti()
    {
        return partecipanti;
    }
    public TipoLavoro getTipoLavoro()
    {
        return tipoLavoro;
    }
    public int getDurata() {
        return durata;
    }

    public int calcolaImporto (String id)     //calcola importo totale per ogni impiegato
    {
        int somma = 0;
        for (Impiegato impiegato : partecipanti) {
            if (impiegato.getId().equals(id))
                somma += importo;
        }
        return somma;
    }
    public int calcolaOccorrenze(String id)   //quante volte compare uno specifico id nella lista dei partecipanti al lavoro
    {
        int conta = 0;
        for (Impiegato impiegato : partecipanti) {
            if (impiegato.getId().equals(id))
                conta++;
        }
        return conta;
    }
    public boolean verificaAbilitazione(Impiegato impiegato)
    {
        boolean verifica = true;              //verifica diventerà false se e solo se trova una non abilitazione
        switch (impiegato.tipoImpiegato) {
            case architetto -> {
                if (tipoLavoro == TipoLavoro.ristrutturazione)
                    verifica = false;
            }
            case geometra -> {
                if (tipoLavoro == TipoLavoro.costruzione)
                    verifica = false;
            }
            case ingegnere -> {
                if (tipoLavoro == TipoLavoro.ritocco)
                    verifica = false;
            }
        }
        return verifica;
    }
    public int contaImpiegatiNonAbilitati() {
        int conta=0;
        for (Impiegato impiegato : partecipanti) {
            if(!verificaAbilitazione(impiegato))
                conta++;
        }
        return conta;
    }
    public int contaLavori (int p)  //conta i lavori di durata > p
    {
        if(durata > p)
            return 1;
        else
            return 0;
    }
    public boolean verificaTipologie()    //trova eventuali doppioni di tipologie di impiegati
    {
        boolean verifica = true;
        ArrayList<TipoImpiegato> listaTipi = new ArrayList<>();
        for (Impiegato impiegato : partecipanti)                 //scorre la lista di partecipanti al lavoro
        {
            if(!listaTipi.contains(impiegato.getTipoImpiegato()))
                listaTipi.add(impiegato.getTipoImpiegato());
            else
                verifica = false;
        }
        return verifica;
    }
}
