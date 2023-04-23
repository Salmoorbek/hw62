package com.example.microgram.dao;

import com.example.microgram.dto.LikeDto;
import com.example.microgram.entity.Like;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class LikeDao extends BaseDao{
    public LikeDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE if not exists likes (" +
                "likeId SERIAL PRIMARY KEY, " +
                "userId INTEGER NOT NULL, " +
                "publicationId INTEGER NOT NULL, " +
                "likeDate TEXT, " +
                "FOREIGN KEY (userId) REFERENCES users(userId))");
    }
    public void saveAll(List<Like> likes) {
        String sql = "INSERT INTO likes (userId, publicationId, likeDate)" +
                "VALUES (?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, likes.get(i).getUserId());
                ps.setInt(2, likes.get(i).getLikedPublicationId());
                ps.setString(3, String.valueOf(likes.get(i).getLickedTime()));
            }

            @Override
            public int getBatchSize() {
                return likes.size();
            }
        });
    }

    public void deleteAll() {
        String sql = "delete from likes";
        jdbcTemplate.update(sql);
    }

    public void alterSequenceLike() {
        String sql = "alter sequence likes_likeid_seq restart with 1";
        jdbcTemplate.update(sql);
    }

    public String userLikedPublication(int publicationId) {
        String result = String.valueOf(getPublicationsLikes(publicationId));
        if (!result.isEmpty()) {
            return "есть лайк";
        } else return "нет лайка";
    }

    public List<Like> getPublicationsLikes(int publicationId) {
        String query = "SELECT * FROM \"likes\" WHERE publicationId = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Like.class), publicationId);
    }

    public void save(Like likes) {
        String sql = "INSERT INTO likes (userId, publicationId, likeDate)" +
                "VALUES (?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, likes.getUserId());
            ps.setInt(2, likes.getLikedPublicationId());
            ps.setString(3, String.valueOf(likes.getLickedTime()));
            return ps;
        });
    }

    public List<LikeDto> getAllLikes() {
        String sql = "SELECT * FROM likes";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LikeDto.class));
    }
}
