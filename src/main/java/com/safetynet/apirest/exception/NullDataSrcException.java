package com.safetynet.apirest.exception;

/**
 * NullDataSrcException, exception qui indique que la reference au Bean DataSrc
 * est nulle => pas de base de donnees en memoire.
 * 
 */

@SuppressWarnings("serial")
public class NullDataSrcException extends RuntimeException {

	/**
	 * le message descriptf de l'exception
	 * 
	 */
	String msg;

//	private static final long serialVersionUID = 1L;
//	public NullDataSrcException(String s) {
//		super(s);
//		msg = s;
//
//	}
	public NullDataSrcException() {
		super("  *** Reference dataSrc == null ***  ");
		msg = new String("  *** Reference dataSrc == null ***  ");

	}

	public String getMsg() {
		return msg;
	}

}
