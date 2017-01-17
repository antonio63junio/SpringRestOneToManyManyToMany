
 package com.antonio.controladores;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.antonio.dto.FieldErrorDTO;
import com.antonio.dto.ValidationErrorDTO;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class RestErrorHandler {

    //private static final Logger logger = LoggerFactory.getLogger(RestErrorHandler.class);
    final static Logger logger = Logger.getLogger(RestErrorHandler.class);

	@Autowired
    private MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        logger.debug("Gestion validacion de errores");

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }

        return dto;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

        //If a message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }

        return localizedErrorMessage;
    }

    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    
    public FieldErrorDTO gesErrorHibernate(DataIntegrityViolationException e) {

       logger.error("gesErrorHibernate: " + 
 	               " \n e.toString(): " + e.toString() + " *** mas informacion a continuacion .." );
       
       logger.error("gesErrorHibernate: " + 
  	               ",\n e.getMessage(): " + e.getMessage() + 
  	               ",\n e.getCause(): " + e.getCause() +
                   ",\n e.getCause().getMessage(): " + e.getCause().getMessage() +
                   ",\n e.getMostSpecificCause(): " + e.getMostSpecificCause() + 
                   ",\n e.getMostSpecificCause().toString(): " + e.getMostSpecificCause().toString() + 
                   ",\n e.getMostSpecificCause().getMessage(): " + e.getMostSpecificCause().getMessage() 
  			  );
    	
        String result = e.getMostSpecificCause().toString();
        FieldErrorDTO errorDTO = new FieldErrorDTO ("ssn/id", result);

        return errorDTO;
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    
    public FieldErrorDTO gesError(Exception e) {

        logger.error("gesError: " 
        		    + " \n e.toString(): " + e.toString()  
  	                + ",\n e.getMessage(): " + e.getMessage() 
/* 	                + ",\n e.getCause(): " + e.getCause() 
                    + ",\n e.getCause().getMessage(): " + e.getCause().getMessage()*/
                  );

        String result; 
        if (e.getCause() != null) result = e.getCause().getMessage();
        	else result = e.getMessage();
        
        FieldErrorDTO errorDTO = new FieldErrorDTO ("ssn/id", result);
        
        return errorDTO;
    }
}
