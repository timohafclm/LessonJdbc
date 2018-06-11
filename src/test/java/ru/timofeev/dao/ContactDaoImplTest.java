package ru.timofeev.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.timofeev.model.Contact;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

public class ContactDaoImplTest {
    private static ContactDaoImpl contactDao;
    private static GenericXmlApplicationContext ctx;

    @BeforeClass
    public static void setUp() {
        if (Objects.isNull(ctx)) {
            ctx = new GenericXmlApplicationContext();
            ctx.load("classpath:app-context.xml");
            ctx.refresh();
            contactDao = ctx.getBean("contactDao", ContactDaoImpl.class);
        }
    }

    @Test
    public void findFirstNameById() {
        String firstName = contactDao.findFirstNameById(1L);
        assertTrue(firstName.equals("Chris"));
    }

    @Test
    public void findLastNameById() {
        String lastName = contactDao.findLastNameById(1L);
        assertTrue(lastName.equals("Schaefer"));
    }

    @Test
    public void findAll() {
        List<Contact> result = contactDao.findAll();
        assertTrue(result.size() == 3);
    }

    @Test
    public void findByFirstName() {
        Contact contact = contactDao.findByFirstName("Chris").get(0);
        assertTrue(contact.getFirstName().equals("Chris"));
        assertTrue(contact.getLastName().equals("Schaefer"));
    }

    @Test
    public void update() {
        // given
        Contact newContact = new Contact();
        newContact.setId(1L);
        newContact.setFirstName("Tailer");
        newContact.setLastName("Derden");
        newContact.setBirthDate(new Date(
                (new GregorianCalendar(1990, 11, 27)).getTime().getTime()));
        String oldContact = contactDao.findFirstNameById(1L);
        assertTrue(oldContact.equals("Chris"));
        // when
        contactDao.update(newContact);
        // then
        String newContactName = contactDao.findFirstNameById(1L);
        assertTrue(newContactName.equals("Tailer"));
    }
}
