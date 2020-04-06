<template>
  <div>

    <!-- 搜索栏 -->
    <div>
      <el-input
        v-if="type===2"
        v-model="searchValue.employeeOriginalId"
        placeholder="人员ID"
        style="width: 15%;min-width: 200px"
        clearable
      >
      </el-input>
      <el-date-picker
        v-model="searchValue.startTime"
        name="date"
        type="date"
        format="yyyy-MM-dd"
        placeholder="日期"
        style="width: 15%;min-width: 200px"
        clearable
      >
      </el-date-picker>
      <el-select
        v-model="searchValue.auditingStatus"
        placeholder="审核状态"
        style="width: 15%;min-width: 120px"
        clearable
      >
        <el-option
          v-for="(item,index) in options.manHourAuditingStatusOptions"
          :key="index"
          :label="item"
          :value="index">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="search" @keyup.enter="search">
        <i class="el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button  v-if="type===1" type="primary" @click="openAddWorkHourDialog" >
        <i class="el-icon-plus"></i>
        <span>添加工时信息</span>
      </el-button>
    </div>

    <!-- 表格 -->
    <div>
      <div>
        <el-table
          v-loading="loading"
          :data="table.data"
          style="width: 100%; margin-top: 30px"
          empty-text="无工时条目"
        >
          <el-table-column
            prop="employeeBasics.employeeId"
            label="员工ID"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="employeeBasics.name"
            label="姓名"
            align="center"
            min-width="40"
          >
          </el-table-column>
          <el-table-column
            label="日期"
            align="center"
            width="200"
          >
            <template slot-scope="{row}">
              <span>{{getDate(row.startTime)}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="时间"
            align="center"
            width="200"
          >
            <template slot-scope="{row}">
              <span>{{getTime(row.startTime)}}-{{getTime(row.endTime)}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="featureName"
            label="功能名称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="活动名称"
            align="center"
          >
            <template slot-scope="{row}">
              <span>{{options.activityOptions[row.activityId/10].children[row.activityId%10].label}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="auditingStatus"
            label="审核状态"
            align="center"
          >
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            align="center"
          >
            <template slot-scope="{row}">
              <!-- 审核中的工时可以进行操作 -->
              <span v-if="row.auditingStatus === 0">
                <!-- 撤回自己的工时 -->
                <span v-if="type === 1">
                  <el-button type="text"  style="color: #E6A23C" @click="withdraw(row)">撤销</el-button>
                </span>
                <!-- 审核下属工时 -->
                <span v-else>
                  <el-button type="text"  style="color: #85ce61" @click="pass(row)">通过</el-button>
                  <el-button type="text"  style="color: #f78989" @click="refuse(row)">驳回</el-button>
                </span>
              </span>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <pagination
        :total="table.total"
        :page.sync="table.page"
        :limit.sync="table.limit"
        @pagination="getWorkHour">
      </pagination>
    </div>

    <!-- 添加工时信息 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="工时信息"
      :visible.sync="addWorkHourDialog.show"
      width="500px"
      center
    >
      <el-form
        ref="addWorkHourForm"
        :model="addWorkHour.data"
        :rules="addWorkHourDialog.rules"
        :hide-required-asterisk="true"
        label-width="120px"
      >
        <!-- TODO 日期限定 -->
        <el-form-item label="日期">
          <el-date-picker
            v-model="addWorkHourDialog.data.date"
            name="addWorkHourDate"
            type="date"
            format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="起始时间">
          <el-time-picker
            v-model="addWorkHourDialog.data.startTime"
            name="addWorkHourStartTime"
            :picker-options="options.timeOptions"
          >
          </el-time-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-time-picker
            v-model="addWorkHourDialog.data.endTime"
            name="addWorkHourEndTime"
            :picker-options="options.timeOptions"
          >
          </el-time-picker>
        </el-form-item>
        <el-form-item label="功能名称">
          <el-select v-model="addWorkHourDialog.data.featureId">
            <!--TODO 和功能列表关联 -->
            <el-option
              v-for="item in options.featureOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="活动名称">
          <el-cascader
            v-model="addWorkHourDialog.data.activity"
            name="addWorkHourActivityId"
            :options="options.activityOptions"
            :props="{ expandTrigger: 'hover' }"
          >
          </el-cascader>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addWorkHourDialog.show = false">取 消</el-button>
        <el-button type="primary" @click="addWorkHour">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Pagination from '@/components/Pagination/index'
  import {dateToString, stringToChinese, stringToTime} from "../../../../utils/date";
  export default {
    components: {
      Pagination
    },
    props: {
      type: {
        type: Number,
        default: 1
      }
    },
    data () {
      return {
        searchValue: {
          employeeOriginalId: '',
          startTime: '',
          auditingStatus: ''
        },
        options: {
          manHourAuditingStatusOptions: ['审核中', '已通过', '已驳回', '已撤回'],
          activityOptions: [
            {
              value: 0,
              label: '工程活动',
              children:[
                {
                value: 0,
                label: '需求开发'
                },
                {
                  value: 1,
                  label: '设计'
                },
                {
                  value: 2,
                  label: '编码'
                },
                {
                  value: 3,
                  label: '单体测试'
                },
                {
                  value: 4,
                  label: '集成测试'
                },
                {
                  value: 5,
                  label: '系统测试'
                },
                {
                  value: 6,
                  label: '交付'
                },
                {
                  value: 7,
                  label: '维护'
                }
              ]
            },
            {
              value: 1,
              label: '管理活动',
              children:[
                {
                  value: 0,
                  label: '范围管理'
                },
                {
                  value: 1,
                  label: '计划与tiaozheng'
                },
                {
                  value: 2,
                  label: '监控与分析'
                },
                {
                  value: 3,
                  label: '联络与沟通'
                }
              ]
            },
            {
              value: 2,
              label: '外包活动',
              children:[
                {
                  value: 0,
                  label: '外包管理'
                },
                {
                  value: 1,
                  label: '外包验收'
                },
                {
                  value: 2,
                  label: '外包支持'
                }
              ]
            },
            {
              value: 3,
              label: '支持活动',
              children:[
                {
                  value: 0,
                  label: '配置管理'
                },
                {
                  value: 1,
                  label: 'QA审计'
                },
                {
                  value: 2,
                  label: '会议报告总结'
                },
                {
                  value: 3,
                  label: '培训'
                },
                {
                  value: 4,
                  label: '其他'
                }
              ]
            }
          ],
          timeOptions: {
            selectableRange: '08:00:00 - 23:59:59'
          },
          featureOptions: [],
        },

        loading: false,
        table: {
          data: [],
          total: 0,
          page: 1,
          limit: 10,
          searchCondition: {
            employeeOriginalId: '',
            startTime: '',
            auditingStatus: ''
          }
        },

        addWorkHourDialog: {
          show: false,
          data: {
            date: '',
            startTime: '',
            endTime: '',
            featureId: '',
            activity: '',
            activityId: ''
          },
          rules: {}
        }
      }
    },
    mounted() {
      this.getWorkHour()
    },
    methods: {
      /**
       * 时间转换
       */
      getDate(date) {
        return stringToChinese(date)
      },
      getTime(date) {
        return stringToTime(date)
      },

      /**
       * 获取工时信息
       */
      getWorkHour() {

      },
      search() {

      },
      setSearchValue() {

      },

      /**
       * 添加工时信息
       */
      addWorkHour() {

      },
      openAddWorkHourDialog() {
        // TODO 获取功能列表

        // 初始化数据
        this.addWorkHourDialog.data.date = dateToString(new Date())
        this.addWorkHourDialog.data.startTime = ''
        this.addWorkHourDialog.data.endTime = ''
        this.addWorkHourDialog.data.featureId = ''
        this.addWorkHourDialog.data.activity = ''
        this.addWorkHourDialog.data.activityId = ''

        // 打开对话框
        this.addWorkHourDialog.show = true
      },

      /**
       * 更新工时审核状态
       */
      updateAuditingStatus(params) {

      },
      /**
       * 撤销
       */
      withdraw(row) {

      },
      /**
       * 通过
       */
      pass(row) {

      },
      /**
       * 拒绝
       */
      refuse(row) {

      }
    }
  }
</script>

<style scoped>

</style>
