/*
 * @# PreviewManager.java Aug 2, 2012 1:52:04 PM
 * 
 * Copyright (C) 2011 - 2012 博瑞立方
 * BoRuiCube information technology co. ltd.
 * 
 * All rights reserved!
 */
package com.br.ott.common.io.image.preview;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
/*
 * @author pananz
 * TODO
 */
public final class PreviewManager {

	private static final PreviewManager MAGICK_PREVIEW_MANAGER = new PreviewManager(new MagickPreviewGenerator());

	private PreviewGenerator previewGenerator;

	public PreviewManager(PreviewGenerator previewGenerator) {
		this.previewGenerator = previewGenerator;
	}

	/**
	 * magick方式的生成图片预览图的策略.
	 */
	public static PreviewManager magickPreviewManager() {
		return MAGICK_PREVIEW_MANAGER;
	}

	/**
	 * 同比例缩放图片的宽/高度, 生成的图片不一定按给定的width/height缩放.(在原图上直接操作).
	 * 
	 * @param source
	 *            图片路径.
	 * @param width
	 *            要缩放的宽度.
	 * @param height
	 *            要缩放的高度.
	 * @throws IOException
	 */
	public void generate(String source, int width, int height) throws IOException {
		generate(source, source, width, height);
	}

	/**
	 * @see #generateBySameProportion(String, String, int, int)
	 */
	public void generate(String source, String target, int width, int height) throws IOException {
		generateBySameProportion(source, target, width, height);
	}

	/**
	 * 完全按照给定的图片宽/高度进行缩放.
	 * 
	 * @param source
	 *            源图片路径.
	 * @param target
	 *            目标图片路径
	 * @param width
	 *            要缩放的宽度.
	 * @param height
	 *            要缩放的高度.
	 * @throws IOException
	 */
	public void generateForce(String source, String target, int width, int height) throws IOException {
		if (source == null) {
			throw new IOException("源图片文件不存在");
		}
		makeDirectoryIfNonexists(target);

		Dimension d = previewGenerator.getSize(source);
		int w = (int) d.getWidth();
		int h = (int) d.getHeight();
		// 如果图片较小，就直接copy过去  
		if (w <= width && h <= height) {
			if (source.equals(target)) { // 目录相同
				previewGenerator.generate(source, target, w, h);
				return;
			}
			copy(source, target);
			return;
		}
		previewGenerator.generate(source, target, width, height);
	}

	/**
	 * 同比例缩放图片的宽/高度, 生成的图片不一定按给定的width/height缩放.
	 * 
	 * @param source
	 *            源图片路径.
	 * @param target
	 *            目标图片路径
	 * @param width
	 *            要缩放的宽度.
	 * @param height
	 *            要缩放的高度.
	 * @throws IOException
	 */
	public void generateBySameProportion(String source, String target, int width, int height) throws IOException {
		if (source == null) {
			throw new IOException("源图片文件不存在");
		}
		makeDirectoryIfNonexists(target);

		Dimension d = previewGenerator.getSize(source);
		int w = (int) d.getWidth();
		int h = (int) d.getHeight();
		// 如果图片较小，就直接copy过去  
		if (w <= width && h <= height) {
			if (source.equals(target)) { // 目录相同
				previewGenerator.generate(source, target, w, h);
				return;
			}
			copy(source, target);
			return;
		}
		//同比缩放  
		if (w * height > h * width) {
			height = h * width / w;
		} else {
			width = w * height / h;
		}
		previewGenerator.generate(source, target, width, height);
	}

	public void crop(String source, String target, Rectangle rect) throws IOException {
		previewGenerator.crop(source, target, rect);
	}

	public void crop(String source, String target, Rectangle rect, Dimension dimension) throws IOException {
		previewGenerator.crop(source, target, rect, dimension);
	}

	private void makeDirectoryIfNonexists(String target) {
		File dir = new File(target).getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	private void copy(String source, String target) throws IOException {
		FileUtils.copyFile(new File(source), new File(target));
	}

	/**
	 * 处理生成预览图的策略方法(只限制高度).
	 * 
	 * @param source
	 *            源图片文件路径
	 * @param target
	 *            目标图片文件路径
	 * @param height
	 *            预览图高度
	 * @throws IOException
	 */
	public void height(String source, String target, int height) throws IOException {
		if (source == null) {
			throw new IOException("源图片文件不存在");
		}
		makeDirectoryIfNonexists(target);

		int maxWidth = 600;
		Dimension d = previewGenerator.getSize(source);
		int w = (int) d.getWidth(), width;
		int h = (int) d.getHeight();
		// 如果图片较小，就直接copy过去  
		if (h <= height) {
			if (w <= maxWidth) {
				if (source.equals(target)) { // 目录相同
					previewGenerator.generate(source, target, w, h);
					return;
				}
				copy(source, target);
				return;
			} else {
				generate(source, target, maxWidth, height);
				return;
			}
		}
		// 缩放  
		width = w * height / h;
		previewGenerator.generate(source, target, width, height);
	}

	/**
	 * 处理生成预览图的策略方法(只限制宽度).
	 * 
	 * @param source
	 *            源图片文件路径
	 * @param target
	 *            目标图片文件路径
	 * @param width
	 *            预览图宽度
	 * @throws IOException
	 */
	public void width(String source, String target, int width) throws IOException {
		if (source == null) {
			throw new IOException("源图片文件不存在");
		}
		makeDirectoryIfNonexists(target);

		Dimension d = previewGenerator.getSize(source);
		int w = (int) d.getWidth();
		int h = (int) d.getHeight(), height;
		// 如果图片较小，就直接copy过去  
		if (w <= width) {
			copy(source, target);
			return;
		}
		// 缩放  
		height = h * width / w;
		previewGenerator.generate(source, target, width, height);
	}

	public static void main(String[] args) throws IOException {
		PreviewManager.magickPreviewManager().height("e:/1.jpeg", "e:/1-w80.jpeg", 80);
	}
}
