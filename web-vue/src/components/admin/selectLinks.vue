<template>
  <div class="links">
    <!-- 弹窗 -->
    <el-dialog
      :title="$t('selectLinks.dialogTitle')"
      :visible.sync="dialogVisible"
      width="70%"
      :fullscreen='true'
      :modal-append-to-body="false"
    >
      <div class="body">
        <div class="left_box">
          <ul class="ul_out">
            <li
              @mouseover="enter_out(index)"
              @mouseleave="leave_out(index)"
              @click="level_one_click(index)"
              :class="item.flagindex==index?'bg_class':''"
              v-for="(item,index) in level_one_DataList"
              :key="index"
            >
              <span
                class="liSpan"
                :class="bottom_line_flagindex==index?'click_active':''"
              >{{item.title}}</span>
              <div
                class="three-circle"
                v-if="index==3||index==4?true:false"
              >
                <span></span>
                <span></span>
                <span></span>
              </div>
            </li>
          </ul>
          <ul
            class="hiddlen"
            v-if="threeTofour_flag_1"
            @mouseover="en_over(1)"
            @mouseleave="en_leave(1)"
          >
            <li
              @mouseover="enter_in_one(index)"
              @mouseleave="leave_in_one(index)"
              @click="level_two_click(index)"
              :class="item.flagindex==index?'bg_class':''"
              v-for="(item,index) in level_two_DataList_one"
              :key="index"
              class="level_two_li"
            >
              <span
                class="level_two_span"
                :class="bottom_level_line_one==index?'click_active':''"
              >{{item.title}}</span>

            </li>
          </ul>
          <ul
            class="hiddlen"
            v-if="threeTofour_flag_2"
            @mouseover="en_over(2)"
            @mouseleave="en_leave(2)"
          >
            <li
              @mouseover="enter_in_two(index)"
              @mouseleave="leave_in_two(index)"
              @click="level_two_click(index)"
              :class="item.flagindex==index?'bg_class':''"
              v-for="(item,index) in level_two_DataList_two"
              :key="index"
              class="level_two_li"
            >
              <span
                class="level_two_span"
                :class="bottom_level_line_two==index?'click_active':''"
              >{{item.title}}</span>

            </li>
          </ul>
        </div>
        <div
          class="right_box"
          style="overflow:auto"
        >
          <!-- <router-view></router-view> -->
          <!-- 动态组价 -->
          <!-- 组件会在 currentComponent 改变时改变 -->
          <component v-bind:is="currentComponent"></component>
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="dialogVisible = false"
        >{{$t('selectLinks.cancel')}}</el-button>
        <el-button
          type="primary"
          size="small"
          @click="handleSure()"
        >{{$t('selectLinks.sure')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { mapActions, mapGetters } from 'vuex'
// 引入组件
import commonLinks from './selectLinksComponents/commonLinks'
import commodityLinks from './selectLinksComponents/commodityLinks'
import formPage from './selectLinksComponents/formPage'
import customPage from './selectLinksComponents/customPage'
import pageJump from './selectLinksComponents/pageJump'
import smallProgramJump from './selectLinksComponents/smallProgramJump'
import groupDrawing from './selectLinksComponents/groupDrawing'
import classificationOfCommodities from './selectLinksComponents/classificationOfCommodities'
export default {
  components: {
    commonLinks,
    commodityLinks,
    customPage,
    pageJump,
    smallProgramJump,
    formPage,
    groupDrawing,
    classificationOfCommodities
  },
  props: {
    tuneUpSelectLink: Boolean
  },
  data () {
    return {
      currentComponent: commonLinks,
      dialogVisible: false,
      click_active: 'click_active',
      bg_class: 'bg_class',
      level_one_DataList: [],
      level_two_DataList_one: [],
      level_two_DataList_two: [],
      bottom_line_flagindex: 0,
      bottom_in_line_flagindex: 0,
      bottom_level_line_one: null,
      bottom_level_line_two: null,
      level_two_show_flag: false,
      threeTofour_flag_1: '',
      threeTofour_flag_2: '',
      suerPath: ''
    }
  },
  computed: {
    ...mapGetters(['choisePath']),
    choisePath_ () {
      return this.choisePath
    }
  },
  watch: {
    choisePath_ (newData, oldData) {
      this.suerPath = newData
    },
    tuneUpSelectLink () {
      this.dialogVisible = true
    },
    lang () {
      this.level_one_DataList = this.$t('selectLinks.level_one_DataList')
      this.level_two_DataList_one = this.$t('selectLinks.level_two_DataList_one')
      this.level_two_DataList_two = this.$t('selectLinks.level_two_DataList_two')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    ...mapActions(['changeSelectlink', 'changeSelectLinkLeft', 'afferentPathToPage']),
    // 确定按钮
    handleSure () {
      this.afferentPathToPage(this.suerPath)
      this.dialogVisible = false
      console.log(this.suerPath)
      // 把选中的链接回传
      this.$emit('selectLinkPath', this.suerPath)
      // 回传路径加页面名称
    },
    handleClose (done) {
      this.dialogVisible = false
    },
    // 一级列表移入
    enter_out (index) {
      this.level_one_DataList[index].flagindex = index
      if (index === 3) {
        this.threeTofour_flag_1 = true
      } else {
        this.threeTofour_flag_1 = false
      }
      if (index === 4) {
        this.threeTofour_flag_2 = true
      } else {
        this.threeTofour_flag_2 = false
      }
    },
    // 二级列表移入
    enter_in_one (index) {
      this.level_two_DataList_one[index].flagindex = index
    },
    enter_in_two (index) {
      this.level_two_DataList_two[index].flagindex = index
    },
    en_over (index) {
      if (index === 1) {
        this.threeTofour_flag_1 = true
      } else {
        this.threeTofour_flag_2 = true
      }
    },
    // 一级列表移出
    leave_out (index) {
      this.level_one_DataList[index].flagindex = null
    },
    // 二级列表移出
    leave_in_one (index) {
      this.level_two_DataList_one[index].flagindex = null
      //   this.threeTofour_flag_1 = false
    },
    en_leave (index) {
      if (index === 1) {
        this.threeTofour_flag_1 = false
      } else {
        this.threeTofour_flag_2 = false
      }
    },
    leave_in_two (index) {
      this.level_two_DataList_two[index].flagindex = null
      //   this.threeTofour_flag_2 = false
    },
    ul_one_leave () {
      this.threeTofour_flag_1 = false
    },
    ul_otwo_leave () {
      this.threeTofour_flag_2 = false
    },
    // 一级列表点击
    level_one_click (index) {
      console.log(12321321)
      if (index === 3 || index === 4) return
      this.bottom_line_flagindex = index
      this.bottom_level_line_one = null
      this.bottom_level_line_two = null
      switch (index) {
        case 0:
          this.currentComponent = commonLinks
          break
        case 1:
          this.currentComponent = commodityLinks
          break
        case 2:
          this.currentComponent = customPage

          break
        case 5:
          this.currentComponent = pageJump

          break
        case 6:
          this.currentComponent = smallProgramJump

          break
        case 7:
          this.currentComponent = formPage

          this.changeSelectLinkLeft(6)

          break
        case 8:
          this.currentComponent = formPage

          this.changeSelectLinkLeft(7)

          break
      }
    },
    // 二级列表点击
    level_two_click (index) {
      if (this.threeTofour_flag_1) {
        let obj = {
          levelIndex: 1,
          navText: this.level_two_DataList_one[index].title,
          index: index
        }
        this.changeSelectlink(obj)
        this.currentComponent = groupDrawing
        // this.$router.push({
        //   name: 'groupDrawing'
        // })

        this.bottom_line_flagindex = 3
        this.bottom_level_line_two = null
        this.bottom_level_line_one = index
      } else {
        let obj = {
          levelIndex: 2,
          index: index
        }
        this.changeSelectlink(obj)
        this.currentComponent = classificationOfCommodities

        // this.$router.push({
        //   name: 'classificationOfCommodities'
        // })
        this.bottom_line_flagindex = 4
        this.bottom_level_line_one = null
        this.bottom_level_line_two = index
      }
    }
  }
}

</script>
<style scoped>
.body {
  width: 100%;
  height: 100%;
  overflow: hidden;
  display: flex;
}
.left_box {
  width: 110px;
  border: 1px solid #eee;
  height: 657px;
  position: relative;
}
.ul_out li {
  line-height: 35px;
  text-align: center;
  position: relative;
  color: #000;
  display: flex;
  justify-content: center;
  align-items: center;
  /* height: 35px; */
  cursor: pointer;
}
.ul_out li .liSpan {
  display: block;
  padding: 10px 12px;
  display: flex;
  align-items: center;
  line-height: 16px;
  /* width: 56px; */
  /* height: 26px; */
}
.click_active {
  color: #5a8bff;
  border-bottom: 2px solid #5a8bff;
}
.bg_class {
  background-color: #e4ecff;
}
.three-circle {
  position: absolute;
  right: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  top: 6px;
}
.three-circle > span {
  display: block;
  width: 4px;
  height: 4px !important;
  border-radius: 4px;
  background: #5a8bff;
  margin-top: 3px;
}
.hiddlen {
  position: absolute;
  width: 110px;
  height: 674px;
  top: -1px;
  left: 109px;
  border-top: 1px solid #eee;
  border-right: 1px solid #eee;
  border-bottom: 1px solid #eee !important;
  z-index: 100;
  background-color: #fff;
}
.level_two_li {
  /* line-height: 35px; */
  text-align: center;
  position: relative;
  color: #000;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  /* height: 35px; */
  cursor: pointer;
  padding: 10px 12px;
}
.right_box {
  flex: 1;
  width: 100%;
  height: 657px;
  overflow-y: auto;
  padding: 8px;
  border-bottom: 1px solid #eee;
  padding-bottom: 80px;
}
</style>
<style>
.links .top_left .el-input {
  width: auto !important;
}
.links .el-dialog.is-fullscreen {
  width: 52% !important;
  overflow-y: hidden !important;
}
.links .el-dialog__footer {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 100%;
  background: #fff;
}
.links .el-dialog__header {
  text-align: center;
  padding-top: 10px !important;
}
.links .el-dialog__title {
  font-size: 14px !important;
}
.links .el-dialog__body {
  overflow: hidden;
  padding-top: 0 !important;
}

.links .el-dialog__header {
  background: #f3f3f3;
}

.links .el-dialog__body {
  padding-left: 0 !important;
}
.links .el-dialog__headerbtn {
  top: 14px !important;
}
.links .el-dialog__footer {
  padding: 10px 20px 10px !important;
}
</style>
