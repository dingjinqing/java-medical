<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>商品模块</h2>
      <!-- <div class="title">
        <span>模块标题:</span>
        <div>
          <el-radio
            v-model="titleRadio"
            label="1"
          >不设置</el-radio>
          <el-radio
            v-model="titleRadio"
            label="2"
          >文字标题</el-radio>
          <el-radio
            v-model="titleRadio"
            label="3"
          >图片标题</el-radio>
        </div>
      </div> -->
      <div class="listStyle">
        <div class="title">列表样式</div>
        <div class="content">
          <div
            class="typeContainer"
            v-for="(item,index) in listTypeData"
            :key="index"
            :style="item.isChecked?'border: 1px solid #5c81f4;':''"
            @click="handleToClickType(index)"
          >
            <div class="type">
              <div class="typeTop">
                <span class="odd_left"></span>
                <div class="odd_right">
                  <span></span>
                  <span></span>
                  <span></span>
                  <span></span>
                </div>
              </div>
              <p>{{item.typeName}}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      titleRadio: '1',
      listTypeData: [
        {
          typeName: '单列',
          isChecked: true
        }
        // {
        //   typeName: '双列',
        //   isChecked: false
        // },
        // {
        //   typeName: '三列',
        //   isChecked: false
        // },
        // {
        //   typeName: '横向滑动',
        //   isChecked: false
        // },
        // {
        //   typeName: '大图',
        //   isChecked: false
        // }
      ]
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
      },
      immediate: true
    }
  },
  methods: {
    // 点击列表样式
    handleToClickType (index) {
      this.listTypeData.forEach(item => {
        item.isChecked = false
      })
      this.listTypeData[index].isChecked = true
    }
    // this.$emit('handleToBackData', obj) 数据回传调用
  }
}
</script>
<style lang="scss" scoped>
.rightCommodity {
  .rightCommodityMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    .title {
      span {
        display: inline-block;
        margin-right: 10px;
      }
      display: flex;
      /deep/ .el-radio {
        margin-right: 10px;
      }
    }
    .listStyle {
      margin-top: 10px;
      .title {
        margin-bottom: 10px;
      }
      .content {
        display: flex;
        flex-wrap: wrap;
        background-color: #fff;
        padding: 8px 10px 0;
        border: 1px solid #e5e5e5;
        cursor: pointer;
        .typeContainer {
          padding: 8px 10px;
          width: 30%;
          margin-right: 3%;
          height: 96px;
          border: 1px solid #e5e5e5;
          margin-bottom: 10px;
          .type {
            p {
              margin-top: 10px;
              text-align: center;
            }
            .typeTop {
              padding: 5px 5px 0 5px;
              display: flex;
              justify-content: space-between;
              span {
                background: #e9f8fd;
              }
              .odd_left {
                width: 50%;
                height: 50px;
              }
              .odd_right {
                height: 50px;
                width: 44%;
                span {
                  display: block;
                  &:nth-of-type(1) {
                    height: 12px;
                  }
                  &:nth-of-type(2) {
                    width: 60%;
                    height: 12px;
                    margin-top: 8px;
                  }
                  &:nth-of-type(3) {
                    height: 12px;
                    width: 60%;
                    margin-top: 6px;
                    display: inline-block;
                  }
                  &:nth-of-type(4) {
                    height: 12px;
                    width: 20%;
                    height: 12px;
                    display: inline-block;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
