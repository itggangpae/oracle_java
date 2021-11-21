package oracle_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GoodDAOImpl implements GoodDAO {
	// 싱글톤 클래스를 만드는 코드
	private GoodDAOImpl() {}

	private static GoodDAOImpl obj;

	public static GoodDAOImpl getInstance() {
		if (obj == null)
			obj = new GoodDAOImpl();
		return obj;
	}

	// 데이터베이스 연동 메서드에서 사용할 변수 선언
	private Connection con;
	private PreparedStatement pstmt;

	// 데이터베이스 연결 메서드
	private boolean connect() {
		boolean result = false;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//con과 pstmt 연결 해제하는 메서드
	private void close() {
		try {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Goods 테이블의 모든 데이터를 읽어서 리턴하는 메서드
	public List<Good> allSelect() {
		// 데이터를 저장해서 리턴할 인스턴스 생성
		List<Good> list = new ArrayList<Good>();
		// select 구문의 결과를 저장할 변수 생성
		ResultSet rs = null;
		try {
			// 연결에 성공하면
			if (connect()) {
				// SQL을 실행할 수 있는 인스턴스 생성
				pstmt = con.prepareStatement("select * from goods");
				// select 구문의 결과를 rs에 저장
				rs = pstmt.executeQuery();
				// 검색된 데이터를 읽어서 list에 저장
				if (rs.next()) {
					do {
						Good good = new Good();
						good.setCode(rs.getString("code"));
						good.setName(rs.getString("name"));
						good.setManufacture(rs.getString("manufacture"));
						good.setPrice(rs.getInt("price"));
						list.add(good);
					} while (rs.next());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				close();
			} catch (Exception e) {}
		}
		return list;
	}

	// code(Primary Key)를 받아서 goods 테이블에서 데이터를 검색한 후 리턴하는 메서드
	// 데이터는 없거나 1개만 나올 수 있습니다.
	// 없을 때는 null을 리턴하고 있을 때는 1개를 리턴
	// 목록에서 제목을 눌렀을 때 상세보기를 하거나 회원정보 수정을 눌렀을 때 회원정보 보여주는 화면을 만들 때 사용
	public Good getGood(String code) {
		// 검색된 정보를 저장해서 리턴하기 위한 변수
		Good good = null;
		// 검색된 데이터 정보를 저장할 변수
		ResultSet rs = null;

		try{
			//데이터베이스 연결
			if(connect()){
				//SQL 실행 인스턴스 생성
				pstmt = con.prepareStatement(
						"select * from goods where trim(code) = ?");

				//물음표에 데이터 바인딩
				pstmt.setString(1, code);
				//select 구문을 실행해서 결과를 rs에 저장
				rs = pstmt.executeQuery();
				//검색된 데이터는 1개가 넘지 않습니다.
				if(rs.next()){
					good = new Good();
					good.setCode(rs.getString("code").trim());
					good.setName(rs.getString("name"));
					good.setManufacture(rs.getString("manufacture"));
					good.setPrice(rs.getInt("price"));
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs != null)
					rs.close();
				close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return good;
	}

	//하나의 데이터를 삽입하는 메서드
	public Boolean insertGood(Good good){
		Boolean result = false;
		try{
			if(connect()){
				pstmt = con.prepareStatement(
						"insert into goods(" + 
								"code, name, manufacture,price)"+
						" values(?,?,?,?)");
				pstmt.setString(1, good.getCode());
				pstmt.setString(2, good.getName());
				pstmt.setString(3, good.getManufacture());
				pstmt.setInt(4, good.getPrice());
				int rownum = pstmt.executeUpdate();
				if(rownum > 0)
					result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return result;
	}


	public Boolean updateGood(Good good) {
		Boolean result = false;
		try{
			if(connect()){
				pstmt = con.prepareStatement(	"update goods " + 
						"set name = ?, manufacture = ?, price = ? "+
						"where trim(code) = ?");

				pstmt.setString(1, good.getName());
				pstmt.setString(2, good.getManufacture());
				pstmt.setInt(3, good.getPrice());
				pstmt.setString(4, good.getCode());
				int rownum = pstmt.executeUpdate();
				if(rownum > 0)
					result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return result;
	}

	@Override
	public Boolean deleteGood(String code) {
		Boolean result = false;
		try{
			if(connect()){
				pstmt = con.prepareStatement(
						"delete from goods " + 
						"where trim(code) = ?");

				pstmt.setString(1, code);

				int rownum = pstmt.executeUpdate();
				if(rownum > 0)
					result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close();
		}
		return result;
	}
}