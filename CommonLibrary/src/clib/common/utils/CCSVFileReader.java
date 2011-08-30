/*
 * CCSVReader.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.utils;

import java.util.ArrayList;
import java.util.List;

import clib.common.filesystem.CFile;

public class CCSVFileReader<T> {

	public interface CSVMapper<U> {
		public U create(List<String> data);
	}

	private CSVMapper<T> mapper;

	public CCSVFileReader(CSVMapper<T> mapper) {
		this.mapper = mapper;
	}

	public List<T> read(CFile file) {
		List<T> records = new ArrayList<T>();

		for (String line : file.loadTextAsList()) {
			try {
				List<String> data = CCSVReader.read(line);
				T record = mapper.create(data);
				if (record == null) {
					continue;
				}
				records.add(record);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return records;
	}
}
