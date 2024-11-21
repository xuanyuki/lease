package cc.xianyu.lease.web.admin.mapper;

import cc.xianyu.lease.model.entity.GraphInfo;
import cc.xianyu.lease.model.enums.ItemType;
import cc.xianyu.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity cc.xianyu.lease.model.GraphInfo
 */
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

  List<GraphVo> selectListByItemType(@Param("itemType") ItemType itemType, @Param("id") Long id);
}




