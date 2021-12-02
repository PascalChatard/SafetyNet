package com.safetynet.apirest.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.safetynet.apirest.model.ExceptionMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * ExceptionHandlerCtrlAdvice, Controller Advice permettant une portee globale
 * des methodes "exception handler" de gestion des exceptions remontees par les
 * differents controleurs de l'API. exception qui indique que la reference au
 * Bean DataSrc est nulle => pas de base de donnees en memoire.
 * 
 */

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerCtrlAdvice {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	/**
	 * Gestionnaire de l'exception IndexOutOfBoundsException.
	 * 
	 * @param request   l'objet requete source le la levee de l'exception.
	 * @param exception l'objet exception leve et capture.
	 * @return un ResponseEntity contenant un ExcetionMessage et le status
	 *         INTERNAL_SERVER_ERROR.
	 * 
	 */
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<ExceptionMessage> indexOutOfBoundsExceptionHandler(HttpServletRequest request,
			IndexOutOfBoundsException exception) {

		// format le message associe a l'exception a retourner
		ExceptionMessage message = ExceptionMessage.builder().date(LocalDateTime.now().format(formatter))
				.status(Integer.valueOf(HttpStatus.BAD_REQUEST.value()).toString())
				.error(HttpStatus.BAD_REQUEST.getReasonPhrase()).path(request.getRequestURI().toString())
				.exception(exception.getClass().getName()).message(exception.getMessage()).build();

		log.error("\"IndexOutOfBoundsException\" sur Requete HTTP {}, Uri: {}", request.getMethod(),
				request.getRequestURI());

		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Gestionnaire de l'exception NullPointerException.
	 * 
	 * @param request   l'objet requete source le la levee de l'exception.
	 * @param exception l'objet exception leve et capture.
	 * @return un ResponseEntity contenant un ExcetionMessage et le status
	 *         INTERNAL_SERVER_ERROR.
	 * 
	 */
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ExceptionMessage> nullPointerExceptionHandler(HttpServletRequest request,
			NullPointerException exception) {

		// format le message associe a l'exception a retourner
		ExceptionMessage message = ExceptionMessage.builder().date(LocalDateTime.now().format(formatter))
				.status(Integer.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()).toString())
				.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).path(request.getRequestURI().toString())
				.exception(exception.getClass().getName()).message(exception.getMessage()).build();

		log.error("\"NullPointerException\" sur Requete HTTP {}, Uri: {}", request.getMethod(),
				request.getRequestURI());

		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Gestionnaire de l'exception NullDataSrcException.
	 * 
	 * @param request   l'objet requete source le la levee de l'exception.
	 * @param exception l'objet exception leve et capture.
	 * @return un ResponseEntity contenant un ExcetionMessage et le status HTTP
	 *         INTERNAL_SERVER_ERROR.
	 * 
	 */
	@ExceptionHandler(NullDataSrcException.class)
	public ResponseEntity<ExceptionMessage> genericExceptionHandler(HttpServletRequest request, Exception exception) {

		// format le message associe a l'exception a retourner
		ExceptionMessage message = ExceptionMessage.builder().date(LocalDateTime.now().format(formatter))
				.status(Integer.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()).toString())
				.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).path(request.getRequestURI().toString())
				.exception(exception.getClass().getName()).message(exception.getMessage()).build();

		log.error("\"exception.toString()\" sur Requete HTTP {}, Uri: {}", request.getMethod(),
				request.getRequestURI());
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
