package com.pcwk.theme.service;

import java.util.List;

import com.pcwk.theme.domain.ThemeVO;

public interface ThemeService {

	
	public List<ThemeVO> findAll();
	
	public List<ThemeVO> searchByCondition(ThemeVO param);
}
