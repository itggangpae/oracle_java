package oracle_java;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class ProcedureMain {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("메시지를 입력:");
		String message = "";
		try {
			message = in.nextLine();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "scott";
		String pass = "tiger";
		String query1 = "{call MyProc(?)}";

		try {
			conn = DriverManager.getConnection(url, id, pass);
			CallableStatement call = conn.prepareCall(query1);
			call.setString(1, message);
			call.executeQuery();
			call.close();
			conn.close();
			in.close();
			System.out.println("프로시저 실행 성공");
		} catch (Exception e) {
			System.out.println("실패:" + e.getMessage());
		}
	}
}
