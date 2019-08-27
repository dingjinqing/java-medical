<template>
  <div class="addingBusClassDialog">
    <div class="addingBusClassDialogMain">
      <el-dialog
        title="添加商家分类"
        :visible.sync="dialogVisible"
        width="30%"
      >
        <div class="dialogMain">
          <ul
            v-for="(item,index) in newArr"
            :key="index"
            :class="index%2===0?'':'bgClass'"
          >
            <Tree :data="item" />
          </ul>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogVisible = false">取 消</el-button>
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
import { initGrandgetRequest } from '@/api/admin/brandManagement.js'
export default {
  components: {
    Tree: () => import('./tree.vue')
  },
  data () {
    return {
      dialogVisible: false,
      newArr: [],
      check: false,
      clickBandArr: [],
      imgUrl: [
        {
          img_open: this.$imageHost + '/image/admin/cate_jia.png',
          img_close: this.$imageHost + '/image/admin/cate_jian.png'
        }

      ],
      trueArr: []
    }
  },
  mounted () {
    this.$http.$on('userBrandDialog', res => {
      console.log(res, 'brand')
      this.dialogVisible = true

      // 初始化数据
      this.defaultData()
    })
    this.$http.$on('clickBrandNode', res => {
      let newArr = []
      console.log(this.clickBandArr.length)
      let flag = this.clickBandArr.filter((item, index) => {
        return item.sortId === res.sortId
      })
      console.log(flag)
      if (!flag.length) {
        this.clickBandArr.push(res)
      }
      this.clickBandArr.forEach((item, index) => {
        if (item.sortId === res.sortId) {
          item.checked = res.checked
        }
        if (item.checked) {
          newArr.push(item.sortId)
        }
      })
      // this.clickArrBus.push(res)
      this.trueArr = newArr
      console.log(this.clickBandArr)
    })
  },
  methods: {
    // 弹窗确认
    handleSure () {
      console.log(this.trueArr)
      this.$http.$emit('BusClassTrueArr', this.trueArr)
      this.dialogVisible = false
    },
    defaultData () {
      // 弹窗数据获取
      initGrandgetRequest().then((res) => {
        console.log(res.content)

        if (res.error === 0) {
          let goodsSorts = res.content.sysCates
          let buckets = {
            0: { children: [] }
          }

          for (var i = 0; i < goodsSorts.length; i++) {
            let item = goodsSorts[i]

            let selfNode = buckets[item.catId]

            if (selfNode === undefined) {
              buckets[item.catId] = item
              buckets[item.catId].children = []
              selfNode = buckets[item.catId]
            } else {
              selfNode.catId = item.catId
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
          this.newArr = buckets[0].children

          console.log(buckets[0].children)
        }
      })
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
    height: 500px;
    overflow-y: auto;
  }
  .dialogMain {
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
