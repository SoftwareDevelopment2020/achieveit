<template>
  <div>
    <div>
      <el-input
        v-model="searchValue.name"
        placeholder="项目名称"
        style="width: 50%;min-width: 260px"
        clearable
      >
      </el-input>
      <el-select
        v-model="searchValue.statusId"
        placeholder="项目状态"
        style="width: 15%;min-width: 120px"
        clearable
      >
        <el-option
          v-for="(item,index) in statusOptions"
          :key="index"
          :label="item.label"
          :value="index">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="search" @keyup.enter="search">
        <i class="el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button type="primary" @click="addProject">
        <i class="el-icon-plus"></i>
        <span>新建项目</span>
      </el-button>
    </div>

    <div>
      <el-table
        v-loading="loading"
        :data="table.data"
        style="width: 100%; margin-top: 30px"
        empty-text="未参与项目"
      >
        <el-table-column
          type="index"
          :index="indexMethod"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          label="项目名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="projectManagerName"
          label="创建人"
          width="150"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="scheduledDate"
          label="创建时间"
          width="150"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="statusId"
          label="项目状态"
          width="150"
          align="center"
        >
          <template slot-scope="{ row }">
            <span v-if="row.statusId !== null">
              <svg-icon :icon-class="statusOptions[getStatusId(row.statusId)].svg"></svg-icon>
              <span>{{ statusOptions[getStatusId(row.statusId)].label }}</span>
            </span>
          </template>
        </el-table-column>
        <el-table-column
          min-width="200">
          <template slot-scope="{ row }">
            <el-button type="text" size="mini" @click="selectProject(row)">进入项目</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination :total="table.total" :page="table.page" :limit="table.limit" @pagination="getProjects"></pagination>
    </div>

  </div>
</template>

<script>

  import Pagination from '@/components/Pagination/index'
  import {getProjects} from "@/api/project";
  import {setTable} from "@/utils/common";

  export default {
    components: {
      Pagination
    },
    data() {
      return {
        loading: false,

        searchValue: {
          name: '',
          statusId: '',
        },
        statusOptions: this.Constant.projectStatus,

        table: {
          data: [],
          searchCondition: {
            name: null,
            statusId: null,
          },
          total: 0,
          page: 1,
          limit: 10
        }
      }
    },
    mounted () {
      this.getProjects()
    },
    methods: {
      indexMethod (index) {
        return index+1
      },
      getStatusId (statusId) {
        return parseInt(statusId.toString().charAt(0))
      },
      getProjects () {
        this.loading = true
        getProjects({
          current: this.table.page,
          size: this.table.limit,
          searchCondition: this.table.searchCondition
        }).then(response => {
          setTable(response.data, this.table)
          this.searchValue.name = this.table.searchCondition.name
          this.searchValue.statusId = this.table.searchCondition.statusId
          this.loading = false
        }).catch(error => {
          console.error(error)
          this.loading = false
        })
      },
      search() {
        this.table.searchCondition.name = this.searchValue.name === '' ? null : this.searchValue.name
        this.table.searchCondition.statusId = this.searchValue.statusId === '' ? null : this.searchValue.statusId
        this.table.page = 1
        this.getProjects()
      },
      addProject() {
        this.$emit('changePage')
      },
      selectProject (project) {
        this.$emit('selectProject', project)
      }
    }
  }
</script>
