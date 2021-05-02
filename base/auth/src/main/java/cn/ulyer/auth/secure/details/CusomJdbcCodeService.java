package cn.ulyer.auth.secure.details;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
public class CusomJdbcCodeService extends JdbcAuthorizationCodeServices {

    private static final String DEFAULT_SELECT_STATEMENT = "select code, authentication from oauth_code where code = ?";
    private static final String DEFAULT_INSERT_STATEMENT = "insert into oauth_code (code, authentication) values (?, ?)";
    private static final String DEFAULT_DELETE_STATEMENT = "delete from oauth_code where code = ?";
    private String selectAuthenticationSql = "select code, authentication from oauth_code where code = ?";
    private String insertAuthenticationSql = "insert into oauth_code (code, authentication) values (?, ?)";
    private String deleteAuthenticationSql = "delete from oauth_code where code = ?";
    private  JdbcTemplate jdbcTemplate;



    public CusomJdbcCodeService(DataSource dataSource) {
        super(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public OAuth2Authentication remove(String code) {
        OAuth2Authentication authentication;
        try {
            authentication = this.jdbcTemplate.queryForObject(this.selectAuthenticationSql, new RowMapper<OAuth2Authentication>() {
                @SneakyThrows
                @Override
                public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
                   return (OAuth2Authentication) new ObjectInputStream(new ByteArrayInputStream(rs.getBytes("authentication"))).readObject();
                }
            }, new Object[]{code});
        } catch (EmptyResultDataAccessException var4) {
            return null;
        }

        if (authentication != null) {
            this.jdbcTemplate.update(this.deleteAuthenticationSql, new Object[]{code});
        }

        return authentication;
    }
}
