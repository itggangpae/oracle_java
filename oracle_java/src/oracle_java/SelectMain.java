package oracle_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelectMain {

	static final String sid = "xe";
	static final String id = "scott";
	static final String pass = "tiger";

	public static void main(String[] args) {

		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		String query = "SELECT * FROM SAMPLE";
		try {
			// 접속할 데이타베이스의 URL을 생성
			String url = "jdbc:oracle:thin:@localhost:1521:" + sid;
			// 접속
			connection = DriverManager.getConnection(url, id, pass);
			// sql을 실행 시킬 수 있는 Statement 인스턴스 생성
			stmt = connection.createStatement();
			// select 구문을 실행시키고 그 결과를 rs에 저장
			rs = stmt.executeQuery(query);
			// 데이터가 있다면
			if (rs.next()) {
				do {
					// 첫번째 컬럼의 값은 정수로 가져오고 두번째 컬럼의 값은 문자열로 가져와서 출력
					System.out.println(rs.getInt(1) + ":" + rs.getString(2));
				} while (rs.next());
			}
			// 읽어온 데이터가 없다면
			else {
				System.out.println("읽은 데이터가 없습니다.");
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("에러:" + e.getMessage());
		}
	}
}

