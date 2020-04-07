<template>
    <div>
      <el-tabs v-model="activeName">
        <el-tab-pane v-if="show.my" label="我的工时" name="my">
          <work-hour v-if="activeName === 'my'" :type="1" />
        </el-tab-pane>
        <el-tab-pane v-if="show.all" label="所有工时" name="all">
          <work-hour v-if="activeName === 'all'" :type="3" />
        </el-tab-pane>
        <el-tab-pane label="审核工时" name="audit">
          <work-hour v-if="activeName === 'audit'" :type="2" />
        </el-tab-pane>
      </el-tabs>
    </div>
</template>

<script>
  import WorkHour from './work-hour'
  import checkPermission from "../../../../utils/permission";
  export default {
    components: {
      WorkHour
    },
    data(){
      return{
        activeName: 'my',
        /**
         * 开发Leader、开发、测试Leader、测试、QA需要登记工时
         */
        addWorkHourPermission: ['ROLE_DEVLEADER', 'ROLE_DEV', 'ROLE_TESTLEADER', 'ROLE_TEST', 'ROLE_QA'],
        /**
         * 项目经理、上级可以看到所有人的工时
         */
        readAllWorkHourPermission: ['ROLE_PM', 'ROLE_SUPERIOR'],
        show: {
          my:  true,
          all: true,
          audit: true
        }
      }
    },
    mounted() {
      /**
       * 如果需要登记工时，则进入我的工时页面
       * 否则，如果是项目经理、上级，则进入所有工时页面
       * 否则，进入审核工时页面
       */
      this.show.my = checkPermission(this.addWorkHourPermission)
      this.show.all = checkPermission(this.readAllWorkHourPermission)
      this.activeName = this.show.my ? 'my' : (this.show.all? 'all' : 'audit')
    }
  }
</script>
