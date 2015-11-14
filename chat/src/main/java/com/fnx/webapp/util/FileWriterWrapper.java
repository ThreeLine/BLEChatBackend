package com.fnx.webapp.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.IOUtils;

public class FileWriterWrapper {
	private File file;
	private PrintWriter writer;

	public FileWriterWrapper(File directory, String name) throws IOException {
		this.file = new File(directory, name);
		this.file.createNewFile();
		this.writer = new PrintWriter(new BufferedWriter(new FileWriter(this.file)));
	}

	public void write(String line) {
		if (file.length() > 0) {
			writer.println();
		}
		writer.print(line);
		writer.flush();
	}

	public String getName() {
		return this.file.getName();
	}

	public String getPath() {
		return this.file.getAbsolutePath();
	}

	public void close() {
		IOUtils.closeQuietly(writer);
	}
}
