const getters = {
  admin_leftVav_flag: state => state.leftnav.admin_leftVav_flag,
  system_leftVav_flag: state => state.leftsysnav.system_leftVav_flag,
  clickNode: state => state.smallProcedures.clickNode,
  allNodes: state => state.smallProcedures.allNodes,
  selectlinksIndex: state => state.smallProgramManagement.selectlinksIndex,
  selectlinksLevelOneBottom: state => state.smallProgramManagement.selectlinksLevelOneBottom,
  choisePath: state => state.smallProgramManagement.choisePath,
  afferentPath: state => state.smallProgramManagement.afferentPath,
  crumbsTitle: state => state.crumbs.crumbsTitle,
  goodsIds: state => state.goodsManagement.goodsIds,
  editGoodsId: state => state.goodsManagement.editGoodsId,
  proAndUrData: state => state.crumbs.proAndUrData
}

export default getters
