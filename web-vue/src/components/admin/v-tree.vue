<template>
  <div class="tree_container">
    <div v-show="showContextMenu">
      <ul id="menu_video">
        <span class="menu">
          <el-button
            size="mini"
            type="text"
            @click="onUpdate()"
            v-if="showBtn.edit"
          >
            {{$t("vTree.edit")}}
          </el-button>

          <el-button
            size="mini"
            type="text"
            @click="onAppend()"
            v-if="showBtn.add"
          >
            {{$t("vTree.add")}}
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="onRemove()"
            v-if="showBtn.del"
          >
             {{$t("vTree.del")}}
          </el-button>
        </span>
      </ul>
    </div>
    <el-card>
      <div class="ly-tree-container">
        <el-tree
          ref="tree"
          :data="treeData"
          :props="defaultProps"
          default-expand-all
          :expand-on-click-node="false"
          @node-click="onNodeClick"
          @node-contextmenu="onNodeRight"
          node-key="id"
          :highlight-current="true"
          :current-node-key="selectedId"
        >
        </el-tree>
      </div>
    </el-card>
  </div>
</template>

<script>

export default {
  props: {
    /**
     * must has name and child
     * [{
     *  name: '',
     *  child:[]
     * }]
     */
    treeData: Array,
    selectedId: 0
  },
  data () {
    return {
      defaultProps: {
        children: 'child',
        label: 'name'
      },
      showBtn: {
        add: false,
        del: false,
        edit: false
      },
      showDelConfirmDialog: false,
      contextObject: null,
      showContextMenu: false
    }
  },
  methods: {
    // 节点右键点击
    onNodeRight (event, object, value, element) {
      this.showContextMenu = true
      this.contextObject = object
      let menu = document.querySelector('#menu_video')

      menu.style.left = event.clientX + 2 + 'px'
      menu.style.top = event.clientY + 'px'
      this.showBtn = {
        add: value.level < 6,
        del: value.level > 1,
        edit: value.level > 1
      }
      document.addEventListener('click', this.hideMenu)
      this.$emit('node-right-click', this.contextObject)
    },
    hideMenu () {
      this.showContextMenu = false
      document.removeEventListener('click', this.hideMenu)
    },

    onNodeClick (data, node, mynode) {
      this.$emit('node-click', data)
    },

    onAppend () {
      this.showContextMenu = false
      let _this = this
      this.$prompt(this.$t('vTree.pleaseInputName'), this.$t('vTree.tip'), {
        confirmButtonText: this.$t('vTree.ok'),
        cancelButtonText: this.$t('vTree.cancel')
      }).then(({ value }) => {
        _this.$emit('node-append', _this.contextObject, value)
      }).catch(() => {})
    },
    setCurrentKey (key) {
      this.$refs.tree.setCurrentKey(key)
    },

    onRemove () {
      this.showContextMenu = false
      let _this = this
      this.$confirm(this.$t('vTree.delVideoTip'), this.$t('vTree.tip'), {
        confirmButtonText: this.$t('vTree.ok'),
        cancelButtonText: this.$t('vTree.cancel'),
        type: 'warning'
      }).then(() => {
        _this.$emit('node-remove', _this.contextObject)
      }).catch(() => {})
    },

    onUpdate () {
      this.showContextMenu = false
      let _this = this
      this.$prompt(this.$t('vTree.pleaseInputName'), this.$t('vTree.tip'), {
        confirmButtonText: this.$t('vTree.ok'),
        cancelButtonText: this.$t('vTree.cancel')
      }).then(({ value }) => {
        _this.$emit('node-update', _this.contextObject, value)
      }).catch(() => {})
    }
  }
}
</script>
<style scoped>
.tree_container {
  width: 160px;
}
#menu_video {
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
}
.ly-tree-container {
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
    visibility: hidden;
    z-index: 10;
    background-color: #f3f3f3 !important;
  }

  .ly-edit__text {
    width: 25%;
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
    justify-content: flex-start;
    font-size: 14px;
    padding-right: 8px;
    width: 100%;
  }

  .ly-tree-node > div > span:last-child {
    display: inline-block;
    text-align: left;
  }

  .ly-tree-node > span:last-child {
    display: inline-block;
    text-align: left;
  }

  .el-tree-node .el-tree-node__content {
    height: 30px;
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
</style>
