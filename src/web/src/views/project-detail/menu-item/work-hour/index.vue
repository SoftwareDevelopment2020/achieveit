<template>
    <div>
      <div>
        <el-input
          v-model="searchValue.ID"
          placeholder="人员ID"
          style="width: 15%;min-width: 200px"
          clearable
        >
        </el-input>
        <el-input
          v-model="searchValue.date"
          placeholder="日期"
          style="width: 15%;min-width: 200px"
          clearable
        >
        </el-input>
        <el-select
          v-model="searchValue.isApproved"
          placeholder="审核状态"
          style="width: 15%;min-width: 120px"
          clearable
        >
          <el-option
            v-for="item in isApprovedOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-button type="primary" style="margin-left: 10px" @click="search" @keyup.enter="search">
          <i class="el-icon-search"></i>
          <span>搜索</span>
        </el-button>
        <el-button  type="primary" @click="dialogFormVisible = true" >
          <i class="el-icon-plus"></i>
          <span>添加工时信息</span>
        </el-button>
      </div>
      <!-- 添加工时信息 -->
      <el-dialog title="工时信息" :visible.sync="dialogFormVisible">
        <el-form :model="timeForm">
          <el-form-item label="员工ID":label-width="formLabelWidth">
            <el-input v-model="timeForm.ID" placeholder="请输入身份ID"></el-input>
          </el-form-item>
          <el-form-item label="日期" :label-width="formLabelWidth">
            <el-date-picker
              v-model="timeForm.date"
              type="date"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="功能名称" :label-width="formLabelWidth">
            <el-select v-model="timeForm.function">
              <!--和功能列表关联 -->
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="活动名称" :label-width="formLabelWidth">
            <el-cascader
              v-model="timeForm.action"
              :options="options"
              :props="{ expandTrigger: 'hover' }"
              @change="handleChange"></el-cascader>
          </el-form-item>
          <el-form-item label="起始时间" :label-width="formLabelWidth">
            <el-time-picker
              v-model="timeForm.startTime"
              :picker-options="{selectableRange: '00:00:00 - 23:59:59'}"
            >
            </el-time-picker>
          </el-form-item>
          <el-form-item label="结束时间":label-width="formLabelWidth">
            <el-time-picker
              v-model="timeForm.endTime"
              :picker-options="{selectableRange: '00:00:00 - 23:59:59'}"
            >
            </el-time-picker>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>
      </el-dialog>

      <div>
        <el-table
          v-loading="loading"
          :data="tableData"
          style="width: 100%; margin-top: 30px"
          empty-text="无工时条目"
        >
          <el-table-column
            prop="id"
            label="员工ID"
            align="center"
            min-width="40"
          >
          </el-table-column>
          <el-table-column
            prop="name"
            label="姓名"
            align="center"
            min-width="40"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            label="日期"
            align="center"
            min-width="40"
          >
          </el-table-column>
          <el-table-column
            prop="startTime"
            label="起始时间"
            align="center"
            min-width="40"
          >
          </el-table-column>
          <el-table-column
            prop="endTime"
            label="结束时间"
            align="center"
            min-width="40"
          >
          </el-table-column>
          <el-table-column
            prop="approvalStatus"
            label="审核状态"
            align="center"
            min-width="40"
          >
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            align="center"
            min-width="70">
            <template slot-scope="scope">
              <el-button type="text"  style="color: #85ce61" >通过</el-button>
              <el-button type="text"  style="color: #f78989" >驳回</el-button>
              <el-button type="text"  style="color: #E6A23C" >撤销</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

    </div>
</template>

<script>
  export default {
    data(){
      return{
        searchValue: {
          ID: '',
          date: '',
          isApproved: ''
        },
        isApprovedOptions: this.Constant.isApproved, //未审核、通过、驳回三种
        dialogFormVisible: false,
        timeForm: {
          ID:'',
          date:'',
          function:'',
          action:'',
          startTime:'',
          endTime:''
        },
        formLabelWidth: '120px',
        options:[
          {
            value:'gongchenghuodong',
            label:'工程活动',
            children:[{
              value:'xuqiukaifa',
              label:'需求开发'
            },
              {
                value:'sheji',
                label:'设计'
              },
              {
                value:'bianma',
                label:'编码'
              },
              {
                value:'danticeshi',
                label:'单体测试'
              },
              {
                value:'jichengceshi',
                label:'集成测试'
              },
              {
                value:'xitongceshi',
                label:'系统测试'
              },
              {
                value:'jiaofu',
                label:'交付'
              },
              {
                value:'weihu',
                label:'维护'
              }
            ]
          },
          {
            value:'guanlihuodong',
            label:'管理活动',
            children:[
              {
                value:'fanweiguanli',
                label:'范围管理'
              },
              {
                value:'jihuayutiaozheng',
                label:'计划与tiaozheng'
              },
              {
                value:'jiankongyufenxi',
                label:'监控与分析'
              },
              {
                value:'lianluoyugoutong',
                label:'联络与沟通'
              }
            ]
          },
          {
            value:'waibaohuodong',
            label:'外包活动',
            children:[
              {
                value:'waibaoguanli',
                label:'外包管理'
              },
              {
                value:'waibaoyanshou',
                label:'外包验收'
              },
              {
                value:'waibaozhichi',
                label:'外包支持'
              }
            ]
          },
          {
            value:'zhichihuodong',
            label:'支持活动',
            children:[
              {
                value:'peizhiguanli',
                label:'配置管理'
              },
              {
                value:'QAshenji',
                label:'QA审计'
              },
              {
                value:'huiyibaogaozongjie',
                label:'会议报告总结'
              },
              {
                value:'peixun',
                label:'培训'
              },
              {
                value:'qita',
                label:'其他'
              }
            ]
          }],
        tableData:[
          {
            id:'0001',
            name:'wuwang',
            date:'2020-4-5',
            startTime:'10:03:02',
            endTime:'20:07:00',
            approvalStatus:'未审核'
          },
        ]
      }
    }

  }
</script>

<style scoped>

</style>
