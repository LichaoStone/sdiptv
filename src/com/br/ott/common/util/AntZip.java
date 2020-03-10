package com.br.ott.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.br.ott.common.util.log.LogUtil;

/**
 * zip文件操作类 <功能详细描述>
 * 
 * @author pKF46373
 * @version [版本号, 2011-10-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AntZip {

	private static final int DEFAULT_BUFFER_SIZE = 512;

	private static LogUtil logUtil = new LogUtil(AntZip.class);

	private ZipFile zipFile;

	private byte[] buf;

	private int readedBytes;

	/**
	 * <构造函数>
	 */
	public AntZip() {
		this(DEFAULT_BUFFER_SIZE);
	}

	/**
	 * <构造函数>
	 */
	public AntZip(int bufSize) {
		this.buf = new byte[bufSize];
	}

	/**
	 * 解压指定zip文件 <功能详细描述>
	 * 
	 * @param unZipfileName
	 *            需要解压的zip文件全路径名
	 * @param saveZipFileName
	 *            zip解压后保存的文件夹
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void unZip(String unZipfileName, String saveZipFileName) {
		// unZipfileName需要解压的zip文件名
		FileOutputStream fileOut = null;
		File file;
		InputStream inputStream = null;
		try {
			this.zipFile = new ZipFile(unZipfileName, "GB2312");
			for (Enumeration<?> entries = this.zipFile.getEntries(); entries
					.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				file = new File(saveZipFileName + entry.getName());
				if (entry.isDirectory()) {
					file.mkdirs();
					file.createNewFile();
				} else {
					File parent = file.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
						file.createNewFile();
					}
					inputStream = zipFile.getInputStream(entry);
					fileOut = new FileOutputStream(file);
					while ((this.readedBytes = inputStream.read(this.buf)) > 0) {
						fileOut.write(this.buf, 0, this.readedBytes);
					}

				}
			}
			this.zipFile.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (fileOut != null)
					fileOut.close();
				if (inputStream != null)
					inputStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 测试AntZip类
	public static void main(String[] args) throws Exception {
		// 你要压缩的zip文件路径
		String zipFileName = "D:/Tomcat 6.0/webapps/OTTAppSource/template/102481911520608/a22b12b8b862336628b7b064483e7c6a.zip";
		// 压缩后的zip文件存放路径
		String saveZipFileName = "D:/Tomcat 6.0/webapps/OTTAppSource/template/102481911520608/";

		AntZip zip = new AntZip();

		if (zipFileName.lastIndexOf(".zip") > -1) {
			System.out.println("解压:java AntZip -unzip fileName.zip");
			zip.unZip(zipFileName, saveZipFileName);
		} else {
			System.out.println("Usage:");
			System.out.println("压缩:java AntZip -zip directoryName");
		}
	}

	/**
	 * 压缩
	 * 
	 * @param inputFileName
	 *            [输入文件路径]
	 * @param outFileName
	 *            [输出zip文件路径]
	 * @return boolean [是否成功？]
	 */
	public static boolean zip(String inputFileName, String outFileName) {
		File inputFile = new File(inputFileName);
		File outputFile = new File(outFileName);
		byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
		FileInputStream input = null;
		ZipOutputStream output = null;
		try {
			input = new FileInputStream(inputFile);
			output = new ZipOutputStream(new FileOutputStream(outputFile));
			output.putNextEntry(new ZipEntry(inputFile.getName()));
			int len = 0;
			while ((len = input.read(buf)) > 0) {
				output.write(buf, 0, len);
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null)
					output.close();
			} catch (IOException e) {
				logUtil.error(e.toString());
			}
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
				logUtil.error(e.toString());
			}
		}
		return false;
	}

}
