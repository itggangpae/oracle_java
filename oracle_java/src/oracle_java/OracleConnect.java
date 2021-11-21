package oracle_java;

import java.sql.Connection;
import java.sql.DriverManager;
public class OracleConnect {
	static final String sid = "xe";
	static final String id = "scott";
	static final String pw = "tiger";

	public static void main(String[] args) {
		
		System.out.println("오라클 JDBC 드라이버 로딩중...");
		//오라클 데이타베이스 서버 접속
		Connection connection = null;
		try {
			// 접속할 데이타베이스의 URL을 만든다.
			String url = "jdbc:oracle:thin:@localhost:1521:" + sid;

			// 접속한다(Login)
			connection = DriverManager.getConnection(url, id, pw);
			System.out.println("접속 / 로그인 성공");
		}
		catch (Exception e) {
			System.out.println("접속 / 로그인 실패");
			System.out.println(e.getMessage());
		}
		//연결을 종료한다.
		try {
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
