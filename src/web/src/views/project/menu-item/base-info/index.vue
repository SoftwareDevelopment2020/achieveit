<template>
  <div>
    <h2>项目信息</h2>
      <div>
        <el-form ref="form" :model="form" label-width="100px" style="width: 80%" >
          <el-form-item label="项目ID">
            <el-input v-model="form.projectID" value="form.projectID" style="width: 50%;"></el-input>
          </el-form-item>
          <el-form-item label="项目名称">
             <el-input v-model="form.projectName" value="form.projectName"></el-input>
          </el-form-item>
          <el-form-item label="客户信息">
              <el-input v-model="form.customInfo" value="form.customInfo"></el-input>
          </el-form-item>
          <el-form-item label="预定时间">
               <el-date-picker type="date" v-model="form.date1" value="form.date1" style="width: 50%;"></el-date-picker>
          </el-form-item>
          <el-form-item label="交付时间">
               <el-date-picker type="date" v-model="form.date2" value="form.date2" style="width: 50%;"></el-date-picker>
          </el-form-item>
          <el-form-item label="项目上级">
                <el-input v-model="form.projectSuperior" value="form.projectSuperior" style="width: 50%;"></el-input>
          </el-form-item>
          <el-form-item label="主要里程碑">
                <el-input v-model="form.milestone" value="form.milestone"></el-input>
          </el-form-item>
           <el-form-item label="业务领域">
                 <el-input v-model="form.businessArea" value="form.businessArea"></el-input>
           </el-form-item>
           <el-form-item label="主要功能">
                 <el-input v-model="form.function" value="form.function"></el-input>
           </el-form-item>
           <el-form-item label="项目状态">
                  <el-input v-model="form.status" :disabled="true" value="form.status"></el-input>
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
  data() {
    return {
      form: {
                projectID: '123456',
                projectName: '教务系统开发',
                customInfo:'华东师范大学软件工程学院',
                date1: '2020-03-06',
                date2: '2020-06-06',
                projectSuperior: 'Severus Snape',
                milestone:'XXXXXX',
                businessArea:'XXXXXX',
                function:'XXXXXXX',
                status: '进行中'
              }
    }
  },
   methods: {
        onSubmit() {
          console.log('submit!');
        }
      }
}
</script>
<style scoped>

</style>
