<template>
  <div>
    <div>
      <el-input
        v-model="searchValue.id"
        placeholder="风险ID"
        style="width: 15%;min-width: 200px"
        name="riskIdSearch"
        @keyup.enter.native="getRisk"
        clearable
      >
      </el-input>
      <el-autocomplete
        class="inline-input"
        v-model="searchValue.type"
        :fetch-suggestions="querySearch"
        placeholder="风险类型"
        name="riskTypeSearch"
        @keyup.enter.native="getRisk"
      ></el-autocomplete>
<!--      <el-input-->
<!--        v-model="searchValue.name"-->
<!--        placeholder="风险责任人"-->
<!--        style="width: 20%;min-width: 200px"-->
<!--        name="riskResponsibleSearch"-->
<!--        @keyup.enter.native="getRisk"-->
<!--        clearable-->
<!--      >-->
<!--      </el-input>-->
      <el-select
        v-model="searchValue.responsible"
        placeholder="风险责任人"
        name="editRiskResponsible"
        style="width: 20%;min-width: 200px"
        filterable
        clearable
      >
        <el-option
          v-for="employee in employees"
          :key="employee.id"
          :label="employee.name"
          :value="employee.id"
        >
          <span style="float: left">{{ employee.name }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">{{ employee.id }}</span>
        </el-option>
      </el-select>
      <el-select v-model="searchValue.status" placeholder="风险状态" name="riskStatusSearch" clearable>
        <el-option v-for="state in riskStatus" :key="state.id" :label="state.value" :value="state.id"
                   name="riskStatusOptionSearch">
        </el-option>
      </el-select>
      <el-button icon="el-icon-search" circle @click="getRisk" name="searchRiskButton">
      </el-button>
      <el-button type="primary" @click="openNewRiskDialog" name="openNewRiskButton" v-permission="['ROLE_PM']" v-if="canEdit()">
        <i class="el-icon-plus"></i>
        <span>新建风险</span>
      </el-button>
      <el-dialog title="新建风险" :visible.sync="dialogFormVisible_1">
        <el-form :model="newRisk" :rules="riskRules" ref="newRisk">
          <el-row>
            <el-col :span="12">
              <el-form-item label="风险类型" :label-width="formLabelWidth" prop="type">
                <el-autocomplete
                  class="inline-input"
                  v-model="newRisk.type"
                  :fetch-suggestions="querySearch"
                  placeholder="请填写风险类型"
                  name="riskType"
                ></el-autocomplete>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="风险状态" :label-width="formLabelWidth" prop="status">
                <el-select v-model="newRisk.status" placeholder="风险状态" name="riskStatus">
                  <el-option v-for="state in riskStatus" :key="state.id" :label="state.value" :value="state.id"
                             name="newRiskStatusOption">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="风险级别" :label-width="formLabelWidth" prop="level">
                <el-select v-model="newRisk.level" placeholder="风险级别" name="riskLevel">
                  <el-option v-for="level in riskLevel" :key="level.id" :label="level.value" :value="level.id"
                             name="newRiskLevelOption">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="风险责任人" :label-width="formLabelWidth" prop="responsible">
                <el-select v-model="newRisk.responsible" placeholder="风险责任人" name="riskResponsible" filterable>
                  <el-option v-for="employee in employees" :key="employee.id" :label="employee.name"
                             :value="employee.id" name="newRiskResponsibleOption">
                    <span style="float: left">{{ employee.name }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ employee.id }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="风险影响度" :label-width="formLabelWidth" prop="affect">
                <el-select v-model="newRisk.affect" placeholder="风险影响度" name="riskAffect">
                  <el-option v-for="affect in riskAffect" :key="affect.id" :label="affect.value" :value="affect.id"
                             name="newRiskAffectOption">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="风险跟踪频度(天)" :label-width="formLabelWidth" required>
                <el-input-number v-model="newRisk.trackFreq" :min="1" :max="9999" label="天数"
                                 name="riskTrackFreq"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="风险相关者" :label-width="formLabelWidth">
              <el-select v-model="newRisk.related" placeholder="风险相关者" multiple style="width: 100%" name="riskRelated"
                         filterable>
                <el-option v-for="employee in employees" :key="employee.id" :label="employee.name" :value="employee.id"
                           name="newRiskRelatedOption">
                  <span style="float: left">{{ employee.name }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ employee.id }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>

          <el-form-item label="风险应对" :label-width="formLabelWidth" prop="react">
            <el-input
              placeholder="风险应对方法"
              v-model="newRisk.react"
              name="riskReact"
              clearable>
            </el-input>
          </el-form-item>
          <el-form-item label="应对策略" :label-width="formLabelWidth" prop="strategy">
            <el-input
              placeholder="风险应对策略"
              v-model="newRisk.strategy"
              name="riskStrategy"
              clearable>
            </el-input>
          </el-form-item>

          <el-form-item label="风险描述" :label-width="formLabelWidth" prop="description">
            <el-input
              type="textarea"
              :rows="4"
              placeholder="请输入风险的描述内容"
              name="riskDescription"
              v-model="newRisk.description">
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible_1 = false" name="cancelSubmitButton">取 消</el-button>
          <el-button type="primary" @click="submitRisk" name="submitButton">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="编辑风险" :visible.sync="editRiskDialogVisible">
        <el-form :model="editRisk" :rules="riskRules" ref="editRisk">
          <el-row>
            <el-col :span="12">
              <el-form-item label="风险类型" :label-width="formLabelWidth" prop="type">
                <el-autocomplete
                  class="inline-input"
                  v-model="editRisk.type"
                  :fetch-suggestions="querySearch"
                  placeholder="请填写风险类型"
                  name="editRiskType"
                ></el-autocomplete>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="风险状态" :label-width="formLabelWidth" prop="status">
                <el-select v-model="editRisk.status" placeholder="风险状态" name="editRiskStatus">
                  <el-option v-for="state in riskStatus" :key="state.id" :label="state.value" :value="state.id"
                             name="editRiskStatusOption">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="风险级别" :label-width="formLabelWidth" prop="level">
                <el-select v-model="editRisk.level" placeholder="风险级别" name="editRiskLevel">
                  <el-option v-for="level in riskLevel" :key="level.id" :label="level.value" :value="level.id"
                             name="editRiskLevelOption">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="风险责任人" :label-width="formLabelWidth" prop="responsible">
                <el-select v-model="editRisk.responsible" placeholder="风险责任人" name="editRiskResponsible" filterable>
                  <el-option v-for="employee in employees" :key="employee.id" :label="employee.name"
                             :value="employee.id" name="editRiskResponsibleOption">
                    <span style="float: left">{{ employee.name }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ employee.id }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="风险影响度" :label-width="formLabelWidth" prop="affect">
                <el-select v-model="editRisk.affect" placeholder="风险影响度" name="editRiskAffect">
                  <el-option v-for="affect in riskAffect" :key="affect.id" :label="affect.value" :value="affect.id"
                             name="editRiskAffectOption">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="风险跟踪频度(天)" :label-width="formLabelWidth" required>
                <el-input-number v-model="editRisk.trackFreq" :min="1" :max="9999" label="天数"
                                 name="editRiskTrackFreq"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="风险相关者" :label-width="formLabelWidth">
              <el-select v-model="editRisk.related" placeholder="风险相关者" multiple style="width: 100%"
                         name="editRiskRelated"
                         filterable>
                <el-option v-for="employee in employees" :key="employee.id" :label="employee.name" :value="employee.id"
                           name="editRiskRelatedOption">
                  <span style="float: left">{{ employee.name }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ employee.id }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>

          <el-form-item label="风险应对" :label-width="formLabelWidth" prop="react">
            <el-input
              placeholder="风险应对方法"
              v-model="editRisk.react"
              name="editRiskReact"
              clearable>
            </el-input>
          </el-form-item>
          <el-form-item label="应对策略" :label-width="formLabelWidth" prop="strategy">
            <el-input
              placeholder="风险应对策略"
              v-model="editRisk.strategy"
              name="editRiskStrategy"
              clearable>
            </el-input>
          </el-form-item>

          <el-form-item label="风险描述" :label-width="formLabelWidth" prop="description">
            <el-input
              type="textarea"
              :rows="4"
              placeholder="请输入风险的描述内容"
              name="editRiskDescription"
              v-model="editRisk.description">
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="editRiskDialogVisible = false" name="editRiskCancelSubmitButton">取 消</el-button>
          <el-button type="primary" @click="submitEditRisk" name="editRiskSubmitButton">确 定</el-button>
        </div>
      </el-dialog>
      <el-button type="primary" @click="importRisk" name="openImportRiskButton" v-permission="['ROLE_PM']" v-if="canEdit()">
        导入已有风险<i class="el-icon-upload el-icon--right"></i>
      </el-button>

      <el-dialog title="组织标准库" :visible.sync="dialogFormVisible_2">
        <!--        <el-form :model="form_2">-->
        <!--          <el-form-item label="风险ID" :label-width="formLabelWidth">-->
        <!--            <el-input v-model="form_2.riskId" autocomplete="off"></el-input>-->
        <!--          </el-form-item>-->
        <!--        </el-form>-->
        <el-table :data="standardRisks">
          <el-table-column label="风险类型" prop="type"></el-table-column>
          <el-table-column label="风险应对" prop="react"></el-table-column>
          <el-table-column label="风险策略" prop="strategy"></el-table-column>
          <el-table-column label="风险描述" prop="description"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                name="importRiskButton"
                @click="handleImport(scope.row)">导入风险
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible_2 = false" name="cancelImportButton">取 消</el-button>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-table
        :data="risks"
        style="width: 100%" v-loading="loading">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="风险 ID">
                <span>{{ props.row.id }}</span>
              </el-form-item>
              <el-form-item label="风险类型">
                <span>{{ props.row.type }}</span>
              </el-form-item>
              <el-form-item label="风险相关者">
                <span v-for="item in props.row.related">{{ item.name+" " }}</span>
              </el-form-item>
              <el-form-item label="风险责任人">
                <span>{{ props.row.responsible.name }}</span>
              </el-form-item>
              <el-form-item label="风险影响度">
                <span>{{ props.row.affect }}</span>
              </el-form-item>
              <el-form-item label="风险应对">
                <span>{{ props.row.react }}</span>
              </el-form-item>
              <el-form-item label="风险状态">
                <span>{{ props.row.status }}</span>
              </el-form-item>
              <el-form-item label="应对策略">
                <span>{{ props.row.strategy }}</span>
              </el-form-item>
              <el-form-item label="跟踪频度">
                <span>{{ props.row.trackFreq }} (天/次)</span>
              </el-form-item>
              <el-form-item label="风险级别">
                <span>{{ props.row.level }}</span>
              </el-form-item>
              <el-form-item label="风险描述">
                <span>{{ props.row.description }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="风险 ID"
          name="idResults"
          prop="id">
        </el-table-column>
        <el-table-column
          label="风险类型"
          prop="type">
        </el-table-column>
        <el-table-column
          label="风险责任人"
          prop="responsible.name">
        </el-table-column>
        <el-table-column
          label="风险状态"
          prop="status">
        </el-table-column>
        <el-table-column
          align="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              v-permission="['ROLE_PM']"
              @click="handleEdit(scope.row)" :disabled="scope.row.status=='CLOSED'" name="openEditDialogButton">编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :total="table.total" :page.sync="table.current" :limit.sync="table.limit"
                  @pagination="getRisk"></pagination>
    </div>
  </div>
</template>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
  }
</style>

<script>
  import Pagination from '@/components/Pagination/index'
  import {getAllProjectEmployeeBasics, getEmployeesByProjectId} from "../../../../api/employee";

  export default {
    inject: ['reload'],
    components: {
      Pagination
    },
    data() {
      return {
        riskTypeOptions: this.Constant.riskType,
        loading: false,
        dialogFormVisible_1: false,
        dialogFormVisible_2: false,
        editRiskDialogVisible: false,
        project: this.$store.getters.project,
        form_2: {
          riskId: ''
        },
        editRisk: {
          "affect": 0,
          "description": "",
          "id": 0,
          "level": 0,
          "projectId": 0,
          "react": "",
          "related": "",
          "responsible": "",
          "status": 0,
          "strategy": "",
          "trackFreq": 0,
          "type": ""
        },
        formLabelWidth: '150px',
        risks: [],
        employees: null,
        riskAffect: this.Constant.riskAffect,
        riskLevel: this.Constant.riskLevel,
        riskStatus: this.Constant.status,
        riskRules: this.Constant.riskRules,
        standardRisks: null,
        newRisk: {
          "affect": "",
          "description": "",
          "id": 0,
          "level": "",
          "react": "",
          "related": "",
          "responsible": "",
          "status": "",
          "strategy": "",
          "trackFreq": "",
          "type": "",
        },//风险 ID，风险类型，风险描述，风险级别，风险影响度，风险应对策略，风险状态，风险责任人，风险跟踪频度，风险相关者
        searchValue: {
          id: '',
          name: '',
          status: '',
          type: '',
          responsible: ''
        },
        table: {total: 0, limit: 10, current: 1}
      }
    },
    methods: {
      canEdit() {
        return this.project.statusId === 3111
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
      getRisk() {
        this.loading = true
        const data = {
          'current': this.table.current,
          'size': this.table.limit,
          'searchCondition': this.searchValue
        }
        if (this.searchValue.id.length !== 0 || this.searchValue.name.length !== 0 ||
          this.searchValue.status.length !== 0 || this.searchValue.type.length !== 0) {
          data.current = 1;
        }
        this.$store.dispatch('risk/getRisks', data).then(response => {
          this.risks = response.records
          this.table.total = response.total
        }).catch(error => {

        }).finally(() => {
          this.loading = false
        })
      },
      search() {
        console.log('查询风险')
      },
      //产生风险类型的输入建议
      querySearch(queryString, cb) {
        const riskTypes = this.riskTypeOptions;
        const results = queryString ? riskTypes.filter(this.createFilter(queryString)) : riskTypes;
        cb(results);
      },
      createFilter(queryString) {
        return (riskType) => {
          return (riskType.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
        };
      },
      //打开新增风险对话框
      openNewRiskDialog() {
        this.dialogFormVisible_1 = true

        // if (this.employees == null) {
        //   getEmployeesByProjectId(this.$store.getters.projectId).then(res => {
        //     this.employees = res.data
        //     this.dialogFormVisible_1 = true
        //     return this.employees
        //   }).catch(error => {
        //
        //   })
        // } else {
        //   return this.employees
        // }
      },
      //新增风险
      submitRisk() {
        this.$refs['newRisk'].validate((valid) => {
          if (valid) {
            this.$store.dispatch('risk/addRisk', this.newRisk).then(() => {
              this.$store.dispatch('risk/setRisks', null).then(() => {
                this.reload();
                this.$message({
                  type: 'success',
                  message: '风险新增成功',
                  duration: 2 * 1000
                })
              })

            }).catch(err => {

            })
          } else {
            this.$message({
              message: '请完整填写所需字段',
              type: 'error',
              duration: 2 * 1000
            })
            return false
          }
        })
      },
      importRisk() {
        if (this.standardRisks == null) {
          this.$store.dispatch('risk/importRisk').then(res => {
            this.dialogFormVisible_2 = true
            this.standardRisks = res
          }).catch(error => {

          })
        } else {
          this.dialogFormVisible_2 = true
        }
      },
      handleImport(row) {
        this.newRisk = row
        this.dialogFormVisible_2 = false
        this.openNewRiskDialog()
      },
      handleEdit(row) {
        if (this.employees == null) {
          getEmployeesByProjectId(this.$store.getters.projectId).then(res => {
            this.employees = res.data
            this.setEditRisk(row)
            this.editRiskDialogVisible = true
          }).catch(error => {

          })
        } else {
          this.setEditRisk(row)
          this.editRiskDialogVisible = true
        }
      },
      submitEditRisk() {
        this.$refs['editRisk'].validate((valid) => {
          if (valid) {
            this.$store.dispatch('risk/updateRisk',this.editRisk).then(() => {
              this.reload()
              this.$message({
                type: 'success',
                message: '更新成功',
                duration: 2 * 1000
              })
            })
          } else {
            this.$message({
              type: 'error',
              message: '请完整填写所需字段',
              duration: 2 * 1000
            })
          }
        })
      },
      setEditRisk(row) {
        var statusId = 0;
        if (row.status === 'OPENED') {
          statusId = 1;
        } else if (row.status === 'PROCESSED') {
          statusId = 2;
        } else if (row.status === 'SOLVED') {
          statusId = 3;
        } else if (row.status === 'CLOSED') {
          statusId = 4;
        }
        var affect = 0
        if (row.affect === '较大影响') {
          affect = 1;
        } else if (row.affect === '中等影响') {
          affect = 2;
        } else if (row.affect === '较小影响') {
          affect = 3;
        } else if (row.affect === '可忽略影响') {
          affect = 4;
        }
        var riskLevel = 0
        if (row.level === "中") {
          riskLevel = 1
        } else if (row.level === "低") {
          riskLevel = 2
        }
        this.editRisk.id = row.id
        this.editRisk.description = row.description
        this.editRisk.level = riskLevel.toString()
        this.editRisk.react = row.react
        this.editRisk.strategy = row.strategy
        this.editRisk.trackFreq = row.trackFreq
        this.editRisk.type = row.type
        this.editRisk.affect = affect.toString()
        this.editRisk.status = statusId
        this.editRisk.responsible = row.responsible.id
        var relatedId = []
        row.related.forEach((item) => {
          relatedId.push(item.id)
        })
        this.editRisk.related = relatedId

        this.editRisk.status = statusId.toString()

      }
    },
    mounted() {
      this.getRisk()
      getAllProjectEmployeeBasics({
        id: this.$store.getters.project.id
      }).then(response => {
        this.employees = response.data
      })
    },
  }
</script>

