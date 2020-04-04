<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  created() {
    // 页面刷新时将将项目信息存储在sessionStorage中
    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('projectKey', this.$store.getters.projectKey)
    })
  }
}
</script>
