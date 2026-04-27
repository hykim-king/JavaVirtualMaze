/**
 * 
 */
package com.pcwk.cmn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 로거 인터페이스
 */
public interface PLogger {
	static final Logger log = LogManager.getLogger(PLogger.class);
}
