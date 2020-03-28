import {login, logout, getInfo} from '@/api/user'
import {getToken, setToken, removeToken} from '@/utils/auth'
import router, {resetRouter} from '@/router'

const state = {
  token: getToken(),
  username: '',
  name: '',
  avatar: '',
  introduction: '',
  roles: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
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
  login({commit}, userInfo) {
    const {username, password} = userInfo
    return new Promise((resolve, reject) => {
      login({username: username.trim(), password: password}).then(response => {
        console.log('登录成功返回token：' + response.data)
        commit('SET_TOKEN', 'Bearer' + response.data)
        setToken('Bearer' + response.data)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({commit, state}) {
    // const response = {'code': 20000,
    //   'data': {
    //     'roles': ['admin'],
    //     'introduction': 'I am a super administrator',
    //     'avatar': 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    //     'name': 'Super Admin'
    //   }
    // }
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

  // user logout
  logout({commit, state, dispatch}) {
    commit('SET_TOKEN', '')
    commit('SET_ROLES', [])
    removeToken()
    resetRouter()
    dispatch('tagsView/delAllViews', null, {root: true})
    // dispatch('project/removeCurrentProject', null, {root: true})
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
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
