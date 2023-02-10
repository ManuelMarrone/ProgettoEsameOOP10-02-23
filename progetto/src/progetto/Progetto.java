package progetto;

import java.util.*;

public class Progetto {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numImpiegati =scanner.nextInt();                //numero di impiegati
        int numCitta  =scanner.nextInt();                //numero di città
        int numLavori =scanner.nextInt();                //numero di lavori

        scanner.nextLine();   //altrimenti non va a capo lo scanner

        ArrayList<Impiegato> impiegati = new ArrayList<>();   // Lista di impiegati
        ArrayList<Citta> citta = new ArrayList<>();           // Lista di città
        ArrayList<Lavoro> lavori = new ArrayList<>();         // Lista di lavori


        while(numImpiegati > 0)  //Scorre le righe degli impiegati
        {
            String temp1 = scanner.nextLine();
            String[] campi1 = temp1.split(" ");

            impiegati.add(new Impiegato(campi1[0] , TipoImpiegato.valueOf(campi1[1]) , campi1[2] , campi1[3] ));
            numImpiegati--;
        }
        while(numCitta  > 0)    //Scorre le righe delle città
        {
            String temp2 = scanner.nextLine();
            String[] campi2 = temp2.split(" ");

            String[] listaLavori = campi2[2].split(",");

            citta.add(new Citta(campi2[0] , Grandezza.valueOf(campi2[1]) , listaLavori ));

            numCitta --;
        }
        while(numLavori > 0)     //Scorre le righe dei lavori
        {
            String temp3 = scanner.nextLine();
            String[] campi3 = temp3.split(" ");

            String[] listaImpiegati = campi3[1].split(","); //divide a sua volta gli elementi con la virgola

            ArrayList<Impiegato> imp = new ArrayList<>();

            for(int i = 0; i < listaImpiegati.length  ; i++)         //riempie la lista di impiegati secondo quanto dato in input
            {
                for (Impiegato impiegato : impiegati) {
                    if (listaImpiegati[i].equals(impiegato.getId())) {

                        imp.add(i, impiegato);

                        break;
                    }
                }
            }
            //riempie anche un campo della classe Lavoro(partecipanti) con gli oggetti Impiegato
            lavori.add(new Lavoro(campi3[0] , imp , TipoLavoro.valueOf(campi3[2]) , Integer.parseInt(campi3[3]) , Integer.parseInt(campi3[4])));
            numLavori--;
        }

        String scelta = scanner.nextLine();
        String[] campiScelta = scelta.split(" ");
        switch (campiScelta[0]) {
            case "TASK1" -> Progetto.task1(lavori, citta, impiegati);
            case "TASK2" -> {
                int p = Integer.parseInt(campiScelta[1]);
                int q = Integer.parseInt(campiScelta[2]);
                int r = Integer.parseInt(campiScelta[3]);
                if(Progetto.task2(lavori, citta, impiegati, p, q, r))
                    System.out.println("YES");
                else
                    System.out.println("NO");
            }
            case "TASK3" ->{
                int n = Integer.parseInt(campiScelta[1]);
                int m = Integer.parseInt(campiScelta[2]);

                ArrayList<Lavoro> simulazione = new ArrayList<>();
                String str;
                for ( int i = 0 ; i < m ; i++)
                {
                    str = scanner.nextLine();
                    for (Lavoro l:lavori) {
                        if(l.getNome().equals(str))
                            simulazione.add(l);
                    }
                }
                if(Progetto.task3(simulazione, n))
                    System.out.println("VALID");
                else
                    System.out.println("NOT VALID");
            }
        }
        scanner.close();
    }

    //trova il massimo in un array e restituisce l'indice del massimo
    public static int trovaMax(int[] occorrenze , ArrayList<Impiegato> impiegati)
    {
        int indice = 0;
        int a = occorrenze[0];
        for ( int i  = 1 ; i < occorrenze.length ; i++)
        {
            if(a < occorrenze[i]) {
                a = occorrenze[i];
                indice = i;
            }else if(a == occorrenze[i])
            {
                if(impiegati.get(indice).getId().compareTo(impiegati.get(i).getId()) > 0) //i precede indice in ordine lessicografico
                    indice = i;
            }
        }
        return indice;
    }
    public static void task1(ArrayList<Lavoro> lavori , ArrayList<Citta> citta , ArrayList<Impiegato> impiegati)
    {
        /*
        * PRIMO PUNTO
        * numero di impiegati non abilitati in azienda
        */
        int conta = 0;
        for (Lavoro value : lavori) {
             conta += value.contaImpiegatiNonAbilitati();
            }
        System.out.println(conta);

        /*
        * SECONDO PUNTO
        * nome della città con meno lavori commissionati
        * Se più soluzioni allora ordine lessicografico crescente
        */
        int indice = 0;
        int minore = (citta.get(0)).getLunghezzaLavori();
        for ( int i = 1 ; i < citta.size() ; i++)
        {
            if(minore > citta.get(i).getLunghezzaLavori()) {
                minore = citta.get(i).getLunghezzaLavori();
                indice = i;
            } else if (minore == citta.get(i).getLunghezzaLavori()) {
                if(citta.get(indice).getNome().compareTo(citta.get(i).getNome()) > 0) //i precede indice in ordine lessicografico
                    indice = i;
            }
        }
        System.out.println(citta.get(indice).getNome());

        /*
         * TERZO PUNTO
         * ID dell'impiegato assegnato a più lavori
         * Se più soluzioni allora ordine lessicografico crescente
         */
        int[] occorrenze = new int[impiegati.size()];
        for (int j =  0 ; j < impiegati.size() ; j++) {
            for (Lavoro lavoro : lavori) {
                occorrenze[j] += lavoro.calcolaOccorrenze(impiegati.get(j).getId());
            }
        }
        int max = trovaMax(occorrenze , impiegati);
        System.out.println(impiegati.get(max).getId());

        /*
         * QUARTO PUNTO
         * ID impiegato con cifra totale maggiore
         * Se più soluzioni allora ordine lessicografico crescente
         */
        int[] somma = new int[impiegati.size()];
        for (int j =  0 ; j < impiegati.size() ; j++) {
            for (Lavoro lavoro : lavori) {
                somma[j] += lavoro.calcolaImporto(impiegati.get(j).getId());
            }
        }
        int sommaMax = trovaMax(somma , impiegati);
        System.out.println(impiegati.get(sommaMax).getId());

        /*
         * QUINTO PUNTO
         * grandezza della città con minimo di impiegati DISTINTI ATTIVI
         * Se più soluzioni allora ordine crescente di grandezza[piccola,media,grande]
         */
        int min = Integer.MAX_VALUE;
        Citta cittaTemp = new Citta();
        Grandezza nome = null ;
        for(Citta singolaCitta:citta)
        {
            ArrayList<String> impiegatiCitta = singolaCitta.trovaImpiegatiDistinti(lavori);
            if(impiegatiCitta.size() < min) {
                min = impiegatiCitta.size();
                cittaTemp = singolaCitta;
                nome = singolaCitta.getGrandezza();
            }else if(impiegatiCitta.size() == min)
            {
                if(singolaCitta.grandezza.compareTo(cittaTemp.grandezza) < 0) { //singolaCitta è minore di cittaTemp
                    nome = singolaCitta.getGrandezza();
                    cittaTemp = singolaCitta;
                }
            }
        }
        System.out.println(nome);

        /*
         * SESTO PUNTO
         * numero di impiegati DISTINTI coinvolti in lavori relativi alla città con più lavori
         */

        //città con più lavori commissionati
        int index = 0;
        int lung = (citta.get(0)).getLunghezzaLavori();
        for ( int i  = 1 ; i < citta.size() ; i++)
        {
            if(lung < citta.get(i).getLunghezzaLavori()) {
                lung = citta.get(i).getLunghezzaLavori();
                index = i;
            } else if (lung == citta.get(i).getLunghezzaLavori()) {
                if(citta.get(index).getNome().compareTo(citta.get(i).getNome()) > 0) //i precede index in ordine lessicografico
                    index = i;
            }
        }
        System.out.println(citta.get(index).trovaImpiegatiDistinti(lavori).size());

        /*
         * SETTIMO PUNTO
         * il numero totale di lavoratori distinti assegnati a lavori in città di ciascuna tipologia
         * (ordinate rispettivamente piccole, medie, grandi), separati da uno spazio
         *
         */
        StringBuilder risultato = new StringBuilder();

        ArrayList<String> piccole = new ArrayList<>();
        ArrayList<String> medie = new ArrayList<>();
        ArrayList<String> grandi = new ArrayList<>();

        for(Citta ci:citta) //scorre la lista città
        {
            switch (ci.getGrandezza())
            {
                case piccola -> {
                    for (String i:ci.trovaImpiegatiDistinti(lavori)) {
                        if(!piccole.contains(i))
                            piccole.add(i);
                    }
                }
                case media -> {
                    for (String i:ci.trovaImpiegatiDistinti(lavori)) {
                        if(!medie.contains(i))
                            medie.add(i);
                    }
                }
                case grande -> {
                    for (String i:ci.trovaImpiegatiDistinti(lavori)) {
                        if(!grandi.contains(i))
                            grandi.add(i);
                    }
                }
            }
        }
        risultato.append(piccole.size()).append(" ").append(medie.size()).append(" ").append(grandi.size());
        System.out.println(risultato);
    }

    public static boolean task2(ArrayList<Lavoro> lavori , ArrayList<Citta> citta , ArrayList<Impiegato> impiegati , int p , int q , int r)
    {
        //PRIMO CONTROLLO: numero di lavori con durata > p è max q
        int num = 0;
        for (Lavoro l:lavori) {
            num += l.contaLavori(p);
        }
        if(num > q) {
            return false;
        }

//SECONDO CONTROLLO: per ogni impiegato c'è una sola città in cui la somma degli importi dei lavori dell'impiegato in quella città è > r
        boolean controllo;
        for (Impiegato imp:impiegati) {
            controllo = false;
            for(Citta cit:citta)
            {
                int importo = cit.trovaImportoImpiegati(lavori , imp.getId());  //calcola importo rispetto alla città
                if (importo > r && !controllo) {
                    controllo = true;            //controllo diventerà true al primo importo > r
                }
                else if (importo > r && controllo) {
                    return false;
                }
            }
            if(!controllo) {
                return false;
            }
        }

        //TERZO CONTROLLO: per ogni città non ci sono lavori della stessa tipologia attivi
        for(Citta cit:citta)
        {
            if(!cit.confrontaTipologiaLavori(lavori)) {
                return false;
            }
        }

        //QUARTO CONTROLLO: per ogni lavoro solo impiegati di tipologie diverse
        for (Lavoro l:lavori)
        {
            if(!l.verificaTipologie()) {
                return false;
            }
        }


        return true;
    }

    public static boolean task3(ArrayList<Lavoro> simulazione , int n)
    {
        boolean task3 = false;
        double giornoInizio = 0;
        double durataComplessiva = 0;
        int durata = 0;
        int perc = 0;
        for (Lavoro l:simulazione) {
            switch (l.getTipoLavoro())
            {
                case ritocco -> durataComplessiva = Math.max(giornoInizio + l.getDurata() , durataComplessiva);
                case costruzione -> {
                    giornoInizio += ((double)durata/100*perc);
                    giornoInizio = Math.ceil(giornoInizio);
                    durataComplessiva = Math.max(giornoInizio + l.getDurata() , durataComplessiva);
                    durata = l.getDurata();
                    perc = 20;
                }
                case ristrutturazione -> {
                    giornoInizio += ((double)durata/100*perc);
                    giornoInizio = Math.ceil(giornoInizio);
                    durataComplessiva = Math.max(giornoInizio + l.getDurata() , durataComplessiva);
                    durata = l.getDurata();
                    perc = 30;
                }
            }
        }
        if(durataComplessiva <= n)
            task3 = true;

        return task3;
    }
}