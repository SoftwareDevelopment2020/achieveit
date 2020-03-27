<template>
  <div style="margin: 30px 10px">
    <Search v-if="searchPage" @changePage="changePage" @selectProject="selectProject"></Search>
    <Add v-else @changePage="changePage"></Add>
  </div>
</template>

<script>
  import Search from './components/search'
  import Add from './components/add'
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
        this.$store.dispatch('project/setProject', project)
        console.info(this.$store.getters.projectId)
      }
    }
  }
</script>

<style scoped>

</style>
