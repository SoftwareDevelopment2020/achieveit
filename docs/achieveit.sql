create table bug
(
    id                 int(11) unsigned auto_increment
        primary key,
    project_id         int           not null comment '项目ID',
    bug_title          varchar(255)  not null,
    bug_introducer_id  int           not null comment 'bug提出人的ID',
    bug_responsible_id int           not null comment 'bug负责人的ID',
    start_time         datetime      null comment 'bug开始时间',
    end_time           datetime      null comment 'bug结束时间',
    bug_description    varchar(1023) null comment 'bug描述',
    priority           int           null comment '优先级',
    status             int           null comment '缺陷状态：New Opened Processed Solved Closed'
);

INSERT INTO achieveit.bug (id, project_id, bug_title, bug_introducer_id, bug_responsible_id, start_time, end_time,
                           bug_description, priority, status)
VALUES (1, 1, '1', 1, 1, '2020-02-27 14:08:37', '2020-02-27 14:08:39', '1', 1, 1);
create table employee_basics
(
    id            int(11) unsigned auto_increment
        primary key,
    name          varchar(255) not null,
    email_address varchar(255) null,
    department    varchar(255) null,
    tel           varchar(255) null
);

INSERT INTO achieveit.employee_basics (id, name, email_address, department, tel)
VALUES (1, '张三', 'wowowo', null, null);
INSERT INTO achieveit.employee_basics (id, name, email_address, department, tel)
VALUES (2, '李四', 'lililili', null, null);
create table feature
(
    id             int(11) unsigned auto_increment
        primary key,
    project_id     int           not null comment '项目ID',
    first_tier_id  int(3)        not null comment '第一层需求ID',
    second_tier_id int(3)        not null comment '第二层需求ID',
    feature_name   varchar(255)  not null comment '功能名称',
    feature_detail varchar(1023) null comment '功能细节'
);


create table man_hour
(
    id              int(11) unsigned auto_increment comment '工时ID'
        primary key,
    project_id      int          not null,
    employee_id     int          not null,
    feature_id      int          null,
    feature_name    varchar(255) null,
    activity_id     int          null comment '活动名称',
    start_time      datetime     null,
    end_time        datetime     null,
    auditing_status int          null comment '审核状态'
);


create table permission_basics
(
    id     int(11) unsigned auto_increment comment 'permission_id'
        primary key,
    name   varchar(255) not null comment 'permission_name',
    detail varchar(511) null comment 'permission_detail  explaination'
);

INSERT INTO achieveit.permission_basics (id, name, detail)
VALUES (0, 'enter', 'basic permission to access to the project');
INSERT INTO achieveit.permission_basics (id, name, detail)
VALUES (1, 'git', 'git read permission');
INSERT INTO achieveit.permission_basics (id, name, detail)
VALUES (2, 'filesys', 'filesys read permission');
INSERT INTO achieveit.permission_basics (id, name, detail)
VALUES (3, 'mail', 'mail read permission');
create table person_permission
(
    id                  int(11) unsigned auto_increment
        primary key,
    project_employee_id int not null,
    permission_id       int not null
);

create index idx_project_employee_id
    on person_permission (project_employee_id);

INSERT INTO achieveit.person_permission (id, project_employee_id, permission_id)
VALUES (1, 1, 0);
INSERT INTO achieveit.person_permission (id, project_employee_id, permission_id)
VALUES (2, 1, 2);
INSERT INTO achieveit.person_permission (id, project_employee_id, permission_id)
VALUES (3, 1, 3);
INSERT INTO achieveit.person_permission (id, project_employee_id, permission_id)
VALUES (4, 2, 0);
INSERT INTO achieveit.person_permission (id, project_employee_id, permission_id)
VALUES (5, 2, 2);
create table person_role
(
    id                  int(11) unsigned auto_increment
        primary key,
    project_employee_id int not null,
    role_id             int not null
);

INSERT INTO achieveit.person_role (id, project_employee_id, role_id)
VALUES (1, 1, 1);
create table project_basics
(
    id                                           int(11) unsigned auto_increment
        primary key,
    project_id                                   char(11)     not null comment 'project_id  由“四位年份-四位客户代码-研发类型1位（开发：D，维护：M，服务：S，其他：O）-顺序号2位”构成，且从外部系统导入，是一个选择项，不可更改。',
    project_manager_id                           int          not null comment '项目经理的id',
    project_manager_name                         varchar(255) null,
    name                                         varchar(255) null comment '项目名称',
    status_id                                    int          null comment '项目状态id 0 已归档
1 审核中
2 驳回
3 进行中  小状态用后三位表示 比如3001 3101  只有3111时表示正式立项完成 开始干活',
    is_archived                                  tinyint(1)   null comment '项目是否归档 暂时废弃',
    scheduled_date                               date         null comment '预定时间 项目预期开始时间',
    delivery_date                                date         null comment '交付日期 项目预期结束时间',
    client_id                                    int          null comment '客户ID 从客户管理系统中拉取详细信息',
    superior                                     varchar(255) null comment '项目上级 负责项目立项审批',
    major_milestone                              varchar(255) null comment '主要里程碑?????',
    main_technique                               varchar(512) null comment '主要技术 语言 平台 架构 工具等',
    business_field                               varchar(255) null comment '业务领域',
    main_function                                varchar(255) null comment '主要业务功能',
    git_address                                  varchar(255) null comment 'git仓库地址',
    project_basic_datasheet                      tinyint(1)   null comment '项目基础数据表是否提交',
    project_proposal                             tinyint(1)   null comment '项目提案书是否提交',
    project_quotation                            tinyint(1)   null comment '项目报价书是否提交',
    project_estimates                            tinyint(1)   null comment '项目估算表',
    project_plan                                 tinyint(1)   null comment '项目计划书',
    project_process_crop_table                   tinyint(1)   null comment '项目过程裁剪表',
    project_cost_management_table                tinyint(1)   null comment '项目成本管理表',
    project_requirements_change_management_table tinyint(1)   null comment '项目需求变更管理表',
    project_risk_management_table                tinyint(1)   null comment '项目风险管理表',
    client_check_problems_table                  tinyint(1)   null comment '客户验收问题表',
    client_check_report                          tinyint(1)   null comment '客户验收报告',
    project_summary                              tinyint(1)   null comment '项目总结',
    experience_and_lessons                       tinyint(1)   null comment '最佳经验和教训',
    development_tools                            tinyint(1)   null comment '开发工具',
    development_templates                        tinyint(1)   null comment '开发模板',
    check_sheets                                 tinyint(1)   null comment '各阶段检查单',
    qa_summary                                   tinyint(1)   null comment 'QA总结'
);

create index idx_name
    on project_basics (name)
    comment '对项目名称的索引';

create index idx_project_id
    on project_basics (project_id)
    comment '对项目ID的索引';

INSERT INTO achieveit.project_basics (id, project_id, project_manager_id, project_manager_name, name, status_id,
                                      is_archived, scheduled_date, delivery_date, client_id, superior, major_milestone,
                                      main_technique, business_field, main_function, git_address,
                                      project_basic_datasheet, project_proposal, project_quotation, project_estimates,
                                      project_plan, project_process_crop_table, project_cost_management_table,
                                      project_requirements_change_management_table, project_risk_management_table,
                                      client_check_problems_table, client_check_report, project_summary,
                                      experience_and_lessons, development_tools, development_templates, check_sheets,
                                      qa_summary)
VALUES (1, '12345678901', 1, '张三', '测试项目1', 1, 1, '2020-02-28', '2020-02-28', 1, '1', '1', '1', '1', '1', '1', 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO achieveit.project_basics (id, project_id, project_manager_id, project_manager_name, name, status_id,
                                      is_archived, scheduled_date, delivery_date, client_id, superior, major_milestone,
                                      main_technique, business_field, main_function, git_address,
                                      project_basic_datasheet, project_proposal, project_quotation, project_estimates,
                                      project_plan, project_process_crop_table, project_cost_management_table,
                                      project_requirements_change_management_table, project_risk_management_table,
                                      client_check_problems_table, client_check_report, project_summary,
                                      experience_and_lessons, development_tools, development_templates, check_sheets,
                                      qa_summary)
VALUES (2, '23456789012', 1, '张三', '测试项目2', 2, 0, '2020-03-18', '2020-03-28', 1, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO achieveit.project_basics (id, project_id, project_manager_id, project_manager_name, name, status_id,
                                      is_archived, scheduled_date, delivery_date, client_id, superior, major_milestone,
                                      main_technique, business_field, main_function, git_address,
                                      project_basic_datasheet, project_proposal, project_quotation, project_estimates,
                                      project_plan, project_process_crop_table, project_cost_management_table,
                                      project_requirements_change_management_table, project_risk_management_table,
                                      client_check_problems_table, client_check_report, project_summary,
                                      experience_and_lessons, development_tools, development_templates, check_sheets,
                                      qa_summary)
VALUES (3, '12310239109', 1, '张三', '测试项目3', 3000, 0, '2020-03-01', '2020-03-01', 2, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO achieveit.project_basics (id, project_id, project_manager_id, project_manager_name, name, status_id,
                                      is_archived, scheduled_date, delivery_date, client_id, superior, major_milestone,
                                      main_technique, business_field, main_function, git_address,
                                      project_basic_datasheet, project_proposal, project_quotation, project_estimates,
                                      project_plan, project_process_crop_table, project_cost_management_table,
                                      project_requirements_change_management_table, project_risk_management_table,
                                      client_check_problems_table, client_check_report, project_summary,
                                      experience_and_lessons, development_tools, development_templates, check_sheets,
                                      qa_summary)
VALUES (4, '16598456516', 2, '李四', '测试项目4', 3111, 0, '2020-03-24', '2020-03-31', 2, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO achieveit.project_basics (id, project_id, project_manager_id, project_manager_name, name, status_id,
                                      is_archived, scheduled_date, delivery_date, client_id, superior, major_milestone,
                                      main_technique, business_field, main_function, git_address,
                                      project_basic_datasheet, project_proposal, project_quotation, project_estimates,
                                      project_plan, project_process_crop_table, project_cost_management_table,
                                      project_requirements_change_management_table, project_risk_management_table,
                                      client_check_problems_table, client_check_report, project_summary,
                                      experience_and_lessons, development_tools, development_templates, check_sheets,
                                      qa_summary)
VALUES (5, '98198165165', 2, '李四', '测试项目5', 3101, 0, '2020-04-01', '2020-04-05', 2, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO achieveit.project_basics (id, project_id, project_manager_id, project_manager_name, name, status_id,
                                      is_archived, scheduled_date, delivery_date, client_id, superior, major_milestone,
                                      main_technique, business_field, main_function, git_address,
                                      project_basic_datasheet, project_proposal, project_quotation, project_estimates,
                                      project_plan, project_process_crop_table, project_cost_management_table,
                                      project_requirements_change_management_table, project_risk_management_table,
                                      client_check_problems_table, client_check_report, project_summary,
                                      experience_and_lessons, development_tools, development_templates, check_sheets,
                                      qa_summary)
VALUES (6, '47516845162', 2, '李四', '测试项目6', 0, 1, '2020-02-26', '2020-02-29', 1, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
create table project_employee
(
    id          int(11) unsigned auto_increment comment 'project_employee_id'
        primary key,
    project_id  int  not null,
    employee_id int  not null,
    join_time   date null,
    exit_time   date null,
    superior_id int  null comment '上级id'
);

create index idx_employee_id
    on project_employee (employee_id)
    comment '从employee_id查找';

INSERT INTO achieveit.project_employee (id, project_id, employee_id, join_time, exit_time, superior_id)
VALUES (1, 1, 1, null, null, null);
INSERT INTO achieveit.project_employee (id, project_id, employee_id, join_time, exit_time, superior_id)
VALUES (2, 2, 1, null, null, null);
create table property
(
    id               int(11) unsigned auto_increment comment '资产ID'
        primary key,
    project_id       int          null comment '项目id',
    type             varchar(255) not null comment '资产类型',
    manager_id       int          null comment '资产管理者',
    usage_time_limit date         null comment '资产使用期限',
    device_condition varchar(255) null comment '设备状态 是否完好',
    is_returned      tinyint(1)   null comment '设备是否归还',
    return_date      date         null comment '归还日期'
);


create table risk
(
    id          int(11) unsigned auto_increment
        primary key,
    project_id  int          not null comment '项目ID',
    type        varchar(255) null comment '风险类型',
    description varchar(255) null comment '风险描述',
    level       int          null comment '风险级别',
    affect      int          null comment '风险影响度',
    react       varchar(255) null comment '风险应对',
    strategy    varchar(255) null comment '策略',
    status      int(255)     null comment '风险状态',
    responsible varchar(255) null comment '风险责任人',
    track_freq  varchar(255) null comment '风险跟踪频度',
    related     varchar(255) null comment '风险相关者'
);


create table role_basics
(
    id     int(11) unsigned auto_increment comment 'role_id'
        primary key,
    name   varchar(255) not null comment 'role_name',
    detail varchar(511) null comment 'role_detail  explaination'
);

INSERT INTO achieveit.role_basics (id, name, detail)
VALUES (1, 'ROLE_PM', '项目经理');
INSERT INTO achieveit.role_basics (id, name, detail)
VALUES (2, 'ROLE_DEVLEADER', '开发Leader');
INSERT INTO achieveit.role_basics (id, name, detail)
VALUES (3, 'ROLE_DEV', '开发');
INSERT INTO achieveit.role_basics (id, name, detail)
VALUES (4, 'ROLE_TESTLEADER', '测试Leader');
INSERT INTO achieveit.role_basics (id, name, detail)
VALUES (5, 'ROLE_TEST', '测试');
create table user
(
    id                         int(11) unsigned auto_increment comment 'user_id'
        primary key,
    username                   varchar(255)         not null,
    password                   varchar(255)         null,
    employee_basics_id         int                  null,
    is_account_non_expired     tinyint(1) default 1 not null comment '是否未过期',
    is_account_non_locked      tinyint(1) default 1 not null comment '是否未锁定',
    is_credentials_non_expired tinyint(1) default 1 not null comment '密码是否未过期',
    is_enabled                 tinyint(1) default 1 not null comment '是否启用'
);

create index idx_username
    on user (username);

INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (1, 'zhangsan', '$2a$10$c9sFXBKUZafiM1mmj85J1.lGFp8n9JaJ6gbLtOWQNw3yLRmw/VU0m', 1, 1, 1, 1, 1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (2, 'lisi', '$2a$10$c9sFXBKUZafiM1mmj85J1.lGFp8n9JaJ6gbLtOWQNw3yLRmw/VU0m', 2, 1, 1, 1, 1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (4, 'wangwu', '$2a$10$WhBFSW01EmRSU9Au349FUudqroTkf.hRAwcoGjEJ/8/lwUMAvE5Y6', null, 1, 1, 1, 1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (206, 'performance_test_user_3', '$2a$10$1Zp11vQm4hsmRSA4y2cS2es7/AbGln7npYRdLYgsxXpub.FCJdDMm', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (207, 'performance_test_user_5', '$2a$10$.1XZwV4inItdyq1rKONkzeD4X/z7aeqhE5ub6.cmxegM0k/8UtFoG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (208, 'performance_test_user_9', '$2a$10$EPs1veMlOIfjnlaP4gkNPe8/ENZWBEprhN14CDCRO7ga6N6Yle792', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (209, 'performance_test_user_10', '$2a$10$HJ8l/8HFJ0/KsXVAEieMVuFY5PAKtsreGWUjedmt4MgzB0nKwNR2.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (210, 'performance_test_user_1', '$2a$10$HHFIY1Ei.wxEj3AeVmAdCuTjC/hS3lyBeX2oOHenz0bJNFiKtls4m', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (211, 'performance_test_user_11', '$2a$10$/yJJEIqOB0OS2RrR1kjsn.91l4/fYPa4i4tCVUfksuYOTkhIN2lMG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (212, 'performance_test_user_7', '$2a$10$tIuQFKysu3.woudrmkHq.uZi0BrK7j82Exe4bpeRsiNkAwlok9Kx6', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (213, 'performance_test_user_13', '$2a$10$5hOFcUFy1sghPki/xMLlDeJR3SfEN8qeBMNPaFTqZg/qww.VH7Q66', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (214, 'performance_test_user_15', '$2a$10$rTHdxqSVmtN4iju0gxf7PeULcV2UCz6eQWnR5bsCVEBI96kRVw.sW', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (215, 'performance_test_user_6', '$2a$10$cSaQE6AR1E.pvwkiWsNDWehgR5lk7YtvMoE.bjSe9ZTz110khCHe.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (216, 'performance_test_user_4', '$2a$10$tnxeMckNTetFvZzOUCOJV.blyx2FOAuQPWfKfcmYKHh9EWSXguxhu', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (217, 'performance_test_user_17', '$2a$10$5SDo1K61P0JyRJzfzpVC8OGdPqARvt.L4Knp6GW3PC06AFqit.OWS', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (218, 'performance_test_user_18', '$2a$10$fq1dXiuHLMyv3irrDzbVCOUp0acqFq2IiTktPFx0u4OMSHv9HmgrG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (219, 'performance_test_user_2', '$2a$10$iZL3hL551fvJ/MQMBm2OBuEd5MkgV490PGHaUGNtxnYrk0cyFmjjC', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (220, 'performance_test_user_21', '$2a$10$zqKmM4WF2QA4yNS8Zk6zDOWkvN2IWr8KIzjDsgnP2Z85MX4VP8.JO', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (221, 'performance_test_user_20', '$2a$10$JENLkxbGz28lkboYaentvORQzxHcbJJ.Z4MjNozGIAXFx07rnCI6W', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (222, 'performance_test_user_14', '$2a$10$c7N5T7yTIFazbdFNemcvpeA414RUFkbLwLYX61zsMzTNuZK1pF98.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (223, 'performance_test_user_12', '$2a$10$TtrPBRR2t2tNsWgYv9TJ2.wxjVThN23NaA7vku/g2waphpPqNuXA.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (224, 'performance_test_user_23', '$2a$10$2CxNUQ/Jt4UysBaDO2AHAO2/4NOS3TLcRvzZP7ZE0wEoZL17BrYLa', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (225, 'performance_test_user_16', '$2a$10$K/rynF8rkPp3zEeDQ6cGne2eRTTST6W5Zf0XdTPxMKbTaC.dwJpZW', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (226, 'performance_test_user_22', '$2a$10$176DS3XWf953y4uYLMDaR.uSsRNoAAi489iggBBuKAcUeHPzuJapa', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (227, 'performance_test_user_24', '$2a$10$iLZvNXtnWvb5DtC3r3k77.UBhdtt0v6iZiMTqBNGzNTn8yGYFkudi', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (228, 'performance_test_user_26', '$2a$10$C4Ee5aqoUYZArsMviIhuG.00Wp.qsA9QjMdrNR0uW4a612YHuxmAm', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (229, 'performance_test_user_27', '$2a$10$g4yDmXTtro0TSvVzBVmij.yPIzR5opDEDUfsQBMF4gk.f7VqqsgQC', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (230, 'performance_test_user_8', '$2a$10$Py5dN0Sa/D0yBaZ5yOLtU.wgCxELmUm413aVXuFWSQ5viOxY2rUIa', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (231, 'performance_test_user_29', '$2a$10$aKPxmkH2pUz4ouc5J1LxbOF5RSZl8rjUDtXDxT0rKGlnu1BtdSfhO', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (232, 'performance_test_user_19', '$2a$10$YsA4w.L61ZZRQ0Qk2kGOtuMmwH8GWWH7SGR4jeKkGqNHhrXaAH12y', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (233, 'performance_test_user_30', '$2a$10$YqQjkP7sUFD6.Rj.vB0FXevItZI6Bm.RbJc4nKA2ECHecbIdts5.u', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (234, 'performance_test_user_31', '$2a$10$9cmIEz9PCJns173m4ItL1ev/aEHMUsQPM01/pgUR/upZOAEzd02La', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (235, 'performance_test_user_25', '$2a$10$8L5GrpchEuxrpnGDzH/umuo3mVJ5wkj3eyokfEJqYb30Y0rnGdFxG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (236, 'performance_test_user_32', '$2a$10$ghW8z6bTLGy6mtzXPf46quVyJWy.tY4uaQzYkGY0GgrpmsjJJ13Em', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (237, 'performance_test_user_34', '$2a$10$C3AzLgmnNxrC4hYunACKuetQf75qirblBlVR4WJT.GhWA7aXOTIwC', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (238, 'performance_test_user_28', '$2a$10$BeryEpjJg7wvkvNLvJRKq.hZe07prOIJDre96MpPu9qYXUbDoNGaO', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (239, 'performance_test_user_35', '$2a$10$IFBK0mBSlHMPie1jjiTD9.oROg6uUL1vYDMFrRTj3ieSxhAg/6QW.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (240, 'performance_test_user_36', '$2a$10$/3crdjLHQm.X/ulCLje7.uAwen0DYBCL998DpWmGUY1iLBs6KyZAG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (241, 'performance_test_user_37', '$2a$10$.S8ZQxF2Ub5/.ryoIk5sIuQmydz.mEJl/iKQqsO8vVwbJM5OPhCjG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (242, 'performance_test_user_38', '$2a$10$ebh9wd1pxqaVoeKjBRcEL.6Homw4ZImPgC4kHRwfcsU3rkWiaLK5u', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (243, 'performance_test_user_39', '$2a$10$qZbCE8Wm/SfXEAZuRZ.Yw.X11IAbp/1AL8Q9MIC1AQWVlc7JGywn2', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (244, 'performance_test_user_33', '$2a$10$acN1eq2ZxPe1b9q7fab6Rec9Nucq/IuPkLZ71270Pe3FrP3Y72Cd.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (245, 'performance_test_user_40', '$2a$10$0csw/5nPjAvuk457X8wvneCZuZGzyKRhD5Zgydx92UT.PNQfsBZ86', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (246, 'performance_test_user_41', '$2a$10$AD/9AUTI/2vb3cAdpNJvpOO6YECOJcb.g5oO0.b81VqJrstg2PUDi', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (247, 'performance_test_user_42', '$2a$10$4sc79ydnn0hQSQwNmNVUFuaKkqrhwbhjYKVV1Uhf5GL6d6tNnVBHO', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (248, 'performance_test_user_43', '$2a$10$6NzWyolgs78z0TRZvmkUqOH9E3FdKeb6pFMaK6Li2KAncMel79Lk6', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (249, 'performance_test_user_44', '$2a$10$h.2Cp0Wg94zWx4V151.qXuEjC/AoR51Sgxd/3MT2riYt/gygYv9A2', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (250, 'performance_test_user_45', '$2a$10$.x/Tq4g99kc.agNp/o38t.AV0kQ/OwkgUTghPgzmlrzIFY0z50OmK', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (251, 'performance_test_user_46', '$2a$10$OUzkfbGA/ZIubw/L30brZebuc3BgbnyLyEn6ir5VGaD4jTTRZUS/W', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (252, 'performance_test_user_47', '$2a$10$RX7Zh6FDod1W0LNNl4n5UeyUyh9G1ke36zZZPcRK.sPmi/NWIMany', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (253, 'performance_test_user_48', '$2a$10$Cy6n.Q6up5RByifbxV8a.edVUhs7d2oAObOPS3SwQB8ZldPiYquyK', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (254, 'performance_test_user_49', '$2a$10$loBJB2fvtr4k57/I5dt1GeKhFmmf1kKEzUbiwGbAHJsoaXMISG6.C', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (255, 'performance_test_user_51', '$2a$10$0AC/F8QN/3aCEL3LyQ0LbO1w1EtIHV5o4Kc9oaKjy.WLq0LgwBcE.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (256, 'performance_test_user_50', '$2a$10$qWvQeHatdKpJjWtabAnkcO09MLISK0SKIQiJmx/ODAOAFrFtJy00q', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (257, 'performance_test_user_53', '$2a$10$sc0AR2xinBNzkibVbdaqhedNren3wxvjr70REWOuZ5dOXHXttakZC', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (258, 'performance_test_user_55', '$2a$10$EABr.qbsIFEUImlDwW3Li.NDnD8noHRYCoIlXpyTmmEPovdSHpX4m', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (259, 'performance_test_user_52', '$2a$10$uSy5efIKDT6KnCDW8ar8FeIPwZ36Xwckggk.Db5Vj2jICGwhAL3By', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (260, 'performance_test_user_56', '$2a$10$9Y8pGSbaa1sGz.XSIi9GeuyasiKTvzvkYYvTtvj7COMvHnazPoaxS', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (261, 'performance_test_user_57', '$2a$10$8tW6SPHkk2e/JNxXS/abqO9isKUVol2v2KW//KEdoSjVF4ItoloXm', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (262, 'performance_test_user_58', '$2a$10$CpNsk551gNSJtEeC.1mdqeKvNBMxrVwk/IpJDJZDY1VaqkyyOEgUW', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (263, 'performance_test_user_54', '$2a$10$OQAjUEpgZHcn8j0EXPNVTeHH1f6bdJ5OKWvr4FdqY.FY4jFQBmcaO', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (264, 'performance_test_user_59', '$2a$10$nxms5iS0rFadMdiobO1CeeSSPIOXSOiq/jSkvJzPPzDDuKQeXDvC.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (265, 'performance_test_user_60', '$2a$10$1CnoMSMoLvqH4VQGYVZBMOnYCf0ctjM3YFHEk/N/hpQtAFNrfsFFu', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (266, 'performance_test_user_61', '$2a$10$j.0PJlA7Yavq5GsWD/eFw.qoN1l9kEtHUzMx2am2GLluoBhGIrkny', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (267, 'performance_test_user_62', '$2a$10$H4TSenlw/1QNYsg6C5U77usNG4GtY7WuC9glKMORM/T6esMZTXSLC', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (268, 'performance_test_user_63', '$2a$10$TSirIAIlu37bUYnp3HK0veoDjhAawzlAfRxFSK70JdJX.16TbEytC', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (269, 'performance_test_user_64', '$2a$10$3HK/s8UxsKsr7NwrGvNLae3z8t0/5hKGLj0eHUhe6Ca9Ek2Qd79i.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (270, 'performance_test_user_65', '$2a$10$.zjgbqjK2zlFmArsAs86nuaBJDy4Uoy38ENo5UexgtxjAuHWLAnQ2', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (271, 'performance_test_user_66', '$2a$10$fXOQS42qA1OdnFDPg/zSGO290gDaSP4N1xFnRuzhMZKWmubiJ6Bvi', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (272, 'performance_test_user_67', '$2a$10$Qwc3dtYg.k.6iljU3Myl0eCuhiBuPzzsj6ODQqSJLp7oohr1crKKe', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (273, 'performance_test_user_68', '$2a$10$WBpCULJ0LWi/8k4aTtAJaOynwW15EUPm3X9ktHTW/edMb02Y7W5by', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (274, 'performance_test_user_70', '$2a$10$0kSHW539/LXsnCd8..2KH.vDtEjvIzXdW1IHkqiYPkxDZ4egY1r3q', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (275, 'performance_test_user_71', '$2a$10$D.bME8KKAlnTzjKSxv6RiOjw3YlI4PB/M3/EMVE24nF6jUKk3OIt.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (276, 'performance_test_user_72', '$2a$10$ttuuFK4XN1gzwJDdJW/cWuMa8gCAP4I5uL6H6v6sraKLr.L1hFbia', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (277, 'performance_test_user_69', '$2a$10$RmCgINe2rkNzo8bmdNHH9OtzUYy317Va.Hv94tXFzopz6TsHyX1am', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (278, 'performance_test_user_74', '$2a$10$dCtAGxWY1eTOVNdAE99.oO5lHscPwq4WStR8UJI.3Sq7kmYghQjP2', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (279, 'performance_test_user_73', '$2a$10$5yXqRkatFAdghiO0A5tu0e44AVDz/no9iEi.8Xfl3kei2xGr9whn.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (280, 'performance_test_user_75', '$2a$10$sEL4PprCLOk/xNaYqC4Ktu0zStr7rFQ.ruOWzIWjWvcrwlFtsasPO', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (281, 'performance_test_user_76', '$2a$10$5p6Geer75jVZeZtzdID50.OYSCQObjo7KJXuAkD9QqCycTK.cWN0e', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (282, 'performance_test_user_77', '$2a$10$MqxRjFk.axv0qVinsr3xN.sCfKMQEm7eEv5FFayurHuV3wf6j6/ZG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (283, 'performance_test_user_78', '$2a$10$.Eqspu26Gxt4D.7CDZPc0ud61fRnnJ0/Q81vvOXFAsv6QoAqHsuQG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (284, 'performance_test_user_79', '$2a$10$HSMVyRL7xzRBrWrZI6azgu3CF.VsoJ84WMqUEuOXQ3xBGJaB0qT7q', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (285, 'performance_test_user_80', '$2a$10$Z1BCQaw4zOfIky6ZUsMLmeYwor4keBeyn4I9lP54VpBcanXwVZLnu', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (286, 'performance_test_user_81', '$2a$10$1qf6MZFSGWZ.DV2mvkMqN.bzpLay2UtTQmYlI3Na19NPCsJxBWBde', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (287, 'performance_test_user_82', '$2a$10$sbSraslmWPqJ/y8YJ1oqqu1wpuM7pzby4J8guyVxt.JOs7LX4Hpou', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (288, 'performance_test_user_83', '$2a$10$CMIcnEiQKBQa5iAMNNs16eg0nSkz.ledtKnIU21j1ZB56/WxugrU.', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (289, 'performance_test_user_84', '$2a$10$HYrTwsMY33/hxMTZv3CQ2eeX4bADaAoN9KS5SwF.xPkZ48fpR1KcW', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (290, 'performance_test_user_85', '$2a$10$q8HgAWRlav13TOSBV82vQOYCsAJ41VJ7vMGgoccJLVSEqDzZ4gjSu', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (291, 'performance_test_user_86', '$2a$10$vutg9HUzFuLGsqEfc.2iHOFsP4cf3LkO2JCRywwbEafrxW/ktvwE6', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (292, 'performance_test_user_87', '$2a$10$cXvJnKElai.OELivCbEMxu/41Kn2WcdodkY72WNOXUQWYX4xWiRVy', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (293, 'performance_test_user_88', '$2a$10$9VMfjchGAb7QgEWK/suLwufHtIclj1VAXpfKGbLZEnTQVMAxtNzgG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (294, 'performance_test_user_90', '$2a$10$cSpdT0I9sHWwWjjaVvdyGeCXaYzkMzc3HiLozXO314ZfAco7smp1m', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (295, 'performance_test_user_89', '$2a$10$xdT04ihEyPoJGmkfQGPijeA8aJSPuQcQJ6uV4QHOICezdHjFYTi5K', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (296, 'performance_test_user_92', '$2a$10$0PqV61qCfBQNSKpDy6oN1eb09EZQVWOzY5TzdoMMzNimEL1rspBbG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (297, 'performance_test_user_97', '$2a$10$.R2B5zlLs03UO25IZjsgvOHZvQOk1KFkOZ4QpUVfjnhB7fGVf7cwG', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (298, 'performance_test_user_91', '$2a$10$dLZnDlVI6N1hDFPGy4fZA.Y6ppQ.Zd8KDJ2t/344RlSlfglzizH3i', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (299, 'performance_test_user_99', '$2a$10$4A9rPvVPNxBn5L6DpPKKy.qIxj1U.yevopr29M2SUQ9jg9goqsPiS', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (300, 'performance_test_user_93', '$2a$10$Eme5ZyHD6kMTNyefUqEIr.n5dlsnjKsA11TI/Gdeuxe7gdHrXojOW', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (301, 'performance_test_user_95', '$2a$10$GH0fBhHGuSus/ulxk0uvu.PH0giiZ1D7Gmti2CPFcu7cXXdYVTANC', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (302, 'performance_test_user_94', '$2a$10$R.2TO6iLMKI54Shxqdl4M.ZJyKorHl8lPJzT3yueHfcPh3lnTpn92', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (303, 'performance_test_user_96', '$2a$10$O9RZ0WRYXiVvfEeXv1PKzO40kviudnQ4YEnJNe6tapvaHJ84jHc0i', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (304, 'performance_test_user_98', '$2a$10$zUEXNXJxZq0UAG9sLOcOBeRXScDmBuWw.EeEZv6302xqNBbNqLhOW', null, 1, 1, 1,
        1);
INSERT INTO achieveit.user (id, username, password, employee_basics_id, is_account_non_expired, is_account_non_locked,
                            is_credentials_non_expired, is_enabled)
VALUES (305, 'performance_test_user_100', '$2a$10$xr.fNyeuHxtwOMbJJLle9.9KxzfiNahLztYporbfLU6MmjaxuTcy6', null, 1, 1, 1,
        1);
create table user_role
(
    id      int(11) unsigned auto_increment
        primary key,
    user_id int not null comment '用户id',
    role_id int not null comment '角色id'
);

INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (1, 4, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (203, 207, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (204, 208, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (205, 209, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (206, 210, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (207, 211, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (208, 206, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (209, 212, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (210, 214, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (211, 213, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (212, 215, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (213, 216, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (214, 217, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (215, 218, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (216, 219, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (217, 220, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (218, 221, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (219, 222, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (220, 224, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (221, 223, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (222, 225, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (223, 227, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (224, 226, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (225, 228, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (226, 229, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (227, 230, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (228, 231, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (229, 233, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (230, 232, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (231, 234, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (232, 235, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (233, 236, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (234, 237, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (235, 238, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (236, 239, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (237, 240, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (238, 241, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (239, 242, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (240, 243, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (241, 244, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (242, 245, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (243, 246, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (244, 247, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (245, 248, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (246, 249, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (247, 250, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (248, 251, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (249, 252, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (250, 253, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (251, 254, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (252, 255, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (253, 256, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (254, 257, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (255, 259, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (256, 260, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (257, 258, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (258, 261, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (259, 262, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (260, 263, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (261, 264, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (262, 265, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (263, 266, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (264, 267, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (265, 268, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (266, 269, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (267, 270, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (268, 271, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (269, 272, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (270, 273, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (271, 274, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (272, 275, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (273, 276, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (274, 277, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (275, 278, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (276, 280, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (277, 279, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (278, 281, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (279, 282, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (280, 283, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (281, 285, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (282, 284, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (283, 288, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (284, 286, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (285, 287, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (286, 289, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (287, 290, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (288, 291, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (289, 292, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (290, 293, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (291, 294, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (292, 297, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (293, 298, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (294, 299, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (295, 300, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (296, 301, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (297, 295, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (298, 302, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (299, 303, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (300, 304, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (301, 305, 3);
INSERT INTO achieveit.user_role (id, user_id, role_id)
VALUES (302, 296, 3);
