package progetto;

import java.util.ArrayList;

public class Citta {
    private String nome;
    public Grandezza grandezza;
    private String[] lavori;

    public Citta(){}
    public Citta(String nome , Grandezza grandezza , String[] lavori)
    {
        this.nome = nome;
        this.grandezza = grandezza;
        this.lavori = lavori;
    }
    public String getNome()
    {
        return nome;
    }
    public Grandezza getGrandezza()
    {
        return grandezza;
    }
    public int getLunghezzaLavori()
    {
        return lavori.length;
    }
    public ArrayList<String> trovaImpiegatiDistinti(ArrayList<Lavoro> listaLavori)  //trova gli impiegati distinti per ogni città
    {
        ArrayList<String> impiegati = new ArrayList<>();
        for (String s : lavori) {                                   //scorre la lista lavori di Citta
            for (Lavoro lavoro : listaLavori) {                       //scorre la lista lavori data in input
                if (s.equals(lavoro.getNome())) {                         //se c'è corrispondenza
                    for (Impiegato impiegato : lavoro.getPartecipanti()) {   //scorre gli impiegati partecipanti al lavoro
                        if(!impiegati.contains(impiegato.getId()))
                            impiegati.add(impiegato.getId());
                    }
                    break;
                }
            }
        }
        return impiegati;
    }
    public int  trovaImportoImpiegati(ArrayList<Lavoro> listaLavori , String id)     //da fare per la singola città
    {
        int importo = 0;
        for (String s : lavori) {                             //scorre la lista lavori di Citta
            for (Lavoro lavoro : listaLavori) {               //scorre la lista lavori data in input
                if (s.equals(lavoro.getNome())) {                //se trova corrispondenza
                    importo += lavoro.calcolaImporto(id);
                }
            }
        }
        return importo;
    }
    public boolean confrontaTipologiaLavori(ArrayList<Lavoro> listaLavori)     //da fare per la singola città
    {
        boolean verifica = true;
        ArrayList<TipoLavoro> listaTipi = new ArrayList<>();
        for (String s : lavori) {                             //scorre la lista lavori di Citta
            for (Lavoro lavoro : listaLavori) {               //scorre la lista lavori data in input
                if (s.equals(lavoro.getNome())) {                //se trova corrispondenza
                    if(!listaTipi.contains(lavoro.getTipoLavoro()))
                       listaTipi.add(lavoro.getTipoLavoro());
                    else
                        verifica = false;                                     //condizione non verificata
                }
            }
        }
        return verifica;
    }
}
