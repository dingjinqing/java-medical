<template>
  <div class="tree_container">
    <el-dialog
      title="确认要删除此项吗？"
      :visible.sync="delDialogVisible"
      width="30%"
      :append-to-body="true"
    >

      <span slot="footer">
        <el-button
          size="small"
          @click="delDialogVisible = false"
        >
          取 消
        </el-button>
        <el-button
          size="small"
          type="primary"
          @click="delSelect"
        >
          确 定
        </el-button>
      </span>
    </el-dialog>

    <div v-show="menuVisible">
      <ul id="menu_">
        <span class="menu">
          <el-button
            size="mini"
            type="text"
            @click="update(node, data)"
          >
            编辑
          </el-button>

          <el-button
            size="mini"
            type="text"
            @click="append(node, data)"
            v-if="add_btn"
          >
            添加
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="remove(node, data)"
            v-if="del_btn"
          >
            删除
          </el-button>
        </span>
      </ul>
    </div>
    <el-card>
      <div class="ly-tree-container">
        <div
          class="userPic"
          :style="isHoldUserPicColorFlga?'backgroundColor:#f3f3f3':'backgroundColor:#e6e9f0'"
          @click="handleToClickUserPic()"
        >用户图片</div>
        <el-tree
          :data="treeData"
          :props="defaultProps"
          default-expand-all
          :expand-on-click-node="false"
          :render-content="renderContent"
          @node-click="nodeClick"
          @node-contextmenu="nodeRight"
        >
        </el-tree>
      </div>
    </el-card>
  </div>
</template>

<script>
import {
  getTreeListRequest,
  groupAddRequest,
  groupDelRequest,
  renameRequest
} from '@/api/system/pictureSpace.js'
// getHeadTreeListRequest
import { renameHeadRequest, groupHeadAddRequest, groupHeadDelRequest, getHeadTreeListRequest } from '@/api/system/tree.js'
import {
  getEditContent,
  getDefaultContent
} from '@/util/tree.utils.js'
import { mapActions } from 'vuex'
export default {
  props: ['pageIndex'],
  name: 'ly-tree',
  data () {
    return {
      treeData: [],
      isEdit: false,
      edit_name: '',
      defaultProps: {
        children: 'child',
        label: 'name'
      },
      select_id: null,
      select_level: null,
      select_node: null,
      delDialogVisible: false,
      menu_flag: true,
      menuVisible: false,
      node: '',
      data: '',
      add_btn: '',
      del_btn: '',
      isHoldUserPicColorFlga: false // 用户图片点击控制背景颜色flag
    }
  },
  created () {
    this.refresh()
  },
  mounted () {
    console.log(this.pageIndex)
  },
  methods: {
    ...mapActions(['changeTreeNode', 'allNodes']),
    // 节点右键点击
    nodeRight (event, object, value, element) {
      this.menuVisible = true
      let menu = document.querySelector('#menu_')
      /* 菜单定位基于鼠标点击位置 */
      menu.style.left = event.clientX + 2 + 'px'
      menu.style.top = event.clientY + 'px'

      this.data = object
      this.node = value

      if (value.level !== 6) {
        this.add_btn = true
      } else {
        this.add_btn = false
      }
      if (value.level !== 1) {
        this.del_btn = true
      } else {
        this.del_btn = false
      }
      // console.log('右键被点击的event:', event)
      // console.log('右键被点击的object:', object)
      // console.log('右键被点击的value:', value)
      // console.log('右键被点击的element:', element)
      document.addEventListener('click', this.foo)
    },
    foo () { // 取消鼠标监听事件 菜单栏
      console.log('关闭菜单')
      this.menuVisible = false
      document.removeEventListener('click', this.foo)
    },

    // 节点被点击时的状态
    nodeClick (data, node, mynode) {
      this.$emit('clickUserPic', 1)
      this.isHoldUserPicColorFlga = false
      console.log('123')
      console.log(data, node, mynode)
      this.allNodes(data)
      this.menuVisible = false
    },
    refresh () {
      console.log(12312312312)
      // let res = getServiceTree()
      if (this.pageIndex === 'pictureSpace') {
        getTreeListRequest().then((res) => {
          console.log(res)
          console.log(res.content[0].id)
          this.changeTreeNode(res.content[0])
          this.allNodes(res)
          this.treeData = res.content
        })
      } else {
        console.log('tree!')
        getHeadTreeListRequest().then((res) => {
          console.log(res)
          console.log(res.content[0].id)
          this.changeTreeNode(res.content[0])
          this.allNodes(res)
          this.treeData = res.content
        })
      }

      // console.log(res)
    },
    append (node, data, e) {
      e = event || window.event
      e.stopPropagation()
      if (!this.isEdit) {
        this.select_id = data.id
        this.edit_name = ''
        const newChild = {
          name: '',
          level: data.level + 1,
          isEdit: true
        }
        console.log(newChild)

        this.isEdit = true
        if (!data.child) {
          this.$set(data, 'child', [])
        }
        console.log(data.child)
        data.child.unshift(newChild)
      } else {
        this.$notify({
          type: 'error',
          title: '操作提示',
          message: '有正在编辑或添加的选项未完成！',
          duration: 2000
        })
      }
      this.menuVisible = false
    },

    remove (node, data, e) {
      e = event || window.event
      e.stopPropagation()
      if (this.isEdit) {
        this.$notify({
          type: 'error',
          title: '操作提示',
          message: '有正在编辑或添加的选项未完成！',
          duration: 2000
        })
        return
      }
      this.select_node = node
      this.delDialogVisible = true
      this.menuVisible = false
    },

    delSelect () {
      let obj = {
        imgCatId: this.select_node.data.id
      }
      if (this.pageIndex === 'pictureSpace') {
        groupDelRequest(obj).then((res) => {
          if (res.error === 0) {
            this.delDialogVisible = false
            this.refresh()
            this.$notify({
              type: 'success',
              title: '操作提示',
              message: '删除成功!',
              duration: 2000
            })
          }
          console.log(res)
        })
      } else {
        groupHeadDelRequest(obj).then((res) => {
          if (res.error === 0) {
            this.delDialogVisible = false
            this.refresh()
            this.$notify({
              type: 'success',
              title: '操作提示',
              message: '删除成功!',
              duration: 2000
            })
          }
          console.log(res)
        })
      }
    },

    update (node, data, e) {
      e = event || window.event
      e.stopPropagation()
      if (this.isEdit) {
        this.$notify({
          type: 'error',
          title: '操作提示',
          message: '有正在编辑或添加的选项未完成！',
          duration: 2000
        })
        return
      }
      this.select_id = data.id
      this.select_level = data.level
      this.edit_name = data.name
      this.isEdit = true
      this.menuVisible = false
    },

    editMsg (data, node, e) {
      console.log('添加')
      e = event || window.event
      e.stopPropagation()
      if (this.edit_name.replace(/^\s+|\s+$/g, '')) {
        console.log(data)
        if (!data.id) {
          let virtualNode = node.parent
          let params = {
            imgCatName: this.edit_name,
            imgCatParentId: virtualNode.data.id,
            sort: ''
          }
          console.log(virtualNode)
          //   let addChild = addItem(this.treeData, params)
          if (this.pageIndex === 'pictureSpace') {
            groupAddRequest(params).then((res) => {
              if (res.error === 0) {
                virtualNode.data.child.forEach((item, i) => {
                  if (!item.id) {
                    virtualNode.data.child.splice(i, 1)
                  }
                })
                this.isEdit = false
                this.select_id = null
                this.select_level = null
                this.$notify({
                  type: 'success',
                  title: '操作提示',
                  message: '添加成功！',
                  duration: 2000
                })
                this.refresh()
              }
            })
          } else {
            groupHeadAddRequest(params).then((res) => {
              if (res.error === 0) {
                virtualNode.data.child.forEach((item, i) => {
                  if (!item.id) {
                    virtualNode.data.child.splice(i, 1)
                  }
                })
                this.isEdit = false
                this.select_id = null
                this.select_level = null
                this.$notify({
                  type: 'success',
                  title: '操作提示',
                  message: '添加成功！',
                  duration: 2000
                })
                this.refresh()
              }
            })
          }

          // 如果是用的真api,需要在添加的接口返回添加的节点
          // 添加成功后，将返回的节点加入数据中，然后删除掉没有id的假节点
          return
        }

        let params = {
          imgCatName: this.edit_name,
          imgCatId: data.id
        }
        if (this.pageIndex === 'pictureSpace') {
          renameRequest(params).then((res) => {
            console.log(res)
            if (res.error === 0) {
              this.isEdit = false
              this.select_id = null
              this.select_level = null
              this.$notify({
                type: 'success',
                title: '操作提示',
                message: '编辑成功！',
                duration: 2000
              })
              this.refresh()
            }
          })
        } else {
          console.log(params)
          renameHeadRequest(params).then((res) => {
            console.log(res)
            if (res.error === 0) {
              this.isEdit = false
              this.select_id = null
              this.select_level = null
              this.$notify({
                type: 'success',
                title: '操作提示',
                message: '编辑成功！',
                duration: 2000
              })
              this.refresh()
            }
          })
        }
      }
    },

    close (data, node, e) {
      e = event || window.event
      e.stopPropagation()
      if (!data.id) {
        node.parent.data.child.forEach((item, i) => {
          if (!item.id) {
            node.parent.data.child.splice(i, 1)
          }
        })
      }
      this.select_id = null
      this.select_level = null
      this.edit_name = data.name
      this.isEdit = false
    },

    nameChange (e) {
      e = event || window.event
      e.stopPropagation()
      this.edit_name = e.target.value
      console.log(e.target.value)
    },

    isSelect (data) {
      return data.id === this.select_id &&
        data.level === this.select_level
    },
    nameBlur (data, node) {
      this.editMsg(data, node)
    },
    renderContent (h, { node, data }) {
      return (
        <span class="ly-tree-node">
          {
            (this.isEdit === true && this.isSelect(data)) || data.isEdit
              ? <input
                placeholder="名称不能为空"
                class="ly-edit__text"
                style="width:80px"
                on-keyup={() => this.nameChange()}
                on-blur={() => this.nameBlur(data, node)}
                value={this.edit_name} />
              : <span>{data.name}</span>
          }
          {
            (this.isEdit === true && this.isSelect(data)) || data.isEdit
              ? getEditContent.call(this, h, data, node)
              : getDefaultContent.call(this, h, data, node)
          }
        </span>)
    },
    // 用户图片点击事件
    handleToClickUserPic () {
      this.isHoldUserPicColorFlga = true
      this.$emit('clickUserPic', -1)
    }
  }
}
</script>
<style scoped>
.tree_container {
  width: 160px;
  background: #e6e9f0;
}
#menu_ {
  position: fixed;
  z-index: 10000;
}
.menu {
  background-color: #fff;
  display: flex;
  flex-direction: column;
  padding: 7px;
  border-radius: 2px;
  box-shadow: 1px 1px 50px rgba(0, 0, 0, 0.3);
}
</style>

<style lang="scss">
.el-button + .el-button {
  margin-left: 0;
}

.ly-tree-node {
  height: 40px !important;
}

.el-tree-node > .el-tree-node__children {
  overflow: visible;
}
.el-card__body {
  padding: 0 !important;
}
.is-always-shadow {
  height: 100%;
  overflow: auto;
}
.ly-tree-container {
  //   margin: 20px 0 20px 20px;
  //   width: 60%;

  .el-tree {
    background-color: #e6e9f0 !important;
  }
  padding: 20px;
  .menu {
    display: flex !important;
    flex-direction: column !important;
    display: none !important;
  }
  span {
    font-size: 14px;
  }

  .el-tree > .el-tree-node > .el-tree-node__content:first-child {
    &::before,
    &::after {
      border: none;
    }
  }

  .ly-visible {
    // margin-left: 50px;
    visibility: hidden;
    // background-color: #fff;
    z-index: 10;
    background-color: #f3f3f3 !important;

    // position: absolute;
    // left: 10px;
    // top: 30px;
  }

  .ly-edit__text {
    width: 96px !important;
    height: 25px;
    border: 1px solid #e6e6e6;
    border-radius: 3px;
    color: #666;
    text-indent: 10px;
  }

  .ly-tree__loading {
    color: #666;
    font-weight: bold;
  }

  .ly-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    // justify-content: space-between;
    justify-content: flex-start;
    font-size: 14px;
    padding-right: 8px;
    width: 100%;
  }

  .ly-tree-node > div > span:last-child {
    display: inline-block;
    // width: 110px;
    text-align: left;
  }

  .ly-tree-node > span:last-child {
    display: inline-block;
    // width: 110px;
    text-align: left;
  }

  .el-tree-node .el-tree-node__content {
    height: 30px;

    // &:hover .ly-visible {
    //   visibility: visible;
    //   background-color: #fff;
    //   z-index: 10;
    //   // position: absolute;
    //   // left: 10px;
    //   // top: 30px;
    // }
    // &:hover .menu {
    //   // display: none !important;
    // }
    .rows {
      background-image: url(../../assets/adminImg/menu_down.png);
      background-position: center center;
      background-repeat: no-repeat;
      height: 24px;
      width: 24px;
      line-height: 24px;
      margin-left: 5px;
      display: block;
    }
    // .rows:hover {
    //   display: none !important;
    // }
    // .rows:hover .menu {
    //   // display: block !important;
    // }
    &::before,
    &::after {
      content: "";
      position: absolute;
      right: auto;
    }
  }

  .el-tree .el-tree-node {
    position: relative;
  }
}
.userPic {
  font-size: 14px;
  padding-left: 26px;
  color: #606266;
  user-select: none;
  cursor: pointer;
  height: 30px;
  line-height: 30px;
  &:hover {
    background-color: #f3f3f3 !important;
  }
}
</style>
<style lang="scss" scoped>
/deep/ .el-card {
  background-color: #e6e9f0;
}
</style>
