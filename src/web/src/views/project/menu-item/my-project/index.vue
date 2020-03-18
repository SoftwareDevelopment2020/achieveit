<template>
  <div>
    <div>
      <el-input
        v-model="searchValue.name"
        placeholder="项目名称"
        style="width: 50%;min-width: 260px"
      >
      </el-input>
      <el-select
        v-model="searchValue.status"
        placeholder="项目状态"
        style="width: 15%;min-width: 120px"
        clearable
      >
        <el-option
          v-for="item in statusOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="search">
        <i class="el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button type="primary">
        <i class="el-icon-plus"></i>
        <span>新建项目</span>
      </el-button>
    </div>

    <div>
      <el-table
        :data="table.data"
        style="width: 100%; margin-top: 30px"
        empty-text="未参与项目"
      >
        <el-table-column
          type="index"
          :index="index+1"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          label="项目名称"
          min-width="100">
        </el-table-column>
        <el-table-column
          prop="projectManagerName"
          label="创建人"
          width="150">
        </el-table-column>
        <el-table-column
          prop="scheduledDate"
          label="创建时间"
          width="300">
        </el-table-column>
        <el-table-column
          label="项目状态"
          width="150">
        </el-table-column>
        <el-table-column
          width="200">
          <el-button type="text" size="mini">进入项目</el-button>
        </el-table-column>
      </el-table>

      <pagination :total="table.total" :page="table.page" :limit="table.limit"></pagination>
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
        searchValue: {
          name: '',
          archive: '',
        },
        statusOptions: [{
          value: 0,
          label: '审核中',
        }, {
          value: 1,
          label: '进行中'
        }, {
          value: 2,
          label: '已归档'
        }],

        table: {
          data: [],
          total: 0,
          page: 1,
          limit: 10
        }
      }
    },
    methods: {
      search() {

      }
    }
  }
</script>

<style scoped>

</style>
