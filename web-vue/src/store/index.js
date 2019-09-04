import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import leftnav from './modules/admin/leftNav'
import leftsysnav from './modules/system/leftsysNav'
import smallProcedures from './modules/admin/smallProcedures'
import smallProgramManagement from './modules/admin/smallProgramManagement'
import crumbs from './modules/admin/crumbs'
import goodsManagement from './modules/admin/goodsManagement'
import membershipList from './modules/admin/membershipList'
import breadcrumb from './modules/system/breadcrumb'
import util from './modules/admin/util'
import brandDialog from './modules/admin/brandDialog'
import decorationModules from './modules/admin/decorationModules'
Vue.use(Vuex)
export default new Vuex.Store({
  modules: {
    leftnav,
    leftsysnav,
    smallProcedures,
    smallProgramManagement,
    crumbs,
    goodsManagement,
    membershipList,
    breadcrumb,
    util,
    brandDialog,
    decorationModules
  },
  getters
})
