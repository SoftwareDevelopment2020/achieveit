<template>
  <div>
    <div>
      <el-select
        v-model="searchValue.employeeId"
        placeholder="员工ID"
        style="width: 20%;min-width: 200px"
        name="employeeIdSearch"
        clearable
        filterable
      >
        <el-option
          v-for="item in participantOptions"
          :key="item.employeeId"
          :label="item.name + '（' + item.employeeId + '）'"
          :value="item.employeeId">
        </el-option>
      </el-select>
      <el-select
        v-model="searchValue.roles"
        multiple
        placeholder="员工角色"
        style="width: 30%;min-width: 120px"
        name="employeeRolesSearch"
        clearable
      >
        <el-option
          v-for="item in roleOptions"
          :key="item.name"
          :label="item.detail"
          name="employeeRolesSearchOption"
          :value="item.name">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="search" @keyup.enter="search" name="employeeSearch">
        <i class="el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button v-if="canEdit()" v-permission="['ROLE_PM']" type="primary" @click="openAddParticipantDialog"
                 name="openAddEmployeeDialog">
        <i class="el-icon-plus"></i>
        <span>添加人员</span>
      </el-button>
    </div>
    <div>
      <el-table
        v-loading="loading"
        :data="table.data"
        style="width: 100%; margin-top: 30px"
      >
        <el-table-column
          type="index"
          :index="indexMethod"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="employeeBasics.employeeId"
          label="员工ID"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="employeeBasics.name"
          label="姓名"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="roles"
          label="角色"
          width="150"
          align="center"
        >
          <template slot-scope="{row}">
            <p v-for="role in row.roles" :key="role.id">{{role.detail}}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="permissions"
          label="权限"
          width="150"
          align="center"
        >
          <template slot-scope="{row}">
            <p v-for="permission in row.permissions" :key="permission.id">{{permission.name | permissionDefine}}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="employeeBasics"
          label="基本信息"
          width="300"
          header-align="center"
        >
          <template slot-scope="{row}">
            <el-card>
              <p>部门：{{row.employeeBasics.department}}</p>
              <p>电话：{{row.employeeBasics.tel}}</p>
              <p>邮件地址：{{row.employeeBasics.emailAddress}}</p>
              <p>加入时间：{{row.joinTime}}</p>
            </el-card>
          </template>
        </el-table-column>
        <el-table-column
          prop="superiorBasics"
          label="上级"
          width="300"
          header-align="center"
        >
          <template slot-scope="{row}">
            <el-card v-if="row.superiorBasics">
              <p>{{row.superiorBasics.name}}（{{row.superiorBasics.employeeId}}）</p>
              <p>部门：{{row.superiorBasics.department}}</p>
              <p>电话：{{row.superiorBasics.tel}}</p>
              <p>邮件地址：{{row.superiorBasics.emailAddress}}</p>
            </el-card>
            <span v-else>
              <p align="center">无</p>
            </span>
          </template>
        </el-table-column>
        <el-table-column
          min-width="200">
          <template slot-scope="{ row }">
            <span v-if="row.exitTime === null">
              <span v-if="canEdit()" v-permission="['ROLE_PM']">
                <el-button type="text" size="mini" @click="openSetRoleDialog(row)"
                           name="openSetRolesDialogButton">设置角色</el-button>
                <el-button type="text" size="mini" @click="openSetPermissionDialog(row)"
                           name="openSetPermissionsDialogButton">设置权限</el-button>
                <el-button type="text" size="mini" @click="deleteParticipant(row)"
                           name="deleteEmployeeButton">删除</el-button>
              </span>
            </span>
            <span v-else style="color: red; font-size: 13px">
                {{row.exitTime}}退出
              </span>
          </template>
        </el-table-column>
      </el-table>

      <pagination :total="table.total" :page.sync="table.page" :limit.sync="table.limit"
                  @pagination="getParticipants"></pagination>
    </div>

    <!-- 添加人员 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      title="添加人员"
      :visible.sync="dialog.addParticipant.show"
      width="60%"
      center
    >
      <el-form
        ref="addParticipantForm"
        :model="dialog.addParticipant.data"
        :rules="dialog.addParticipant.rules"
        :hide-required-asterisk="true"
        label-width="100px"
        style="width: 80%"
      >
        <el-form-item label="员工ID" prop="employeeId">
          <el-select
            v-model="dialog.addParticipant.data.employeeId"
            name="addEmployeeId"
            style="width: 100%"
            filterable
            clearable>
            <el-option
              v-for="item in employeeOptions"
              :key="item.id"
              :label="item.name + '（' + item.employeeId + '）'"
              :value="item.id"
              :disabled="item.employeeId === dialog.addParticipant.data.employeeId"
              name="addEmployeeOption"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="roles">
          <el-select v-model="dialog.addParticipant.data.roles" style="width: 100%" name="addEmployeeRoles" multiple
                     clearable @change="autoSetPermission">
            <el-option
              v-for="item in roleOptions.slice(1, roleOptions.length)"
              :key="item.name"
              :label="item.detail"
              name="addEmployeeRolesOption"
              :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="权限" prop="permissions">
          <el-select v-model="dialog.addParticipant.data.permissions" name="addEmployeePermissions" style="width: 100%"
                     multiple clearable @clear="autoSetPermission">
            <el-option
              v-for="item in permissionOptions"
              :key="item.name"
              :label="item.detail"
              :value="item.name"
              name="addEmployeePermissionsOption"
              :disabled="item.name === 'bug'">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目上级ID" prop="superiorKey">
          <el-select
            v-model="dialog.addParticipant.data.superiorKey"
            name="addEmployeeSuperior"
            style="width: 100%"
            filterable
            clearable>
            <el-option
              v-for="item in participantOptions"
              :key="item.id"
              :label="item.name + '（' + item.employeeId + '）'"
              :value="item.id"
              :disabled="item.employeeId === dialog.addParticipant.data.employeeId"
              name="addEmployeeSuperiorOption"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog.addParticipant.show = false" name="addEmployeeCancelSubmit">取 消</el-button>
        <el-button type="primary" @click="addParticipant" name="addEmployeeSubmit">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 设置角色 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="dialog.setRole.title"
      :visible.sync="dialog.setRole.show"
      width="30%"
      center
    >
      <el-checkbox-group v-model="dialog.setRole.data.roles" :min="1">
        <el-checkbox
          v-for="item in roleOptions"
          :key="item.name"
          :label="item.name"
          :name="item.name"
          :disabled="item.name === 'ROLE_PM'"
          style="width: 70%;margin-left: 20%;"
        >
          {{item.detail}}
        </el-checkbox>
      </el-checkbox-group>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog.setRole.show = false" name="setRolesCancelSubmit">取 消</el-button>
        <el-button type="primary" @click="setRole" name="setRolesSubmit">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 设置权限 -->
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="dialog.setPermission.title"
      :visible.sync="dialog.setPermission.show"
      width="30%"
      center
    >
      <el-checkbox-group v-model="dialog.setPermission.data.permissions">
        <el-checkbox
          v-for="item in permissionOptions"
          :key="item.name"
          :name="item.name"
          :label="item.name"
          :disabled="item.name === 'bug'"
          style="width: 70%;margin-left: 20%;"
        >
          {{item.detail}}
        </el-checkbox>
      </el-checkbox-group>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog.setPermission.show = false" name="setPermissionsCancelSubmit">取 消</el-button>
        <el-button type="primary" @click="setPermission" name="setPermissionSubmit">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  import Pagination from '@/components/Pagination/index'
  import {
    addProjectEmployee,
    deleteProjectEmployee,
    getAllProjectEmployeeBasics,
    getProjectEmployees,
    setPermission,
    setRole
  } from "../../../../api/employee";
  import {getNullOrValue, setTable} from "../../../../utils/common";

  export default {
    components: {
      Pagination
    },
    data() {
      const checkRoles = (rule, value, callback) => {
        if (value.length === 0) {
          callback(new Error('角色不能为空'))
        }
        callback()
      }
      return {
        loading: false,
        project: this.$store.getters.project,
        table: {
          data: [],
          total: 0,
          page: 1,
          limit: 10,
          searchCondition: {
            employeeId: null,
            employeeName: null,
            roles: null
          }
        },
        roleOptions: this.Constant.roles,
        permissionOptions: this.Constant.permissions,
        employeeOptions: null,
        participantOptions: [],
        searchValue: {
          employeeId: '',
          employeeName: '',
          roles: []
        },
        dialog: {
          addParticipant: {
            show: false,
            data: {
              employeeId: '',
              roles: [],
              permissions: [],
              superiorKey: ''
            },
            superiorOptions: [],
            rules: {
              employeeId: [
                {required: true, message: '员工ID不能为空', trigger: 'blur'}
              ],
              roles: [
                {
                  validator: checkRoles,
                  trigger: 'blur'
                }
              ]
            }
          },
          setRole: {
            title: '',
            show: false,
            data: {
              projectEmployeeId: '',
              roles: []
            }
          },
          setPermission: {
            title: '',
            show: false,
            data: {
              projectEmployeeId: '',
              permissions: []
            }
          }
        }
      }
    },
    mounted() {
      this.getAllProjectEmployeeBasics()
      this.getParticipants()
    },
    methods: {
      /**
       * 行号
       */
      indexMethod(index) {
        return index + 1
      },

      /**
       * 判断当前项目状态，只有3111时可以进行人员编辑
       */
      canEdit() {
        return this.project.statusId === 3111
      },

      /**
       * 获取项目人员
       */
      getAllProjectEmployeeBasics() {
        getAllProjectEmployeeBasics({
          id: this.project.id
        }).then(response => {
          this.participantOptions = response.data
        })
      },
      getParticipants() {
        this.loading = true
        getProjectEmployees({
          current: this.table.page,
          size: this.table.limit,
          searchCondition: {
            projectId: this.project.id,
            employeeId: this.table.searchCondition.employeeId,
            employeeName: this.table.searchCondition.employeeName,
            roles: this.table.searchCondition.roles
          }
        }).then(response => {
          setTable(response.data, this.table, this.setSearchValue())
        }).catch(error => {
          console.error(error)
        }).finally(() => {
          this.loading = false
        })
      },
      search() {
        this.table.page = 1
        this.table.searchCondition.employeeId = getNullOrValue(this.searchValue.employeeId)
        this.table.searchCondition.employeeName = getNullOrValue(this.searchValue.employeeName)
        this.table.searchCondition.roles = getNullOrValue(this.searchValue.roles)
        this.getParticipants()
      },
      setSearchValue() {
        this.searchValue.employeeId = this.table.searchCondition.employeeId
        this.searchValue.employeeName = this.table.searchCondition.employeeName
        this.searchValue.roles = this.table.searchCondition.roles
      },

      /**
       * 添加项目人员
       */
      addParticipant() {
        this.$refs.addParticipantForm.validate(valid => {
          if (valid) {
            addProjectEmployee({
              projectKey: this.project.id,
              ...this.dialog.addParticipant.data
            }).then(() => {
              this.$message({
                message: '添加成功',
                type: 'success',
                duration: 2 * 1000
              })
            }).catch((error) => {
              console.error(error)
            }).finally(() => {
              // 关闭对话框
              this.dialog.addParticipant.show = false
              // 刷新
              this.getParticipants()
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
      openAddParticipantDialog() {
        // 获取所有人员信息
        if (this.employeeOptions == null) {
          getAllProjectEmployeeBasics({
            id: null
          }).then(response => {
            this.employeeOptions = response.data
          })
        }
        // 初始化数据
        this.dialog.addParticipant.data.employeeId = ''
        this.dialog.addParticipant.data.roles = []
        this.dialog.addParticipant.data.permissions = []
        this.dialog.addParticipant.data.superiorKey = ''
        // 打开对话框
        this.dialog.addParticipant.show = true
      },

      /**
       * 删除项目人员
       */
      deleteParticipant(row) {
        this.$confirm('确认删除该人员?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          closeOnClickModal: false,
          closeOnPressEscape: false
        }).then(() => {
          deleteProjectEmployee({
            projectEmployeeId: row.id
          }).then(() => {
            this.$message.success('删除成功')
          }).finally(() => {
            // 刷新
            this.getParticipants()
          })
        })
      },

      /**
       * 设置角色
       */
      openSetRoleDialog(row) {
        // 设置标题
        this.dialog.setRole.title = '设置角色：' + row.employeeBasics.name + '（' + row.employeeBasics.employeeId + '）'
        // 设置人员id
        this.dialog.setRole.data.projectEmployeeId = row.id
        // 设置已选角色
        this.dialog.setRole.data.roles = []
        row.roles.forEach(role => {
          this.dialog.setRole.data.roles.push(role.name)
        })
        // 打开对话框
        this.dialog.setRole.show = true
      },
      setRole() {
        setRole(this.dialog.setRole.data).then(() => {
          this.$message.success('成功')
        }).catch(error => {
          console.error(error)
        }).finally(() => {
          // 关闭对话框
          this.dialog.setRole.show = false
          // 刷新
          this.getParticipants()
        })
      },

      /**
       * 设置权限
       */
      openSetPermissionDialog(row) {
        // 设置标题
        this.dialog.setPermission.title = '设置权限：' + row.employeeBasics.name + '（' + row.employeeBasics.employeeId + '）'
        // 设置项目人员id
        this.dialog.setPermission.data.projectEmployeeId = row.id
        // 设置当前权限
        this.dialog.setPermission.data.permissions = []
        row.permissions.forEach(permission => {
          this.dialog.setPermission.data.permissions.push(permission.name)
        })
        // 打开对话框
        this.dialog.setPermission.show = true
      },
      setPermission() {
        setPermission(this.dialog.setPermission.data).then(response => {
          this.$message.success('成功')
        }).catch(error => {
          console.error(error)
        }).finally(() => {
          // 关闭对话框
          this.dialog.setPermission.show = false
          // 刷新
          this.getParticipants()
        })
      },
      /**
       * 产品经理、开发Leader、测试Leader自动配置缺陷追踪管理权限
       */
      autoSetPermission() {
        // 已选权限中获取追踪管理权限下标
        const bugPermissionIndex = this.dialog.addParticipant.data.permissions.indexOf('bug')
        if (this.dialog.addParticipant.data.roles.some(
          role => (role === 'ROLE_PM' || role === 'ROLE_DEVLEADER' || role === 'ROLE_TESTLEADER'))) {
          // 如果有产品经理、开发Leader、测试Leader角色，在权限中添加缺陷追踪管理
          if (bugPermissionIndex === -1) {
            this.dialog.addParticipant.data.permissions.push('bug')
          }
        } else {
          // 没有如果有产品经理、开发Leader、测试Leader角色，在权限中去除缺陷追踪管理
          if (bugPermissionIndex > -1) {
            this.dialog.addParticipant.data.permissions.splice(bugPermissionIndex, 1)
          }
        }
      }
    }
  }
</script>
