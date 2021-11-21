package oracle_java;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class FileReadMain {
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
			// test2 테이블의 모든 데이터를 읽어오는 sql
			sql = "SELECT * FROM FILESAMPLE";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					// 숫자, 문자열, 날짜 읽어오기
					int num = rs.getInt("NUM");
					String filename = rs.getString("FILENAME");
					Timestamp uploadDate = rs.getTimestamp("UPLOADDATE");
					System.out.println(num + ":" + filename + ":" + uploadDate);
					// BLOB 가져오기
					InputStream is = rs.getBinaryStream("filecontent");
					// 가져온 데이터를 filename에 기록하기
					FileOutputStream fos = new FileOutputStream("./" + filename);

					while (true) {
						byte[] b = new byte[1024];
						int len = is.read(b);
						if (len <= 0)
							break;
						fos.write(b, 0, len);
					}
					fos.close();

				} while (rs.next());
			} else {
				System.out.println("데이터가 없습니다.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
