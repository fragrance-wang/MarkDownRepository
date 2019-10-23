package cn.com.greattimes.npds.prm.service.index;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.greattimes.npds.common.util.LoggerUtils;
import cn.com.greattimes.npds.prm.orm.service.PcapBlockIndexService;
import cn.com.greattimes.pcap.packet.index.PcapBlockIndex;

/**
 * @description Pcap块索引存储器
 * @project npds-prm
 * @company www.greattimes.com.cn
 * @copyright 2015-2019 Beijing Greattimes Technology Co., Ltd. All rights reserved
 * @author wangshan
 * @date 2019-07-31 18:50:11
 * @version 1.0.1
 */
@Component
public class IndexStoreHandler {

	/**
	 * @description 日志记录工具
	 */
	private static final Logger LOGGER = LoggerUtils.getLogger("service");

	/**
	 * @description Pcap块索引数据访问服务
	 */
	@Autowired
	private PcapBlockIndexService pcapBlockIndexService;

	/**
	 * @description 将Pcap块索引对象存储到数据库中
	 * @param pcapBlockIndex
	 * @throws Exception
	 */
	public void handleIndexBlock(PcapBlockIndex pcapBlockIndex) throws Exception {
		pcapBlockIndexService.createTableIfNotExist(pcapBlockIndex);
		for (int i = 0; i < 3; i++) {
			try {
				pcapBlockIndexService.insertPcapBlockIndex(pcapBlockIndex);
				break;
			} catch (Exception ex) {
				if(i==2) {
					LOGGER.error("Pcap块索引对象插入数据库失败 -> 失败原因 = ", ex);
				}
			}
		}
	}

}
