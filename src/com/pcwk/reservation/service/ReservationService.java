/**
 * 
 */
package com.pcwk.reservation.service;

import java.util.List;

import com.pcwk.reservation.dao.ReservationDao;
import com.pcwk.reservation.domain.ReservationVO;

/**
 * 
 */
public class ReservationService {

	ReservationDao dao;
	
	/**
	 * 기본 생성자,  ReservationDao 인스턴스 생성
	 */
	public ReservationService() {	
		dao = new ReservationDao(ReservationDao.RESERVATION_DATA);
	}
	
	/**
	 * 예약 메서드
	 * @param param
	 * @return flag
	 */
	public int saveReservation(ReservationVO param) {
		
		int flag = 0;
		
		// 예약할때 날짜 테마 예약된거 있는지 확인
		if(true == isExistsDateandThemename(param))
		{
			// 이미 예약된 테마 flag 2반환
			flag = 2;
			return flag;
		}
		
		
		// 여기로 넘어왔으면 예약 안된 테마라 예약가능
		flag = dao.doSave(param); // 예약
		
		if(flag == 1) // 예약 성공 flag = 1
		{
			// 예약된 데이터 CSV파일에 새로작성
			dao.writerReservation(ReservationDao.RESERVATION_DATA);
		}
		
		// flag 1반환
		return flag;
	}
	
	/**
	 * 예약삭제 메서드
	 * @param param
	 * @return flag
	 */
	public int deleteReservation(ReservationVO param) {
		
		int flag = 0;
		
		// 중복 번호 있는지 확인
		if(false == isExistsMobileNumber(param))
		{	
			//false = 중복번호 없음
			flag = 2;
			return flag;
		}
		
		// 중복된 번호 있으니까 삭제 실행
		flag = dao.doDelete(param);
		
		if(flag == 1) // 삭제완료 flag = 1
		{
			// 삭제후 CSV파일에 새로작성
			dao.writerReservation(ReservationDao.RESERVATION_DATA);
		}
		
		return flag; // flag반환
	}
	
	public int updateReservation(ReservationVO param) {
		
		int flag = 0;
		
		// 중복 번호 있는지 확인
		if(false == isExistsMobileNumber(param))
		{	
			//false = 중복번호 없음
			flag = 2;
			return flag;
		}
		
		// 중복 번호 있으니까 수정
		flag = dao.doUpdate(param);
		
		if(flag == 3)
		{
			dao.writerReservation(ReservationDao.RESERVATION_DATA);
		}
		
		return flag; // flag반환
		
	}
	
	/**
	 * 예약 정보 조회
	 * @param param
	 */
	public void selectReservation(ReservationVO param) {
		dao.doSelectOne(param);
	}
	

	/**
	 * 모바일넘버 중복 존재 확인
	 * @param param
	 * @return true = 번호 존재, false = 번호 없음
	 */
	public boolean isExistsMobileNumber(ReservationVO param) {
		
		//CSV파일에서 읽어온 데이터 담기
		List<ReservationVO> list = dao.getAllReservation();
		
		for(ReservationVO vo : list)
		{
			if(vo.getMobileNumber().equals(param.getMobileNumber()))
			{	// true면 중복 번호 있다는뜻
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 날짜, 테마 중복 존재 확인
	 * @param param
	 * @return true = 존재, false = 없음
	 */
	public boolean isExistsDateandThemename(ReservationVO param) {
		
		//csv파일에서 읽어온 데이터 담기
		List<ReservationVO> list = dao.getAllReservation();
		
		for(ReservationVO vo : list)
		{
			// 예약된 테마인지 확인
			if(vo.getDate().equals(param.getDate()) &&
			   vo.getTheme().equals(param.getTheme()))
			{
				// true = 예약된테마
				return true;
			}
		}
		// 예약없음
		return false;
	}
}
