package com.example.microgram.dao;

import com.example.microgram.entity.Publication;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class PublicationDao extends BaseDao{
    public PublicationDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }
    private Connection conn;

    @Override
    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE if not exists publications (" +
                "publication_id SERIAL PRIMARY KEY, " +
                "user_id INTEGER, " +
                "image TEXT, " +
                "description TEXT, " +
                "publication_date TEXT, " +
                "FOREIGN KEY (user_id) REFERENCES users(userId))");

    }
    public void saveAll(List<Publication> publications) {
        String sql = "INSERT INTO publications (user_id, image, description, publication_date)" +
                "VALUES (?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, publications.get(i).getUserID());
                ps.setString(2, publications.get(i).getImg());
                ps.setString(3, publications.get(i).getDescription());
                ps.setString(4, String.valueOf(publications.get(i).getTimeOfPublication()));
            }

            @Override
            public int getBatchSize() {
                return publications.size();
            }
        });
    }

    public void deleteAll() {
        String sql = "delete from publications";
        jdbcTemplate.update(sql);
    }

    public void alertSequencePublication() {
        String sql = "alter sequence publications_publication_id_seq restart with 1";
        jdbcTemplate.update(sql);
    }

    public List<Publication> getPublicationsForUser(int userId) {
        String query = "select * from \"publications\" " +
                "where user_id != ?;";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Publication.class), userId);
    }

    public List<Publication> getPublicationsForUserBySubscriptions(int userId) {
        String query = "select\n" +
                "    p.image,\n" +
                "    p.description,\n" +
                "    p.publication_date,\n" +
                "    s.subscribedto\n" +
                "    from \"publications\" as p\n" +
                "inner join \"users\" as u\n" +
                "on u.userid = p.user_id\n" +
                "inner join \"subscriptions\" as s\n" +
                "on s.subscribedto = u.userid\n" +
                "where u.userid = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Publication.class), userId);
    }
    public void save(Publication publication) {
        String sql = "insert into publications (image, description, publication_date) " +
                "values(?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, publication.getImg());
            ps.setString(2, publication.getDescription());
            ps.setString(3, String.valueOf(publication.getTimeOfPublication()));
            return ps;
        });
    }

    public void deleteById(Long publicationId) {
        String sql = "delete from publications " +
                "where publicationid = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, publicationId);
            return ps;
        });
    }

    public List<Publication> getAllPubs() {
        String sql = "SELECT * FROM publications";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Publication.class));
    }

    public int findUserByUserEmail(String username) {
        String sql = "SELECT userid FROM users " +
                " WHERE email = ? ";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }
}
