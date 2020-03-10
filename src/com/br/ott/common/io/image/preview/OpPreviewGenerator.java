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
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * 标准的Image处理的装饰器模式实现.
 * 
 * @author alvin hwang
 */
public class OpPreviewGenerator {
	
	public Dimension getSize(String source) throws IOException {
		BufferedImage image = ImageIO.read(new File(source));
		return new Dimension(image.getWidth(), image.getHeight());
	}

	public void generate(String source, int width, int height) throws IOException {
		generate(source, source, width, height);
	}
	
	public void generate(String source, String target, int width, int height) throws IOException {
		BufferedImage sourceImage = ImageIO.read(new File(source));
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.scale(width, height);
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, hints);
		BufferedImage image = new BufferedImage(width, height, sourceImage.getType());
		image = affineTransformOp.filter(sourceImage, image);
		saveImage(image, target);
	}
	
	private void saveImage(BufferedImage image, String target) throws IOException {
		File targetFile = new File(target);
		ImageWriter writer = null;
		ImageOutputStream outputStream = null;
		try {
			ImageTypeSpecifier type = ImageTypeSpecifier
					.createFromRenderedImage(image);
			Iterator<ImageWriter> iterator = ImageIO.getImageWriters(type, "JPEG");
			if (!iterator.hasNext()) {
				return;
			}
			writer = (ImageWriter) iterator.next();
			IIOImage iioImage = new IIOImage(image, null, null);
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(1.0f);
			outputStream = ImageIO.createImageOutputStream(targetFile);
			writer.setOutput(outputStream);
			writer.write(null, iioImage, param);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (writer != null) {
				writer.abort();
			}
		}
	}

}
