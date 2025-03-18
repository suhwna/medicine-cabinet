package indie.backend;

import com.indie.BackendApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = BackendApplication.class)
public class BackendApplicationTests {

	@Test
	void contextLoads() {
		// 애플리케이션 컨텍스트가 정상적으로 로드되는지 확인
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void testDatabaseConnection() {
		// 간단한 쿼리 실행 (예: SELECT 1)
		Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
		assertNotNull(result); // 1이 반환되면 성공
		assertEquals(1, result); // 1이 맞는지 확인
	}

}
