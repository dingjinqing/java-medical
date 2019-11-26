<template>
  <div class="addingBusClassDialog">
    <div class="addingBusClassDialogMain">
      <el-dialog
        title="添加商家分类"
        :visible.sync="dialogVisible"
        width="30%"
        :modal-append-to-body='false'
      >
        <div
          class="dialogMain"
          v-loading="loading"
        >
          <div
            class="dialogMainDiv"
            v-show="flag === 2"
          >
            <el-tree
              :data="newArr"
              show-checkbox
              default-expand-all
              empty-text=''
              node-key="catId"
              ref="cardTree"
              highlight-current
              :props="defaultArrTwo"
              :default-checked-keys="defaultArr"
            >
            </el-tree>

          </div>
          <div
            class="dialogMainDiv"
            v-show="flag !== 2"
          >
            <el-tree
              :data="newArr"
              show-checkbox
              default-expand-all
              node-key="sortId"
              empty-text=''
              ref="sortTree"
              highlight-current
              :props="defaultProps"
              :default-checked-keys="defaultArr"
            >
            </el-tree>
          </div>

        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="$emit('update:dialogVisible', false)">取 消</el-button>
          <el-button
            type="primary"
            @click="handleSure()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>
<script>
import { cateListApi } from '@/api/admin/selectLinksApi/selectLinksApi'
export default {
  props: {
    dialogVisible: { // 弹窗调起flag
      type: Boolean,
      default: () => false
    },
    classFlag: Number,
    backDataArr: Array
  },
  data () {
    return {
      newArr: [],
      check: false,
      imgUrl: [
        {
          img_open: this.$imageHost + '/image/admin/cate_jia.png',
          img_close: this.$imageHost + '/image/admin/cate_jian.png'
        }

      ],
      clickArrBus: [],
      trueArr: [],
      defaultProps: {
        children: 'children',
        label: 'sortName'
      },
      defaultArrTwo: {
        children: 'children',
        label: 'catName'
      },
      defaultArr: [],
      sortId: '',
      flag: null,
      loading: true
    }
  },
  watch: {
    dialogVisible (newData) {
      console.log(newData, this.backDataArr)
      console.log()
      if (newData) {
        this.dialogVisible = true
        this.loading = true
        this.newArr = []
        this.defaultArr = []
        // 初始化数据
        this.defaultData(this.backDataArr, this.classFlag)
      } else {
        this.$emit('update:dialogVisible', false)
      }
    }
  },
  mounted () {
    // this.$http.$on('addingBusClassDialog', (res, flag) => {
    //   console.log(flag)
    //   this.dialogVisible = true
    //   this.loading = true
    //   this.newArr = []
    //   this.defaultArr = []
    //   // 初始化数据
    //   this.defaultData(res, flag)
    // })
    this.$http.$on('clickBusNode', res => {
      console.log(res)
      let newArr = []
      console.log(this.clickArrBus.length)
      let flag = this.clickArrBus.filter((item, index) => {
        return item.sortId === res.sortId
      })
      console.log(flag)
      if (!flag.length) {
        this.clickArrBus.push(res)
      }
      this.clickArrBus.forEach((item, index) => {
        if (item.sortId === res.sortId) {
          item.checked = res.checked
        }
        if (item.checked) {
          newArr.push(item.sortId)
        }
      })
      // this.clickArrBus.push(res)
      this.trueArr = newArr
      console.log(this.clickArrBus)
    })
  },
  methods: {
    // 弹窗确认
    handleSure () {
      let arr = ''
      let detailData = ''
      if (this.flag === 2) {
        arr = this.$refs.cardTree.getCheckedKeys()
      } else {
        arr = this.$refs.sortTree.getCheckedKeys()
      }
      if (this.flag === 2) {
        detailData = this.$refs.cardTree.getCheckedNodes()
      } else {
        detailData = this.$refs.sortTree.getCheckedNodes()
      }
      console.log(this.$refs.sortTree.getCheckedNodes())
      this.$emit('BusClassTrueDetailData', detailData) // 返回选中节点详细数据
      this.$emit('BusClassTrueArr', arr) // 返回选中节点id数据
      this.$emit('update:dialogVisible', false)
      this.dialogVisible = false
    },
    defaultData (backData, flag) {
      console.log(flag)
      this.flag = flag
      let params = {
        needGoodsSort: true,
        needSysCategory: true
      }
      // 弹窗数据获取
      cateListApi(params).then((res) => {
        console.log(res)

        if (res.error === 0) {
          let data = ''
          if (flag === 2) {
            data = res.content.goodsCategories
          } else {
            data = res.content.goodsSorts
          }
          let obj = {
            data,
            backData,
            flag
          }
          this.handleToData(obj)
        }
      })
    },
    // 数据处理
    handleToData (obj) {
      let goodsSorts = ''
      goodsSorts = obj.data
      let buckets = {
        0: { children: [] }
      }
      let handleData = ''
      console.log(obj)
      if (obj.flag === 2) {
        handleData = this.handletoPlatform(goodsSorts, buckets)
      } else {
        handleData = this.handletocommodity(goodsSorts, buckets)
      }
      console.log(handleData)
      this.loading = false
      this.newArr = handleData
      console.log(this.newArr, obj.backData)

      this.defaultArr = obj.backData
    },
    handletocommodity (goodsSorts, buckets) {
      for (var i = 0; i < goodsSorts.length; i++) {
        let item = goodsSorts[i]

        let selfNode = buckets[item.sortId]

        if (selfNode === undefined) {
          buckets[item.sortId] = item
          buckets[item.sortId].children = []
          selfNode = buckets[item.sortId]
        } else {
          selfNode.sortId = item.sortId
          selfNode.sortName = item.sortName
          selfNode.parentId = item.parentId
          selfNode.level = item.level
          selfNode.hasChild = item.hasChild
        }

        let parentNode = buckets[selfNode.parentId]

        if (parentNode !== undefined) {
          parentNode.children.push(selfNode)
        } else {
          buckets[selfNode.parentId] = { children: [] }
          parentNode = buckets[selfNode.parentId]
          parentNode.children.push(selfNode)
        }
      }
      return buckets[0].children
    },
    handletoPlatform (goodsSorts, buckets) {
      for (var i = 0; i < goodsSorts.length; i++) {
        let item = goodsSorts[i]

        let selfNode = buckets[item.catId]

        if (selfNode === undefined) {
          buckets[item.catId] = item
          buckets[item.catId].children = []
          selfNode = buckets[item.catId]
        } else {
          selfNode.catId = item.catId
          selfNode.catName = item.catName
          selfNode.parentId = item.parentId
          selfNode.level = item.level
          selfNode.hasChild = item.hasChild
        }

        let parentNode = buckets[selfNode.parentId]

        if (parentNode !== undefined) {
          parentNode.children.push(selfNode)
        } else {
          buckets[selfNode.parentId] = { children: [] }
          parentNode = buckets[selfNode.parentId]
          parentNode.children.push(selfNode)
        }
      }
      return buckets[0].children
    }

  }
}
</script>
<style lang="scss" scoped>
.addingBusClassDialog {
  /deep/ .el-dialog__header {
    text-align: center;
    background: #f3f3f3;
  }
  /deep/ .el-dialog__body {
    padding: 20px 20px;
  }
  .dialogMain {
    .dialogMainDiv {
      height: 400px;
      overflow-y: auto;
    }
    .bgClass {
      background: #f3f3f3;
    }
    .sort_li {
      padding: 10px;
      .first_sort_cate {
        display: flex;
        align-items: center;
        img {
          width: 8px;
          height: 8px;
          display: inline-block;
          margin-right: 10px;
        }
        .check_div {
          /deep/ .el-checkbox {
            margin-right: 5px;
          }
        }
      }
      .sort_list {
        margin-top: 5px;
        display: flex;
        padding-left: 50px;
        /deep/ .el-checkbox {
          margin-right: 5px;
        }
        .sort_div {
          margin-right: 30px;
        }
      }
    }
  }
}
</style>
