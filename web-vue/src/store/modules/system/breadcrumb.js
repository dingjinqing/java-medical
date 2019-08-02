export default {
  state: {
    breadcrumbTitle: ''
  },
  mutations: {
    UPDATE_BREADCRUMB_TITLE (state, title) {
      state.breadcrumbTitle = title
    }
  }
}
