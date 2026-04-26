package com.pcwk.theme.service;

import java.util.List;

import Dao.ThemeDao;
import VO.ThemeVO;

public class ThemeService {
	
	ThemeDao themeDao;
	
	public ThemeService() {
		themeDao = new ThemeDao("themes.csv");
	}
	
	public List<ThemeVO> findAll() {
	
		return themeDao.findAll();
		
	}
	
	public List<ThemeVO> searchByCondition(ThemeVO param) {
		
		List<ThemeVO> result = themeDao.findByCondition(param);
		
		return result;
	}
}
