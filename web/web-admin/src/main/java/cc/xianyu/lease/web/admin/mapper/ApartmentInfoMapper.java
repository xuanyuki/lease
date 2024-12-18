package cc.xianyu.lease.web.admin.mapper;

import cc.xianyu.lease.model.entity.ApartmentInfo;
import cc.xianyu.lease.web.admin.vo.apartment.ApartmentItemVo;
import cc.xianyu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity cc.xianyu.lease.model.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

  IPage<ApartmentItemVo> pageItem(@Param("page") IPage<ApartmentItemVo> page, @Param("queryVo") ApartmentQueryVo queryVo);
}




