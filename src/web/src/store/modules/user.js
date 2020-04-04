import {login, getInfo} from '@/api/user'
import {getToken, setToken, removeToken} from '@/utils/auth'
import router, {resetRouter} from '@/router'

const state = {
  token: getToken(),
  username: '',
  name: '',
  roles: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_USERNAME: (state, username) => {
    state.username = username
  }
}

const actions = {
  // user login
  login({commit,dispatch}, userInfo) {
    const {username, password} = userInfo

    return new Promise((resolve, reject) => {
      login({username: username.trim(), password: password}).then(response => {
        console.log('登录成功返回token：' + response.data)
        commit('SET_TOKEN', 'Bearer' + response.data)
        setToken('Bearer' + response.data)

        // 重置
        dispatch('resetSystem')

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({commit}) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const {data} = response
        var roles = []
        data.roles.forEach((item) => {
          console.log(item)
          //{id: 3, name: "ROLE_DEV", detail: "开发"}
          roles.push(item.name)
        })
        //角色信息，一个数组
        console.log(roles)
        commit('SET_ROLES', roles)
        commit('SET_USERNAME', data.username)
        commit('SET_NAME', data.name)
        resolve(data)
      }).catch(err => {
        reject(err)
      })
    })
  },
  // set user info
  setInfo({commit}, {userDetail, roles}) {
    const thisRoles = []
    if (roles) {
      roles.forEach(item => {
        console.log(item)
        thisRoles.push(item.name)
      })
    }
    commit('SET_ROLES', thisRoles)
    commit('SET_USERNAME', userDetail.username)
    commit('SET_NAME', userDetail.name)
  },

  // user logout
  logout({commit, state, dispatch}) {
    commit('SET_TOKEN', '')
    commit('SET_ROLES', [])
    removeToken()
    resetRouter()
    dispatch('tagsView/delAllViews', null, {root: true})

    // 重置
    dispatch('resetSystem')
  },

  // remove token
  resetToken({commit}) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  changeRoles({commit, dispatch}, role) {
    console.log('进入changeRoles函数')
    return new Promise(async resolve => {
      const token = role + '-token'

      commit('SET_TOKEN', token)
      setToken(token)

      const {roles} = await dispatch('getInfo')

      resetRouter()

      // generate accessible routes map based on roles
      const accessRoutes = await dispatch('permission/generateRoutes', roles, {root: true})

      // dynamically add accessible routes
      router.addRoutes(accessRoutes)

      // reset visited views and cached views
      dispatch('tagsView/delAllViews', null, {root: true})

      resolve()
    })
  },

  resetSystem({dispatch}) {
    // 清空缓存
    sessionStorage.clear()
    // 登录后重置store中存储的project
    dispatch('project/removeCurrentProject', null, {root: true})
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
