package script.db

databaseChangeLog(logicalFilePath: 'script/db/scux_sinochemintl_po_plan_header.groovy') {
    changeSet(author: "tianjing.gui@hand-china.com", id: "2021-09-08-scux_sinochemintl_po_plan_header") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'scux_sinochemintl_po_plan_header_s', startValue: "1")
        }
        createTable(tableName: "scux_sinochemintl_po_plan_header", remarks: "采购计划头表") {
            column(name: "po_plan_header_id", type: "bigint(20)", autoIncrement: true, remarks: "表ID，主键，供其他表做外键") { constraints(primaryKey: true) }
            column(name: "tenant_id", type: "bigint(20)", remarks: "采购方租户") { constraints(nullable: "false") }
            column(name: "title", type: "varchar(" + 50 * weight + ")", remarks: "标题") { constraints(nullable: "false") }
            column(name: "status", type: "varchar(" + 50 * weight + ")", remarks: "状态(值集SCUX.SINOCHEMINTL.PO_PLAN_NUMBER)") { constraints(nullable: "false") }
            column(name: "plan_type", type: "varchar(" + 50 * weight + ")", remarks: "计划类型") { constraints(nullable: "false") }
            column(name: "po_plan_number", type: "varchar(" + 50 * weight + ")", remarks: "采购计划单号") { constraints(nullable: "false") }
            column(name: "application_sum", type: "double(20,6)", remarks: "申请总额") { constraints(nullable: "false") }
            column(name: "create_id", type: "bigint(20)", remarks: "创建人id")
            column(name: "create_name", type: "varchar(" + 30 * weight + ")", remarks: "创建人") { constraints(nullable: "false") }
            column(name: "deadline", type: "datetime", remarks: "拼单截至时间") { constraints(nullable: "false") }
            column(name: "company_code", type: "varchar(" + 50 * weight + ")", remarks: "公司编码")
            column(name: "company_name", type: "varchar(" + 50 * weight + ")", remarks: "公司") { constraints(nullable: "false") }
            column(name: "business_code", type: "varchar(" + 50 * weight + ")", remarks: "业务实体编码")
            column(name: "business_name", type: "varchar(" + 50 * weight + ")", remarks: "业务实体") { constraints(nullable: "false") }
            column(name: "purchase_org_code", type: "varchar(" + 50 * weight + ")", remarks: "采购组织编码")
            column(name: "purchase_org_name", type: "varchar(" + 50 * weight + ")", remarks: "采购组织") { constraints(nullable: "false") }
            column(name: "purchase_code", type: "varchar(" + 50 * weight + ")", remarks: "采购员编码")
            column(name: "purchase_name", type: "varchar(" + 30 * weight + ")", remarks: "采购员") { constraints(nullable: "false") }
            column(name: "po_source", type: "varchar(" + 50 * weight + ")", remarks: "单据来源") { constraints(nullable: "false") }
            column(name: "applicant", type: "varchar(" + 50 * weight + ")", remarks: "申请人") { constraints(nullable: "false") }
            column(name: "department_code", type: "varchar(" + 50 * weight + ")", remarks: "部门编码")
            column(name: "Department_name", type: "varchar(" + 50 * weight + ")", remarks: "所属部门") { constraints(nullable: "false") }
            column(name: "application_date", type: "datetime", remarks: "申请日期") { constraints(nullable: "false") }
            column(name: "original_currency", type: "varchar(" + 30 * weight + ")", remarks: "原币币种")
            column(name: "appendix_id", type: "bigint(20)", remarks: "附件id")
            column(name: "plan_illustrate", type: "longtext", remarks: "计划说明")
            column(name: "object_version_number", type: "bigint(20)", defaultValue: "1", remarks: "行版本号，用来处理锁") { constraints(nullable: "false") }
            column(name: "creation_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") { constraints(nullable: "false") }
            column(name: "created_by", type: "bigint(20)", defaultValue: "-1", remarks: "") { constraints(nullable: "false") }
            column(name: "last_updated_by", type: "bigint(20)", defaultValue: "-1", remarks: "") { constraints(nullable: "false") }
            column(name: "last_update_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") { constraints(nullable: "false") }
            column(name: "attribute1", type: "varchar(" + 150 * weight + ")", remarks: "扩展字段1")
            column(name: "attribute2", type: "varchar(" + 150 * weight + ")", remarks: "扩展字段2")
            column(name: "attribute3", type: "varchar(" + 150 * weight + ")", remarks: "扩展字段3")
            column(name: "attribute4", type: "varchar(" + 150 * weight + ")", remarks: "扩展字段4")
            column(name: "attribute5", type: "varchar(" + 150 * weight + ")", remarks: "扩展字段5")
            column(name: "attribute6", type: "varchar(" + 150 * weight + ")", remarks: "扩展字段6")
            column(name: "attribute7", type: "varchar(" + 150 * weight + ")", remarks: "扩展字段7")
            column(name: "attribute8", type: "varchar(" + 150 * weight + ")", remarks: "扩展字段8")
            column(name: "attribute9", type: "varchar(" + 150 * weight + ")", remarks: "扩展字段9")
            column(name: "attribute10", type: "varchar(" + 150 * weight + ")", remarks: "扩展字段10")
        }
    }
}