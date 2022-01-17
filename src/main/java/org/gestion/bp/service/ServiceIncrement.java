package org.gestion.bp.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceIncrement {
	int i =0;
	public int increment () {
		return i=i+1;
	}
}
