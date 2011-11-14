/**
 * 
 */
package com.netappsid.utf8conversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

/**
 * @author xjodoin
 * @author NetAppsID inc.
 * 
 * @version
 * 
 */
public class Conversion
{
	private String inputEncoding = "ISO-8859-1";
	private String outputEncoding = "UTF-8";
	private String dir;

	public String getInputEncoding()
	{
		return inputEncoding;
	}

	public void setInputEncoding(String inputEncoding)
	{
		this.inputEncoding = inputEncoding;
	}

	public String getOutputEncoding()
	{
		return outputEncoding;
	}

	public void setOutputEncoding(String outputEncoding)
	{
		this.outputEncoding = outputEncoding;
	}

	public String getDir()
	{
		return dir;
	}

	public void setDir(String dir)
	{
		this.dir = dir;
	}

	/**
	 * 
	 */
	public void convert()
	{
		try
		{
			Collection<File> listFiles = FileUtils.listFiles(new File(dir), new SuffixFileFilter(new String[] { ".java", ".scala", ".properties", ".xml" }),
					HiddenFileFilter.VISIBLE);

			for (File source : listFiles)
			{
				System.out.println(source);

				InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(source), inputEncoding);
				File createTempFile = File.createTempFile("utf", ".conv");
				FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
				IOUtils.copy(inputStreamReader, fileOutputStream, outputEncoding);
				fileOutputStream.close();
				inputStreamReader.close();

				FileUtils.copyFile(createTempFile, source, false);
				createTempFile.delete();
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

}
