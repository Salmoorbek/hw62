package com.example.microgram.dao;

import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component

public class UserDao extends BaseDao{
    private final PasswordEncoder encoder;

    public UserDao(JdbcTemplate jdbcTemplate, PasswordEncoder encoder) {
        super(jdbcTemplate);
        this.encoder = encoder;
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE if not exists users" +
                "(userId SERIAL PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "accName TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "enabled boolean NOT NULL, " +
                "subscribers INTEGER, " +
                "publications INTEGER, " +
                "subscriptions INTEGER," +
                "role text)");
    }
    public void saveAll(List<User> users) {
        String sql = "INSERT INTO users (name, email, accName, password, enabled, subscribers, publications, subscriptions, role ) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1,users.get(i).getName());
                ps.setString(2,users.get(i).getEmail());
                ps.setString(3,users.get(i).getAccName());
                ps.setString(4, encoder.encode(users.get(i).getPassword()));
                ps.setBoolean(5,users.get(i).getEnabled());
                ps.setInt(6,users.get(i).getCountFollower());
                ps.setInt(7,users.get(i).getCountPublication());
                ps.setInt(8,users.get(i).getCountSubscription());
                ps.setString(9, "user");
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }

    public void deleteAll() {
        String sql = "delete from users";
        jdbcTemplate.update(sql);
    }

    public void alertSequenceUser() {
        String sql = "alter sequence users_userid_seq restart with 1";
        jdbcTemplate.update(sql);
    }

    public User findUsersByName(String name) {
        String query = "select * from \"users\"\n" +
                "         where name = ?";
        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), name);
    }

    public User findUserByAccountName(String accName) {
        String query = "select * from \"users\" where accName = ?";
        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), accName);
    }

    public User findUserByEmail(String email) {
        String query = "select * from \"users\" where email =?";
        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), email);
    }

    public String isRegisteredEmail(String email) {
        String result = String.valueOf(findUserByEmail(email));
        if (!result.isEmpty()) {
            return "Пользователь есть в системе";
        } else return "Пользователя нету в системе";
    }

    public User login(String accname, String password) {
        String sql = "select * from users where accname = ? and password = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), accname, password);
    }

    public void register(User user) {
        String sql = "insert into users (name, email,accName, password, enabled, subscribers, publications, subscriptions, role ) " +
                "values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, "user.getAccName()");
            ps.setString(4, this.encoder.encode(user.getPassword()));
            ps.setBoolean(5, Boolean.TRUE);
            ps.setInt(6, 0);
            ps.setInt(7, 0);
            ps.setInt(8, 0);
            ps.setString(9, "user");
            return ps;
        });
    }

    public List<UserDto> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class));
    }
}
