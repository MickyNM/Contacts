package com.example.kontakti.podaci;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class PodaciSingleton {
    private static PodaciSingleton instanca = new PodaciSingleton();
    private static String fajl = "podaci.txt";
    protected ObservableList<Kontakti> stavkeOLista;

    public static PodaciSingleton getInstance() {
        return instanca;
    }

    public void dodajStavku(Kontakti kontakti) {
        stavkeOLista.add(kontakti);
    }

    public void obrisiStavku(Kontakti kontakti) {
        stavkeOLista.remove(kontakti);
    }

    public ObservableList<Kontakti> getStavkeOLista() {
        return stavkeOLista;
    }

    public void ucitajPodatke() throws IOException {
        stavkeOLista = FXCollections.observableArrayList();
        Path path = Paths.get(fajl);
        BufferedReader br = Files.newBufferedReader(path);

        String red;
        try {
            while ((red = br.readLine()) != null) {
                String[] nizRed = red.split("\t");
                String firstName = nizRed[0];
                String lastName = nizRed[1];
                String phoneNumber = nizRed[2];
                String eMail = nizRed[3];
                Kontakti kontakti = new Kontakti(firstName, lastName, phoneNumber, eMail);
                stavkeOLista.add(kontakti);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void sacuvajPodatke() throws IOException {
        Path path = Paths.get(fajl);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<Kontakti> iterator = stavkeOLista.listIterator();
            while (iterator.hasNext()) {
                Kontakti kontakti = iterator.next();
                bw.write(String.format("%s\t%s\t%s\t%s",
                        kontakti.getFirstName(),
                        kontakti.getLastName(),
                        kontakti.getPhoneNumber(),
                        kontakti.getEMail()));
                bw.newLine();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }
}
