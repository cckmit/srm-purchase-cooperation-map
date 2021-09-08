package org.srm.purchasecooperation.cux.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.app.service.SinochemintlPoPlanService;
import org.srm.purchasecooperation.cux.domain.entity.SinochemintlPoPlanHeader;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 采购计划 管理 API
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@RestController("sinochemintlPoPlanHeaderController.v1")
@RequestMapping("/v1/{organizationId}/sinochemintl-po-plan-headers")
public class SinochemintlPoPlanController extends BaseController {

    private final SinochemintlPoPlanService sinochemintlPoPlanHeaderService;

    @Autowired
    public SinochemintlPoPlanController(SinochemintlPoPlanService sinochemintlPoPlanHeaderService) {
        this.sinochemintlPoPlanHeaderService = sinochemintlPoPlanHeaderService;
    }

    @ApiOperation(value = "采购计划头表列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/list")
    @ProcessLovValue
    public ResponseEntity<Page<SinochemintlPoPlanHeader>> list(@PathVariable("organizationId") Long organizationId, SinochemintlPoPlanHeader sinochemintlPoPlanHeader, @ApiIgnore @SortDefault(value = SinochemintlPoPlanHeader.FIELD_PO_PLAN_HEADER_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        sinochemintlPoPlanHeader.setTenantId(organizationId);
        Page<SinochemintlPoPlanHeader> list = sinochemintlPoPlanHeaderService.list(sinochemintlPoPlanHeader, pageRequest);
        return Results.success(list);
    }

    @ApiOperation(value = "新增/保存/修改采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/addPoPlan")
    @ProcessLovValue
    public ResponseEntity<SinochemintlPoPlanHeader> addPoPlan(@PathVariable("organizationId") Long organizationId, @RequestBody @Encrypt SinochemintlPoPlanHeaderDTO dto) {
        dto.setTenantId(organizationId);
        SinochemintlPoPlanHeader sinochemintlPoPlanHeader = sinochemintlPoPlanHeaderService.addPoPlan(dto);
        return Results.success(sinochemintlPoPlanHeader);
    }

    @ApiOperation(value = "获取头行单表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/getPoPlan/{poPlanHeaderId}")
    @ProcessLovValue
    public ResponseEntity<SinochemintlPoPlanHeaderDTO> getPoPlan(@PathVariable("organizationId") Long organizationId, @Encrypt @PathVariable("poPlanHeaderId") Long poPlanHeaderId, @ApiIgnore @SortDefault(value = SinochemintlPoPlanHeader.FIELD_PO_PLAN_HEADER_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        SinochemintlPoPlanHeaderDTO result = sinochemintlPoPlanHeaderService.getPoPlan(organizationId, poPlanHeaderId, pageRequest);
        return Results.success(result);
    }

    @ApiOperation(value = "删除采购计划头表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/delHeader")
    public ResponseEntity<Void> delHeader(@RequestBody @Encrypt List<Long> ids) {
        sinochemintlPoPlanHeaderService.delHeader(ids);
        return Results.success();
    }

    @ApiOperation(value = "删除采购计划行表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/delLine")
    public ResponseEntity<Void> delLine(@RequestBody @Encrypt List<Long> ids) {
        sinochemintlPoPlanHeaderService.delLine(ids);
        return Results.success();
    }

    @ApiOperation(value = "提交采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/submit/{poPlanHeaderId}")
    public ResponseEntity<Void> submit(@Encrypt @PathVariable("poPlanHeaderId") Long poPlanHeaderId) {
        sinochemintlPoPlanHeaderService.submit(poPlanHeaderId);
        return Results.success();
    }

    @ApiOperation(value = "取消采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/cancel/{poPlanHeaderId}")
    public ResponseEntity<Void> cancel(@Encrypt @PathVariable("poPlanHeaderId") Long poPlanHeaderId) {
        sinochemintlPoPlanHeaderService.cancel(poPlanHeaderId);
        return Results.success();
    }
}
