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
import java.io.IOException;

/**
 * 预览图生成的策略接口.
 * 
 * @author alvin hwang
 */
public interface PreviewGenerator {

	/**
	 * 获取图片的大小.
	 * 
	 * @param source
	 *            源图片文件路径
	 * @return Dimension 图片的大小
	 * @throws IOException
	 */
	Dimension getSize(String source) throws IOException;

	/**
	 * 处理生成预览图的策略方法.
	 * 
	 * @param source
	 *            源图片文件路径
	 * @param target
	 *            目标图片文件路径
	 * @param width
	 *            预览图宽度
	 * @param height
	 *            预览图高度
	 * @throws IOException
	 */
	void generate(String source, String target, int width, int height) throws IOException;

	/**
	 * 处理生成预览图的策略方法(在原图的基础上直接修改).
	 * 
	 * @param source
	 *            图片文件路径
	 * @param width
	 *            预览图宽度
	 * @param height
	 *            预览图高度
	 * @throws IOException
	 */
	void generate(String source, int width, int height) throws IOException;

	/**
	 * 按照指定的大小进行图片的切割.
	 * 
	 * @param source
	 *            源图片文件路径
	 * @param target
	 *            目标图片文件路径
	 * @param rect
	 *            切割的比例
	 * @throws IOException
	 */
	void crop(String source, String target, Rectangle rect) throws IOException;

	/**
	 * 按照指定的大小进行图片的切割, 并按照精度进行压缩.
	 * 
	 * @param source
	 *            源图片文件路径
	 * @param target
	 *            目标图片文件路径
	 * @param rect
	 *            切割的比例
	 * @param dimension
	 *            缩放的精度
	 * @throws IOException
	 */
	void crop(String source, String target, Rectangle rect, Dimension dimension) throws IOException;
}
