package oracle_java;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileUploadMain {
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rownum = -1;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
			// 파일을 선택하기 위한 대화상자
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지", "jpg","jpeg","gif","png");
			fc.setFileFilter(filter);
			// 대화상자를 화면에 출력하고 누른 버튼의
			// 값을 result에 저장
			int result = fc.showOpenDialog(null);
			// 누른 버튼이 열기버튼이면
			if (result == JFileChooser.APPROVE_OPTION) {
				// 선택한 파일을 f에 저장
				File f = fc.getSelectedFile();
				String filename = f.getName();
				FileInputStream fis = new FileInputStream(f);
				// 현재 날짜 및 시간을 Date 인스턴스로 만들기
				Calendar cal = Calendar.getInstance();
				Timestamp today = new Timestamp(cal.getTimeInMillis());
				
				pstmt = con.prepareStatement("INSERT INTO FILESAMPLE VALUES(" + "id_sequence.nextval, ?,?,?)");
				pstmt.setString(1, filename);
				// blob 바인딩
				pstmt.setBinaryStream(2, fis, (int) f.length());
				// 날짜 및 시간 바인딩
				pstmt.setTimestamp(3, today);
				
				rownum = pstmt.executeUpdate();
				if (rownum > 0)
					System.out.println("삽입 성공");
				pstmt.close();
				con.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

