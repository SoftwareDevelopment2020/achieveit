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
      <el-input
        v-model="searchValue.name"
        placeholder="风险责任人"
        style="width: 20%;min-width: 200px"
        name="riskResponsibleSearch"
        @keyup.enter.native="getRisk"
        clearable
      >
      </el-input>
      <el-select v-model="searchValue.status" placeholder="风险状态" name="riskStatusSearch" clearable>
        <el-option v-for="state in riskStatus" :key="state.id" :label="state.value" :value="state.id" name="riskStatusOptionSearch">
        </el-option>
      </el-select>
      <el-button icon="el-icon-search" circle @click="getRisk" name="searchRiskButton">
      </el-button>
      <el-button type="primary" @click="openNewRiskDialog" name="openNewRiskButton" v-permission="['ROLE_PM']">
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
                  <el-option v-for="state in riskStatus" :key="state.id" :label="state.value" :value="state.id" name="newRiskStatusOption">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="风险级别" :label-width="formLabelWidth" prop="level">
                <el-select v-model="newRisk.level" placeholder="风险级别" name="riskLevel">
                  <el-option v-for="level in riskLevel" :key="level.id" :label="level.value" :value="level.id" name="newRiskLevelOption">
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
                  <el-option v-for="affect in riskAffect" :key="affect.id" :label="affect.value" :value="affect.id" name="newRiskAffectOption">
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
              <el-select v-model="newRisk.related" placeholder="风险相关者" multiple style="width: 100%" name="riskRelated" filterable>
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

      <el-button type="primary" @click="dialogFormVisible_2 = true" name="openImportRiskButton" v-permission="['ROLE_PM']">
        导入已有风险<i class="el-icon-upload el-icon--right"></i>
      </el-button>

      <el-dialog title="导入风险" :visible.sync="dialogFormVisible_2">
        <el-form :model="form_2">
          <el-form-item label="风险ID" :label-width="formLabelWidth">
            <el-input v-model="form_2.riskId" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible_2 = false" name="cancelImportButton">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible_2 = false" name="importButton">确 定</el-button>
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
              type="danger"
              icon="el-icon-delete" circle
              @click="handleDelete(scope.$index, scope.row)"></el-button>
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
  import {getEmployeesByProjectId} from "../../../../api/employee";

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
        form_2: {
          riskId: ''
        },
        formLabelWidth: '150px',
        risks: [],
        employees: null,
        riskAffect: this.Constant.riskAffect,
        riskLevel: this.Constant.riskLevel,
        riskStatus: this.Constant.status,
        riskRules: this.Constant.riskRules,
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
          type: ''
        },
        table: {total: 0, limit: 10, current: 1}
      }
    },
    methods: {
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
        if (this.employees == null) {
          console.log('后端获取人员信息')
          getEmployeesByProjectId(this.$store.getters.projectId).then(res => {
            this.employees = res.data
            return this.employees
          }).catch(error => {

          })
        } else {
          return this.employees
        }
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
      }
    },
    mounted() {
      this.getRisk()
    },
  }
</script>

