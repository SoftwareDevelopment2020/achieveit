<template>
  <el-container>
    <el-aside style="width: auto;">
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical-demo"
        :collapse="menuCollapse"
        @select="handleSelect"
      >
        <el-button
          type="text"
          style="width: 100%"
          @click="menuCollapse = !menuCollapse">
          <i v-if="menuCollapse" class="el-icon-arrow-right"></i>
          <i v-else class="el-icon-arrow-left"></i>
        </el-button>

        <el-menu-item v-for="item in menu" :key="item.index" :index="item.index">
          <svg-icon :icon-class="item.class"/>
          <span slot="title">{{ item.title }}</span>
        </el-menu-item>

      </el-menu>
    </el-aside>
    <el-main v-if="project !== null">
      <el-card v-if="activeMenu!=='base-info'">
        <span>当前项目：{{project.name}} ({{project.projectId}})</span>
        <el-divider direction="vertical"></el-divider>
        <span>{{getDate(project.scheduledDate)}} - {{getDate(project.deliveryDate)}}</span>
      </el-card>
      <div style="margin-top: 30px">
        <base-info v-if="activeMenu==='base-info'"></base-info>
        <participant v-if="activeMenu==='participant'"></participant>
        <function-list v-if="activeMenu==='function-list'"></function-list>
        <risk v-if="activeMenu==='risk'"></risk>
        <defect v-if="activeMenu==='defect'"></defect>
        <device v-if="activeMenu==='device'"></device>
        <work-hour v-if="activeMenu==='work-hour'"></work-hour>
        <file v-if="activeMenu==='file'"></file>
      </div>
    </el-main>
  </el-container>
</template>

<script>
  import BaseInfo from './menu-item/base-info'
  import Participant from './menu-item/participant'
  import FunctionList from './menu-item/function-list'
  import Risk from './menu-item/risk'
  import Defect from './menu-item/defect'
  import Device from './menu-item/device'
  import WorkHour from './menu-item/work-hour'
  import File from './menu-item/file'
  import {stringToChinese} from "../../utils/date";

  export default {
    components: {
      BaseInfo,
      Participant,
      FunctionList,
      Risk,
      Defect,
      Device,
      WorkHour,
      File
    },
    data() {
      return {
        menuCollapse: false,
        menu: [{
          index: 'base-info',
          class: 'base-info',
          title: '项目信息'
        }, {
          index: 'participant',
          class: 'participant',
          title: '项目人员'
        }, {
          index: 'function-list',
          class: 'function-list',
          title: '功能列表'
        }, {
          index: 'risk',
          class: 'risk',
          title: '风险管理'
        }, {
          index: 'defect',
          class: 'defect',
          title: '缺陷管理'
        }, {
          index: 'device',
          class: 'device',
          title: '设备管理'
        }, {
          index: 'work-hour',
          class: 'work-hour',
          title: '工时信息'
        }, {
          index: 'file',
          class: 'file',
          title: '归档管理'
        }],
        activeMenu: null,
        project: this.$store.getters.project
      }
    },
    created() {
      // 页面加载时读取sessionStorage里的当前页面index
      this.activeMenu = sessionStorage.getItem('project-detail-menu-index') ?
        sessionStorage.getItem('project-detail-menu-index') : this.menu[0].index
    },
    mounted() {
      if (this.project === null) {
        this.$message.warning('请选择具体项目')
        this.$router.replace({
          path: '/'
        })
      }
    },
    methods: {
      handleSelect(index) {
        this.activeMenu = index
        // 在sessionStorage中存储当前页面index
        sessionStorage.setItem('project-detail-menu-index', this.activeMenu)
      },
      getDate(date) {
        return stringToChinese(date)
      }
    }
  }
</script>
