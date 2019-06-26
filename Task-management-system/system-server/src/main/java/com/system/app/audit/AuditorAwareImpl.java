package com.system.app.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

	/**
	 *  this class is to just print name of person who created and modified this
	 */
	
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of("Devloper");
	}

}
