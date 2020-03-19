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

    // const {roles, name, avatar, introduction} = data
    // commit('SET_ROLES', roles)
    // commit('SET_NAME', name)
    // commit('SET_AVATAR', avatar)
    // commit('SET_INTRODUCTION', introduction)
    return new Promise((resolve, reject) => {

      getInfo().then(res => {
        const response = res
        const {data} = response
        // 设定角色信息，后端暂时无roles信息，所以暂时假定角色信息为admin
        //TODO
        commit('SET_ROLES', ['admin'])
        commit('SET_USERNAME', data.username)
        commit('SET_NAME', data.name)
        resolve(data)
      }).catch(err => {
        reject(err)
      })
    })
    // return new Promise((resolve, reject) => {
    //   getInfo(state.token).then(response => {
    //     // 由于没有getInfo接口暂且以admin填充
    //     // TODO
    //     // const { data } = response
    //     //
    //     // if (!data) {
    //     //   reject('Verification failed, please Login again.')
    //     // }
    //     //
    //     // const { roles, name, avatar, introduction } = data
    //     //
    //     // // roles must be a non-empty array
    //     // if (!roles || roles.length <= 0) {
    //     //   reject('getInfo: roles must be a non-null array!')
    //     // }
    //
    //     // commit('SET_ROLES', roles)
    //     // commit('SET_NAME', name)
    //     // commit('SET_AVATAR', avatar)
    //     // commit('SET_INTRODUCTION', introduction)
    //     // resolve(data)
    //     resolve()
    //   }
    //   ).catch(error => {
    //     reject(error)
    //   })
    // })
  },

  // user logout
  logout({commit, state, dispatch}) {
    dispatch('tagsView/delAllViews', null, {root: true})
    commit('SET_TOKEN', '')
    commit('SET_ROLES', [])
    removeToken()
    resetRouter()
    dispatch('tagsView/delAllViews', null, {root: true})
    // reset visited views and cached views
    // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
    // 取消与后端交互
    // dispatch('tagsView/delAllViews', null, { root: true })
    // return new Promise((resolve, reject) => {
    //   logout(state.token).then(() => {
    //     commit('SET_TOKEN', '')
    //     commit('SET_ROLES', [])
    //     removeToken()
    //     resetRouter()
    //
    //     // reset visited views and cached views
    //     // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
    //     dispatch('tagsView/delAllViews', null, { root: true })
    //
    //     resolve()
    //   }).catch(error => {
    //     reject(error)
    //   })
    // })
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
