package ru.timofeev.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import ru.timofeev.model.Contact;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * MappingSqlQuery<T> подходит только для отображения одиночной строки на объект предметной области.
 * Для вложенного объекта по-прежнему нужно применять JdbcTemplate с ResultSetExtractor,
 * как это делается в примере метода findAllWithDetail().
 */
public class SelectContactByFirstName extends MappingSqlQuery<Contact>{
    private static final String SQL_FIND_BY_FIRST_NAME =
            "select id, first_name, last_name, birth_date from contact where first_name = :first_name";

    public SelectContactByFirstName(DataSource dataSource) {
        super(dataSource, SQL_FIND_BY_FIRST_NAME);
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
    }

    @Override
    protected Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getLong("id"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setBirthDate(rs.getDate("birth_date"));
        return contact;
    }
}
