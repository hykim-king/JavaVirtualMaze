package com.pcwk.theme.service;

import java.util.List;

import com.pcwk.theme.dao.ThemeDao;
import com.pcwk.theme.domain.ThemeVO;

public class ThemeService {
	
	ThemeDao themeDao;
	
	public ThemeService() {
		themeDao = new ThemeDao();
	}
	
	/**
	 * 테마 전체 목록을 리스트에 담아 반환
	 * @return List<ThemeVO>
	 */
	public List<ThemeVO> findAll() {
	
		return themeDao.findAll();		
	}
	
	/**
	 * 입력받은 조건검색에 부합하는 테마들을 리스트에 담아 반환
	 * @param param
	 * @return List<ThemeVO>
	 */
	public List<ThemeVO> searchByCondition(ThemeVO param) {
		
		List<ThemeVO> result = themeDao.findByCondition(param);
		
		return result;
	}
}
