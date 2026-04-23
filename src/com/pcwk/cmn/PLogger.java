/**
 * 파일명: PLogger.java <br>
 * 설명 :   <br>
 * 작성자: Hee  <br>
 * 생성일: 2026-04-24 <br>
 */
package com.pcwk.cmn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public interface PLogger {
	static final Logger log = LogManager.getLogger(PLogger.class);
}
