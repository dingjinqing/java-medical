<template>
  <div class="setHotAreaDialog">
    <div
      class="model"
      v-if="modelFlag"
    ></div>
    <el-dialog
      title="编辑图片热区"
      :visible.sync="hotDialogVisible"
      width="30%"
      :modal="false"
    >
      <div class="hotMain">
        <div class="hotMainTop">
          <div
            v-for="(item,index) in hotMainTopData"
            :key="index"
          >
            <span>{{item.num}}</span>
            {{item.text}}
          </div>
        </div>
        <div class="hotMainMiddle">
          <div :style="'width:533px;height:533px;background-size:533px 533px;'+`background-image:url(${imgUrl})`">
            <VueDragResize
              :isActive="true"
              :w="item.w"
              :h="item.h"
              v-on:resizing="resize($event,index)"
              v-on:dragging="resize($event,index)"
              :x="item.x"
              :y="item.y"
              :parentLimitation="true"
              v-for="(item,index) in hotAreaData"
              :key="index"
              :z="item.z"
            >
              <div class="hotArea">
                <div
                  class="top"
                  @click="handleToCallLink()"
                >
                  {{item.link_text?item.link_text:'设置关联链接'}}
                </div>
                <div class="footer">
                  <div @click="handleToCallLink()">
                    添加链接
                  </div>

                </div>
                <div
                  class="del"
                  @click="handleToDel(index)"
                >
                  <img :src="$imageHost+'/image/admin/hot-close.png'">
                </div>
              </div>

            </VueDragResize>
          </div>
        </div>
      </div>
      <SelectLinks
        :tuneUpSelectLink="tuneUpSelectLink"
        @handleToGetData="handleToGetData"
      />
      <span
        slot="footer"
        class="dialog-footer"
      >
        <div class="choose_el_area">已添加热区数量：<span>{{hotAreaData.length}}</span> 个</div>
        <div>
          <el-button @click="handleToAddHot()">添加热区</el-button>
          <el-button
            type="primary"
            @click="handleToSave()"
          >保存</el-button>
        </div>

      </span>
    </el-dialog>
  </div>
</template>
<script>
import Vue from 'vue'
import VueDragResize from 'vue-drag-resize'
Vue.component('vue-drag-resize', VueDragResize)
export default {
  components: {
    VueDragResize,
    SelectLinks: () => import('@/components/admin/selectLinks') // 选择链接弹窗
  },
  props: {
    hotDialogVisible: { // 弹窗调起
      type: Boolean,
      default: false
    },
    imgUrl: String // 图片路径
  },
  watch: {
    hotDialogVisible (newVal) {
      if (!newVal) {
        this.modelFlag = false
        this.$emit('update:hotDialogVisible', false)
      } else {
        this.modelFlag = true
      }
    }
  },
  data () {
    return {
      hotMainTopData: [
        { num: 1, text: '添加热区' },
        { num: 2, text: '调整热区大小和位置' },
        { num: 3, text: '设置关联链接' },
        { num: 4, text: '保存生效' }
      ],
      hotAreaData: [
        {
          hot_idx: 1,
          x: 0,
          y: 0,
          w: 115,
          h: 115,
          link_url: '',
          link_text: '',
          z: 1
        }
      ],
      tuneUpSelectLink: false,
      modelFlag: false,
      nowClickIndex: null
    }
  },
  methods: {
    // 添加热区
    handleToAddHot () {
      let obj = {
        hot_idx: 1,
        x: 0,
        y: 0,
        w: 115,
        h: 115,
        link_url: '',
        link_text: '',
        z: 1
      }
      this.hotAreaData.push(obj)
    },
    // 保存
    handleToSave () {
      let flag = true
      this.hotAreaData.forEach((item, index) => {
        if (!item.link_url) {
          item.z = 99
          flag = false
        } else {
          item.z = 1
        }
      })
      if (!flag) return
      this.$emit('handleToGetHotData', this.hotAreaData)
      this.$emit('update:hotDialogVisible', false)
    },
    resize (newRect, index) {
      console.log(newRect, index)
      this.nowClickIndex = index
      this.hotAreaData[index].w = newRect.width
      this.hotAreaData[index].h = newRect.height
      this.hotAreaData[index].y = newRect.top
      this.hotAreaData[index].x = newRect.left
    },
    // 选择链接调起
    handleToCallLink () {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择链接弹窗数据回传
    handleToGetData ({ title, path }) {
      this.hotAreaData[this.nowClickIndex].link_text = title
      this.hotAreaData[this.nowClickIndex].link_url = path
    },
    // 删除热区
    handleToDel (index) {
      this.hotAreaData.splice(index, 1)
    }
  }
}
</script>
<style lang="scss" scoped>
.model {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  opacity: 0.5;
  background: #000;
}
.hotMain {
  .hotMainTop {
    color: #666;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 10px;
    span {
      width: 20px;
      display: inline-block;
      border-radius: 20px;
      border: 1px solid #666;
      text-align: center;
      margin-right: 10px;
    }
  }
  .hotMainMiddle {
    position: relative;
    margin-top: 10px;
    height: 300px;
    overflow-x: hidden;
    overflow-y: auto;
    .hotArea {
      width: 100%;
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      position: relative;
      background: rgba(51, 136, 255, 0.5);
      .top {
        text-align: center;
        margin-top: 20px;
        cursor: pointer;
        color: #fff;
        font-size: 14px;
      }
      .footer {
        display: flex;
        div {
          color: #fff;
          padding: 0px 2px;
          background: rgba(100, 136, 125, 0.5);
          font-size: 12px;
          cursor: pointer;
        }
      }
      .del {
        position: absolute;
        top: -14px;
        right: -14px;
        border-radius: 10px;
        cursor: pointer;
        opacity: 1;
      }
    }
  }
}
.dialog-footer {
  width: 100%;
  display: inline-block;
  display: flex;
  justify-content: space-between;
  .choose_el_area {
    color: #333;
    height: 40px;
    line-height: 40px;
  }
}
</style>
