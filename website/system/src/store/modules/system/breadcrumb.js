export default {
  state: {
    breadcrumbTitle: ''
  },
  mutations: {
    UPDATE_BREADCRUMB_TITLE (state, title) {
      console.log(title)
      state.breadcrumbTitle = title
    }
  }
}
