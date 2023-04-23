package com.example.microgram.dao;

import com.example.microgram.entity.Comment;
import com.example.microgram.entity.Publication;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class CommentDao extends BaseDao{
    public CommentDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE if not exists comments (" +
                "comment_id SERIAL PRIMARY KEY, " +
                "user_id INTEGER," +
                "publication_id INTEGER," +
                "comment_text TEXT, " +
                "comment_date TEXT," +
                "FOREIGN KEY (publication_id) REFERENCES publications(publication_id)," +
                "FOREIGN KEY (user_id) REFERENCES users(userId))");
    }

    public void saveAll(List<Comment> comments) {
        String sql = "INSERT INTO comments (user_id, comment_text, comment_date, publication_id) " +
                "VALUES (?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, comments.get(i).getUserId());
                ps.setString(2, comments.get(i).getCommentText());
                ps.setString(3, String.valueOf(comments.get(i).getTimeOfComment()));
                ps.setInt(4, comments.get(i).getPublicationId());
            }

            @Override
            public int getBatchSize() {
                return comments.size();
            }
        });
    }

    public void deleteAll() {
        String sql = "delete from comments";
        jdbcTemplate.update(sql);
    }

    public void alterSequenceComment() {
        String sql = "alter sequence comments_comment_id_seq restart with 1";
        jdbcTemplate.update(sql);
    }

    public void addComment(Comment comment) {
        String sql = "insert into comments (user_id, publication_id, comment_text, comment_date) " +
                "values(?,?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, comment.getUserId());
            ps.setInt(2, comment.getPublicationId());
            ps.setString(3, comment.getCommentText());
            ps.setString(4, String.valueOf(comment.getTimeOfComment()));
            return ps;
        });
    }
    public void deleteById(Long publicationId, String email) {
        String sql = "DELETE FROM comments " +
                "    USING users " +
                "WHERE comments.user_id = users.userid " +
                "  AND comments.comment_id = ? " +
                "  AND users.email = ?; ";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, publicationId);
            ps.setString(2, email);
            return ps;
        });
    }
    public int checkForDelete(Long publicationId, String email) {
        String sql = "SELECT comments.comment_id " +
                "FROM comments " +
                "    INNER JOIN users on users.userid = comments.user_id " +
                "WHERE comments.comment_id = ? " +
                "  AND users.email = ? ";
        return jdbcTemplate.queryForObject(sql, Integer.class, publicationId, email);
    }

    public int findUserByUserEmail(String username) {
        String sql = "SELECT userid FROM users " +
                " WHERE email = ? ";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }
}
