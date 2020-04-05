<template>

  <div class="app-container">
    <h3>功能列表</h3>
    <el-tree :data="functionList" :props="defaultProps" v-loading="loading">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          {{data.detail}}
        </span>
      </span>
    </el-tree>
    <upload-excel-component :on-success="handleSuccess" :before-upload="beforeUpload"/>
    <el-table :data="tableData" border highlight-current-row style="width: 100%;margin-top:20px;">
      <el-table-column v-for="item of tableHeader" :key="item" :prop="item" :label="item"/>
    </el-table>
    <div class="components-container">

      <el-button :loading="downloadLoading" style="margin:0 0 20px 20px; float: right;" type="primary"
                 icon="el-icon-document" @click="handleDownload" name="downloadFunctionList">
        导出Excel
      </el-button>
      <el-button :loading="uploadLoading" style="margin:0 0 20px 20px; float: right;" type="primary"
                 icon="el-icon-document" @click="handleUpload" name="uploadFunctionList">
        上传Excel
      </el-button>
    </div>
  </div>

</template>


<script>
  import UploadExcelComponent from '@/components/UploadExcel/index.vue'

  export default {
    name: 'UploadExcel',
    inject: ['reload'],
    components: {UploadExcelComponent},
    data() {
      return {
        loading: false,
        tableData: [],
        tableHeader: [],
        downloadLoading: false,
        functionList: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        uploadLoading: false,
        file: null
      }
    },
    mounted() {
      this.loading = true
      const features = this.$store.getters.features
      if (features === null) {
        this.$store.dispatch('feature/getFeatures').then(res => {
          console.log(JSON.stringify(res))
          this.functionList = res
        }).finally(() => {
          this.loading = false
        })
      } else {
        this.functionList = features
        this.loading = false
      }
    },
    methods: {
      beforeUpload(file) {
        const isLt1M = file.size / 1024 / 1024 < 1
        if (isLt1M) {
          this.file = file

          return true
        }

        this.$message({
          message: 'Please do not upload files larger than 1m in size.',
          type: 'warning'
        })
        return false
      },
      handleUpload() {
        this.uploadLoading = true
        this.$store.dispatch('feature/uploadFeatures', this.file).then(response => {
          //成功上传后，清空store中的features，重新加载组件
          this.$store.dispatch('feature/setFeatures', null).then(() => {
            this.reload()
            this.$message({
              type: 'success',
              message: '成功上传',
              duration: 2 * 1000
            })
          })
        }).catch(error => {
          this.uploadLoading = false
        })
      },

      handleSuccess({results, header}) {
        this.tableData = results
        this.tableHeader = header
      },
      handleDownload() {
        this.downloadLoading = true
        this.$store.dispatch('feature/downloadExcel').then(res => {
          this.downloadLoading = false
          this.$message({
            type: 'success',
            message: '下载成功',
            duration: 2 * 1000
          })
        }).catch(error => {
          this.downloadLoading = false
        })
      },
    }
  }
</script>
<style scoped>
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 15px;
    padding-right: 8px;
  }
</style>
