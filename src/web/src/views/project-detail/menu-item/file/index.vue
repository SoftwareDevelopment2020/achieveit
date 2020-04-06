<template>
    <div>
      <div>
        <h2 align="center">未提交资产</h2>
        <el-table
          v-loading="loading"
          :data="assetData"
          style="width: 100%; margin-top: 30px"
          empty-text="无未提交资产"
        >
          <el-table-column
            prop="asset"
            label="未提交资产项"
            align="center"
            min-width="40"
          >
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            align="center"
            min-width="70">
            <template slot-scope="scope">
              <el-button type="text" >提交</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div>
        <h2 align="center" >已提交资产</h2>
        <el-table
          v-loading="loading"
          :data="submitData"
          style="width: 100%; margin-top: 30px"
          empty-text="暂无提交资产"
        >
          <el-table-column
            prop="assetSubmitted"
            label="已提交资产项"
            align="center"
            min-width="40"
          >
          </el-table-column>
          <el-table-column
            prop="time"
            label="提交时间"
            align="center"
            min-width="70"
          >
          </el-table-column>
        </el-table>
      </div>
    </div>
</template>

<script>
  export default {
    data(){
      return{
        assetData:[  //具体项目列表见需求说明书
          {
            asset:'项目基础数据表'
          },
          {
            asset:'项目提案书'
          },
          {
            asset:'项目报价书'
          }
        ],
        submitData:[
          {
            assetSubmitted:'项目成本管理表',
            time:'2020-02-11'
          },
          {
            assetSubmitted:'开发工具',
            time:'2020-02-24'
          }
        ]
      }
    }
  }
</script>

<style scoped>

</style>
