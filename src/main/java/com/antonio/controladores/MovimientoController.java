package com.antonio.controladores;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.bytecode.buildtime.spi.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.antonio.modelo.Movimiento;
import com.antonio.servicios.MovimientoService;
 
@RestController
public class MovimientoController {
	final static Logger logger = Logger.getLogger(MovimientoController.class);
 
    @Autowired
    MovimientoService movimientoService;  //Service which will do all data retrieval/manipulation work
 
/*    @RequestMapping(value = "/movimiento/", method = RequestMethod.GET)
    public ResponseEntity<List<Movimiento>>listAllMovimientos() {
        List<Movimiento> movimientos = movimientoService.findAllMovimientos();
        if(movimientos.isEmpty()){
            return new ResponseEntity<List<Movimiento>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Movimiento>>(movimientos, HttpStatus.OK);
    }
    */
    @RequestMapping(value = "/movimientopage/", method = RequestMethod.GET)
    
    public ResponseEntity <Map <String, Object>> listPageMovimientos(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "50") int size,
            @RequestParam(value = "columnSort", required = false, defaultValue = "Id") String columnSort,
            @RequestParam(value = "sentido", required = false, defaultValue = "asc") String sentido
    		) {

    	Map<String, Object> pageMovimientos = movimientoService.findPageMovimientos(page, size, columnSort, sentido);
        if(pageMovimientos == null){
            return new ResponseEntity<Map <String, Object>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        } else { 
        	return new ResponseEntity<Map <String, Object>>(pageMovimientos, HttpStatus.OK);
          }      
        }
    
    @RequestMapping(value = "/movimientocount/", method = RequestMethod.GET)
    
    public ResponseEntity <Long> countMovimientos() { 
    	
    	logger.debug("Solicitada consulta del numero de registros del fichero de movimientos"); 

    	Long rows = movimientoService.countMovimientos();
        if(rows == null){
            return new ResponseEntity <Long>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        } else { 
        	return new ResponseEntity <Long>(rows, HttpStatus.OK);
          }      
        }
    
    
}