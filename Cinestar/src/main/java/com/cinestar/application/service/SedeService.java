package com.cinestar.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinestar.application.repository.SedeRepository;

@Service
public class SedeService {
	@Autowired
	SedeRepository repository;
}