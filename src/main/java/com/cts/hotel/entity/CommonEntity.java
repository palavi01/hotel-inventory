package com.cts.hotel.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class CommonEntity implements Serializable {

	@Transient
	private static final long serialVersionUID = -4246787040534790267L;

	private Integer status;
	
	private String createdDate;
	
	private String createdBy;
	
	private String modifiedDate;
	
	private String modifiedBy;
}