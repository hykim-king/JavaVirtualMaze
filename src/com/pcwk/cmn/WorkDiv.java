/**
 * 파일명: WorkDiv.java <br>
 * 설명 : DAO표준 
 * 작성자: user  <br>
 * 생성일: 2026-04-23 <br>
 */
package com.pcwk.cmn;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO CRUD메서드 정의
 */
public interface WorkDiv<T> {

	/**
	 * 예약 등록을 수행한다.
	 * 화면에서 입력을 받아 서비스에 전달하고 결과를 출력한다.
	 * @param param
	 * @return 1:성공,0:실패,2:예약존재
	 */
	int doSave(T param);

	/**
	 * 예약 수정을 수행한다.
	 * 수정할 회원 정보를 입력받아 서비스에 전달한다.
	 * @param param
	 * @return 1:성공,0:실패
	 */
	int doUpdate(T param);

	/**
	 * 예약 삭제를 수행한다.
	 * 회원ID를 입력받아 삭제 처리 후 결과를 출력한다.
	 * @param param
	 * @return 1:성공,0:실패
	 */
	int doDelete(T param);

	/**
	 * 예약 단건 조회를 수행한다.
	 * 전화번호를 기준으로 예약 정보를 조회하여 화면에 출력한다.
	 * @param param
	 * @return DTO
	 */
	ArrayList<T> doSelectOne(T param);

}