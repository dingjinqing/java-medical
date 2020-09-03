const getters = {
  admin_leftVav_flag: state => state.leftnav.admin_leftVav_flag,
  system_leftVav_flag: state => state.leftsysnav.system_leftVav_flag,
  clickNode: state => state.smallProcedures.clickNode,
  allNodes: state => state.smallProcedures.allNodes,
  selectlinksIndex: state => state.smallProgramManagement.selectlinksIndex,
  selectlinksLevelOneBottom: state =>
    state.smallProgramManagement.selectlinksLevelOneBottom,
  choisePath: state => state.smallProgramManagement.choisePath,
  afferentPath: state => state.smallProgramManagement.afferentPath,
  crumbsTitle: state => state.crumbs.crumbsTitle,
  goodsIds: state => state.goodsManagement.goodsIds,
  editGoodsId: state => state.goodsManagement.editGoodsId,
  proAndUrData: state => state.crumbs.proAndUrData,
  cardholderData: state => state.crumbs.cardholderData,
  membershipdetailflag: state => state.membershipList.membershipdetailflag,
  toHandleSetUpMemflag: state => state.membershipList.toHandleSetUpMemflag,
  toHandleSelectingUsersflag: state =>
    state.membershipList.toHandleSelectingUsersflag,
  menuFlag: state => state.util.menuFlag,
  activeFlag: state => state.util.activeFlag,
  cropperFlag: state => state.util.cropperFlag,
  activeFresh: state => state.util.activeFresh,
  picSpaceCropperFlag: state => state.util.picSpaceCropperFlag,
  Micropage: state => state.smallProcedures.Micropage,
  // getUserName: state => state.getUserName
  dialogVisible: state => state.brandDialog.dialogVisible,
  showMenuData: state => state.leftnav.showMenuData
}

export default getters
