<template>
  <div>
    <div>
      <div>
        <!--        <el-input-->
        <!--          v-model="searchValue.ID"-->
        <!--          placeholder="编号"-->
        <!--          style="width: 50%;min-width: 260px"-->
        <!--          clearable-->
        <!--        >-->
        <!--        </el-input>-->
        <el-input
          v-model="searchValue.id"
          placeholder="缺陷ID"
          style="width: 15%;min-width: 200px"
          name="bugIdSearch"
          @keyup.enter.native="getBugs"
          clearable
        ></el-input>
        <el-input
          v-model="searchValue.bugTitle"
          placeholder="缺陷标题"
          style="width: 15%;min-width: 200px"
          name="bugTitleSearch"
          @keyup.enter.native="getBugs"
          clearable
        ></el-input>
<!--        <el-input-->
<!--          v-model="searchValue.bugIntroducer"-->
<!--          placeholder="缺陷提出人"-->
<!--          style="width: 15%;min-width: 200px"-->
<!--          name="bugIntroducerSearch"-->
<!--          @keyup.enter.native="getBugs"-->
<!--          clearable-->
<!--        ></el-input>-->
        <el-select
          v-model="searchValue.bugIntroducerId"
          placeholder="缺陷提出人"
          style="width: 15%;min-width: 200px"
          name="bugIntroducerSearch"
          @keyup.enter.native="getBugs"
          filterable
          clearable
        >
          <el-option
            v-for="employee in employees"
            :key="employee.id"
            :label="employee.name"
            :value="employee.id">
            <span style="float: left">{{ employee.name }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ employee.id }}</span>
          </el-option>
        </el-select>
        <el-select
          v-model="searchValue.bugResponsibleId"
          placeholder="缺陷负责人"
          style="width: 15%;min-width: 200px"
          name="bugResponsibleSearch"
          @keyup.enter.native="getBugs"
          filterable
          clearable
        >
          <el-option
            v-for="employee in employees"
            :key="employee.id"
            :label="employee.name"
            :value="employee.id">
            <span style="float: left">{{ employee.name }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ employee.id }}</span>
          </el-option>
        </el-select>
        <el-select
          v-model="searchValue.status"
          placeholder="状态"
          style="width: 15%;min-width: 120px"
          name="bugStatusSearch"
          clearable
        >
          <el-option v-for="item in statusOptions" :key="item.id" :label="item.value" name="bugStatusSearchOption"
                     :value="item.id"></el-option>
        </el-select>
        <el-button type="primary" style="margin-left: 10px" @click="getBugs" name="bugSearchButton">
          <i class="el-icon-search"></i>
          <span>搜索</span>
        </el-button>
        <el-button type="primary" @click="openNewBugDialog" name="bugOpenAddBugDialogButton" v-permission="['ROLE_PM']" v-if="canEdit()">
          <i class="el-icon-plus"></i>
          <span>添加缺陷</span>
        </el-button>

        <el-dialog title="新建缺陷" :visible.sync="dialogFormVisible">
          <el-form :model="newBug" :rules="bugRules" ref="newBug">
            <el-form-item label="标题" :label-width="formLabelWidth" prop="bugTitle">
              <el-input v-model="newBug.bugTitle" autocomplete="off" name="newBugTitle"></el-input>
            </el-form-item>
            <el-row>
              <el-col :span="12">
                <el-form-item label="缺陷负责人" :label-width="formLabelWidth" prop="bugResponsibleId" >
                  <el-select v-model="newBug.bugResponsibleId" placeholder="缺陷负责人" name="newBugResponsible">
                    <el-option v-for="employee in employees" :key="employee.id" :label="employee.name" name="newBugResponsibleOption"
                               :value="employee.id">
                      <span style="float: left">{{ employee.name }}</span>
                      <span style="float: right; color: #8492a6; font-size: 13px">{{ employee.id }}</span>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <div>
                  <el-form-item label="优先级" :label-width="formLabelWidth" prop="priority" >
                    <el-select v-model="newBug.priority" placeholder="请选择优先级" name="newBugPriority">
                      <el-option v-for="item in bugPriority" :key="item.id" :label="item.value" name="newBugPriorityOption"
                                 :value="item.id"></el-option>
                    </el-select>
                  </el-form-item>
                </div>
              </el-col>
            </el-row>
            <el-row>

              <el-col :span="12">
                <div>
                  <el-form-item label="开始时间" :label-width="formLabelWidth" prop="startTime">
                    <el-date-picker type="date" v-model="newBug.startTime" name="scheduledDate"
                                    format="yyyy 年 MM 月 dd 日"
                                    value-format="yyyy-MM-dd" style="width: 90%;" disabled></el-date-picker>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="12">
                <el-form-item label="缺陷状态" :label-width="formLabelWidth" prop="status">
                  <el-select v-model="newBug.status" placeholder="请选择缺陷状态" disabled>
                    <el-option v-for="item in statusOptions" :key="item.id" :label="item.value"
                               :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="缺陷描述" :label-width="formLabelWidth" prop="bugDescription" >
              <el-input
                name="newBugDescription"
                type="textarea"
                :rows="4"
                placeholder="请输入缺陷的描述内容"
                v-model="newBug.bugDescription">
              </el-input>
            </el-form-item>


          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false" name="newBugCancelSubmit">取 消</el-button>
            <el-button type="primary" @click="submitBug" name="newBugSubmit">确 定</el-button>
          </div>
        </el-dialog>
      </div>

      <el-dialog title="编辑缺陷" :visible.sync="editBugDialogVisible">
        <el-form :model="editBug" :rules="bugRules" ref="editBugForm">
          <el-form-item label="标题" :label-width="formLabelWidth" prop="bugTitle" >
            <el-input v-model="editBug.bugTitle" autocomplete="off" name="editBugTitle"></el-input>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <div>
                <el-form-item label="开始时间" :label-width="formLabelWidth" prop="startTime">
                  <el-date-picker type="date" v-model="editBug.startTime" name="startTime" format="yyyy 年 MM 月 dd 日"
                                  disabled
                  ></el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="12">
              <div>
                <el-form-item label="优先级" :label-width="formLabelWidth" >
                  <el-select v-model="editBug.priority" placeholder="请选择优先级" name="editBugPriority">
                    <el-option v-for="priority in bugPriority" :key="priority.id" :label="priority.value" name="editBugPriorityOption"
                               :value="priority.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="缺陷负责人" :label-width="formLabelWidth" >
                <el-select v-model="editBug.bugResponsibleId" placeholder="缺陷负责人" name="editBugResponsible">
                  <el-option v-for="employee in employees" :key="employee.id" :label="employee.name" name="editBugResponsibleOption"
                             :value="employee.id">
                    <span style="float: left">{{ employee.name }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ employee.id }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="缺陷状态" :label-width="formLabelWidth">
                <el-select v-model="editBug.status" placeholder="请选择缺陷状态" name="editBugStatus">
                  <el-option v-for="item in statusOptions" :key="item.id" :label="item.value" name="editBugStatusOption"
                             :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="缺陷描述" :label-width="formLabelWidth" prop="bugDescription" >
            <el-input
              type="textarea"
              :rows="4"
              name="editBugDescription"
              placeholder="请输入缺陷的描述内容"
              v-model="editBug.bugDescription">
            </el-input>
          </el-form-item>


        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="editBugDialogVisible=false" name="editBugCancelSubmit">取 消</el-button>
          <el-button type="primary" @click="submitEditBug" name="editBugSubmit">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-table
        v-loading="loading"
        :data="bugs.records"
        style="width: 100%; margin-top: 30px"
        empty-text="无缺陷条目"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="缺陷标题">
                <span>{{ props.row.bugTitle }}</span>
              </el-form-item>
              <el-form-item label="缺陷ID">
                <span>{{ props.row.id }}</span>
              </el-form-item>
              <el-form-item label="提出人">
                <span>{{ props.row.bugIntroducer.name}}</span>
              </el-form-item>
              <el-form-item label="负责人">
                <span>{{ props.row.bugResponsible.name}}</span>
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
                <span v-for="priority in bugPriority" v-if="priority.id==props.row.priority">{{ priority.value }}</span>
              </el-form-item>
              <el-form-item label="缺陷描述">
                <span>{{ props.row.bugDescription }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          prop="bugTitle"
          label="标题"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="bugIntroducer.name"
          label="提出人"
          width="150"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="bugResponsible.name"
          label="负责人"
          width="150"
          align="center"
        >
        </el-table-column>
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
        <el-table-column align="right">
          <template slot-scope="scope">
            <el-button
              v-permission="['ROLE_PM']"
              size="mini"
              @click="handleEdit(scope.$index, scope.row)" :disabled="scope.row.status==='CLOSED'" name="openEditDialogButton">编辑
            </el-button>
<!--            <el-button-->
<!--              size="mini"-->
<!--              type="danger"-->
<!--              name="bugDeleteButton"-->
<!--              @click="handleDelete(scope.$index, scope.row)">删除-->
<!--            </el-button>-->
          </template>
        </el-table-column>
      </el-table>

      <pagination :total="bugs.total" :page.sync="bugs.current" :limit.sync="bugs.size"
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
  import {getBugs, addBug, updateBug} from "../../../../api/bug";
  import dayjs from 'dayjs'
  import {getAllProjectEmployeeBasics, getEmployeesByProjectId} from "../../../../api/employee";

  export default {
    inject: ['reload'],
    components: {
      Pagination,
    },
    data() {
      return {
        loading: false,
        editBugDialogVisible: false,
        project: this.$store.getters.project,
        searchValue: {
          id: '',
          status: '',
          bugTitle: '',
          bugIntroducer: '',
          bugIntroducerId: '',
          bugResponsibleId: ''
        },
        statusOptions: this.Constant.status,
        employees: null,
        bugPriority: this.Constant.bugPriority,
        bugRules: this.Constant.bugRules,
        bugs: {
          size: 10,
          current: 1,
          total: 0,
          records: []
        },
        dialogTableVisible: false,
        dialogFormVisible: false,
        newBug: {
          bugTitle: '',
          bugIntroducerId: '',
          bugResponsibleId: '',
          startTime: new Date(),
          endTime: '',
          bugDescription: '',
          priority: '',
          status: '0'
        },
        editBug: {
          bugTitle: '',
          bugIntroducerId: '',
          bugResponsibleId: '',
          startTime: '',
          endTime: '',
          bugDescription: '',
          priority: null,
          status: null
        },
        formLabelWidth: '120px'
      }
    },
    mounted() {
      this.getBugs()
      getAllProjectEmployeeBasics({
        id: this.$store.getters.projectKey
      }).then(res => {
        this.employees = res.data
      })
    },
    methods: {
      canEdit() {
        return this.project.statusId === 3111
      },
      getBugs() {
        this.loading = true
        console.log('查询bug页数为' + this.bugs.current)
        console.log('查询bug条目数量为' + this.bugs.size)
        const data = {
          current: this.bugs.current,
          size: this.bugs.size,
          searchCondition: this.searchValue
        }
        if (this.searchValue.id.length !== 0 || this.searchValue.status.length !== 0 ||
          this.searchValue.bugTitle.length !== 0 || this.searchValue.bugIntroducer.length !== 0) {
          data.current = 1;
        }
        getBugs(this.$store.getters.projectId, data).then(response => {
          this.bugs = response.data
        }).catch(error => {

        }).finally(() => {
          this.loading = false
        })
      },
      dateFormat(time) {
        if (time == null)
          return "未定"
        return dayjs(time).format('YYYY年MM月DD日')
      },
      openNewBugDialog() {
        this.dialogFormVisible = true
      },
      submitBug() {
        this.$refs['newBug'].validate(valid => {
          if (valid) {
            addBug(this.$store.getters.projectId, this.newBug).then(() => {
              this.reload()
              this.$message({
                type: 'success',
                message: '新增缺陷成功',
                duration: 2 * 1000
              })
            }).catch(error => {

            })
          } else {
            this.$message({
              type: 'error',
              message: '请完整填写所需内容',
              duration: 2 * 1000
            })
            return false
          }
        })
      },
      handleEdit(index, row) {
        if (this.employees == null) {
          console.log('后端获取人员信息')
          getEmployeesByProjectId(this.$store.getters.projectId).then(res => {
            this.employees = res.data
            this.setEditBug(row)
            this.editBugDialogVisible = true
          }).catch(error => {
          })
        } else {
          this.setEditBug(row)
          this.editBugDialogVisible = true
        }

      },
      handleDelete(index, row) {
        console.log(row)
      },
      submitEditBug() {
        this.$refs['editBugForm'].validate(valid => {
          if (valid) {
            updateBug(this.$store.getters.projectId, this.editBug).then(() => {
              this.reload();
              this.$message({
                type: 'success',
                message: '缺陷更新成功',
                duration: 2 * 1000
              })
            })
          } else {
            this.$message({
              type: 'error',
              message: '请完整填写所需内容',
              duration: 2 * 1000
            })
            return false
          }
        })
      },
      setEditBug(row) {
        var statusId = 0;
        if (row.status === 'OPENED') {
          statusId = 1;
        } else if (row.status === 'PROCESSED') {
          statusId = 2;
        } else if (row.status === 'SOLVED') {
          statusId = 3;
        } else if (row.status === 'CLOSED') {
          statusId = 4;
        }
        this.editBug.id = row.id
        this.editBug.bugTitle = row.bugTitle
        this.editBug.bugResponsibleId = row.bugResponsible.id
        this.editBug.startTime = row.startTime
        this.editBug.bugDescription = row.bugDescription
        this.editBug.priority = row.priority.toString()
        this.editBug.status = statusId.toString()
      }
    }
  }
</script>
