<template>
    <div>

      <el-tabs v-model="activeName">
        <el-tab-pane label="我的工时" name="my">
          <work-hour v-if="activeName === 'my'" :type="1" />
        </el-tab-pane>
        <el-tab-pane label="审核工时" name="all">
          <work-hour v-if="activeName === 'all'" :type="2" />
        </el-tab-pane>
      </el-tabs>
    </div>
</template>

<script>
  import WorkHour from './work-hour'
  export default {
    components: {WorkHour},
    data(){
      return{
        activeName: 'my',
      }
    }
  }
</script>
