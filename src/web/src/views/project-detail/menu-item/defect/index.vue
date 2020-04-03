<template>
  <div>
    <div>
      <el-input
        v-model="searchValue.ID"
        placeholder="编号"
        style="width: 50%;min-width: 260px"
        clearable
      >
      </el-input>
      <el-select
        v-model="searchValue.statusId"
        placeholder="状态"
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
      <el-button type="primary" @click="dialogFormVisible = true">
        <i class="el-icon-plus"></i>
        <span>新建项目</span>
      </el-button>

      <el-dialog title="新建缺陷" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="标题" :label-width="formLabelWidth">
            <el-input v-model="form.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="负责人" :label-width="formLabelWidth">
            <el-input v-model="form.personInCharge" autocomplete="off"></el-input>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <div>
                <el-form-item label="开始时间" :label-width="formLabelWidth">
                  <el-date-picker type="date" v-model="form.startDate" name="scheduledDate" format="yyyy 年 MM 月 dd 日"
                                  value-format="yyyy-MM-dd" style="width: 90%;"></el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div>
                <el-form-item label="结束时间" :label-width="formLabelWidth">
                  <el-date-picker type="date" v-model="form.endDate" name="scheduledDate" format="yyyy 年 MM 月 dd 日"
                                  value-format="yyyy-MM-dd" style="width: 90%;"></el-date-picker>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <div>
                <el-form-item label="严重程度" :label-width="formLabelWidth">
                  <el-select v-model="form.severity" placeholder="请选择严重程度">
                    <el-option label="致命" value="致命"></el-option>
                    <el-option label="严重" value="beijing"></el-option>
                    <el-option label="一般" value="beijing"></el-option>
                    <el-option label="建议" value="beijing"></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div>
                <el-form-item label="缺陷类型" :label-width="formLabelWidth">
                  <el-select v-model="form.type" placeholder="请选择缺陷类别">
                    <el-option label="功能问题" value="shanghai"></el-option>
                    <el-option label="性能问题" value="beijing"></el-option>
                    <el-option label="接口问题" value="beijing"></el-option>
                    <el-option label="安全问题" value="beijing"></el-option>
                    <el-option label="UI问题" value="beijing"></el-option>
                    <el-option label="兼容性问题" value="beijing"></el-option>
                    <el-option label="易用性问题" value="beijing"></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <div>
                <el-form-item label="复现概率" :label-width="formLabelWidth">
                  <el-select v-model="form.prob" placeholder="请选择复现概率">
                    <el-option label="必现" value=0></el-option>
                    <el-option label="大概率复现" value=1></el-option>
                    <el-option label="小概率复现" value=2></el-option>
                    <el-option label="仅出现一次" value=3></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div>
                <el-form-item label="优先级" :label-width="formLabelWidth">
                  <el-select v-model="form.priority" placeholder="请选择优先级">
                    <el-option label="最高" value="shanghai"></el-option>
                    <el-option label="较高" value="beijing"></el-option>
                    <el-option label="普通" value="beijing"></el-option>
                    <el-option label="较低" value="beijing"></el-option>
                    <el-option label="最低" value="beijing"></el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-form-item label="描述" :label-width="formLabelWidth">
            <el-input
              type="textarea"
              :rows="3"
              placeholder="请输入内容"
              v-model="form.describe">
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-table
        v-loading="loading"
        :data="res.records"
        style="width: 100%; margin-top: 30px"
        empty-text="无缺陷条目"
      >
        <el-table-column type="expand" fixed="left">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="缺陷标题">
                <span>{{ props.row.bugTitle }}</span>
              </el-form-item>
              <el-form-item label="缺陷ID">
                <span>{{ props.row.id }}</span>
              </el-form-item>
              <el-form-item label="提出人">
                <span>{{ props.row.bugIntroducerId}}</span>
              </el-form-item>
              <el-form-item label="负责人">
                <span>{{ props.row.bugResponsibleId}}</span>
              </el-form-item>
              <el-form-item label="开始时间">
                <span>{{ dateFormat(props.row.startTime) }}</span>
              </el-form-item>
              <el-form-item label="结束时间">
                <span>{{ dateFormat(props.row.endTime) }}</span>
              </el-form-item>
              <el-form-item label="缺陷状态">
                <span>{{ props.row.status }}</span>
              </el-form-item>
              <el-form-item label="优先级">
                <span>{{ props.row.priority }}</span>
              </el-form-item>
              <el-form-item label="缺陷描述">
                <span>{{ props.row.bugDescription }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          fixed="left"
          prop="id"
          label="缺陷ID"
          align="center"
          min-width="120"
        >
        </el-table-column>
        <el-table-column
          fixed="left"
          prop="bugTitle"
          label="标题"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="bugIntroducerId"
          label="提出人"
          width="150"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="status"
          label="缺陷状态"
          width="150"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="开始时间"
          width="200"
          align="center"
        >
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ dateFormat(scope.row.startTime) }}</span>
          </template>
        </el-table-column>
      </el-table>

      <pagination :total="res.total" :page.sync="res.current" :limit.sync="res.limit"
                  @pagination="getBugs"></pagination>
    </div>

  </div>
</template>

<style>
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
<script>

  import Pagination from '@/components/Pagination/index'
  import {getBugs} from "../../../../api/bug";

  import dayjs from 'dayjs'
  export default {
    components: {
      Pagination
    },
    data() {
      return {
        loading: false,

        searchValue: {
          ID: '',
          statusId: '',
        },
        statusOptions: this.Constant.defectStatus, //这里要设置缺陷类型

        res: {
          size: 10,
          current: 1,
          total: 0,
          records: []
        },
        dialogTableVisible: false,
        dialogFormVisible: false,
        form: {
          title: '',
          personInCharge: '',
          startDate: '',
          endDate: '',
          severity: '',
          type: '',
          prob: '',
          priority: '',
          describe: ''
        },
        formLabelWidth: '120px'
      }
    },
    mounted() {
      this.getBugs()
    },
    methods: {
      getBugs() {
        console.log('查询bug页数为' + this.res.current)
        console.log('查询bug条目数量为' + this.res.size)
        getBugs(this.$store.getters.projectId, this.res.current, this.res.size).then(response => {
          this.res = response.data
        }).catch(error => {

        })
      },

      search() {
        this.table.searchCondition.ID = this.searchValue.ID === '' ? null : this.searchValue.ID
        this.table.searchCondition.statusId = this.searchValue.statusId === '' ? null : this.searchValue.statusId
        this.table.page = 1
        this.getProjects()
      },
      dateFormat(time){
        return dayjs(time).format('YYYY-MM-DD HH:mm:ss')
      }
    }
  }
</script>
