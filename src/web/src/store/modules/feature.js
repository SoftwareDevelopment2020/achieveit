import {Message} from 'element-ui'
import {getFeatures, uploadFeatures, downloadExcel} from "@/api/feature";
import store from "@/store";

const state = {
  features: null,
}
const mutations = {
  SET_FEATURES: (state, features) => {
    state.features = features
  },

}

const actions = {
  setFeatures({commit}, features) {
    return new Promise((resolve, reject) => {
      commit('SET_FEATURES', features)
      resolve()
    })
  },
  uploadFeatures({dispatch, commit}, excel) {
    const formData = new FormData();
    formData.append('projectId', store.getters.projectId)
    formData.append('file', excel)
    return new Promise((resolve, reject) => {
      uploadFeatures(formData).then(response => {
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  getFeatures({commit}) {
    return new Promise((resolve, reject) => {
      getFeatures(store.getters.projectId).then(response => {
        console.log("后端获取")
        commit('SET_FEATURES', response.data)
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  downloadExcel() {
    return new Promise((resolve, reject) => {
      downloadExcel(store.getters.projectId).then(response => {
        const blob = new Blob([response.data]);
        const fileName = store.getters.projectId + '.xlsx';
        const linkNode = document.createElement('a');

        linkNode.download = fileName; //a标签的download属性规定下载文件的名称
        linkNode.style.display = 'none';
        linkNode.href = URL.createObjectURL(blob); //生成一个Blob URL
        document.body.appendChild(linkNode);
        linkNode.click();  //模拟在按钮上的一次鼠标单击

        URL.revokeObjectURL(linkNode.href); // 释放URL 对象
        document.body.removeChild(linkNode);
        resolve()
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
