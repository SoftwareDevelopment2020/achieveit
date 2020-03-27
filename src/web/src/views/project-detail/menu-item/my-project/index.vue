<template>
  <Search v-if="searchPage" @changePage="changePage" @selectProject="selectProject"></Search>
  <Add v-else @changePage="changePage"></Add>
</template>

<script>
  import Search from './search'
  import Add from './add'
  export default {
    components: {
      Search,
      Add
    },
    data () {
      return {
        searchPage: true
      }
    },
    methods: {
      changePage() {
        this.searchPage = !this.searchPage
      },
      selectProject (project) {
        this.$emit('selectProject', project)
      }
    }
  }
</script>

<style scoped>

</style>
