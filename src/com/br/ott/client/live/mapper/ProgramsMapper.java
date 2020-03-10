package com.br.ott.client.live.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.br.ott.client.live.entity.Programs;

/* 
 *  
 *  文件名：ProgramsMapper.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方网络科技有限公司
 *  创建人：pananz
 *  创建时间：2014-5-16 - 下午5:42:02
 */
public interface ProgramsMapper {

	/**
	 * 增加节目单 创建人：pananz 创建时间：2014-9-25 - 下午1:59:08
	 * 
	 * @param programs
	 *            返回类型：void
	 * @exception throws
	 */
	void addPrograms(Programs programs);

	/**
	 * 查询节目单 创建人：pananz 创建时间：2014-9-25 - 下午1:51:38
	 * 
	 * @param programs
	 * @return 返回类型：List<Programs>
	 * @exception throws
	 */
	List<Programs> findProgramsByPage(Programs programs);

	int getCountPrograms(Programs programs);

	/**
	 * 按条件查询节目单 创建人：pananz 创建时间：2014-9-25 - 下午1:58:50
	 * 
	 * @param programs
	 * @return 返回类型：List<Programs>
	 * @exception throws
	 */
	List<Programs> findProgramsByCond(Programs programs);

	/**
	 * 按ID查詢节目单 创建人：pananz 创建时间：2014-9-25 - 下午1:57:04
	 * 
	 * @param id
	 * @return 返回类型：Programs
	 * @exception throws
	 */
	Programs getProgramsById(String id);

	/**
	 * 按第三方频道ID和节目节目及播放时间查询
	 * 
	 * 创建人：pananz 创建时间：2015-4-22 - 下午5:06:29
	 * 
	 * @param cid
	 * @param name
	 * @param playTime
	 * @return 返回类型：Programs
	 * @exception throws
	 */
	Programs getProgramsByCidAndName(@Param("cid") String cid,
			@Param("name") String name, @Param("playTime") String playTime);

	/**
	 * 按名称查询当前直播及前一天的数据
	 * 
	 * 创建人：pananz 创建时间：2016-12-12
	 * 
	 * @param @param name
	 * @param @return 返回类型：Programs
	 * @exception throws
	 */
	Programs getLiveProgramByName(@Param("name") String name,
			@Param("playTimeMin") String playTimeMin);

	void updateProgramSource(Programs programs);

	/**
	 * 批次增加节目单 创建人：pananz 创建时间：2014-9-25 - 下午1:53:30
	 * 
	 * @param list
	 *            返回类型：void
	 * @exception throws
	 */
	void addProgramsByList(List<Programs> list);

	/**
	 * 修改节目单 创建人：pananz 创建时间：2014-9-25 - 下午1:53:58
	 * 
	 * @param programs
	 *            返回类型：void
	 * @exception throws
	 */
	void updatePrograms(Programs programs);

	/**
	 * 按频道运营商查询正在直播的节目
	 * 
	 * 创建人：pananz 创建时间：2015-6-12 - 上午10:23:54
	 * 
	 * @param programs
	 * @return 返回类型：List<Programs>
	 * @exception throws
	 */
	List<Programs> findCProgramsByPageAndOperators(Programs programs);

	int getCountCProgramsOperators(Programs programs);

	/**
	 * 按频道删除当天的节目单 创建人：pananz 创建时间：2014-9-25 - 下午1:54:21
	 * 
	 * @param cid
	 *            返回类型：void
	 * @exception throws
	 */
	void delProgramsByCidAndToday(String cid);

	/**
	 * 按频道及日期删除三天前的节目 创建人：pananz 创建时间：2014-9-25 - 下午1:52:42
	 * 
	 * @param channelId
	 * @param playTime
	 *            yyyy-MM-dd 返回类型：void
	 * @exception throws
	 */
	void delProgramBy7Date(@Param("channelId") String channelId,
			@Param("playTime") String playTime);

	/**
	 * 按频道及日期删除节目 创建人：pananz 创建时间：2014-9-25 - 下午1:52:13
	 * 
	 * @param channelId
	 * @param playTime
	 *            yyyy-MM-dd 返回类型：void
	 * @exception throws
	 */
	void delProgramsByCidAndDate(@Param("channelId") String channelId,
			@Param("playTime") String playTime);

	/**
	 * 删除指定天历史数据 并大于当前时间的数据
	 * 
	 * 创建人：pananz 创建时间：2015-4-27 - 下午5:52:20
	 * 
	 * @param playTime
	 *            yyyyMMdd 返回类型：void
	 * @exception throws
	 */
	void delProgramsByDateMore(String playTime);

	/**
	 * 删除指定频道与天历史数据 并大于当前时间的数据
	 * 
	 * 创建人：pananz 创建时间：2015-5-5 - 上午10:39:59
	 * 
	 * @param channelId
	 * @param playTime
	 *            yyyyMMdd 返回类型：void
	 * @exception throws
	 */
	void delProgramsByCidAndDateMore(@Param("channelId") String channelId,
			@Param("playTime") String playTime);

	/**
	 * 按ID删除节目
	 * 
	 * 创建人：pananz 创建时间：2015-5-26 - 下午1:46:44
	 * 
	 * @param id
	 *            返回类型：void
	 * @exception throws
	 */
	void delProgramsByCond(@Param("channelId") String channelId,
			@Param("name") String name, @Param("playTime") String playTime);

	/**
	 * 推荐频道正在直播的节目 创建人：pananz 创建时间：2014-9-25 - 下午3:11:23
	 * 
	 * @param programs
	 * @return 返回类型：List<Programs>
	 * @exception throws
	 */
	List<Programs> findCProgramByPageAndRec(Programs programs);

	/**
	 * 按第三方频道查询节目单
	 * 
	 * 创建人：pananz 创建时间：2015-4-22 - 下午3:13:18
	 * 
	 * @param programs
	 * @return 返回类型：List<Programs>
	 * @exception throws
	 */
	List<Programs> findProgramsByCid(Programs programs);

	Programs getLiveProgramsByChannelId(String channelId);

	int getCountProgramsByCreateTime(@Param("createTime") String createTime);

	/**
	 * 按ID集合删除节目数据
	 * 
	 * 创建人：pananz 创建时间：2015-10-30
	 * 
	 * @param @param ids 返回类型：void
	 * @exception throws
	 */
	void delProgramsList(List<String> ids);

	void updatePorgramsStatus(@Param("status") String status,
			@Param("id") String id);

	/**
	 * 是否支持点播
	 * 
	 * 创建人：pananz 创建时间：2016-11-15
	 * 
	 * @param @param dbPlay
	 * @param @param id 返回类型：void
	 * @exception throws
	 */
	void updateProgramDBPlay(Programs programs);

	
	/**
	 * 批次增加节目单 创建人：lizhenghg 创建时间：2014-9-25 - 下午1:53:30
	 * 
	 * @param Map
	 *            返回类型：void
	 * @exception throws
	 */
	void addProgramsByMap(Map<String, Object> paramsMap);
	
}
