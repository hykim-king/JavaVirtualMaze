/**
 * 
 */
package com.pcwk.reservation.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pcwk.cmn.PLogger;
import com.pcwk.cmn.WorkDiv;
import com.pcwk.reservation.domain.ReservationVO;

/**
 * 
 */
public class ReservationDao implements WorkDiv<ReservationVO>, PLogger {

	// 예약 정보 파일 경로
	private static final String RESERVATION_DATA = "data/RESERVATION_DATA.csv";

	// ReservationVO 리스트
	private List<ReservationVO> reservation = new ArrayList<ReservationVO>();

	/**
	 * 기본 생성자
	 */
	public ReservationDao() {
		this(RESERVATION_DATA);
	}

	public ReservationDao(String path) {
		// 기존 파일 내용 날라가지 않게 읽어오기
		getReadReservation(path);
	}

	/**
	 * Reservation.CSV파일 읽기
	 * 
	 * @param path
	 * @return
	 */
	public void getReadReservation(String path) {

		int count = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = "";

			while ((line = br.readLine()) != null) {
				count++;
				String[] strArray = line.split(",");
				
				if (strArray.length == 4) {
					ReservationVO member = new ReservationVO(strArray[0], strArray[1], strArray[2], strArray[3]);
					reservation.add(member);
				}
			}

		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException {}", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("IOException {}", e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Exception {}", e);
		}

	}

	/**
	 * Reservation.CSV파일 쓰기
	 * 
	 * @param path
	 */
	public void writerReservation() {
		int count = 0;

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESERVATION_DATA))) {
			for (ReservationVO vo : reservation) {
				count++;
				bw.write(vo.toCsv());
				bw.newLine();
			}
		} catch (IOException e) {
			log.error("IOException {}", e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Exception {}", e);
		}

	}

	/**
	 * ReservationVO 리스트 반환 메서드
	 * 
	 * @return
	 */
	public List<ReservationVO> getAllReservation() {

		return reservation;
	}

	@Override
	/**
	 * 예약 메서드
	 * 
	 * @param
	 * @return flag
	 */
	public int doSave(ReservationVO param) {
		int flag = 0;

		reservation.add(param);
		flag = 1;

		return flag;
	}

	@Override
	/**
	 * 예약 정보 수정 메서드
	 * 
	 * @param return flag = 2 : 예약 없음, 3 : 예약 수정
	 */
	public int doUpdate(ReservationVO param) {
		int flag = 0;

		int isDelete = doDelete(param);

		flag = isDelete;

		if (flag == 1) {
			int isSave = doSave(param);
			flag = isDelete + isSave + 1;
		}

		return flag;
	}

	@Override
	/**
	 * 예약 삭제 메서드
	 * 
	 * @param
	 * @return flag
	 */
	public int doDelete(ReservationVO param) {
		int flag = 0;

		Iterator<ReservationVO> iter = reservation.iterator();

		while (iter.hasNext()) {
			ReservationVO vo = iter.next();

			if (vo.getMobileNumber().equals(param.getMobileNumber())) {
				iter.remove();
				flag = 1;
				break;
			}
		}

		return flag;
	}

	@Override
	/**
	 * 예약 내역 조회(모바일번호)
	 * 
	 * @param
	 * @return ArrayList<ReservationVO>
	 */
	public ArrayList<ReservationVO> doSelectOne(ReservationVO param) {

		ArrayList<ReservationVO> list = new ArrayList<ReservationVO>();

		for (ReservationVO vo : reservation) {
			if (vo.getMobileNumber().equals(param.getMobileNumber())) {
				list.add(vo);
			}
		}

		return list;
	}

}
