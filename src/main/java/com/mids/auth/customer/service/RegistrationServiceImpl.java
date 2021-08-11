package com.mids.auth.customer.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mids.auth.customer.dto.PageResponse;
import com.mids.auth.customer.entity.Register;
import com.mids.auth.customer.mapper.RegisterMapper;
import com.mids.auth.customer.repository.RegisterRepository;
import com.mids.auth.customer.request.RegisterRequest;





@Service
public class RegistrationServiceImpl implements RegistrationService {

	private final RegisterRepository registerRepository;
	private final RegisterMapper registerMapper;

	@Autowired
	public RegistrationServiceImpl(RegisterRepository registerRepository) {

		this.registerRepository = registerRepository;
		this.registerMapper = Mappers.getMapper(RegisterMapper.class);
	}

	@Override
	@Transactional
	public String registerCustomer(RegisterRequest register) {

		Register registerEntity = registerMapper.registerToRegisterEntity(register);

		if (registerRepository.existsByCustomerId(register.getCustomerId())) {

			return "existsUserId";

		}

		if (registerRepository.existsByEmail(register.getEmail())) {
			return "existsEmailId";
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(register.getPassword());
		registerEntity.setPassword(encodedPassword);
		/*
		 * List<String> strRoles = register.getRole(); Set<Role> roles = new
		 * HashSet<>();
		 * 
		 * if (strRoles == null) { Role userRole =
		 * roleRepository.findByName(ERole.ROLE_USER) .orElseThrow(() -> new
		 * RuntimeException("Error: Role is not found.")); roles.add(userRole); } else {
		 * strRoles.forEach(role -> { switch (role) { case "admin": Role adminRole =
		 * roleRepository.findByName(ERole.ROLE_ADMIN) .orElseThrow(() -> new
		 * RuntimeException("Error: Role is not found.")); roles.add(adminRole);
		 * 
		 * break; case "mod": Role modRole =
		 * roleRepository.findByName(ERole.ROLE_MODERATOR) .orElseThrow(() -> new
		 * RuntimeException("Error: Role is not found.")); roles.add(modRole);
		 * 
		 * break; default: Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		 * .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		 * roles.add(userRole); } }); }
		 * 
		 * registerEntity.setRoles(roles);
		 */
		registerRepository.save(registerEntity);
		return "Success";

	}
	
	
	 @Override
	    public PageResponse<RegisterRequest> getAllCustomers(Integer pageNumber, Integer recordsPerPage) {
	        Pageable pageable = PageRequest.of(pageNumber, recordsPerPage);
	        Page<Register> rolePage = registerRepository.findAll(pageable);

	        PageResponse<RegisterRequest> pageResponse = new PageResponse<>();
	        pageResponse.setContent(rolePage.getContent().stream()
	                .map(registerMapper::registerEntityToRegister)
	                .collect(Collectors.toList()));
	        pageResponse.setPageNumber(pageNumber);
	        pageResponse.setPageSize(rolePage.getSize());
	        pageResponse.setTotalPages(rolePage.getTotalPages());
	        pageResponse.setTotalElements(rolePage.getTotalElements());
	        pageResponse.setFirst(rolePage.isFirst());
	        pageResponse.setLast(rolePage.isLast());
	        pageResponse.setNumberOfElements(rolePage.getNumberOfElements());
	        return pageResponse;
	    }
	 
	 
	
	@Override
	    public RegisterRequest getCustomer(String userId) {
		 Register registerEntity = registerRepository.findByCustomerId(userId);
	        if(registerEntity!=null) {
	            return registerMapper.registerEntityToRegister(registerEntity);
	        }
	        return null;
	    }
	
	
	
	@Override
	    public RegisterRequest updateCustomer(RegisterRequest register) {
		 

			if (registerRepository.existsByCustomerId(register.getCustomerId())) {

				Register registerEntity = registerRepository.findByCustomerId(register.getCustomerId());
			     long uuid = registerEntity.getId();
				//registerEntity=	registerRepository.findById(registerEntity.getId());
				 registerEntity = registerMapper.registerToRegisterEntity(register);
				 registerEntity.setId(uuid);
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(register.getPassword());
			registerEntity.setPassword(encodedPassword);
			registerRepository.save(registerEntity);
			return registerMapper.registerEntityToRegister(registerEntity);
	    }
		
			return null;
	 }

	   
		@Override
	    public boolean deleteCustomer(String clientId) {
	    	if (registerRepository.existsByCustomerId(clientId)) {
	    		Register registerEntity = registerRepository.findByCustomerId(clientId);
			     long uuid = registerEntity.getId();
	    		registerRepository.deleteById(uuid);
	    		
	            return true;
	        }
	        return false;
	    }

	
}
