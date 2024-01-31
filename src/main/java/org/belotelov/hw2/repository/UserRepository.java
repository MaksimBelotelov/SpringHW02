package org.belotelov.hw2.repository;

import org.belotelov.hw2.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    RowMapper<User> userRowMapper = (r, i) -> {
        User rowObject = new User();
        rowObject.setId(r.getInt("id"));
        rowObject.setFirstName(r.getString("firstName"));
        rowObject.setLastName(r.getString("lastName"));
        return rowObject;
    };

    public List<User> findAll() {
        String sql = "SELECT * FROM userTable";

        return jdbc.query(sql, userRowMapper);
    }

    public User save(User user) {
        String sql = "INSERT INTO userTable (firstName, lastName) VALUES ( ?, ?)";
        jdbc.update(sql, user.getFirstName(), user.getLastName());
        return user;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM userTable WHERE id=(?)";
        jdbc.update(sql, id);
    }

    public User getOne(int id) {
        String sql = "SELECT * FROM userTable WHERE id=(?)";
        return jdbc.query(sql, new Object[]{id}, userRowMapper).get(0);
    }
}
