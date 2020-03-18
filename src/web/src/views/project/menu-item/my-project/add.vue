<template>
  <el-page-header @back="goBack" content="立项申请">
  </el-page-header>
</template>

<script>
  export default {
    methods: {
      goBack () {
        this.$emit('changePage')
      }
    }
  }
</script>

<style scoped>

</style>
