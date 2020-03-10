package com.br.ott.client.live.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.ott.client.live.entity.RecProgram;
import com.br.ott.client.live.mapper.RecProgramMapper;

/* 
 *  节目推荐业务接口
 *  文件名：RecProgramService.java
 *  创建人：pananz
 *  创建时间：2014-8-20 - 下午4:09:20
 */
@Service
public class RecProgramService {

	@Autowired
	private RecProgramMapper recProgramMapper;

	public void addRecProgram(RecProgram recProgram) {
		recProgramMapper.addRecProgram(recProgram);
	}

	public void updateRecProgram(RecProgram recProgram) {
		recProgramMapper.updateRecProgram(recProgram);
	}

	public RecProgram getRecProgramById(String id) {
		return recProgramMapper.getRecProgramById(id);
	}

	public List<RecProgram> findRecProgramByPage(RecProgram recProgram) {
		int totalResult=recProgramMapper.getCountRecProgram(recProgram);
		recProgram.setTotalResult(totalResult);
		return recProgramMapper.findRecProgramByPage(recProgram);
	}

	public List<RecProgram> findRecProgramByCond(RecProgram recProgram) {
		return recProgramMapper.findRecProgramByCond(recProgram);
	}

	public void deleteRecProgramById(String id) {
		recProgramMapper.deleteRecProgramById(id);
	}

	public void updateRecProgramStatus(String status, String id) {
		recProgramMapper.updateRecProgramStatus(status, id);
	}
	
	public int getMaxSequence(String type, String channelId) {
		return recProgramMapper.getMaxSequence(type, channelId);
	}
	
	/**
	 * 改变推荐顺序
	 * @author lizhenghg
	 * @param dn
	 * @param oldQueue
	 * @param queue
	 */
	public void changeDNSequence(RecProgram rec, String oldQueue, String queue){
		int old = Integer.parseInt(oldQueue);
		int now = Integer.parseInt(queue);
		rec.setSequence(oldQueue);
		rec.setOldSequence(old);
		if (old > now) {// 从大到小,小于当前要修改的排序值queue要加1
			recProgramMapper.updateDNAddSequence(rec);
		} else {// 从小到大,queue的排序值要减1
			recProgramMapper.updateDNSubSequence(rec);
		}
		recProgramMapper.updateDNSequence(queue, rec.getId());
	}
}
