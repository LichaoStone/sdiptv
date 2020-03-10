package com.br.ott.client.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.RecProgram;

/* 
 *  节目推荐
 *  文件名：RecProgramMapper.java
 *  创建人：pananz
 *  创建时间：2014-8-20 - 下午3:49:25
 */
public interface RecProgramMapper {

	/**
	 * 增加节目推荐
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:29:37
	 * 
	 * @param recProgram
	 *            返回类型：void
	 * @exception throws
	 */
	void addRecProgram(RecProgram recProgram);

	/**
	 * 修改节目推荐
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:29:44
	 * 
	 * @param recProgram
	 *            返回类型：void
	 * @exception throws
	 */
	void updateRecProgram(RecProgram recProgram);

	/**
	 * 按ID查询节目推荐
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:29:55
	 * 
	 * @param id
	 * @return 返回类型：RecProgram
	 * @exception throws
	 */
	RecProgram getRecProgramById(String id);

	/**
	 * 按分页查询节目推荐
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:30:04
	 * 
	 * @param recProgram
	 * @return 返回类型：List<RecProgram>
	 * @exception throws
	 */
	List<RecProgram> findRecProgramByPage(RecProgram recProgram);
	int getCountRecProgram(RecProgram recProgram);
	
	/**
	 * 按条件查询节目推荐
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:30:16
	 * 
	 * @param recProgram
	 * @return 返回类型：List<RecProgram>
	 * @exception throws
	 */
	List<RecProgram> findRecProgramByCond(RecProgram recProgram);

	/**
	 * 按ID删除节目推荐
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:30:26
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void deleteRecProgramById(String id);

	/**
	 * 按分页查询手机推荐节目(当前时间到后三天的)
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:31:45
	 * 
	 * @param recProgram
	 * @return 返回类型：List<RecProgram>
	 * @exception throws
	 */
	List<RecProgram> findRecProByPage(RecProgram recProgram);
	
	/**
	 * 按分页查询手机推荐节目(当前时间到前三天的)
		 * 
		 * 创建人：pananz 创建时间：2015-9-2
		 * 
		 * @param @param recProgram
		 * @param @return
		 * 返回类型：List<RecProgram>
		 * @exception throws
	 */
	List<RecProgram> findRecProByPage2(RecProgram recProgram);
	
	/**
	 * 按分页查询推荐节目(当前时间到24小时后的)
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:32:35
	 * 
	 * @param recProgram
	 * @return 返回类型：List<RecProgram>
	 * @exception throws
	 */
	List<RecProgram> findRecLProByPage(RecProgram recProgram);
	
	/**
	 * 修改节目推荐状态
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:34:53
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void updateRecProgramStatus(@Param("status") String status,
			@Param("id") String id);
	
	int getMaxSequence(@Param("type") String type, @Param("channelId") String channelId);
	
	/**
	 * 增加排序值
	 * 
	 * 创建人：lizhenghg 创建时间：2017-3-17
	 * 
	 * @param @param dn 返回类型：void
	 * @exception throws
	 */
	void updateDNAddSequence(RecProgram rec);
	
	/**
	 * 减少排序值
	 * 
	 * 创建人：lizhenghg 创建时间：2017-3-17
	 * 
	 * @param @param dn 返回类型：void
	 * @exception throws
	 */
	void updateDNSubSequence(RecProgram rec);

	/**
	 * 修改排序值
	 * 
	 * 创建人：lizhenghg 创建时间：2017-3-17
	 * 
	 * @param @param status
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	void updateDNSequence(@Param("sequence") String sequeue, @Param("id") String id);
}
