<template>
  <div>

    <!-- 搜索栏 -->
    <div>
      <el-select
        v-if="type!==1"
        v-model="searchValue.employeeId"
        placeholder="选择员工"
        style="width: 15%;min-width: 200px"
        clearable
      >
        <el-option
          v-for="item in options.employeeOptions"
          :key="item.id"
          :label="item.name + '（' + item.employeeId + '）'"
          :value="item.id"
        >
        </el-option>
      </el-select>
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
          v-for="(item,index) in options.auditingStatusOptions"
          :key="index"
          :label="item"
          :value="index"
          :disabled="type === 2 && index === 3"
        >
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
              <span>{{getTime(row.startTime)}} - {{getTime(row.endTime)}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="功能名称"
            align="center"
          >
            <template slot-scope="{row}">
              <span>{{row.featureName? row.featureName : getTreeLabel(options.featureOptions, row.featureId)}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="活动名称"
            align="center"
          >
            <template slot-scope="{row}">
              <span>{{getTreeLabel(options.activityOptions, row.activityId)}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="审核状态"
            align="center"
          >
            <template slot-scope="{row}">
              <span>{{options.auditingStatusOptions[row.auditingStatus]}}</span>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            align="center"
          >
            <template slot-scope="{row}">
              <!-- 审核中的工时可以进行操作 -->
              <span v-if="canUpdate(row)">
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
  import {addWorkHour, getActivities, getWorkHours, updateWorkHour} from "../../../../api/work-hour";
  import {getNullOrValue, setTable} from "../../../../utils/common";
  import {getProjectWorkHourEmployee} from "../../../../api/employee";

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
          employeeId: '',
          startTime: '',
          auditingStatus: ''
        },
        options: {
          auditingStatusOptions: ['审核中', '已通过', '已驳回', '已撤回'],
          employeeOptions: [],
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
            employeeId: '',
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
      // 初始化功能列表
      this.getFeatures()
      // 初始化活动列表
      this.getActivities()
      // 初始化员工选项
      if (this.type !== 1) {
        this.getProjectEmployees()
      }
      // 获取工时信息
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
        this.loading = true
        getWorkHours({
          current: this.table.page,
          size: this.table.limit,
          searchCondition: {
            projectId: this.project.id,
            employeeId: this.table.searchCondition.employeeId,
            startTime: this.table.searchCondition.startTime,
            auditingStatus: this.table.searchCondition.auditingStatus,
            type: this.type
          }
        }).then(response => {
          setTable(response.data, this.table, this.setSearchValue())
        }).finally(() => {
          this.loading = false
        })
      },
      search() {
        this.table.page = 1
        this.table.searchCondition.employeeId = getNullOrValue(this.searchValue.employeeId)
        const date = this.searchValue.startTime
        this.table.searchCondition.startTime = date ? new Date(date.getFullYear(), date.getMonth(), date.getDate(), 8, 0, 0) : null
        this.table.searchCondition.auditingStatus = getNullOrValue(this.searchValue.auditingStatus)
        this.getWorkHour()
      },
      setSearchValue() {
        this.searchValue.employeeId = this.table.searchCondition.employeeId
        this.searchValue.startTime = this.table.searchCondition.startTime
        this.searchValue.auditingStatus = this.table.searchCondition.auditingStatus
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
        })
      },
      /**
       * 获取所有员工
       */
      getProjectEmployees() {
        getProjectWorkHourEmployee({
          id: this.project.id,
          isSubordinate: this.type === 2
        }).then(response => {
          this.options.employeeOptions = response.data
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
            this.addWorkHourDialog.data.startTime = new Date(date.getFullYear(), date.getMonth(), date.getDate(), startTime.getHours()+8, startTime.getMinutes(), startTime.getSeconds())
            this.addWorkHourDialog.data.endTime = new Date(date.getFullYear(), date.getMonth(), date.getDate(), endTime.getHours()+8, endTime.getMinutes(), endTime.getSeconds())
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
        }).finally(() => {
          // 关闭对话框
          this.addWorkHourDialog.show = false
          // 刷新
          this.getWorkHour()
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
        this.addWorkHourDialog.data.feature = this.getTreeValue(this.options.featureOptions, row.featureId)
        this.addWorkHourDialog.data.featureId = row.featureId
        this.addWorkHourDialog.data.activity = this.getTreeValue(this.options.activityOptions, row.activityId)
        this.addWorkHourDialog.data.activityId = row.activityId
        // 打开对话框
        this.addWorkHourDialog.show = true
      },

      getTree(options, id) {
        for (let i = 0; i < options.length; i++) {
          const children = options[i].children
          for (let j = 0; j < children.length; j++) {
            if (children[j].id === id) {
             return [options[i], children[j]]
            }
          }
        }
      },
      getTreeValue(options, id) {
        const tree = this.getTree(options, id)
        return [tree[0].id, tree[1].id]
      },
      getTreeLabel(options, id) {
        const tree = this.getTree(options, id)
        if (tree) {
          if (tree[0].label) {
            return tree[0].label + ' / ' + tree[1].label
          }
          if (tree[0].name) {
            return tree[0].name + ' / ' + tree[1].name
          }
        }
      },

      /**
       * 修改工时信息
       */
      updateWorkHour(params) {
        updateWorkHour(params).then(() => {
          this.$message.success('成功')
        }).finally(() => {
          // 关闭对话框
          this.addWorkHourDialog.show = false
          // 刷新
          this.getWorkHour()
        })
      },
      /**
       * 撤销
       */
      withdraw(row) {
        this.$confirm('确定撤销?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          closeOnClickModal: false,
          closeOnPressEscape: false
        }).then(() => {
          this.updateWorkHour({
            id: row.id,
            auditingStatus: 3
          })
        })
      },
      /**
       * 通过
       */
      pass(row) {
        this.updateWorkHour({
          id: row.id,
          auditingStatus: 1
        })
      },
      /**
       * 拒绝
       */
      refuse(row) {
        this.$confirm('确定驳回?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          closeOnClickModal: false,
          closeOnPressEscape: false
        }).then(() => {
          this.updateWorkHour({
            id: row.id,
            auditingStatus: 2
          })
        })
      },
      /**
       * 可以更新工时信息
       */
      canUpdate(row) {
        // 审核中且日期在三天内
        const date = new Date(row.startTime)
        date.setHours(0)
        date.setMinutes(0)
        date.setSeconds(0)
        return this.type !== 3 && row.auditingStatus === 0 && date.getTime() + 4 * 24 * 60 * 60 * 1000 > new Date().getTime()
      }
    }
  }
</script>

<style scoped>

</style>
