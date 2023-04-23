package com.example.microgram.dao;

import com.example.microgram.dto.SubscriptionDto;
import com.example.microgram.entity.Subscription;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class SubscriptionDao extends BaseDao{

    public SubscriptionDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE if not exists subscriptions (" +
                "subscriptionId SERIAL primary key," +
                "subscribes INTEGER, " +
                "subscribedTo INTEGER, " +
                "subscriptionDate TEXT," +
                "FOREIGN KEY (subscribes) REFERENCES users(userId), " +
                "FOREIGN KEY (subscribedTo) REFERENCES users(userId))");
    }
    public void saveAll(List<Subscription> subscriptions) {
        String sql = "INSERT INTO subscriptions (subscribes, subscribedTo, subscriptionDate) " +
                "VALUES (?,?,?) " ;
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1,subscriptions.get(i).getSubscribes());
                ps.setInt(2,subscriptions.get(i).getSubscribedTo());
                ps.setString(3, String.valueOf(subscriptions.get(i).getSubscribeTime()));
            }

            @Override
            public int getBatchSize() {
                return subscriptions.size();
            }
        });
    }

    public void deleteAll() {
        String sql = "delete from subscriptions";
        jdbcTemplate.update(sql);
    }

    public void alterSequenceSubs() {
        String sql = "alter sequence subscriptions_subscriptionid_seq restart with 1";
        jdbcTemplate.update(sql);
    }

    public void save(Subscription subscription) {
        String sql = "INSERT INTO subscriptions (subscribes, subscribedTo, subscriptionDate) " +
                "VALUES (?,?,?) ";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, subscription.getSubscribes());
            ps.setInt(2, subscription.getSubscribedTo());
            ps.setTimestamp(3, Timestamp.valueOf(subscription.getSubscribeTime()));
            return ps;
        });
    }

    public List<SubscriptionDto> getAllSubs() {
        String sql = "SELECT * FROM subscriptions";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SubscriptionDto.class));
    }
}
