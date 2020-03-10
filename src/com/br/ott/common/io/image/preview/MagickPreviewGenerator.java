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

import magick.CompressionType;
import magick.ImageInfo;
import magick.MagickApiException;
import magick.MagickException;
import magick.MagickImage;

import org.apache.log4j.Logger;

/**
 * Unix下最著名的ImageMagicK库的Java调用 .
 * 
 * JMagicK并不处理什么, 只是ImageMagicK到Java的一个jni接口, 其方法大部分是native的
 * 
 * @author alvin hwang
 */
public class MagickPreviewGenerator implements PreviewGenerator {

	private static Logger log = Logger.getLogger(MagickPreviewGenerator.class);

	public MagickPreviewGenerator() {
		System.setProperty("jmagick.systemclassloader", "no");
	}

	@Override
	public Dimension getSize(String source) throws IOException {
		try {
			ImageInfo info = new ImageInfo(source);
			MagickImage image = new MagickImage(info);
			return image.getDimension();
		} catch (MagickApiException ex) {
			throw new IOException(ex.getMessage());
		} catch (MagickException ex) {
			throw new IOException(ex.getMessage());
		}
	}

	@Override
	public void generate(String source, int width, int height) throws IOException {
		generate(source, source, width, height);
	}

	@Override
	public void generate(String source, String target, int width, int height) throws IOException {
		try {
			ImageInfo info = new ImageInfo(source);
			MagickImage image = new MagickImage(info);
			MagickImage canvasImage = image.scaleImage(width, height);
			save(canvasImage, target);
		} catch (MagickApiException ex) {
			log.warn(ex.getMessage(), ex);
			throw new IOException(ex.getMessage());
		} catch (MagickException ex) {
			log.warn(ex.getMessage(), ex);
			throw new IOException(ex.getMessage());
		}
	}

	private void save(MagickImage image, String target) throws IOException {
		try {
			image.setCompression(CompressionType.JPEGCompression);
			image.setFileName(target);
			image.writeImage(new ImageInfo());
		} catch (MagickApiException ex) {
			log.warn(ex.getMessage(), ex);
			throw new IOException(ex.getMessage());
		} catch (MagickException ex) {
			log.warn(ex.getMessage(), ex);
			throw new IOException(ex.getMessage());
		}
	}

	@Override
	public void crop(String source, String target, Rectangle rect) throws IOException {
		crop(source, target, rect, null);
	}

	@Override
	public void crop(String source, String target, Rectangle rect, Dimension dimension) throws IOException {
		MagickImage cropped = null;
		try {
			ImageInfo info = new ImageInfo(source);
			MagickImage image = new MagickImage(info);
			cropped = image.cropImage(rect);
			save(cropped, target);
			if (dimension != null) {
				generate(target, dimension.width, dimension.height);
			}
		} catch (MagickApiException ex) {
			log.warn(ex.getMessage(), ex);
			throw new IOException(ex.getMessage());
		} catch (MagickException ex) {
			log.warn(ex.getMessage(), ex);
			throw new IOException(ex.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException {
		PreviewGenerator generator = new MagickPreviewGenerator();
		Rectangle rect = new Rectangle(70, 0, 300, 300);
		Dimension dimension = new Dimension(80, 80);
		generator.crop("c:/2.jpg", "c:/2-crop.jpg", rect, dimension);
	}
}
