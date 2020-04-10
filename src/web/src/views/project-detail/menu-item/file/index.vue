<template>
    <div>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card :style="style">
            <h2 style="text-align: center">未提交资产</h2>
            <el-button v-permission="['ROLE_GLOBAL_FILE']" v-if="multipleSelectToSubmit.length > 0" type="text" size="mini" @click="updateFile()">
              一键提交所选
            </el-button>
            <el-table
              ref="multipleTable"
              v-loading="loading"
              :data="notSubmitFiles"
              empty-text="所有资产均已提交"
              @selection-change="multipleSelection"
            >
              <el-table-column
                type="selection"
                width="80px"
                align="center"
              >
              </el-table-column>
              <el-table-column
                prop="label"
                label="未提交资产项"
                align="center"
              >
              </el-table-column>
              <el-table-column
                label="操作"
                align="center"
              >
                <template slot-scope="{row}">
                  <el-button v-permission="['ROLE_GLOBAL_FILE']" type="text" size="mini" @click="updateFile(row)">提交</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card :style="style">
            <h2 style="text-align: center">已提交资产</h2>
            <el-table
              v-loading="loading"
              :data="submitFiles"
              empty-text="无资产提交"
            >
              <el-table-column
                prop="label"
                label="已提交资产项"
                align="center"
              >
              </el-table-column>
              <el-table-column
                prop="time"
                label="提交时间"
                align="center"
              >
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
      </el-row>
    </div>
</template>

<script>
  import {updateProject} from "../../../../api/project";
  import {stringToDateTime} from "../../../../utils/date";

  export default {
    data(){
      return{
        style: {
          height: '',
          overflow: 'auto'
        },
        loading: false,
        project: this.$store.getters.project,
        files: [
          {
            label: '项目基础数据表',
            value: 'projectBasicDatasheet'
          },
          {
            label: '项目提案书',
            value: 'projectProposal'
          },
          {
            label: '项目报价书',
            value: 'projectQuotation'
          },
          {
            label: '项目估算表',
            value: 'projectEstimates'
          },
          {
            label: '项目计划书',
            value: 'projectPlan'
          },
          {
            label: '项目过程裁剪表',
            value: 'projectProcessCropTable'
          },
          {
            label: '项目成本管理表',
            value: 'projectCostManagementTable'
          },
          {
            label: '项目需求变更管理表',
            value: 'projectRequirementsChangeManagementTable'
          },
          {
            label: '项目风险管理表',
            value: 'projectRiskManagementTable'
          },
          {
            label: '客户验收问题表',
            value: 'clientCheckProblemsTable'
          },
          {
            label: '客户验收报告',
            value: 'clientCheckReport'
          },
          {
            label: '项目总结',
            value: 'projectSummary'
          },
          {
            label: '最佳经验和教训',
            value: 'experienceAndLessons'
          },
          {
            label: '开发工具',
            value: 'developmentTools'
          },
          {
            label: '开发模板',
            value: 'developmentTemplates'
          },
          {
            label: '各阶段检查单',
            value: 'checkSheets'
          },
          {
            label: 'QA总结',
            value: 'qaSummary'
          }
        ],
        submitFiles: [],
        notSubmitFiles: [],
        multipleSelectToSubmit: [],
      }
    },
    mounted() {
      window.addEventListener('resize', this.getStyle)
      this.getStyle()
      this.setFiles()
    },
    methods: {
      getStyle() {
        this.style.height = window.innerHeight - 200 + 'px'
      },
      getTime(date) {
        return stringToDateTime(date)
      },
      setFiles() {
        this.project = this.$store.getters.project
        this.submitFiles = []
        this.notSubmitFiles = []
        this.files.forEach(file => {
          const time = this.project[file.value]
          if (time) {
            this.submitFiles.push({
              ...file,
              time: this.getTime(time)
            })
          } else {
            this.notSubmitFiles.push(file)
          }
        })
      },

      updateFile (row) {
        this.loading = true
        const params = {}
        const time = new Date(new Date().getTime()+8*60*60*1000)
        if(row) {
          params[row.value] = time
        } else {
          if (this.multipleSelectToSubmit.length === 0) {
            this.$message.warning('未选择文件')
            return
          }

          this.multipleSelectToSubmit.forEach(item => {
            params[item.value] = time
          })
        }
        updateProject({
          projectId: this.project.projectId,
          ...params
        }).then(response => {
          this.$message.success('提交成功')
        }).finally(() => {
          // 刷新
          this.$store.dispatch('project/getCurrentProject', this.project.id).then(response => {

          }).finally(() => {
            // 清空选项
            this.$refs.multipleTable.clearSelection();
            this.multipleSelectToSubmit = []
            // 设置文件
            this.setFiles()
            // 取消加载
            this.loading = false
          })
        })
      },

      multipleSelection(val) {
        this.multipleSelectToSubmit = val
        console.info(val)
      }
    }
  }
</script>

