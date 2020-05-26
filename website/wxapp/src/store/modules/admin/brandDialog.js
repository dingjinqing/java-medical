import { SHOW_DIALOG } from './mutation-types'
const brandDialog = {
  state: {
    dialogVisible: true
  },
  mutations: {
    [SHOW_DIALOG] (state) {
      state.dialogVisible = false
    }
  },
  actions: {}
}
export default brandDialog
