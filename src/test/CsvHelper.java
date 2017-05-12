/**
 *  libswb
 *  Copyright (C) 2008  Sven Buschbeck
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program (LICENSE.txt).  
 *  If not, see <http://www.gnu.org/licenses/>
 *
 */
package test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.hp.hpl.jena.sparql.util.StringUtils;

/**
 * @author Sven Buschbeck
 *
 */
public class CsvHelper {
	
	
	private String[] columns;
	private String   valueSeparator, lineSeparator;
	private List<List<String>> lines;
	private int lineIndex;

	/**
	 * @param columns
	 */
	public CsvHelper(String[] columns) {
		this(columns, ",", "\n");
	}
	
	/**
	 * @param columns
	 */
	public CsvHelper(String[] columns, String valueSeparator, String lineSeparator) {
		this.columns = columns;
		this.valueSeparator = valueSeparator;
		this.lineSeparator = lineSeparator;
		lines = new LinkedList<List<String>>();
		lines.add(new LinkedList<String>());
	}
	
	/**
	 * @param value
	 */
	public void addNext(String value) {
		List<String> line = lines.get(lineIndex); 
		line.add(value);
		if (line.size() >= columns.length) {
			lineIndex++;
			lines.add(new LinkedList<String>());
		}
	}
	
	/**
	 * @param value
	 */
	public void addNext(int value) {
		addNext(Integer.toString(value));
	}
	
	/**
	 * @param value
	 */
	public void addNext(double value) {
		addNext(Double.toString(value));
	}
	
	/**
	 * @return
	 */
	public String getCsv() {
		String result = "";
		for (List<String> line : lines) {
			if (line == null || line.size() <= 0) {
				System.out.println("csvHelper.getCsv: line was null or empty!");
				continue;
			}
			result += StringUtils.join(valueSeparator, line)+lineSeparator; 
		}
		return result;
	}
	
	public String getCsvWithHeader() {
		return StringUtils.join(valueSeparator, columns)+lineSeparator+getCsv();
	}
	
	public String toString() {
		return getCsv();
	}
	
	private String join(List<String> line, String separator) {
		String result = "";
		for (String value : line) {
			result += result+valueSeparator;
		}
		return result;
	}
}
