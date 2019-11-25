/**
 * 将具有层级结构的列表数据转换为对应的树形结构，输入数据需要具有，主键:{@param itemId}，父节点id：parentId, 是否具有孩子节点,
 * 如果是一级节点则parentId=0，无孩子节点则hasChild=0或hasChild = false，（不需要有children字段）,有孩子节点时hasChild=1或hasChild=true
 *  栗子：
 *    输入数据：[{sortId:1,parentId:0,hasChild:1},{sortId:2,parentId:1,hasChild:0},{sortId:3,parentId:0,hasChild:0}]
 *    返回数据：[{sortId:1,parentId:0,hasChild:1,children:[{sortId:2,parentId:1,hasChild:0}]},{sortId:3,parentId:0,hasChild:0}]
 * @param array 待转换数组
 * @param itemId 代表数据id字段的字段名
 * @returns {Array} 处理后的树形结构
 */
export function convertDataFromArrayToTree (array, itemId) {
  // item{"itemId":1,"parentId":0,"hasChild":1}

  let treeBo = {}
  for (let i = 0; i < array.length; i++) {
    // 原始数据对象
    let originalItem = array[i]

    let treeNode = treeBo[originalItem[itemId]]
    // 节点未被遍历创建过
    if (treeNode === undefined) {
      // 初始化对应的treeNode,有孩子节点则添加children项，否则不加
      if (originalItem.hasChild) {
        originalItem.children = []
        // 添加到树中
        treeBo[originalItem[itemId]] = originalItem
        treeNode = originalItem
      } else {
        // 如果没有子节点则不用添加到树的节点上，后续会被自动加到父节点的孩子里，作为叶子节点
        treeNode = originalItem
      }
    } else {
      // 已创建过，（因提前遍历了子节点而创建）
      originalItem.children = treeNode.children
      treeBo[originalItem[itemId]] = originalItem
      treeNode = originalItem
    }
    let parentTreeNode = treeBo[originalItem.parentId]
    // 父节点还未被遍历创建
    if (parentTreeNode === undefined) {
      treeBo[originalItem.parentId] = {children: [treeNode]}
    } else {
      // 父节点已被创建，则直接将自己装入父亲的孩子里
      parentTreeNode.children[parentTreeNode.children.length] = treeNode
    }
  }
  let retTree = []
  if (array.length !== 0) {
    retTree = treeBo[0].children
  }
  return retTree
}
