package com.example.microgram.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public abstract class BaseDao {
    protected final JdbcTemplate jdbcTemplate;

    public abstract void createTable();

}
