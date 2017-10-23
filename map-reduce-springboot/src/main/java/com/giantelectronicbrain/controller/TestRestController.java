package com.giantelectronicbrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giantelectronicbrain.hadoop.RepositoryException;
import com.giantelectronicbrain.hadoop.Word;
import com.giantelectronicbrain.hadoop.hbase.HbaseWordRepository;

@RestController
public class TestRestController {

	@Autowired
	HbaseWordRepository hbaseRepository;

	@GetMapping("/")
	public List<Word> hbaseData() throws RepositoryException {

		if (!hbaseRepository.tableExists()) {
			hbaseRepository.initTable();
			Word word = new Word(100, "DUmmY String");
			hbaseRepository.save(word);
		}

		return hbaseRepository.findAll();
	}
}