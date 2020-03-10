package com.br.ott.client.live.mapper;

import java.util.List;

import com.br.ott.client.live.entity.BasicCProgram;
import com.br.ott.client.live.entity.BasicProgram;

/* 
 *  
 *  文件名：BasicProgramMapper.java
 *  版权：BRLF.Copyright 2014-2015,All rights reserved
 *  公司名称：博瑞立方网络科技有限公司
 *  创建人：pananz
 *  创建时间：2014-5-16 - 下午5:42:02
 */
public interface BasicProgramMapper {

	void addBasicProgram(BasicProgram basicProgram);

	List<BasicProgram> findBasicProgramByPage(BasicProgram basicprogram);

	int getCountBasicProgram(BasicProgram basicprogram);

	/**
	 * 按名称模糊查询 创建人：pananz 创建时间：2014-10-9 - 下午4:13:22
	 * 
	 * @param name
	 * @return 返回类型：List<BasicProgram>
	 * @exception throws
	 */
	List<BasicProgram> findBasicProgramByName(String name);

	/**
	 * 按名称相等查询 创建人：pananz 创建时间：2014-10-9 - 下午4:13:09
	 * 
	 * @param name
	 * @return 返回类型：List<BasicProgram>
	 * @exception throws
	 */
	List<BasicProgram> findBProgramByName(String name);

	BasicProgram getBasicProgramById(String id);

	void updateBasicProgram(BasicProgram basicprogram);

	/**
	 * 取得运营商频道正在直播节目信息
	 * 
	 * 创建人：pananz 创建时间：2016-11-3
	 * 
	 * @param @param basicCProgram
	 * @param @return 返回类型：List<BasicCProgram>
	 * @exception throws
	 */
	List<BasicCProgram> findBasicCProgramByPage(BasicCProgram basicCProgram);

	int getCountBasicCProgram(BasicCProgram basicCProgram);

	/**
	 * 查询正在直播节目信息
	 * 
	 * 创建人：pananz 创建时间：2016-12-7
	 * 
	 * @param @param basicCProgram
	 * @param @return 返回类型：List<BasicCProgram>
	 * @exception throws
	 */
	List<BasicCProgram> findBasicHotProgramByPage(BasicCProgram basicCProgram);
}
