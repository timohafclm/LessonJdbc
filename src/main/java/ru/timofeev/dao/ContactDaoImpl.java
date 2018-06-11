package ru.timofeev.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.timofeev.model.Contact;
import ru.timofeev.model.ContactTelDetail;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("contactDao")
public class ContactDaoImpl implements ContactDao {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SelectContactByFirstName selectContactByFirstName;
    private UpdateContact updateContact;

    public ContactDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        selectContactByFirstName = new SelectContactByFirstName(dataSource);
        updateContact = new UpdateContact(dataSource);
    }

    public String findFirstNameById(Long id) {
        String sql = "select first_name from contact where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
    }

    public String findLastNameById(Long id) {
        String sql = "select last_name from contact where id = :contactId";
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("contactId", id);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, String.class);
    }

    public List<Contact> findAll() {
        String sql = "select id, first_name, last_name, birth_date from contact";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            Contact contact = new Contact();
            contact.setId(rs.getLong("id"));
            contact.setFirstName(rs.getString("first_name"));
            contact.setLastName(rs.getString("last_name"));
            contact.setBirthDate(rs.getDate("birth_date"));
            return contact;
        });
    }

    public List<Contact> findAllWithDetail() {
        String sql = "select c.id, c.first_name, c.last_name, c.birth_date" +
                ", t.id as contact_tel_id, t.tel_type, t.tel_number from contact c" +
                " left join contact_tel_detail t on c.id = t.contact.id";
        return namedParameterJdbcTemplate.query(sql, (ResultSet rs) -> {
            Map<Long, Contact> map = new HashMap<>();
            Contact contact;

            while (rs.next()) {
                Long id = rs.getLong("id");
                contact = map.get(id);
                if (contact == null) {
                    contact = new Contact();
                    contact.setId(id);
                    contact.setFirstName(rs.getString("first_name"));
                    contact.setLastName(rs.getString("last_name"));
                    contact.setBirthDate(rs.getDate("birth_date"));
                    contact.setContactTelDetails(new ArrayList<>());
                    map.put(id, contact);
                }
                Long contactTelDetailId = rs.getLong("contact_tel_id");
                if (contactTelDetailId > 0) {
                    ContactTelDetail contactTelDetail = new ContactTelDetail();
                    contactTelDetail.setId(contactTelDetailId);
                    contactTelDetail.setContactId(id);
                    contactTelDetail.setTelType(rs.getString("tel_type"));
                    contactTelDetail.setTelNumber(rs.getString("tel_number"));
                    contact.getContactTelDetails().add(contactTelDetail);
                }
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public List<Contact> findByFirstName(String firstName) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("first_name", firstName);
        return selectContactByFirstName.executeByNamedParam(paramMap);
    }

    public void insert(Contact contact) {

    }

    public void update(Contact contact) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("first_name", contact.getFirstName());
        paramMap.put("last_name", contact.getLastName());
        paramMap.put("birth_date", contact.getBirthDate());
        paramMap.put("id", contact.getId());
        updateContact.updateByNamedParam(paramMap);
    }

    public void delete(Long contactId) {

    }
}
