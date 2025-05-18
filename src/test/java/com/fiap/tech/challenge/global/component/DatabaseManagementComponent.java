package com.fiap.tech.challenge.global.component;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings({"unchecked", "SqlSourceToSinkFlow"})
public class DatabaseManagementComponent {

    @Value("${spring.flyway.table}")
    private String flywaySchemaHistoryTableName;

    private final DataSource dataSource;

    @Autowired
    public DatabaseManagementComponent(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void populateDatabase(List<String> sqlFileScripts) {
        sqlFileScripts.forEach(sqlFileScript -> {
            Resource resource = new ClassPathResource(sqlFileScript);
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
            databasePopulator.execute(dataSource);
        });
    }

    public void clearDatabase() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        clearDatabase(jdbcTemplate, getTableNames(jdbcTemplate));
    }

    public void clearDatabase(JdbcTemplate jdbcTemplate, List<String> tableNames) {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE;");
        tableNames.forEach(tableName -> {
            jdbcTemplate.execute("BEGIN;");
            jdbcTemplate.execute("TRUNCATE TABLE " + tableName + " RESTART IDENTITY;");
            if (!tableName.equalsIgnoreCase(flywaySchemaHistoryTableName)) jdbcTemplate.execute("ALTER SEQUENCE " + adjustSequenceName(tableName) + " RESTART WITH 1;");
            jdbcTemplate.execute("COMMIT;");
        });
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE;");
    }

    private List<String> getTableNames(JdbcTemplate jdbcTemplate) {
        Object tableNames = jdbcTemplate.execute((ConnectionCallback<Object>) callback -> {
            ArrayList<String> names = new ArrayList<>();
            try (ResultSet tables = callback.getMetaData().getTables(null, null, "T_%", new String[]{"TABLE"})) {
                while (tables.next()) {
                    names.add(tables.getString("TABLE_NAME"));
                }
            }
            return names;
        });
        return (List<String>) tableNames;
    }

    private String adjustSequenceName(String tableName) {
        return "SQ_" + tableName.replaceFirst("T_", Strings.EMPTY);
    }
}