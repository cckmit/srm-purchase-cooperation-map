package script.db

databaseChangeLog(logicalFilePath: 'script/db/scux_sinochemintl_po_plan_line.groovy') {
    changeSet(author: "tianjing.gui@hand-china.com", id: "2021-09-08-scux_sinochemintl_po_plan_line") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'scux_sinochemintl_po_plan_line_s', startValue: "1")
        }
        createTable(tableName: "scux_sinochemintl_po_plan_line", remarks: "采购计划行表") {
            column(name: "po_plan_line_id", type: "bigint(20)", autoIncrement: true, remarks: "表ID，主键，供其他表做外键") { constraints(primaryKey: true) }
            column(name: "tenant_id", type: "bigint(20)", remarks: "采购方租户") { constraints(nullable: "false") }
            column(name: "po_plan_header_id", type: "bigint(20)", remarks: "头表id") { constraints(nullable: "false") }
            column(name: "status", type: "varchar(" + 50 * weight + ")", remarks: "状态(值集SCUX.SINOCHEMINTL.PO_PLAN_NUMBER)") { constraints(nullable: "false") }
            column(name: "spell_doc_province", type: "bigint(20)", remarks: "已拼单省区数量") { constraints(nullable: "false") }
            column(name: "need_date", type: "date", remarks: "需求日期") { constraints(nullable: "false") }
            column(name: "plan_shared_province", type: "varchar(" + 50 * weight + ")", remarks: "计划共享省区") { constraints(nullable: "false") }
            column(name: "province_company", type: "varchar(" + 50 * weight + ")", remarks: "省公司/项目") { constraints(nullable: "false") }
            column(name: "product_name", type: "varchar(" + 50 * weight + ")", remarks: "产品名称") { constraints(nullable: "false") }
            column(name: "specification", type: "varchar(" + 50 * weight + ")", remarks: "规格") { constraints(nullable: "false") }
            column(name: "pack", type: "varchar(" + 50 * weight + ")", remarks: "包装")
            column(name: "initial_supplier", type: "varchar(" + 50 * weight + ")", remarks: "初步沟通供应商") { constraints(nullable: "false") }
            column(name: "final_supplier", type: "varchar(" + 50 * weight + ")", remarks: "最终供应商")
            column(name: "factory_price", type: "double(20,6)", remarks: "出厂价") { constraints(nullable: "false") }
            column(name: "delivery_address", type: "varchar(" + 50 * weight + ")", remarks: "发货地址") { constraints(nullable: "false") }
            column(name: "material_coding", type: "varchar(" + 50 * weight + ")", remarks: "物料编码")
            column(name: "material_class", type: "varchar(" + 50 * weight + ")", remarks: "物料分类")
            column(name: "required_quantity", type: "bigint(20)", remarks: "需求数量") { constraints(nullable: "false") }
            column(name: "unit", type: "varchar(" + 30 * weight + ")", remarks: "单位") { constraints(nullable: "false") }
            column(name: "total_purchase_price", type: "double(20,6)", remarks: "采购总价") { constraints(nullable: "false") }
            column(name: "currency_type", type: "varchar(" + 30 * weight + ")", remarks: "币种") { constraints(nullable: "false") }
            column(name: "freight_supplier", type: "varchar(" + 30 * weight + ")", remarks: "运费供应商")
            column(name: "freight_price", type: "double(20,6)", remarks: "运费价格")
            column(name: "price_terms", type: "varchar(" + 30 * weight + ")", remarks: "价格条款")
            column(name: "remark", type: "longtext", remarks: "备注")
            column(name: "tax_type", type: "varchar(" + 30 * weight + ")", remarks: "税种")
            column(name: "tax_rate", type: "double(20,6)", remarks: "税率")
            column(name: "applicant", type: "varchar(" + 30 * weight + ")", remarks: "申请人")
            column(name: "appendix_id", type: "bigint(20)", remarks: "附件id")
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