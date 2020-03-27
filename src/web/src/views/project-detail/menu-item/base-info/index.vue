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
      >
        <el-form-item label="项目ID" prop="projectId">
          <el-input v-model="form.projectId" name="projectId" style="width: 50%;"></el-input>
        </el-form-item>
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="form.name" name="projectName"></el-input>
        </el-form-item>
        <el-form-item label="客户信息" prop="clientId">
          <el-input v-model="form.clientId"></el-input>
        </el-form-item>
        <el-form-item label="预定时间">
          <el-date-picker type="date" v-model="form.scheduledDate" name="scheduledDate" format="yyyy 年 MM 月 dd 日"
                          value-format="yyyy-MM-dd" style="width: 50%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="交付时间">
          <el-date-picker type="date" v-model="form.deliveryDate" name="deliveryDate" format="yyyy 年 MM 月 dd 日"
                          value-format="yyyy-MM-dd" style="width: 50%;"
                          :picker-options="{
            disabledDate (time) {
              return time.getTime() < new Date(form.scheduledDate)
             }
          }"></el-date-picker>
        </el-form-item>
        <el-form-item label="项目上级" prop="superior">
          <el-input v-model="form.superior" value="form.projectSuperior" name="projectSuperior"
                    style="width: 50%;"></el-input>
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
          <el-button type="primary" @click="onSubmit">保存修改</el-button>
          <el-button type="success">通过审核</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    inject: ['reload'],
    name: 'BaseInfo',
    data() {
      return {
        form: {
          projectId: '12345678901',
          name: '教务系统开发',
          clientId: '1234',
          scheduledDate: '2020-03-06',
          deliveryDate: '2020-06-06',
          superior: 'Severus Snape',
          majorMilestone: 'XXXXXX',
          businessField: 'XXXXXX',
          mainFunction: 'XXXXXXX',
          statusId: '1'
        },
        submitRules: {
          projectId: [{required: true, message: '项目ID不能为空', trigger: 'blur'}],
          name: [{required: true, message: '项目名称不能为空', trigger: 'blur'}]
        },
        status: this.Constant.projectStatus,
      }
    },
    mounted() {
      this.form = {...this.$store.getters.project}
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
      }
    }
  }
</script>
<style scoped>

</style>
