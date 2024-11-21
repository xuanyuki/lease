package cc.xianyu.lease.web.admin.controller.apartment;


import cc.xianyu.lease.common.result.Result;
import cc.xianyu.lease.model.entity.AttrKey;
import cc.xianyu.lease.model.entity.AttrValue;
import cc.xianyu.lease.web.admin.service.AttrKeyService;
import cc.xianyu.lease.web.admin.service.AttrValueService;
import cc.xianyu.lease.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "房间属性管理")
@RestController
@RequestMapping("/admin/attr")
public class AttrController {

  @Autowired
  private AttrKeyService keyService;
  @Autowired
  private AttrValueService valueService;

  @Operation(summary = "新增或更新属性名称")
  @PostMapping("key/saveOrUpdate")
  public Result saveOrUpdateAttrKey(@RequestBody AttrKey attrKey) {
    keyService.saveOrUpdate(attrKey);
    return Result.ok();
  }

  @Operation(summary = "新增或更新属性值")
  @PostMapping("value/saveOrUpdate")
  public Result saveOrUpdateAttrValue(@RequestBody AttrValue attrValue) {
    valueService.saveOrUpdate(attrValue);
    return Result.ok();
  }


  @Operation(summary = "查询全部属性名称和属性值列表")
  @GetMapping("list")
  public Result<List<AttrKeyVo>> listAttrInfo() {
    List<AttrKeyVo> list = keyService.listAttrInfo();
    return Result.ok(list);
  }

  @Operation(summary = "根据id删除属性名称")
  @DeleteMapping("key/deleteById")
  public Result removeAttrKeyById(@RequestParam Long attrKeyId) {
    keyService.removeById(attrKeyId);
    LambdaQueryWrapper<AttrValue> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(AttrValue::getAttrKeyId, attrKeyId);
    valueService.remove(wrapper);
    return Result.ok();
  }

  @Operation(summary = "根据id删除属性值")
  @DeleteMapping("value/deleteById")
  public Result removeAttrValueById(@RequestParam Long id) {
    valueService.removeById(id);
    return Result.ok();
  }

}
