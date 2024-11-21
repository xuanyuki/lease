package cc.xianyu.lease.web.admin.mapper;

import cc.xianyu.lease.model.entity.FacilityInfo;
import cc.xianyu.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author liubo
* @description 针对表【facility_info(配套信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity cc.xianyu.lease.model.FacilityInfo
*/
public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {

  List<FacilityInfo> selectListByApartmentId(@Param("id") Long id);
}




