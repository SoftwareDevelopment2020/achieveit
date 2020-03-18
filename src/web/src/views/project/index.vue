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

        <el-menu-item v-for="item in menu" :index="item.index">
          <svg-icon :icon-class="item.class" />
          <span slot="title">{{ item.title }}</span>
        </el-menu-item>

      </el-menu>
    </el-aside>
    <el-main>
      <div style="margin: 10px">
        <my-project v-if="activeMenu==='my-project'"></my-project>
        <base-info v-if="activeMenu==='base-info'"></base-info>
        <participant v-if="activeMenu==='participant'"></participant>
        <function-list v-if="activeMenu==='function-list'"></function-list>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import MyProject from './menu-item/my-project/index'
import BaseInfo from './menu-item/base-info'
import Participant from './menu-item/participant'
import FunctionList from './menu-item/function-list'

export default {
  components: {
    MyProject,
    BaseInfo,
    Participant,
    FunctionList
  },
  data () {
    return {
      menuCollapse: false,
      menu: [{
        index: 'my-project',
        class: 'my-project',
        title: '我的项目'
      }, {
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
      activeMenu: null
    }
  },
  mounted() {
    this.activeMenu = this.menu[0].index
  },
  methods: {
    handleSelect(index) {
      this.activeMenu = index
    }
  }
}
</script>
