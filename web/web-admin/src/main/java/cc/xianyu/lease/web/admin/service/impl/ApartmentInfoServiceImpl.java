package cc.xianyu.lease.web.admin.service.impl;

import cc.xianyu.lease.common.exception.LeaseException;
import cc.xianyu.lease.common.result.ResultCodeEnum;
import cc.xianyu.lease.model.entity.*;
import cc.xianyu.lease.model.enums.ItemType;
import cc.xianyu.lease.web.admin.mapper.*;
import cc.xianyu.lease.web.admin.service.*;
import cc.xianyu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import cc.xianyu.lease.web.admin.vo.apartment.ApartmentItemVo;
import cc.xianyu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import cc.xianyu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import cc.xianyu.lease.web.admin.vo.fee.FeeValueVo;
import cc.xianyu.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

  @Resource
  private ApartmentInfoMapper apartmentInfoMapper;
  @Resource
  private GraphInfoMapper graphInfoMapper;
  @Resource
  private LabelInfoMapper labelInfoMapper;
  @Resource
  private FacilityInfoMapper facilityInfoMapper;
  @Resource
  private FeeValueMapper feeValueMapper;
  @Resource
  private GraphInfoService graphInfoService;
  @Resource
  private ApartmentFacilityService apartmentFacilityService;
  @Resource
  private ApartmentLabelService apartmentLabelService;
  @Resource
  private ApartmentFeeValueService apartmentFeeValueService;
  @Resource
  private RoomInfoMapper roomInfoMapper;

  @Override
  public void saveOrUpdateApart(ApartmentSubmitVo apartmentSubmitVo) {


  }

  @Override
  public IPage<ApartmentItemVo> pageItem(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo) {
    return apartmentInfoMapper.pageItem(page, queryVo);
  }

  @Override
  public ApartmentDetailVo getDetailById(Long id) {
    ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);

    List<GraphVo> list = graphInfoMapper.selectListByItemType(ItemType.APARTMENT, id);

    List<LabelInfo> labelInfos = labelInfoMapper.selectListByApartmentId(id);

    List<FacilityInfo> facilityInfos = facilityInfoMapper.selectListByApartmentId(id);

    List<FeeValueVo> feeValueVos = feeValueMapper.selectListByApartmentId(id);

    ApartmentDetailVo result = new ApartmentDetailVo();
    BeanUtils.copyProperties(apartmentInfo, result);
    result.setGraphVoList(list);
    result.setLabelInfoList(labelInfos);
    result.setFacilityInfoList(facilityInfos);
    result.setFeeValueVoList(feeValueVos);

    return result;
  }

  @Override
  public void removeApartmentById(Long id) {
    LambdaQueryWrapper<RoomInfo> roomInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
    roomInfoLambdaQueryWrapper.eq(RoomInfo::getApartmentId, id);
    Long count = roomInfoMapper.selectCount(roomInfoLambdaQueryWrapper);
    if (count > 0) {
      throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);
    }

    super.removeById(id);

    LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
    graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
    graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId, id);
    graphInfoService.remove(graphInfoLambdaQueryWrapper);

    LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();
    apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId, id);
    apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);

    LambdaQueryWrapper<ApartmentLabel> apartmentLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
    apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId, id);
    apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);

    LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
    apartmentFeeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId, id);
    apartmentFeeValueService.remove(apartmentFeeValueLambdaQueryWrapper);

  }
}




