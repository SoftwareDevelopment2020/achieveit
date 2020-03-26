<template>
  <div>
    <h2>项目信息</h2>
    <div>
      <el-form ref="form" :model="form" :rules="submitRules" label-width="100px" style="width: 80%">
        <el-form-item label="项目ID" prop="projectId">
          <el-input v-model="form.projectId" name="projectId" style="width: 50%;"></el-input>
        </el-form-item>
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="form.name" name="projectName"></el-input>
        </el-form-item>
        <el-form-item label="客户信息">
          <el-input v-model="form.customInfo" name="customInfo"></el-input>
        </el-form-item>
        <el-form-item label="预定时间">
          <el-date-picker type="date" v-model="form.scheduledDate" name="scheduledDate" format="yyyy 年 MM 月 dd 日"
                          value-format="yyyy-MM-dd" style="width: 50%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="交付时间">
          <el-date-picker type="date" v-model="form.deliveryDate" name="deliveryDate" format="yyyy 年 MM 月 dd 日"
                          value-format="yyyy-MM-dd" style="width: 50%;"
                          :picker-options="deliveryDatePicker"></el-date-picker>
        </el-form-item>
        <el-form-item label="项目上级">
          <el-input v-model="form.superior" value="form.projectSuperior" name="projectSuperior"
                    style="width: 50%;"></el-input>
        </el-form-item>
        <el-form-item label="主要里程碑">
          <el-input v-model="form.majorMilestone" name="majorMilestone"></el-input>
        </el-form-item>
        <el-form-item label="业务领域">
          <el-input v-model="form.businessField" name="businessField"></el-input>
        </el-form-item>
        <el-form-item label="主要功能">
          <el-input v-model="form.mainFunction" name="mainFunction"></el-input>
        </el-form-item>
        <el-form-item label="项目状态">
          <el-input :disabled="true" v-bind:value="status[form.statusId]" name="statusId"></el-input>
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
  import {setBaseInfoTable} from "../../../../utils/common";
  import {Message} from 'element-ui'

  export default {
    inject: ['reload'],
    name: 'BaseInfo',
    data() {
      return {
        form: {
          projectId: '12345678901',
          name: '教务系统开发',
          customInfo: '华东师范大学软件工程学院',
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
        status: ['已归档', '审核中', '已驳回', '进行中'],
        deliveryDatePicker: {
          disabledDate(time){
            // 交付时间应当晚于预定时间，但暂时不会实现，this指向问题
            //TODO
            return time.getTime() < Date.now();
          }
        }
      }
    },
    methods: {
      onSubmit() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.$store.dispatch('project/updateProject', this.form).then(() => {
              this.reload()
              console.log('submit!');
            })
          } else {
            Message({
              message: '请完整填写所需字段',
              type: 'error',
              duration: 2 * 1000
            })
            return false
          }

        })
      },

    },
    mounted() {
      this.$store.dispatch('project/getCurrentProject', this.$store.getters.projectId).then(res => {
        setBaseInfoTable(this.form, res)
      })

    },
    computed: {

    }
  }
</script>
<style scoped>

</style>
