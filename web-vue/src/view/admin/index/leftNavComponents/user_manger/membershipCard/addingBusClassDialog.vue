<template>
  <div class="addingBusClassDialog">
    <div class="addingBusClassDialogMain">
      <el-dialog
        title="添加商家分类"
        :visible.sync="dialogVisible"
        width="30%"
      >
        <div class="dialogMain">
          <ul>
            <li
              class="sort_li"
              v-for="(item,index) in newArr"
              :key="index"
            >
              <div class="first_sort_cate">
                <img
                  style="cursor:pointer"
                  @click="handleToLevel_one(index)"
                  :src="clickArr[index]?imgUrl[0].img_close:imgUrl[0].img_open"
                >
                <div class="check_div">
                  <el-checkbox v-model="check"></el-checkbox>
                  <span>{{item.sortName}}</span>
                </div>
              </div>
              <div class="sort_list">
                <div
                  class="sort_div"
                  v-for="(itemC,indexC) in item.children"
                  :key="indexC"
                >
                  <el-checkbox v-model="check"></el-checkbox>
                  <span>{{itemC.sortName}}</span>
                </div>

              </div>
            </li>
          </ul>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="dialogVisible = false"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>
<script>
import { initGrandgetRequest } from '@/api/admin/brandManagement.js'
export default {
  data () {
    return {
      dialogVisible: false,
      newArr: [],
      check: false,
      clickArr: [],
      imgUrl: [
        {
          img_open: this.$imageHost + '/image/admin/cate_jia.png',
          img_close: this.$imageHost + '/image/admin/cate_jian.png'
        }

      ]
    }
  },
  mounted () {
    this.$http.$on('addingBusClassDialog', res => {
      this.dialogVisible = true
      // 初始化数据
      this.defaultData()
    })
  },
  methods: {
    defaultData () {
      // 弹窗数据获取
      initGrandgetRequest().then((res) => {
        console.log(res.content.goodsSorts)
        if (res.error === 0) {
          let goodsSorts = res.content.goodsSorts
          let buckets = {
            0: { children: [] }
          }

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
          buckets[0].children.forEach((item, index) => {
            this.clickArr[index] = false
          })
          console.log(this.clickArr[0])
          this.newArr = buckets[0].children

          console.log(buckets[0].children)
        }
      })
    },
    // 节点点击
    handleToLevel_one (index) {
      console.log(index)
      this.clickArr[index] = !this.clickArr[index]
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
