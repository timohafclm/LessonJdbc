package ru.timofeev.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.timofeev.model.Contact;

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
}
