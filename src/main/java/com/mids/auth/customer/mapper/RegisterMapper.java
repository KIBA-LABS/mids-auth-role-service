package com.mids.auth.customer.mapper;
import org.mapstruct.Mapper;

import com.mids.auth.customer.entity.Register;
import com.mids.auth.customer.request.RegisterRequest;





@Mapper
public interface RegisterMapper {

	Register registerToRegisterEntity(RegisterRequest register);
	RegisterRequest registerEntityToRegister(Register register);
}
