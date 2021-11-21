package oracle_java;

import java.util.List;

public interface GoodDAO {
	//테이블의 모든 데이터를 가져오는 메서드
	public List<Good> allSelect();

	//기본키를 가지고 하나의 데이터를 가져오는 메서드
	public Good getGood(String code);

	//하나의 데이터를 삽입하는 메서드
	public Boolean insertGood(Good good);
	
	//하나의 데이터를 수정하는 메서드
	public Boolean updateGood(Good good);
	
	//하나의 데이터를 삭제하는 메서드
	public Boolean deleteGood(String code);
}
