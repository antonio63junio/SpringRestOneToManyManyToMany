package com.antonio.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="MOVIMIENTOS")
public class Movimiento {
	
	@Override
	public String toString() {
		return "Movimiento [id=" + id + ", date=" + date + ", accion=" + accion + ",employeeId="+ employeeId +",employeeName=" + employeeName
				+ ", employeeJoiningDate=" + employeeJoiningDate + ", employeeSalary=" + employeeSalary
				+ ", employeeSsn=" + employeeSsn + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(name="DATE", nullable=false)
    private java.sql.Date date;
    
    @NotNull
    @Column(name="ACCION", nullable=false)
    private String accion;

    @Column(name="EMPLOYEE_ID", nullable=false)
    private int employeeId;
    
    @Column(name="EMPLOYEE_NAME", nullable=false)
    private String employeeName;
    
    @Column(name="EMPLOYEE_JOINING_DATE")
    private java.sql.Date employeeJoiningDate;

    @NotNull
    @Digits(integer=8, fraction=2)
    @Column(name="EMPLOYEE_SALARY", nullable=false)
    private BigDecimal employeeSalary;
    
	@NotEmpty
    @Column(name="EMPLOYEE_SSN", nullable=false)
    private String employeeSsn;

    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
    
	public void setEmployeeId(int id){
		this.employeeId = id;
	}
	
	public int getEmployeeId(){
		return employeeId;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public java.sql.Date getEmployeeJoiningDate() {
		return employeeJoiningDate;
	}

	public void setEmployeeJoiningDate(java.sql.Date employeeJoiningDate) {
		this.employeeJoiningDate = employeeJoiningDate;
	}

	public BigDecimal getEmployeeSalary() {
		return employeeSalary;
	}

	public void setEmployeeSalary(BigDecimal employeeSalary) {
		this.employeeSalary = employeeSalary;
	}

	public String getEmployeeSsn() {
		return employeeSsn;
	}

	public void setEmployeeSsn(String employeeSsn) {
		this.employeeSsn = employeeSsn;
	}



}

    