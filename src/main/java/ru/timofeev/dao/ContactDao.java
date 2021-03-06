package ru.timofeev.dao;

import ru.timofeev.model.Contact;

import java.util.List;

public interface ContactDao {

    String findFirstNameById(Long id);

    String findLastNameById(Long id);

    List<Contact> findAll();

    List<Contact> findAllWithDetail();

    List<Contact> findByFirstName(String firstName);

    long insert(Contact contact);

    void insertWithDetail(Contact contact);

    void update(Contact contact);

    void delete(Long contactId);
}
