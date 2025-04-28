package com.example.register2.model;

public class Utente {

    private   int codUtente;  //Primary key
    private   String nome;
    private   String dataNascita;
    private   int codPiano;   //Foreign key
    private   String dataRegistrazione;
    private   String email; //Foreign Key

        //Costruttore
        public Utente(int codUtente,String nome,String dataNascita,int codPiano,String dataRegistrazione,String email){
            this.codUtente=codUtente;
            this.nome=nome;
            this.dataNascita=dataNascita;
            this.codPiano=codPiano;
            this.dataRegistrazione=dataRegistrazione;
            this.email=email;
        }

        // Costruttore senza codUtente quando inserisci nel DB
        public Utente(String nome, String dataNascita, int codPiano, String dataRegistrazione,String email) {
            this.nome = nome;
            this.dataNascita = dataNascita;
            this.codPiano = codPiano;
            this.dataRegistrazione = dataRegistrazione;
            this.email = email;
        }

        //metodi setter
        public void setCodUtente(int codUtente){this.codUtente=codUtente;}
        public void setNome(String nome){this.nome=nome;}
        public void setDataNascita(String dataNascita){this.dataNascita=dataNascita;}
        public void setCodPiano(int codPiano){ this.codPiano=codPiano;}
        public void setDataRegistrazione(String dataRegistrazione){ this.dataRegistrazione=dataRegistrazione;}
        public void setEmail(String email){this.email=email;}


        //metodi getter
        public int getCodUtente()  {return codUtente;}
        public String getNome()  {return nome;}
        public String getDataNascita() {return dataNascita;}
        public int getCodPiano() {return codPiano;}
        public String getDataRegistrazione() {return dataRegistrazione;}
        public String getEmail(){return email;}




    public void verificaDataNascitaValida(){}  //problema---> la data la gestisco come una stringa


    public void verificaCodicePianoValido(){ //controlli da fare e che il codice sia unico nel sistema
            if (codPiano < 0 ) { //il codice del piano deve essere un intero maggiore di 0
                System.out.println("codice del piano inesistente");
                return;
            }
    }

    public void verificaDataRegistrazioneValida(){}  //problema---> la data la gestisco come una stringa


    public void verificaCodiceUtenteValido(){  //controlli da fare e che il codice sia unico nel sistema
        if (codUtente < 0 ) { //il codice dell'utente deve essere un intero maggiore di 0
            System.out.println("codice dell'utente inesistente");
            return;
        }
    }



    //Verifica del nome ---> controllare che il nome non sia vuoto e che non ci siano caratteri strani tipo (.,ç@-_°#[]\|!£$%&/()=^ ecc ) o numeri all'interno.
    public void verificaNomeValido(){
        if (nome == null || nome.isEmpty()) {  //se non si inserisce il nome o il nome è vuoto allora si return
            System.out.println("Nome vuoto o nullo");
            return;
        }
        boolean invalid = false;
        // Array di caratteri vietati con numeri e simboli
        char[] caratteri = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
                ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'
        };
        // Scorriamo ogni carattere del nome
        for (int i = 0; i < nome.length(); i++) {
            char c = nome.charAt(i); // Prendiamo il carattere alla posizione i

            // Confrontiamo il carattere con tutti quelli vietati
            for (int j = 0; j < caratteri.length; j++) {
                if (c == caratteri[j]) { // Se troviamo un carattere vietato
                    invalid = true;      // Impostiamo a true
                    break;               // Usciamo dal ciclo interno
                }
            }
            if (invalid) { // Se abbiamo trovato un carattere vietato
                break;     // Usciamo anche dal ciclo esterno
            }
        }
        // Dopo il controllo, vediamo se il nome contiene caratteri vietati
        if (invalid) {
            System.out.println("Il nome ha un carattere non valido");
        } else {
            System.out.println("Il nome ha tutti caratteri ammessi");
        }
    }

}
