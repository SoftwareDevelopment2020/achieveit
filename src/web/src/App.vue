<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  created() {
    // 页面加载时读取sessionStorage里的项目信息
    if (sessionStorage.getItem('project')) {
      this.$store.dispatch('project/setProject',JSON.parse(sessionStorage.getItem('project')))
    }

    // 页面刷新时将将项目信息存储在sessionStorage中
    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('project', JSON.stringify(this.$store.getters.project))
    })
  }
}
</script>
