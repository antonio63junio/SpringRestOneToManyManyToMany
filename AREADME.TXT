
Proyecto con las siguientes caracteristicas:

1) Proyecto spring rest + hibernate + angularjs que no utiliza xml, solo anotaciones.

1) Crea base de datos MySQL a partir de los modelos definidos. 

2) Es un ejemplo de manejo de una relacion OneToMany ManyToOne (relacion bidireccional en el que un 
   telefono solo puede pertenecer a un empleado), 
   entre employee y telefonos, con anotaciones. 
   Un empleado puede tener varios telefonos. Un telefono pertenece solo a una persona
   
       Employee                    Phone
              1 --------------- 0 .. n 

CREATE TABLE `employee` (
  `EMPLOYEE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `JOINING_DATE` date NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `SALARY` decimal(19,2) NOT NULL,
  `SSN` varchar(255) NOT NULL,
  PRIMARY KEY (`EMPLOYEE_ID`),
  UNIQUE KEY `UK_p136ambt19xg166m0jf37p7wn` (`SSN`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;


CREATE TABLE `phone` (
  `PHONE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONTACTNUMBER` varchar(100) NOT NULL,
  `PHONETYPE` varchar(255) DEFAULT NULL,
  `EMPLOYEE_ID` int(11) NOT NULL,
  PRIMARY KEY (`PHONE_ID`),
  UNIQUE KEY `UK_2xk62p1a6dq0yefbb20x4o31v` (`CONTACTNUMBER`),
  KEY `FK_efbqtb8oaq0rcuywp81d215wd` (`EMPLOYEE_ID`),
  CONSTRAINT `FK_efbqtb8oaq0rcuywp81d215wd` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employee` (`EMPLOYEE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
SELECT * FROM onetomany4.phone;

-------------------------------------------------------------------------------

Se han encontrado las siguientes dificultades:

1) En la consulta de empoyees:

Hemos conseguido la consulta de employees estableciendo el siguiente escenario:

Employee:
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy= "employee")
    private List<Phone> phoneNumbers;
    

Phone:
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
	@JoinColumn(name = "EMPLOYEE_ID", nullable=false)
	//@JsonBackReference
	private Employee employee;
	
	
Carateristicas: 
- No permite baja de empleado si tiene algún telefono.
- Si eliminamos todos los telefonos de un empleado, éste no se elimina automáticamente.


 
 

