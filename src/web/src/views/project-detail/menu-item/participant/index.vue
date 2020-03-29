<template>
  <div>
    <div>
      <el-input
        v-model="searchValue.employeeId"
        placeholder="员工ID"
        style="width: 15%;min-width: 200px"
        clearable
      >
      </el-input>
      <el-input
        v-model="searchValue.name"
        placeholder="员工姓名"
        style="width: 20%;min-width: 200px"
        clearable
      >
      </el-input>
      <el-select
        v-model="searchValue.roles"
        multiple
        placeholder="员工角色"
        style="width: 30%;min-width: 120px"
        clearable
      >
        <el-option
          v-for="item in roleOptions"
          :key="item.name"
          :label="item.detail"
          :value="item.name">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="search" @keyup.enter="search">
        <i class="el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button v-if="canEdit" v-permission="['ROLE_PM']" type="primary" @click="addEmployee">
        <i class="el-icon-plus"></i>
        <span>添加人员</span>
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
          fixed="left"
          prop="employeeId"
          label="ID"
          align="center"
        >
        </el-table-column>
        <el-table-column
          fixed="left"
          prop="name"
          label="姓名"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="roles"
          label="角色"
          width="150"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="permissions"
          label="权限"
          width="150"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="employeeBasics"
          label="基本信息"
          width="150"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="superiorBasics"
          label="上级"
          width="150"
          align="center"
        >
        </el-table-column>
        <el-table-column
          min-width="200">
          <template slot-scope="{ row }">
            <span v-if="canEdit" v-permission="['ROLE_PM']">
              <el-button type="text" size="mini" @click="setRoleDialog(row)">设置角色</el-button>
              <el-button type="text" size="mini" @click="setPermissionDialog(row)">设置权限</el-button>
              <el-button type="text" size="mini" @click="deleteEmployee(row)">删除</el-button>
            </span>
          </template>
        </el-table-column>
      </el-table>

      <pagination :total="table.total" :page.sync="table.page" :limit.sync="table.limit" @pagination="getParticipants"></pagination>
    </div>
  </div>
</template>

<script>
  import Pagination from '@/components/Pagination/index'
  export default {
    components: {
      Pagination
    },
    data() {
      return {
        loading: false,
        project: null,
        table: {
          data: [],
          total: 0,
          page: 1,
          limit: 10,
          searchCondition: {

          }
        },
        roleOptions: this.Constant.roles,
        searchValue: {
          employeeId: '',
          name: '',
          role: '',
          permission: '',

        }
      }
    },
    mounted() {
      this.project =  this.$store.getters.project
    },
    methods: {
      /**
       * 行号
       */
      indexMethod(index) {
        return index+1
      },

      /**
       * 判断当前项目状态，只有3111时可以进行人员编辑
       */
      canEdit() {
        return this.project.statusId === 3111
      },

      /**
       * 获取项目人员
       */
      getParticipants() {

      },
      search() {

      },

      /**
       * 添加项目人员
       */
      addEmployee() {

      },

      /**
       * 删除项目人员
       */
      deleteEmployee(row) {

      },

      /**
       * 设置角色
       */
      setRoleDialog(row) {

      },
      setRole() {

      },

      /**
       * 设置权限
       */
      setPermissionDialog(row) {

      },
      setPermission() {

      }
    }
  }
</script>

<style scoped>

</style>
