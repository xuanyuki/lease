package cc.xianyu.lease.web.admin.service.impl;

import cc.xianyu.lease.model.entity.FeeKey;
import cc.xianyu.lease.web.admin.mapper.FeeKeyMapper;
import cc.xianyu.lease.web.admin.service.FeeKeyService;
import cc.xianyu.lease.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class FeeKeyServiceImpl extends ServiceImpl<FeeKeyMapper, FeeKey>
    implements FeeKeyService{

  @Resource
  private FeeKeyMapper feeKeyMapper;

  @Override
  public List<FeeKeyVo> feeInfoList() {
    List<FeeKeyVo> list= feeKeyMapper.feeInfoList();
    return list;
  }
}




