<template>
  <div>
    <el-page-header @back="goBack" content="立项申请"></el-page-header>
    <el-form
      :model="project"
      ref="project"
      :rules="addProjectRules"
      class="add-form"
      label-width="90px"
      label-position="left"
      :hide-required-asterisk="true"
      v-loading="loading"
    >
      <el-form
        :model="project"
        ref="projectInline"
        :rules="addProjectRules"
        :inline="true"
        :hide-required-asterisk="true"
      >
        <el-form-item label="客户ID" prop="clientId" label-width="90px">
          <el-input v-model="project.clientId" name="newProjectClientId"></el-input>
        </el-form-item>
        <el-form-item label="研发类型" prop="projectId" style="margin-left: 20px;">
          <el-select v-model="project.projectId">
            <el-option
              v-for="item in projectTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <el-form-item label="项目名称" prop="name">
        <el-input v-model="project.name" name="newProjectName"></el-input>
      </el-form-item>
      <el-form  :inline="true">
        <el-form-item label="预定时间" prop="scheduledDate" label-width="90px">
          <el-date-picker
            v-model="project.scheduledDate"
            value-format="yyyy-MM-dd"
            type="date"
            :clearable="false"
            name="newProjectScheduledDate"
            :picker-options="{
            disabledDate (time) {
              return time.getTime() < today;
             }
          }"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="交付日" prop="deliveryDate" style="margin-left: 20px;">
          <el-date-picker
            v-model="project.deliveryDate"
            value-format="yyyy-MM-dd"
            type="date"
            name="newProjectDeliveryDate"
            :clearable="false"
            :picker-options="{
            disabledDate (time) {
              return time.getTime() < new Date(project.scheduledDate);
             }
          }">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <el-form-item label="项目上级" prop="superior">
        <el-select v-model="project.superior" name="newProjectSuperior" filterable>
          <el-option v-for="superior in superiorOptions" :key="superior.id" :value="superior.id"
                     :label="superior.name" name="NewProjectSuperiorOption">
            <span style="float: left">{{superior.name}}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{superior.id}}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="主要里程碑" prop="majorMilestone">
        <el-input
          v-model="project.majorMilestone"
          type="textarea"
          resize="none"
          :rows="5"
          maxlength="500"
          name="newProjectMajorMilestone"
          show-word-limit
        >
        </el-input>
      </el-form-item>
      <el-form-item label="采用技术" prop="mainTechnique">
        <el-input
          v-model="project.mainTechnique"
          type="textarea"
          resize="none"
          :rows="5"
          name="newProjectMainTechnique"
          maxlength="500"
          show-word-limit
        >
        </el-input>
      </el-form-item>
      <el-form-item label="业务领域" prop="businessField">
        <el-input
          v-model="project.businessField"
          type="textarea"
          resize="none"
          :rows="5"
          maxlength="500"
          name="newProjectBusinessField"
          show-word-limit
        >
        </el-input>
      </el-form-item>
      <el-form-item label="主要功能" prop="mainFunction">
        <el-input
          v-model="project.mainFunction"
          type="textarea"
          resize="none"
          :rows="8"
          maxlength="1000"
          name="newProjectMainFunction"
          show-word-limit
        >
        </el-input>
      </el-form-item>
    </el-form>
    <div align="center">
      <el-button type="primary" @click="resetForm">重置</el-button>
      <el-button type="primary" @click="addProject" name="newProjectSubmitButton">添加</el-button>
    </div>
  </div>

</template>

<script>
  import {addProject} from "../../../api/project";
  import {dateToString} from "../../../utils/date";
  import {getAllSuperiors} from "../../../api/employee";

  export default {
    data () {
      return {
        project: {
          clientId: '',
          projectId: 'D',
          name: '',
          scheduledDate: new Date(),
          deliveryDate: new Date(),
          superior: '',
          majorMilestone: '',
          mainTechnique: '',
          businessField: '',
          mainFunction: '',
        },
        addProjectRules: this.Constant.projectRules,
        projectTypeOptions: this.Constant.projectType,
        today: null,
        superiorOptions: [],
        loading:false
      }
    },
    mounted () {
      this.loading=true
      let today = new Date()
      today.setHours(0,0,0,0)
      this.today = today.getTime()
      this.project.scheduledDate = dateToString(today)
      this.project.deliveryDate = dateToString(today)
      const data = {
        current: -1,
        size: 0,
        searchCondition: 'ROLE_SUPERIOR'
      }
      getAllSuperiors(data).then(response => {
        this.superiorOptions = response.data.records
      }).finally(() => {
        this.loading=false
      })

    },
    methods: {
      goBack () {
        this.$emit('changePage')
      },
      resetForm () {
        this.$refs.project.resetFields()
      },
      addProject () {
        this.$refs.projectInline.validate(valid1 => {
          this.$refs.project.validate(valid2 => {
            if (!valid1 || !valid2) {
              this.$message.error('请完整填写所需字段')
              return false
            }

            addProject(this.project).then(response => {
              this.$message.success('立项成功')
              this.goBack()
            }).catch(error => {
              console.info(error)
            })
          })
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .add-form {
    margin: 30px 0;
  }
</style>
