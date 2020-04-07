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
                  <el-button type="text"  style="color: #E6A23C" @click="openUpdateWorkHourDialog(row)">修改</el-button>
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
      :title="addWorkHourDialog.title[addWorkHourDialog.type]"
      :visible.sync="addWorkHourDialog.show"
      width="500px"
      center
    >
      <el-form
        ref="addWorkHourForm"
        :model="addWorkHourDialog.data"
        :rules="addWorkHourDialog.rules"
        :hide-required-asterisk="true"
        label-width="120px"
      >
        <el-form-item label="日期" prop="date">
          <el-date-picker
            v-model="addWorkHourDialog.data.date"
            name="addWorkHourDate"
            type="date"
            :picker-options="options.dateOptions"
            :clearable="false"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="起始时间" prop="startTime">
          <el-time-picker
            v-model="addWorkHourDialog.data.startTime"
            name="addWorkHourStartTime"
            :picker-options="options.startTimeOptions"
            :clearable="false"
          >
          </el-time-picker>
        </el-form-item>
        <el-form-item label="结束时间"  prop="endTime">
          <el-time-picker
            v-model="addWorkHourDialog.data.endTime"
            name="addWorkHourEndTime"
            :picker-options="{selectableRange: addWorkHourDialog.data.startTime + ' - 23:59:59'}"
            :clearable="false"
          >
          </el-time-picker>
        </el-form-item>
        <el-form-item label="功能名称"  prop="feature">
            <el-cascader
              v-model="addWorkHourDialog.data.feature"
              name="addWorkHourActivity"
              :options="options.featureOptions"
              :props="{
                expandTrigger: 'hover',
                value: 'id',
               }"
              style="width: 300px"
            >
            </el-cascader>
        </el-form-item>
        <el-form-item label="活动名称"  prop="activity">
          <el-cascader
            v-model="addWorkHourDialog.data.activity"
            name="addWorkHourActivityId"
            :options="options.activityOptions"
            :props="{
               expandTrigger: 'hover',
               value: 'id',
               label: 'name'
            }"
            style="width: 300px"
          >
          </el-cascader>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addWorkHourDialog.show = false">取 消</el-button>
        <el-button type="primary" @click="addOrUpdateWorkHour">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Pagination from '@/components/Pagination/index'
  import {stringToChinese, stringToTime} from "../../../../utils/date";
  import {getFeatures} from "../../../../api/feature";
  import {addWorkHour, getActivities} from "../../../../api/work-hour";
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
        project: this.$store.getters.project,

        searchValue: {
          employeeOriginalId: '',
          startTime: '',
          auditingStatus: ''
        },
        options: {
          manHourAuditingStatusOptions: ['审核中', '已通过', '已驳回', '已撤回'],
          featureOptions: [],
          activityOptions: [],
          dateOptions: {
            disabledDate(time) {
              // 三天前-今天
              return time.getTime() < new Date().getTime() - 3600 * 1000 * 24 * 4
                || time.getTime() > Date.now();
            },
          },
          startTimeOptions: {
            selectableRange: '00:00:00 - 23:59:59'
          }
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
          title: ['添加工时信息', '修改工时信息'],
          type: 0,
          data: {
            id: '',
            date: '',
            startTime: '',
            endTime: '',
            feature: [],
            featureId: '',
            activity: [],
            activityId: ''
          },
          rules: {
            feature: [{required: true, message: '功能名称不能为空', trigger: 'blur'}],
            activity: [{required: true, message: '活动名称不能为空', trigger: 'blur'}]
          }
        }
      }
    },
    mounted() {
      // 获取工时信息
      this.getWorkHour()
      // 初始化功能列表
      this.getFeatures()
      // 初始化活动列表
      this.getActivities()
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
       * 获取功能列表
       */
      getFeatures() {
        getFeatures(this.project.projectId).then(response => {
          this.options.featureOptions = response.data
        })
      },
      /**
       * 获取活动列表
       */
      getActivities() {
        getActivities().then(response => {
          this.options.activityOptions = response.data
        }).catch(error => {
          console.error(error)
        })
      },

      /**
       * 添加或修改工时信息
       */
      addOrUpdateWorkHour() {
        this.$refs.addWorkHourForm.validate(valid => {
          if (valid) {
            // region 转换数据格式
            // 开始、结束时间
            const date = this.addWorkHourDialog.data.date
            const startTime = this.addWorkHourDialog.data.startTime
            const endTime = this.addWorkHourDialog.data.endTime
            this.addWorkHourDialog.data.startTime = new Date(date.getFullYear(), date.getMonth(), date.getDay(), startTime.getHours(), startTime.getMinutes(), startTime.getSeconds())
            this.addWorkHourDialog.data.endTime = new Date(date.getFullYear(), date.getMonth(), date.getDay(), endTime.getHours(), endTime.getMinutes(), endTime.getSeconds())
            // 功能
            const feature = this.addWorkHourDialog.data.feature
            this.addWorkHourDialog.data.featureId = feature[1]
            // 活动
            const activity = this.addWorkHourDialog.data.activity
            this.addWorkHourDialog.data.activityId = activity[1]
            // endregion

            switch (this.addWorkHourDialog.type) {
              case 0: // 添加
                this.addWorkHour()
                break;
              case 1: // 修改
                this.updateWorkHour(this.addWorkHourDialog.data)
                break;
            }

            // 关闭对话框
            this.addWorkHourDialog.show = false
            // 刷新
            this.getWorkHour()
          }
        })
      },

      /**
       * 添加工时信息
       */
      addWorkHour() {
        addWorkHour({
          projectId: this.project.id,
          ...this.addWorkHourDialog.data
        }).then(response => {
          this.$message.success('成功')
        })
      },
      openAddWorkHourDialog() {
        // 类型
        this.addWorkHourDialog.type = 0
        // 初始化数据
        this.addWorkHourDialog.data.id = null
        this.addWorkHourDialog.data.date = new Date()
        this.addWorkHourDialog.data.startTime = new Date(1900, 1, 1, 0, 0, 0)
        this.addWorkHourDialog.data.endTime = new Date(1900, 1, 1, 23, 59, 59)
        this.addWorkHourDialog.data.feature = ''
        this.addWorkHourDialog.data.featureId = ''
        this.addWorkHourDialog.data.activity = ''
        this.addWorkHourDialog.data.activityId = ''
        // 打开对话框
        this.addWorkHourDialog.show = true
      },
      openUpdateWorkHourDialog(row) {
        // 类型
        this.addWorkHourDialog.type = 1
        // 初始化数据
        this.addWorkHourDialog.data.id = row.id
        this.addWorkHourDialog.data.date = new Date(row.startTime)
        this.addWorkHourDialog.data.startTime = new Date(row.startTime)
        this.addWorkHourDialog.data.endTime = new Date(row.endTime)
        this.addWorkHourDialog.data.feature = this.getTreeValue(row.featureId)
        this.addWorkHourDialog.data.featureId = row.featureId
        this.addWorkHourDialog.data.activity = this.getTreeValue(row.activityId)
        this.addWorkHourDialog.data.activityId = row.activityId
        // 打开对话框
        this.addWorkHourDialog.show = true
      },
      getTreeValue(options, id) {
        options.forEach(option => {
          option.children.forEach(child => {
            if (child.id === id) {
              return [option.id, child.id]
            }
          })
        })
      },

      /**
       * 修改工时信息
       */
      updateWorkHour(params) {

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
