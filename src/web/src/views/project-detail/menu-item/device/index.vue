<template>
    <div>
      <div>
        <el-input
          v-model="searchValue.type"
          placeholder="资产类型"
          style="width: 15%;min-width: 200px"
          clearable
        >
        </el-input>
        <el-select
          v-model="searchValue.deviceCondition"
          placeholder="设备状态"
          style="width: 15%;min-width: 120px"
          clearable
        >
          <el-option
            v-for="item in deviceConditionOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-select
          v-model="searchValue.isReturned"
          placeholder="是否归还"
          style="width: 15%;min-width: 120px"
          clearable
        >
          <el-option
            v-for="item in isReturnedOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-button type="primary" style="margin-left: 10px" @click="search" @keyup.enter="search">
          <i class="el-icon-search"></i>
          <span>搜索</span>
        </el-button>
        <el-button v-if="canAdd" v-permission="['ROLE_GLOBAL_DEVM']" type="primary" @click="addDevice">
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
            fixed="left"
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
            fixed="left"
            prop="managerId"
            label="资产管理者"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="usageTimeLimit"
            label="资产使用期限"
            width="150"
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
              <el-button type="text" size="mini" @click="updateDeviceCondition(row)">
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
                <el-button type="text" size="mini" @click="updateIsReturned(row)">归还</el-button>
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="returnDate"
            label="归还日期"
            width="150"
            align="center"
          >
          </el-table-column>
        </el-table>

        <pagination :total="table.total" :page.sync="table.page" :limit.sync="table.limit" @pagination="getDevices"></pagination>
      </div>
    </div>
</template>

<script>
  import Pagination from '@/components/Pagination/index'
  import {dateToString} from "@/utils/date";

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

          }
        },
        searchValue: {
          type: '',
          deviceCondition: '',
          isReturned: ''
        },
        deviceConditionOptions: this.Constant.deviceConditions,
        isReturnedOptions: this.Constant.isReturned
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
        return index+1
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

      },
      search() {

      },

      /**
       * 添加设备信息
       */
      addDevice() {

      },

      /**
       * 更新设备信息
       */
      updateDevice(params) {

      },
      updateDeviceCondition(row) {
        this.updateDevice({
          id: row.id,
          deviceCondition: row.deviceCondition
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
