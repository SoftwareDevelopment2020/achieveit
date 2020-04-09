<template>
  <div>
    <h2>项目信息</h2>
    <div>
      <el-form
        ref="form"
        :model="form"
        :rules="submitRules"
        :hide-required-asterisk="true"
        label-width="100px"
        style="width: 80%"
        v-loading="loading"
      >
        <el-form-item label="项目ID">
          <el-input v-model="form.projectId" name="projectId" style="width: 50%;" disabled></el-input>
        </el-form-item>
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="form.name" name="projectName"></el-input>
        </el-form-item>
        <el-form-item label="客户信息" prop="clientId">
          <el-input v-model="form.clientId" name="clientId"></el-input>
        </el-form-item>
        <el-form-item label="预定时间" prop="scheduledDate">
          <el-date-picker type="date" v-model="form.scheduledDate" name="scheduledDate" format="yyyy 年 MM 月 dd 日"
                          value-format="yyyy-MM-dd" style="width: 50%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="交付时间" prop="deliveryDate">
          <el-date-picker type="date" v-model="form.deliveryDate" name="deliveryDate" format="yyyy 年 MM 月 dd 日"
                          value-format="yyyy-MM-dd" style="width: 50%;"
                          :picker-options="{
            disabledDate (time) {
              return time.getTime() < new Date(form.scheduledDate)
             }
          }"></el-date-picker>
        </el-form-item>
        <el-form-item label="项目上级" prop="superior">
          <el-select v-model="form.superior" name="projectSuperior" filterable>
            <el-option v-for="superior in superiorOptions" :key="superior.id" :value="superior.id"
                       :label="superior.name" name="projectSuperiorOption">
              <span style="float: left">{{superior.name}}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{superior.id}}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="主要里程碑" prop="majorMilestone">
          <el-input v-model="form.majorMilestone" name="majorMilestone"></el-input>
        </el-form-item>
        <el-form-item label="业务领域" prop="businessField">
          <el-input v-model="form.businessField" name="businessField"></el-input>
        </el-form-item>
        <el-form-item label="主要功能" prop="mainFunction">
          <el-input v-model="form.mainFunction" name="mainFunction"></el-input>
        </el-form-item>
        <el-form-item label="项目状态">
          <el-input :disabled="true" v-bind:value="status[getStatusId(form.statusId)].label" name="statusId"></el-input>
        </el-form-item>
        <el-form-item>
          <div align="center">
            <!-- 审核中 -->
            <div v-permission="['ROLE_SUPERIOR']" v-if="form.statusId === 1">
              <el-button type="primary" @click="examineProject(true)">通过审核</el-button>
              <el-button type="primary" @click="examineProject(false)">拒绝立项</el-button>
            </div>
            <!-- 进行中 -->
            <div v-if="getStatusId(form.statusId) === 3">
              <div v-permission="['ROLE_PM']" v-if="form.statusId === 3111">
                <el-button name="submitInfo" type="primary" @click="onSubmit">修改信息</el-button>
              </div>
              <div v-else>
                <span v-permission="['ROLE_GLOBAL_CONFIG']"
                      v-if="getStatusId(form.statusId) === 3 && form.statusId.toString().charAt(1) === '0'">
                  <el-button type="primary" @click="allot">建立配置库</el-button>
                </span>
                <span v-permission="['ROLE_GLOBAL_EPGLEADER']"
                      v-if="getStatusId(form.statusId) === 3 && form.statusId.toString().charAt(2) === '0'">
                  <el-button type="primary" @click="allot">分配EPG</el-button>
                </span>
                <span v-permission="['ROLE_GLOBAL_QAM']"
                      v-if="getStatusId(form.statusId) === 3 && form.statusId.toString().charAt(3) === '0'">
                  <el-button type="primary" @click="allot">分配QA</el-button>
                </span>
              </div>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import {getAllSuperiors} from "../../../../api/employee";

  export default {
    inject: ['reload'],
    name: 'BaseInfo',
    data() {
      return {
        form: null,
        submitRules: {
          name: [{required: true, message: '项目名称不能为空', trigger: 'blur'}],
          clientId: [{required: true, message: '客户信息不能为空', trigger: 'blur'}],
          scheduledDate: [{required: true, message: '预定时间不能为空', trigger: 'change'}],
          deliveryDate: [{required: true, message: '交付时间不能为空', trigger: 'change'}],
          superior: [{required: true, message: '项目上级不能为空', trigger: 'blur'}],
        },
        status: this.Constant.projectStatus,
        superiorOptions: [],
        loading: false
      }
    },
    created() {
      this.loading = true
      this.form = {...this.$store.getters.project}
      const data = {
        current: -1,
        size: 0,
        searchCondition: 'ROLE_SUPERIOR'
      }
      getAllSuperiors(data).then(response => {
        this.superiorOptions = response.data.records
      }).finally(() => {
        this.loading = false
      })
    },
    methods: {
      getStatusId(statusId) {
        return parseInt(statusId.toString().charAt(0))
      },
      onSubmit() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.$store.dispatch('project/updateProject', this.form).then(() => {
              this.reload()
            })
          } else {
            this.$message({
              message: '请完整填写所需字段',
              type: 'error',
              duration: 2 * 1000
            })
            return false
          }

        })
      },
      examineProject(approved) {
        this.$store.dispatch('project/examineProject', approved).then(() => {
          this.$message({
            type: 'success',
            message: '成功',
            duration: 2 * 1000
          })
          this.reload()
        })
      },
      allot() {
        this.$store.dispatch('project/allot').then(() => {
          this.$message({
            type: 'success',
            message: '成功',
            duration: 2 * 1000
          })
          this.reload()
        })
      }
    }
  }
</script>
<style scoped>

</style>
