package cc.xianyu.lease.web.admin.controller.apartment;


import cc.xianyu.lease.common.result.Result;
import cc.xianyu.lease.model.entity.LabelInfo;
import cc.xianyu.lease.model.enums.ItemType;
import cc.xianyu.lease.web.admin.service.LabelInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "标签管理")
@RestController
@RequestMapping("/admin/label")
public class LabelController {

  @Autowired
  private LabelInfoService labelInfoService;

  @Operation(summary = "（根据类型）查询标签列表")
  @GetMapping("list")
  public Result<List<LabelInfo>> labelList(@RequestParam(required = false) Integer type) {
    LambdaQueryWrapper<LabelInfo> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(type != null, LabelInfo::getType, type);
    List<LabelInfo> list = labelInfoService.list(wrapper);
    return Result.ok(list);
  }

  @Operation(summary = "新增或修改标签信息")
  @PostMapping("saveOrUpdate")
  public Result saveOrUpdateLabel(@RequestBody LabelInfo labelInfo) {
    labelInfoService.saveOrUpdate(labelInfo);
    return Result.ok();
  }

  @Operation(summary = "根据id删除标签信息")
  @DeleteMapping("deleteById")
  public Result deleteLabelById(@RequestParam Long id) {
    labelInfoService.removeById(id);
    return Result.ok();
  }
}