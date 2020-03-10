package com.br.ott.client.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.RecChannel;
import com.br.ott.client.live.entity.RecProgram;

/* 
 *  
 *  文件名：RecChannelMapper.java
 *  创建人：pananz
 *  创建时间：2014-8-20 - 下午3:49:25
 */
public interface RecChannelMapper {

	/**
	 * 增加频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午3:40:29
	 * 
	 * @param recChannel
	 *            返回类型：void
	 * @exception throws
	 */
	void addRecChannel(RecChannel recChannel);

	/**
	 * 修改频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午3:40:38
	 * 
	 * @param recChannel
	 *            返回类型：void
	 * @exception throws
	 */
	void updateRecChannel(RecChannel recChannel);

	/**
	 * 按ID查询频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午3:40:46
	 * 
	 * @param id
	 * @return 返回类型：RecChannel
	 * @exception throws
	 */
	RecChannel getRecChannelById(String id);

	/**
	 * 按分页查询频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午3:40:58
	 * 
	 * @param recChannel
	 * @return 返回类型：List<RecChannel>
	 * @exception throws
	 */
	List<RecChannel> findRecChannelByPage(RecChannel recChannel);
	int getCountRecChannel(RecChannel recChannel);
	
	/**
	 * 按条件查询所有频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午3:41:12
	 * 
	 * @param recChannel
	 * @return 返回类型：List<RecChannel>
	 * @exception throws
	 */
	List<RecChannel> findRecChannelByCond(RecChannel recChannel);

	/**
	 * 删除频道推荐
	 * 
	 * 创建人：pananz 创建时间：2015-2-6 - 下午3:41:22
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void deleteRecChannelById(String id);

	/**
	 * 修改频道推荐状态
	 * 
	 * 创建人：pananz 创建时间：2015-5-19 - 上午10:19:46
	 * 
	 * @param status
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void updateRecChannelStatus(@Param("status") String status,
			@Param("id") String id);
	
	/**
	 * 获取推荐频道的最大排序值
	 * 
	 * 创建人：lizhenghg 创建时间：2017-03-21
	 * 
	 * @param  type
	 * @return int
	 * @exception throws
	 */
	int getMaxSequence(@Param("type") String type);
	
	/**
	 * 增加排序值
	 * 
	 * 创建人：lizhenghg 创建时间：2017-3-17
	 * 
	 * @param @param dn 返回类型：void
	 * @exception throws
	 */
	void updateDNAddSequence(RecChannel rec);
	
	/**
	 * 减少排序值
	 * 
	 * 创建人：lizhenghg 创建时间：2017-3-17
	 * 
	 * @param @param dn 返回类型：void
	 * @exception throws
	 */
	void updateDNSubSequence(RecChannel rec);

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
