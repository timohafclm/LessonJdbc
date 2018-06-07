package ru.timofeev.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.junit.Assert.assertTrue;

public class ContactDaoImplTest {
    ContactDaoImpl contactDao;

    @Before
    public void setUp() {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:app-context.xml");
        ctx.refresh();
        contactDao = ctx.getBean("contactDao", ContactDaoImpl.class);
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
}
