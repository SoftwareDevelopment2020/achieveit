/*
 Navicat Premium Data Transfer

 Source Server         : aliyun rainkq
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : rainkq.tk:3306
 Source Schema         : achieveit

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 27/02/2020 15:39:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bug
-- ----------------------------
DROP TABLE IF EXISTS `bug`;
CREATE TABLE `bug`
(
    `id`                 int(11) UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `project_id`         int(11)                                                        NOT NULL COMMENT '项目ID',
    `bug_title`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `bug_introducer_id`  int(11)                                                        NOT NULL COMMENT 'bug提出人的ID',
    `bug_responsible_id` int(11)                                                        NOT NULL COMMENT 'bug负责人的ID',
    `start_time`         datetime(0)                                                    NULL DEFAULT NULL COMMENT 'bug开始时间',
    `end_time`           datetime(0)                                                    NULL DEFAULT NULL COMMENT 'bug结束时间',
    `bug_description`    varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'bug描述',
    `priority`           int(11)                                                        NULL DEFAULT NULL COMMENT '优先级',
    `status`             int(11)                                                        NULL DEFAULT NULL COMMENT '缺陷状态：New Opened Processed Solved Closed',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for employee_basics
-- ----------------------------
DROP TABLE IF EXISTS `employee_basics`;
CREATE TABLE `employee_basics`
(
    `id`            int(11) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `email_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `department`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tel`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for feature
-- ----------------------------
DROP TABLE IF EXISTS `feature`;
CREATE TABLE `feature`
(
    `id`             int(11) UNSIGNED                                               NOT NULL AUTO_INCREMENT,
    `project_id`     int(11)                                                        NOT NULL COMMENT '项目ID',
    `first_tier_id`  int(3)                                                         NOT NULL COMMENT '第一层需求ID',
    `second_tier_id` int(3)                                                         NOT NULL COMMENT '第二层需求ID',
    `feature_name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '功能名称',
    `feature_detail` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '功能细节',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for man_hour
-- ----------------------------
DROP TABLE IF EXISTS `man_hour`;
CREATE TABLE `man_hour`
(
    `id`              int(11) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT '工时ID',
    `project_id`      int(11)                                                       NOT NULL,
    `employee_id`     int(11)                                                       NOT NULL,
    `feature_id`      int(11)                                                       NULL DEFAULT NULL,
    `feature_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `activity_id`     int(11)                                                       NULL DEFAULT NULL COMMENT '活动名称',
    `start_time`      datetime(0)                                                   NULL DEFAULT NULL,
    `end_time`        datetime(0)                                                   NULL DEFAULT NULL,
    `auditing_status` int(11)                                                       NULL DEFAULT NULL COMMENT '审核状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for permission_basics
-- ----------------------------
DROP TABLE IF EXISTS `permission_basics`;
CREATE TABLE `permission_basics`
(
    `id`     int(11) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'permission_id',
    `name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'permission_name',
    `detail` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'permission_detail  explaination',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for person_permission
-- ----------------------------
DROP TABLE IF EXISTS `person_permission`;
CREATE TABLE `person_permission`
(
    `id`                  int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `project_employee_id` int(11)          NOT NULL,
    `permission_id`       int(11)          NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for person_role
-- ----------------------------
DROP TABLE IF EXISTS `person_role`;
CREATE TABLE `person_role`
(
    `id`                  int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `project_employee_id` int(11)          NOT NULL,
    `role_id`             int(11)          NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_basics
-- ----------------------------
DROP TABLE IF EXISTS `project_basics`;
CREATE TABLE `project_basics`
(
    `id`                                           int(11) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `project_id`                                   char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT 'project_id  由“四位年份-四位客户代码-研发类型1位（开发：D，维护：M，服务：S，其他：O）-顺序号2位”构成，且从外部系统导入，是一个选择项，不可更改。',
    `client_id`                                    int(11)                                                       NULL DEFAULT NULL COMMENT '客户ID 从客户管理系统中拉取详细信息',
    `scheduled_date`                               date                                                          NULL DEFAULT NULL COMMENT '预定时间 项目预期开始时间',
    `delivery_date`                                date                                                          NULL DEFAULT NULL COMMENT '交付日期 项目预期结束时间',
    `superior`                                     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目上级 负责项目立项审批',
    `major_milestone`                              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主要里程碑?????',
    `main_technique`                               varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主要技术 语言 平台 架构 工具等',
    `business_field`                               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务领域',
    `main_function`                                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主要业务功能',
    `git_address`                                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'git仓库地址',
    `status_id`                                    int(11)                                                       NULL DEFAULT NULL COMMENT '项目状态id',
    `is_archived`                                  tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目是否归档',
    `project_basic_datasheet`                      tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目基础数据表是否提交',
    `project_proposal`                             tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目提案书是否提交',
    `project_quotation`                            tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目报价书是否提交',
    `project_estimates`                            tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目估算表',
    `project_plan`                                 tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目计划书',
    `project_process_crop_table`                   tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目过程裁剪表',
    `project_cost_management_table`                tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目成本管理表',
    `project_requirements_change_management_table` tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目需求变更管理表',
    `project_risk_management_table`                tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目风险管理表',
    `client_check_problems_table`                  tinyint(1)                                                    NULL DEFAULT NULL COMMENT '客户验收问题表',
    `client_check_report`                          tinyint(1)                                                    NULL DEFAULT NULL COMMENT '客户验收报告',
    `project_summary`                              tinyint(1)                                                    NULL DEFAULT NULL COMMENT '项目总结',
    `experience_and_lessons`                       tinyint(1)                                                    NULL DEFAULT NULL COMMENT '最佳经验和教训',
    `development_tools`                            tinyint(1)                                                    NULL DEFAULT NULL COMMENT '开发工具',
    `development_templates`                        tinyint(1)                                                    NULL DEFAULT NULL COMMENT '开发模板',
    `check_sheets`                                 tinyint(1)                                                    NULL DEFAULT NULL COMMENT '各阶段检查单',
    `qa_summary`                                   tinyint(1)                                                    NULL DEFAULT NULL COMMENT 'QA总结',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_employee
-- ----------------------------
DROP TABLE IF EXISTS `project_employee`;
CREATE TABLE `project_employee`
(
    `id`          int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'project_employee_id',
    `project_id`  int(11)          NOT NULL,
    `employee_id` int(11)          NOT NULL,
    `join_time`   date             NULL DEFAULT NULL,
    `exit_time`   date             NULL DEFAULT NULL,
    `superior_id` int(11)          NULL DEFAULT NULL COMMENT '上级id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_status_basics
-- ----------------------------
DROP TABLE IF EXISTS `project_status_basics`;
CREATE TABLE `project_status_basics`
(
    `id`     int(11) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'project_status_id',
    `name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'project_status_name',
    `detail` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'project_status_detail explaination',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for property
-- ----------------------------
DROP TABLE IF EXISTS `property`;
CREATE TABLE `property`
(
    `id`               int(11) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT '资产ID',
    `type`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资产类型',
    `manager_id`       int(11)                                                       NULL DEFAULT NULL COMMENT '资产管理者',
    `usage_time_limit` date                                                          NULL DEFAULT NULL COMMENT '资产使用期限',
    `device_condition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备状态 是否完好',
    `is_returned`      tinyint(1)                                                    NULL DEFAULT NULL COMMENT '设备是否归还',
    `return_date`      date                                                          NULL DEFAULT NULL COMMENT '归还日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for risk
-- ----------------------------
DROP TABLE IF EXISTS `risk`;
CREATE TABLE `risk`
(
    `id`          int(11) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `project_id`  int(11)                                                       NOT NULL COMMENT '项目ID',
    `type`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '风险类型',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '风险描述',
    `level`       int(11)                                                       NULL DEFAULT NULL COMMENT '风险级别',
    `affect`      int(11)                                                       NULL DEFAULT NULL COMMENT '风险影响度',
    `react`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '风险应对',
    `strategy`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '策略',
    `status`      int(255)                                                      NULL DEFAULT NULL COMMENT '风险状态',
    `responsible` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '风险责任人',
    `track_freq`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '风险跟踪频度',
    `related`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '风险相关者',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_basics
-- ----------------------------
DROP TABLE IF EXISTS `role_basics`;
CREATE TABLE `role_basics`
(
    `id`     int(11) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'role_id',
    `name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'role_name',
    `detail` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'role_detail  explaination',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
