package org.srm.purchasecooperation.cux.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanExcelDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanHeaderDTO;
import org.srm.purchasecooperation.cux.api.dto.SinochemintlPoPlanLineDTO;
import org.srm.purchasecooperation.cux.app.service.SinochemintlPoPlanService;
import org.srm.purchasecooperation.pr.api.dto.PrActionDTO;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 采购计划 管理 API
 *
 * @author tianjing.gui@hand-china.com 2021-09-06 09:59:20
 */
@Api(
        tags = {"sinochemintl-po-plan"}
)
@RestController("SinochemintlPoPlanController.v1")
@RequestMapping("/v1/{organizationId}/sinochemintl-po-plans")
public class SinochemintlPoPlanController extends BaseController {

    private final SinochemintlPoPlanService sinochemintlPoPlanHeaderService;

    @Autowired
    public SinochemintlPoPlanController(SinochemintlPoPlanService sinochemintlPoPlanHeaderService) {
        this.sinochemintlPoPlanHeaderService = sinochemintlPoPlanHeaderService;
    }

    @ApiOperation(value = "采购计划头表列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/list")
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<SinochemintlPoPlanHeaderDTO>> list(@PathVariable("organizationId") Long organizationId, SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO, @ApiIgnore @SortDefault(value = "poPlanHeaderId",
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        sinochemintlPoPlanHeaderDTO.setTenantId(organizationId);
        Page<SinochemintlPoPlanHeaderDTO> list = sinochemintlPoPlanHeaderService.list(sinochemintlPoPlanHeaderDTO, pageRequest);
        return Results.success(list);
    }

    @ApiOperation(value = "采购计划明细查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/detailList")
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<SinochemintlPoPlanLineDTO>> detailList(@PathVariable("organizationId") Long organizationId, SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO, @ApiIgnore @SortDefault(value = "poPlanHeaderId",
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        sinochemintlPoPlanHeaderDTO.setTenantId(organizationId);
        Page<SinochemintlPoPlanLineDTO> list = sinochemintlPoPlanHeaderService.detailList(sinochemintlPoPlanHeaderDTO, pageRequest);
        return Results.success(list);
    }

    @ApiOperation(value = "新增/保存/修改采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/addPoPlan")
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<SinochemintlPoPlanHeaderDTO> addPoPlan(@PathVariable("organizationId") Long organizationId,
                                                                 @RequestBody @Encrypt SinochemintlPoPlanHeaderDTO dto,
                                                                 @ApiIgnore @SortDefault(value = "poPlanHeaderId", direction = Sort.Direction.DESC) PageRequest pageRequest) {
        dto.setTenantId(organizationId);
        SinochemintlPoPlanHeaderDTO sinochemintlPoPlanHeaderDTO = sinochemintlPoPlanHeaderService.addPoPlan(dto, pageRequest);
        return Results.success(sinochemintlPoPlanHeaderDTO);
    }

    @ApiOperation(value = "获取头行单表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/getPoPlan/{poPlanHeaderId}")
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<SinochemintlPoPlanHeaderDTO> getPoPlan(@PathVariable("organizationId") Long organizationId, @Encrypt @PathVariable("poPlanHeaderId") Long poPlanHeaderId, @ApiIgnore @SortDefault(value = "poPlanHeaderId",
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        SinochemintlPoPlanHeaderDTO result = sinochemintlPoPlanHeaderService.getPoPlan(organizationId, poPlanHeaderId, pageRequest);
        return Results.success(result);
    }

    @ApiOperation(value = "获取行表列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/getPoPlanLine/{poPlanHeaderId}")
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<SinochemintlPoPlanLineDTO>> getPoPlanLine(@PathVariable("organizationId") Long organizationId,
                                                                         @Encrypt @PathVariable("poPlanHeaderId") Long poPlanHeaderId,
                                                                         @ApiIgnore @SortDefault(value = "poPlanHeaderId", direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<SinochemintlPoPlanLineDTO> result = sinochemintlPoPlanHeaderService.getPoPlanLine(organizationId, poPlanHeaderId, pageRequest);
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

    @ApiOperation(value = "提交采购计划头")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit")
    public ResponseEntity<Void> submit(@PathVariable("organizationId") Long organizationId, @RequestBody @Encrypt List<Long> ids) {
        sinochemintlPoPlanHeaderService.submit(organizationId, ids);
        return Results.success();
    }

    @ApiOperation(value = "取消采购计划")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/cancel/{poPlanHeaderId}")
    public ResponseEntity<Void> cancel(@Encrypt @PathVariable("poPlanHeaderId") Long poPlanHeaderId) {
        sinochemintlPoPlanHeaderService.cancel(poPlanHeaderId);
        return Results.success();
    }

    @ApiOperation(value = "采购计划导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/excel")
    @ExcelExport(value = SinochemintlPoPlanExcelDTO.class)
    @CustomPageRequest
    public ResponseEntity<List<SinochemintlPoPlanExcelDTO>> excel(@PathVariable("organizationId") Long tenantId,
                                                                  String poPlanHeaderIds,
                                                                  ExportParam exportParam,
                                                                  HttpServletResponse response) {
        List<SinochemintlPoPlanExcelDTO> list = sinochemintlPoPlanHeaderService.excel(poPlanHeaderIds);
        return Results.success(list);
    }

    @ApiOperation(value = "采购计划批量导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/batchExcel")
    @ExcelExport(value = SinochemintlPoPlanExcelDTO.class)
    @CustomPageRequest
    public ResponseEntity<List<SinochemintlPoPlanExcelDTO>> batchExcel(@PathVariable("organizationId") Long tenantId,
                                                                       SinochemintlPoPlanHeaderDTO dto,
                                                                       ExportParam exportParam,
                                                                       HttpServletResponse response) {
        dto.setTenantId(tenantId);
        List<SinochemintlPoPlanExcelDTO> list = sinochemintlPoPlanHeaderService.batchExcel(dto);
        return Results.success(list);
    }

    @ApiOperation(value = "采购计划行级导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/excelLine")
    @ExcelExport(value = SinochemintlPoPlanExcelDTO.class)
    @CustomPageRequest
    public ResponseEntity<List<SinochemintlPoPlanExcelDTO>> excelLine(@PathVariable("organizationId") Long tenantId,
                                                                      String poPlanLineIds,
                                                                      ExportParam exportParam,
                                                                      HttpServletResponse response) {
        List<SinochemintlPoPlanExcelDTO> list = sinochemintlPoPlanHeaderService.excelLine(poPlanLineIds);
        return Results.success(list);
    }

    @ApiOperation(value = "采购计划行级全导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/batchExcelLine")
    @ExcelExport(value = SinochemintlPoPlanExcelDTO.class)
    @CustomPageRequest
    public ResponseEntity<List<SinochemintlPoPlanExcelDTO>> batchExcelLine(@PathVariable("organizationId") Long tenantId,
                                                                       SinochemintlPoPlanHeaderDTO dto,
                                                                       ExportParam exportParam,
                                                                       HttpServletResponse response) {
        dto.setTenantId(tenantId);
        List<SinochemintlPoPlanExcelDTO> list = sinochemintlPoPlanHeaderService.batchExcelLine(dto);
        return Results.success(list);
    }

    @ApiOperation(value = "采购计划确认")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/confirm")
    public ResponseEntity<Void> confirm(@PathVariable("organizationId") Long organizationId, @RequestBody @Encrypt SinochemintlPoPlanHeaderDTO dto) {
        dto.setTenantId(organizationId);
        sinochemintlPoPlanHeaderService.confirm(dto);
        return Results.success();
    }

    @ApiOperation(value = "批量采购计划确认")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/batchConfirm")
    public ResponseEntity<Void> batchConfirm(@PathVariable("organizationId") Long organizationId, @RequestBody @Encrypt List<Long> ids) {
        sinochemintlPoPlanHeaderService.batchConfirm(organizationId, ids);
        return Results.success();
    }

    @ApiOperation(value = "操作记录")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/operatingRecord/{poPlanHeaderId}")
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<PrActionDTO>> operatingRecord(@PathVariable("organizationId") Long organizationId,
                                                             @Encrypt @PathVariable("poPlanHeaderId") Long poPlanHeaderId,
                                                             @ApiIgnore @SortDefault(value = "poPlanHeaderId", direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<PrActionDTO> response = sinochemintlPoPlanHeaderService.operatingRecord(organizationId, poPlanHeaderId, pageRequest);
        return Results.success(response);
    }

    @ApiOperation(value = "批量维护")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/batchMaint")
    public ResponseEntity<Void> batchMaint(@PathVariable("organizationId") Long organizationId,
                                           @RequestBody @Encrypt SinochemintlPoPlanLineDTO dto) {
        dto.setTenantId(organizationId);
        sinochemintlPoPlanHeaderService.batchMaint(dto);
        return Results.success();
    }

    @ApiOperation(value = "拼单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/shareTheBill")
    public ResponseEntity<SinochemintlPoPlanLineDTO> shareBill(@PathVariable("organizationId") Long organizationId,
                                                               @RequestBody @Encrypt SinochemintlPoPlanLineDTO dto) {
        dto.setTenantId(organizationId);
        SinochemintlPoPlanLineDTO response = sinochemintlPoPlanHeaderService.shareTheBill(dto);
        return Results.success(response);
    }

    @ApiOperation(value = "根据登陆人公司和共享省区取交集")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/province/{userId}/{applicantId}")
    public ResponseEntity<List<Map<String, Object>>> province(@PathVariable("organizationId") Long organizationId,
                                                              @PathVariable("userId") Long userId,
                                                              @PathVariable("applicantId") Long applicantId) {
        List<Map<String, Object>> result = sinochemintlPoPlanHeaderService.province(organizationId, userId, applicantId);
        return Results.success(result);
    }

}
