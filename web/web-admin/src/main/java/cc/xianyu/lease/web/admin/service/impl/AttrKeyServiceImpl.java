package cc.xianyu.lease.web.admin.service.impl;

import cc.xianyu.lease.model.entity.AttrKey;
import cc.xianyu.lease.web.admin.mapper.AttrKeyMapper;
import cc.xianyu.lease.web.admin.service.AttrKeyService;
import cc.xianyu.lease.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【attr_key(房间基本属性表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class AttrKeyServiceImpl extends ServiceImpl<AttrKeyMapper, AttrKey>
        implements AttrKeyService {

  @Resource
  private AttrKeyMapper attrKeyMapper;

  @Override
  public List<AttrKeyVo> listAttrInfo() {
    List<AttrKeyVo> list =attrKeyMapper.listAttrInfo();
    return list;
  }
}




