package ru.timofeev.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Contact implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private List<ContactTelDetail> contactTelDetails;

    @Override
    public String toString() {
        return "Contact - Id: " + id + ", First name: "
                + firstName + ", Last name: " + lastName + ", Birthday: " + birthDate;
    }
}
