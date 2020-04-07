<template>
  <div>
    <div>
      <el-input
        v-model="searchValue.type"
        placeholder="资产类型"
        style="width: 15%;min-width: 200px"
        name="deviceTypeSearch"
        clearable
      >
      </el-input>
      <el-select
        v-model="searchValue.deviceCondition"
        placeholder="设备状态"
        style="width: 15%;min-width: 120px"
        name="deviceStatusSearch"
        clearable
      >
        <el-option
          v-for="item in deviceConditionOptions"
          :key="item.value"
          name="deviceStatusSearchOption"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-select
        v-model="searchValue.isReturned"
        placeholder="是否归还"
        style="width: 15%;min-width: 120px"
        name="deviceIsReturnedSearch"
        clearable
      >
        <el-option
          v-for="item in isReturnedOptions"
          :key="item.value"
          :label="item.label"
          name="deviceIsReturnedSearchOption"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="search" @keyup.enter="search"
                 name="deviceSearchButton">
        <i class="el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button v-if="canAdd" v-permission="['ROLE_GLOBAL_DEVM']" name="openAddDeviceDialogButton" type="primary"
                 @click="openAddDeviceDialog">
        <i class="el-icon-plus"></i>
        <span>添加设备</span>
      </el-button>
    </div>
    <div>
      <el-table
        v-loading="loading"
        :data="table.data"
        style="width: 100%; margin-top: 30px"
      >
        <el-table-column
          type="index"
          :index="indexMethod"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="type"
          label="资产类型"
          align="center"
        >
        </el-table-column>
        <el-table-column
          label="资产管理者"
          align="center"
        >
          <template slot-scope="{row}">
            <span>{{row.managerBasics.name + '（' + row.managerBasics.employeeId + '）'}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="usageTimeLimit"
          label="资产使用期限"
          width="300"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="deviceCondition"
          label="设备状态"
          width="150"
          align="center"
        >
          <template slot-scope="{row}">
            <span v-if="row.deviceCondition">完好</span>
            <span v-else>损坏</span>
            <el-button v-if="!row.isReturned" name="deviceChangeStatusButton" type="text" size="mini"
                       @click="updateDeviceCondition(row)">
              <span v-if="row.deviceCondition">损坏</span>
              <span v-else>完好</span>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="isReturned"
          label="是否归还"
          width="150"
          align="center"
        >
          <template slot-scope="{row}">
            <span v-if="row.isReturned">已归还</span>
            <span v-else>
                <span>未归还</span>
                <el-button type="text" size="mini" name="deviceReturnButton"
                           @click="updateIsReturned(row)">归还</el-button>
              </span>
          </template>
        </el-table-column>
        <el-table-column
          prop="returnDate"
          label="归还日期"
          width="300"
          align="center"
        >
        </el-table-column>
      </el-table>

      <pagination :total="table.total" :page.sync="table.page" :limit.sync="table.limit"
                  @pagination="getDevices"></pagination>
    </div>

    <!-- 添加设备 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="添加设备"
      :visible.sync="addDeviceDialog.show"
      width="60%"
      center
    >
      <el-form
        ref="addDeviceForm"
        :model="addDeviceDialog.data"
        :rules="addDeviceDialog.rules"
        :hide-required-asterisk="true"
        label-width="100px"
        style="width: 80%"
      >
        <el-form-item label="设备类型" prop="type">
          <el-input v-model="addDeviceDialog.data.type" name="addDeviceType" clearable></el-input>
        </el-form-item>
        <el-form-item label="使用期限" prop="usageTimeLimit">
          <el-date-picker
            v-model="addDeviceDialog.data.usageTimeLimit"
            name="addDeviceUsageTimeLimit"
            type="date"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="设备状态" prop="deviceCondition">
          <el-select
            v-model="addDeviceDialog.data.deviceCondition"
            name="addDeviceStatus"
            style="width: 100%"
          >
            <el-option
              v-for="item in deviceConditionOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDeviceDialog.show = false" name="addDeviceCancelSubmit">取 消</el-button>
        <el-button type="primary" @click="addDevice" name="addDeviceSubmit">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  import Pagination from '@/components/Pagination/index'
  import {dateToString} from "@/utils/date";
  import {addDevice, getDevices, updateDevice} from "../../../../api/device";
  import {getNullOrValue, setTable} from "../../../../utils/common";

  export default {
    components: {
      Pagination
    },
    data() {
      return {
        project: this.$store.getters.project,
        loading: false,
        table: {
          data: [],
          total: 0,
          page: 1,
          limit: 10,
          searchCondition: {
            type: null,
            deviceCondition: null,
            isReturned: null
          }
        },
        searchValue: {
          type: '',
          deviceCondition: '',
          isReturned: ''
        },
        deviceConditionOptions: this.Constant.deviceConditions,
        isReturnedOptions: this.Constant.isReturned,
        addDeviceDialog: {
          show: false,
          data: {
            type: '',
            usageTimeLimit: '',
            deviceCondition: true
          },
          rules: {
            type: [{required: true, message: '设备类型不能为空', trigger: 'blur'}]
          }
        }
      }
    },
    mounted() {
      this.getDevices()
    },
    methods: {
      /**
       * 行号
       */
      indexMethod(index) {
        return index + 1
      },

      /**
       * 判断当前项目状态，在审核通过，归档前可以进行人员编辑
       */
      canAdd() {
        return this.project.status.toString().charAt(0) === '3'
      },

      /**
       * 获取设备信息
       */
      getDevices() {
        this.loading = true
        getDevices({
          current: this.table.page,
          size: this.table.limit,
          searchCondition: {
            projectId: this.project.id,
            ...this.table.searchCondition
          }
        }).then(response => {
          setTable(response.data, this.table, this.setSearchCondition)
        }).finally(() => {
          this.loading = false
        })
      },
      search() {
        this.table.page = 1
        this.table.searchCondition.type = getNullOrValue(this.searchValue.type)
        this.table.searchCondition.deviceCondition = getNullOrValue(this.searchValue.deviceCondition)
        this.table.searchCondition.isReturned = getNullOrValue(this.searchValue.isReturned)
        this.getDevices()
      },
      setSearchCondition() {
        this.searchValue.type = this.table.searchCondition.type
        this.searchValue.deviceCondition = this.table.searchCondition.deviceCondition
        this.searchValue.isReturned = this.table.searchCondition.isReturned
      },

      /**
       * 添加设备信息
       */
      addDevice() {
        this.$refs.addDeviceForm.validate(valid => {
          if (valid) {
            addDevice({
              projectId: this.project.id,
              ...this.addDeviceDialog.data
            }).then(response => {
              this.$message.success('成功')
            }).finally(() => {
              // 关闭对话框
              this.addDeviceDialog.show = false
              // 刷新
              this.getDevices()
            })
          } else {
            this.$message({
              type: 'error',
              message: '请完整填写所需字段',
              duration: 2 * 1000
            })
          }
        })
      },
      openAddDeviceDialog() {
        // 初始化数据
        this.addDeviceDialog.data.type = ''
        this.addDeviceDialog.data.usageTimeLimit = dateToString(new Date())
        this.addDeviceDialog.data.deviceCondition = true
        // 打开对话框
        this.addDeviceDialog.show = true
      },

      /**
       * 更新设备信息
       */
      updateDevice(params) {
        updateDevice(params).then(response => {
          this.$message.success('成功')
        }).finally(() => {
          // 刷新
          this.getDevices()
        })
      },
      updateDeviceCondition(row) {
        this.updateDevice({
          id: row.id,
          deviceCondition: !row.deviceCondition
        })
      },
      updateIsReturned(row) {
        this.updateDevice({
          id: row.id,
          isReturned: true,
          returnDate: dateToString(new Date())
        })
      }
    }
  }
</script>

<style scoped>

</style>
