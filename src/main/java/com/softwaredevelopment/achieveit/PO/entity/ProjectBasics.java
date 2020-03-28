package com.softwaredevelopment.achieveit.PO.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author RainkQ
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ProjectBasics对象", description = "")
public class ProjectBasics implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "project_id  由“四位年份-四位客户代码-研发类型1位（开发：D，维护：M，服务：S，其他：O）-顺序号2位”构成，且从外部系统导入，是一个选择项，不可更改。")
    private String projectId;

    @ApiModelProperty(value = "项目经理的ID")
    private Integer projectManagerId;

    @ApiModelProperty(value = "项目经理的姓名")
    private String projectManagerName;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "客户ID 从客户管理系统中拉取详细信息")
    private Integer clientId;

    @ApiModelProperty(value = "预定时间 项目预期开始时间")
    private LocalDate scheduledDate;

    @ApiModelProperty(value = "交付日期 项目预期结束时间")
    private LocalDate deliveryDate;

    @ApiModelProperty(value = "项目上级 负责项目立项审批")
    private Integer superior;

    @ApiModelProperty(value = "主要里程碑?????")
    private String majorMilestone;

    @ApiModelProperty(value = "主要技术 语言 平台 架构 工具等")
    private String mainTechnique;

    @ApiModelProperty(value = "业务领域")
    private String businessField;

    @ApiModelProperty(value = "主要业务功能")
    private String mainFunction;

    @ApiModelProperty(value = "git仓库地址")
    private String gitAddress;

    @ApiModelProperty(value = "项目状态id " +
            "0 已归档\n" +
            "1 审核中\n" +
            "2 驳回\n" +
            "3 进行中  小状态用后三位表示 比如3001 3101  只有3111时表示正式立项完成 开始干活")
    private Integer statusId;

    @ApiModelProperty(value = "项目是否归档 暂时废弃")
    private Boolean isArchived;

    @ApiModelProperty(value = "项目基础数据表是否提交")
    private Boolean projectBasicDatasheet;

    @ApiModelProperty(value = "项目提案书是否提交")
    private Boolean projectProposal;

    @ApiModelProperty(value = "项目报价书是否提交")
    private Boolean projectQuotation;

    @ApiModelProperty(value = "项目估算表")
    private Boolean projectEstimates;

    @ApiModelProperty(value = "项目计划书")
    private Boolean projectPlan;

    @ApiModelProperty(value = "项目过程裁剪表")
    private Boolean projectProcessCropTable;

    @ApiModelProperty(value = "项目成本管理表")
    private Boolean projectCostManagementTable;

    @ApiModelProperty(value = "项目需求变更管理表")
    private Boolean projectRequirementsChangeManagementTable;

    @ApiModelProperty(value = "项目风险管理表")
    private Boolean projectRiskManagementTable;

    @ApiModelProperty(value = "客户验收问题表")
    private Boolean clientCheckProblemsTable;

    @ApiModelProperty(value = "客户验收报告")
    private Boolean clientCheckReport;

    @ApiModelProperty(value = "项目总结")
    private Boolean projectSummary;

    @ApiModelProperty(value = "最佳经验和教训")
    private Boolean experienceAndLessons;

    @ApiModelProperty(value = "开发工具")
    private Boolean developmentTools;

    @ApiModelProperty(value = "开发模板")
    private Boolean developmentTemplates;

    @ApiModelProperty(value = "各阶段检查单")
    private Boolean checkSheets;

    @ApiModelProperty(value = "QA总结")
    private Boolean qaSummary;

    @Override
    public String toString() {
        return "ProjectBasics{" + "\n" +
                "id=" + id + "\n" +
                ", projectId='" + projectId + '\'' + "\n" +
                ", projectManagerId=" + projectManagerId + "\n" +
                ", projectManagerName='" + projectManagerName + '\'' + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", clientId=" + clientId + "\n" +
                ", scheduledDate=" + scheduledDate + "\n" +
                ", deliveryDate=" + deliveryDate + "\n" +
                ", superior=" + superior + "\n" +
                ", majorMilestone='" + majorMilestone + '\'' + "\n" +
                ", mainTechnique='" + mainTechnique + '\'' + "\n" +
                ", businessField='" + businessField + '\'' + "\n" +
                ", mainFunction='" + mainFunction + '\'' + "\n" +
                ", gitAddress='" + gitAddress + '\'' + "\n" +
                ", statusId=" + statusId + "\n" +
                ", isArchived=" + isArchived + "\n" +
                ", projectBasicDatasheet=" + projectBasicDatasheet + "\n" +
                ", projectProposal=" + projectProposal + "\n" +
                ", projectQuotation=" + projectQuotation + "\n" +
                ", projectEstimates=" + projectEstimates + "\n" +
                ", projectPlan=" + projectPlan + "\n" +
                ", projectProcessCropTable=" + projectProcessCropTable + "\n" +
                ", projectCostManagementTable=" + projectCostManagementTable + "\n" +
                ", projectRequirementsChangeManagementTable=" + projectRequirementsChangeManagementTable + "\n" +
                ", projectRiskManagementTable=" + projectRiskManagementTable + "\n" +
                ", clientCheckProblemsTable=" + clientCheckProblemsTable + "\n" +
                ", clientCheckReport=" + clientCheckReport + "\n" +
                ", projectSummary=" + projectSummary + "\n" +
                ", experienceAndLessons=" + experienceAndLessons + "\n" +
                ", developmentTools=" + developmentTools + "\n" +
                ", developmentTemplates=" + developmentTemplates + "\n" +
                ", checkSheets=" + checkSheets + "\n" +
                ", qaSummary=" + qaSummary +
                '}';
    }
}
