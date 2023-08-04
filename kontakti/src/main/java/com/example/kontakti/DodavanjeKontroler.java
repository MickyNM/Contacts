package com.example.kontakti;

import com.example.kontakti.podaci.Kontakti;
import com.example.kontakti.podaci.PodaciSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DodavanjeKontroler {
    @FXML
    private TextField firstNameUnos;
    @FXML
    private TextField lastNameUnos;
    @FXML
    private TextField phoneNumberUnos;
    @FXML
    private TextField eMailUnos;

    public Kontakti ubaciStavku() {
        String firstName = firstNameUnos.getText().trim();
        String lastName = lastNameUnos.getText().trim();
        String phoneNumber = phoneNumberUnos.getText();
        String eMail= eMailUnos.getText().trim();
        if (!firstName.isEmpty() && !lastName.isEmpty() && !phoneNumber.isEmpty() && !eMail.isEmpty()) {
            Kontakti kontakti = new Kontakti(firstName, lastName, phoneNumber, eMail);
            PodaciSingleton.getInstance().dodajStavku(kontakti);
            return kontakti;
        }
        return null;
    }
}




