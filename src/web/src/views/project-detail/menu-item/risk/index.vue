<template>
  <div>
    <div>
      <el-select
        v-model="select"
        placeholder="风险类型"
        style="width: 15%;min-width: 120px"
        clearable
      >
        <el-option
          v-for="(item,index) in riskTypeOptions"
          :key="index"
          :value="index">
        </el-option>
      </el-select>
      <el-button icon="el-icon-search" circle @click="search" @keyup.enter="search">
      </el-button>
      <el-button type="primary" @click="dialogFormVisible_1 = true">
        <i class="el-icon-plus"></i>
        <span>新建风险</span>
      </el-button>
      <el-dialog title="新建风险" :visible.sync="dialogFormVisible_1">
        <el-form :model="form_1">
          <!-- value值要改动 -->
          <el-form-item label="风险类型" :label-width="formLabelWidth">
            <el-select v-model="form_1.type" placeholder="请选择风险类型">
              <el-option label="需求风险" value="shanghai"></el-option>
              <el-option label="过程&标准风险" value="beijing"></el-option>
              <el-option label="组织&人员管理风险" value="beijing"></el-option>
              <el-option label="技术风险" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="风险概率评估" :label-width="formLabelWidth">
            <el-select v-model="form_1.prob" placeholder="概率等级">
              <el-option label="很高" value=0.9></el-option>
              <el-option label="高" value=0.7></el-option>
              <el-option label="中等" value=0.5></el-option>
              <el-option label="低" value=0.3></el-option>
              <el-option label="很低" value=0.1></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="风险描述" :label-width="formLabelWidth">
            <el-input
              type="textarea"
              :rows="4"
              placeholder="请输入内容"
              v-model="form_1.describe">
            </el-input></el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible_1 = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible_1 = false">确 定</el-button>
        </div>
      </el-dialog>

      <el-button type="primary" @click="dialogFormVisible_2 = true">
        导入已有风险<i class="el-icon-upload el-icon--right"></i>
      </el-button>

      <el-dialog title="导入风险" :visible.sync="dialogFormVisible_2">
        <el-form :model="form_2">
          <el-form-item label="风险ID" :label-width="formLabelWidth">
            <el-input v-model="form_2.riskId" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible_2 = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible_2 = false">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-table
        :data="table.data"
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="风险 ID">
                <span>{{ props.row.riskID }}</span>
              </el-form-item>
              <el-form-item label="风险类型">
                <span>{{ props.row.type }}</span>
              </el-form-item>
              <el-form-item label="创建者">
                <span>{{ props.row.creator }}</span>
              </el-form-item>
              <el-form-item label="创建时间">
                <span>{{ props.row.time }}</span>
              </el-form-item>
              <el-form-item label="风险等级评估">
                <span>{{ props.row.prob }}</span>
              </el-form-item>
              <el-form-item label="风险描述">
                <span>{{ props.row.desc }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="风险 ID"
          prop="id">
        </el-table-column>
        <el-table-column
          label="风险类型"
          prop="type">
        </el-table-column>
        <el-table-column
          label="创建者"
          prop="creator">
        </el-table-column>
        <el-table-column
          label="创建时间"
          prop="time">
        </el-table-column>
        <el-table-column
          align="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete" circle
              @click="handleDelete(scope.$index, scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination :total="table.total" :page.sync="table.page" :limit.sync="table.limit" @pagination="getRisk"></pagination>
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
  export default {
    components: {
      Pagination
    },
    data() {
      return {
        select: '',
        riskTypeOptions:this.Constant.riskType, //此处未设置
        dialogFormVisible_1: false,
        form_1:{
          type:'',
          prob:'',
          describe:''
        },
        dialogFormVisible_2: false,
        form_2:{
          riskId:''
        },
        formLabelWidth: '120px',
        table: {
          data: [{
            riskID: '12987122',
            type: '技术风险',
            creator: 'wulei',
            time: '',
            prob: '较低',
            desc: 'XXXXXXXXXXX'

          }],
          total: 0,
          page: 1,
          limit: 10
        }
      }
    },
    methods: {
      handleDelete(index, row) {
        console.log(index, row);
      }
    }
  }
</script>

