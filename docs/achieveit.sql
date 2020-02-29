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

INSERT INTO achieveit.person_permission (id, project_employee_id, permission_id)
VALUES (1, 1, 1);
INSERT INTO achieveit.person_permission (id, project_employee_id, permission_id)
VALUES (2, 1, 2);
INSERT INTO achieveit.person_permission (id, project_employee_id, permission_id)
VALUES (3, 1, 3);
INSERT INTO achieveit.person_permission (id, project_employee_id, permission_id)
VALUES (4, 2, 1);
INSERT INTO achieveit.person_permission (id, project_employee_id, permission_id)
VALUES (5, 2, 2);
create table person_role
(
    id                  int(11) unsigned auto_increment
        primary key,
    project_employee_id int not null,
    role_id             int not null
);


create table project_basics
(
    id                                           int(11) unsigned auto_increment
        primary key,
    project_id                                   char(11)     not null comment 'project_id  由“四位年份-四位客户代码-研发类型1位（开发：D，维护：M，服务：S，其他：O）-顺序号2位”构成，且从外部系统导入，是一个选择项，不可更改。',
    client_id                                    int          null comment '客户ID 从客户管理系统中拉取详细信息',
    scheduled_date                               date         null comment '预定时间 项目预期开始时间',
    delivery_date                                date         null comment '交付日期 项目预期结束时间',
    superior                                     varchar(255) null comment '项目上级 负责项目立项审批',
    major_milestone                              varchar(255) null comment '主要里程碑?????',
    main_technique                               varchar(512) null comment '主要技术 语言 平台 架构 工具等',
    business_field                               varchar(255) null comment '业务领域',
    main_function                                varchar(255) null comment '主要业务功能',
    git_address                                  varchar(255) null comment 'git仓库地址',
    status_id                                    int          null comment '项目状态id',
    is_archived                                  tinyint(1)   null comment '项目是否归档',
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

INSERT INTO achieveit.project_basics (id, project_id, client_id, scheduled_date, delivery_date, superior,
                                      major_milestone, main_technique, business_field, main_function, git_address,
                                      status_id, is_archived, project_basic_datasheet, project_proposal,
                                      project_quotation, project_estimates, project_plan, project_process_crop_table,
                                      project_cost_management_table, project_requirements_change_management_table,
                                      project_risk_management_table, client_check_problems_table, client_check_report,
                                      project_summary, experience_and_lessons, development_tools, development_templates,
                                      check_sheets, qa_summary)
VALUES (1, '12345678901', 1, '2020-02-28', '2020-02-28', '1', '1', '1', '1', '1', '1', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1);
INSERT INTO achieveit.project_basics (id, project_id, client_id, scheduled_date, delivery_date, superior,
                                      major_milestone, main_technique, business_field, main_function, git_address,
                                      status_id, is_archived, project_basic_datasheet, project_proposal,
                                      project_quotation, project_estimates, project_plan, project_process_crop_table,
                                      project_cost_management_table, project_requirements_change_management_table,
                                      project_risk_management_table, client_check_problems_table, client_check_report,
                                      project_summary, experience_and_lessons, development_tools, development_templates,
                                      check_sheets, qa_summary)
VALUES (2, '23456789012', 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null);
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

INSERT INTO achieveit.project_employee (id, project_id, employee_id, join_time, exit_time, superior_id)
VALUES (1, 1, 1, null, null, null);
INSERT INTO achieveit.project_employee (id, project_id, employee_id, join_time, exit_time, superior_id)
VALUES (2, 2, 1, null, null, null);
create table project_status_basics
(
    id     int(11) unsigned auto_increment comment 'project_status_id'
        primary key,
    name   varchar(255) not null comment 'project_status_name',
    detail varchar(511) null comment 'project_status_detail explaination'
);


create table property
(
    id               int(11) unsigned auto_increment comment '资产ID'
        primary key,
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
VALUES (1, '开发Leader', null);
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
VALUES (2, 'lisi', '$2a$10$c9sFXBKUZafiM1mmj85J1.lGFp8n9JaJ6gbLtOWQNw3yLRmw/VU0m', null, 1, 1, 1, 1);
