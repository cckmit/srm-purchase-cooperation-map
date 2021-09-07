package org.srm.purchasecooperation.cux.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.api.dto.ZhnyPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.app.service.ZhnyPoPlanService;
import org.srm.purchasecooperation.cux.domain.entity.ZhnyPoPlanHeader;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 采购计划 管理 API
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@RestController("zhnyPoPlanHeaderController.v1")
@RequestMapping("/v1/{organizationId}/zhny-po-plan-headers")
public class ZhnyPoPlanController extends BaseController {

    private final ZhnyPoPlanService zhnyPoPlanHeaderService;

    @Autowired
    public ZhnyPoPlanController(ZhnyPoPlanService zhnyPoPlanHeaderService) {
        this.zhnyPoPlanHeaderService = zhnyPoPlanHeaderService;
    }

    @ApiOperation(value = "采购计划头表列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/list")
    public ResponseEntity<Page<ZhnyPoPlanHeader>> list(@PathVariable("organizationId") Long organizationId, ZhnyPoPlanHeader zhnyPoPlanHeader, @ApiIgnore @SortDefault(value = ZhnyPoPlanHeader.FIELD_PO_PLAN_HEADER_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        zhnyPoPlanHeader.setTenantId(organizationId);
        Page<ZhnyPoPlanHeader> list = zhnyPoPlanHeaderService.list(zhnyPoPlanHeader, pageRequest);
        return Results.success(list);
    }

    @ApiOperation(value = "新增/保存/修改采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/addPoPlan")
    public ResponseEntity<ZhnyPoPlanHeader> addPoPlan(@PathVariable("organizationId") Long organizationId, @RequestBody @Encrypt ZhnyPoPlanHeaderDTO dto) {
        dto.setTenantId(organizationId);
        ZhnyPoPlanHeader zhnyPoPlanHeader = zhnyPoPlanHeaderService.addPoPlan(dto);
        return Results.success(zhnyPoPlanHeader);
    }

    @ApiOperation(value = "获取头行单表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/getPoPlan/{poPlanHeaderId}")
    public ResponseEntity<ZhnyPoPlanHeaderDTO> getPoPlan(@PathVariable("organizationId") Long organizationId, @Encrypt @PathVariable("poPlanHeaderId") Long poPlanHeaderId, @ApiIgnore @SortDefault(value = ZhnyPoPlanHeader.FIELD_PO_PLAN_HEADER_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        ZhnyPoPlanHeaderDTO result = zhnyPoPlanHeaderService.getPoPlan(organizationId, poPlanHeaderId, pageRequest);
        return Results.success(result);
    }

    @ApiOperation(value = "删除采购计划头表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/delHeader")
    public ResponseEntity<Void> delHeader(@RequestBody @Encrypt List<Long> ids) {
        zhnyPoPlanHeaderService.delHeader(ids);
        return Results.success();
    }

    @ApiOperation(value = "删除采购计划行表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/delLine")
    public ResponseEntity<Void> delLine(@RequestBody @Encrypt List<Long> ids) {
        zhnyPoPlanHeaderService.delLine(ids);
        return Results.success();
    }

    @ApiOperation(value = "提交采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/submit/{poPlanHeaderId}")
    public ResponseEntity<Void> submit(@Encrypt @PathVariable("poPlanHeaderId") Long poPlanHeaderId) {
        zhnyPoPlanHeaderService.submit(poPlanHeaderId);
        return Results.success();
    }

    @ApiOperation(value = "取消采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/cancel/{poPlanHeaderId}")
    public ResponseEntity<Void> cancel(@Encrypt @PathVariable("poPlanHeaderId") Long poPlanHeaderId) {
        zhnyPoPlanHeaderService.cancel(poPlanHeaderId);
        return Results.success();
    }
}
