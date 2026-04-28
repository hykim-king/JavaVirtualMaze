package com.pcwk.reservation.service;

import java.util.ArrayList;

import com.pcwk.reservation.domain.ReservationVO;

public interface ReservationService {

	public int saveReservation(ReservationVO param);
	
	public int deleteReservation(ReservationVO param);
	
	public int updateReservation(ReservationVO param, ReservationVO updateParam);
	
	public ArrayList<ReservationVO> selectReservation(ReservationVO param);
	
	public boolean isExistsMobileNumber(ReservationVO param);
	
	public boolean isExistsDateandThemename(ReservationVO param);
	
}
