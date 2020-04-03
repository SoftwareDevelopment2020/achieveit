import {getRisks, addRisk} from "@/api/risk";
import store from "@/store";

const state = {
  risks: null
}

const mutations = {
  SET_RISKS: function (state, risks) {
    state.risks = risks
  }
}
const actions = {
  getRisks({commit}) {
    return new Promise((resolve, reject) => {
      getRisks(store.getters.projectId).then(response => {
        console.log("后端获取风险信息")
        commit('SET_RISKS', response.data)
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  addRisk(risk) {
    return new Promise((resolve, reject) => {
      addRisk(store.getters.projectId, risk).then(response => {
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })

  }
}
export default {
  namespaced: true,
  state,
  mutations,
  actions
}
