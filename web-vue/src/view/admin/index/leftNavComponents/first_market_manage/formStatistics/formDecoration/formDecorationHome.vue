<template>
  <div class="formDecorationHome">
    <div class="main">
      <div class="decLeft">
        <div class="decLeftDrag">
          <div class="title">
            表单元素
          </div>
          <div class="dragContainer">
            <div
              class="dragLi baseStyle"
              v-for="item in $t('formDecorationHome.formElements')"
              :key="item.id"
              :dataId="item.id"
              @click="handleToClickLeftModule(item.id,item.flag)"
            >
              <img :src="item.imgUrl">
              <span>{{item.text}}</span>
            </div>
          </div>
        </div>
        <div class="decLeftDrag">
          <div class="title">
            图文类
          </div>
          <div class="dragContainer">
            <div
              class="dragLi baseStyle"
              v-for="item in $t('formDecorationHome.graphicCategory')"
              :key="item.id"
              :dataId="item.id"
            >
              <img :src="item.imgUrl">
              <span>{{item.text}}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="decMiddle">
        <div
          class="top"
          :style="`background: url(${$imageHost}/image/admin/shop_beautify/phone_tops.png) no-repeat;`"
        >
        </div>
        <!--中间拖拽接收区域-->
        <div class="drag_area">
          <draggable
            class="list-group"
            :style="showModulesList.length?'padding-bottom:10px':'padding-bottom:127px'"
            element="div"
            v-model="showModulesList"
            :options="dragOptions"
            @start="handleToStart"
            @end="handleToEnd"
          >
            <div
              v-for="(item,index) in showModulesList"
              :key='index'
            >
              <!--模块-->
              <div @click.prevent="handleToClickModule(index)">
                <components
                  :is='middleModulesList[item]'
                  :flag="index"
                  :middleHereFlag="middleHereFlag"
                  :nowRightShowIndex="nowRightShowIndex"
                  @handleToClickIcon="handleToClickIcon"
                ></components>
              </div>

            </div>
          </draggable>
        </div>
      </div>
      <div class="decRight">
        decRight
      </div>
    </div>
    <!--中间模块是否删除弹窗-->
    <el-dialog
      :title="$t('pageSetUp.remind')"
      :visible.sync="deleteVisible"
      width="30%"
    >
      <div style="display:flex;justify-content:center"><span>{{$t('pageSetUp.sureToDelete')}}</span></div>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="deleteVisible = false"
        >{{$t('pageSetUp.cancel')}}</el-button>
        <el-button
          size="small"
          type="primary"
          @click="handleToSureDelete(deleteFlag)"
        >{{$t('pageSetUp.determine')}}</el-button>
      </span>
    </el-dialog>
    <!--保存-->
    <div class="footer">
      <div>
        <el-button
          type="primary"
          size="small"
        >保存</el-button>
        <el-button size="small">保存并发布</el-button>
        <span>发布后不可更改</span>
      </div>
    </div>
  </div>
</template>
<script>
import draggable from 'vuedraggable'
import $ from 'jquery'
require('webpack-jquery-ui')
require('webpack-jquery-ui/css')
export default {
  components: {
    draggable,
    // 表单元素模块池
    Name: () => import('./decorationModules/name'), // 姓名模块
    CellPhoneNumber: () => import('./decorationModules/cellPhoneNumber'), // 手机号模块
    ProvinceAndCity: () => import('./decorationModules/provinceAndCity') // 省市区模块
  },
  data () {
    return {
      middleHereFlag: false, // 中间拖动滑过模块出现的空白占位控制变量
      nowRightShowIndex: null, // 现在右侧显示的模块的id
      middleModulesList: ['Name', 'CellPhoneNumber', 'ProvinceAndCity'], // 中间显示模块名称池
      showModulesList: [], // 中间显示模块id数组
      insertModulesId: -1, // 左侧模块将要插入位置
      isAddBottom: false, // 是否添加到底部flag
      MoveWhiteFlag: false, // 是否移入的是底部空白部分
      deleteVisible: false, // 中间部分点击删除确认弹窗
      dragOptions: { // 中间拖拽配置项
        scroll: false,
        forceFallback: true,
        fallbackClass: 'active',
        sort: false,
        preventOnFilter: false,
        fallbackTolerance: '1'
      }
    }
  },
  watch: {
    showModulesList (newData) {
      console.log(this.nowRightShowIndex, newData)
      console.log(newData)
      this.$http.$emit('modulesClick', this.nowRightShowIndex)
    }
  },
  updated () {
    console.log(this.nowRightShowIndex)
    this.$http.$emit('modulesClick', this.nowRightShowIndex)
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.init_drag_event()
  },
  methods: {
    // 初始化拖拽事件
    init_drag_event () {
      let this_ = this
      // 左侧模块向中间区域拖拽
      $('.dragLi').draggable({
        appendTo: '.decLeft',
        helper: 'clone',
        start: function () {
          console.log('开始拖动')
        },
        drag: function (ev, ui) {
          this_.highlignt_row_item($(ui.helper).offset())
        },
        stop: function () {
          console.log('qqqqqqqq')
          // this_.isDragging = false
          if (this_.MoveWhiteFlag) return
          this_.$nextTick(() => {
            let hightMoudleIndex = this_.insertModulesId + 1
            console.log(this_.nowRightShowIndex, hightMoudleIndex)
            if (this_.nowRightShowIndex === -1) {
              this_.nowRightShowIndex = 0
            } else {
              this_.nowRightShowIndex = hightMoudleIndex
            }
          })
          console.log(this_.nowRightShowIndex)
          $('.modules').removeClass('placeholder')
        },
        zIndex: 10000 // 拖动位置在拖放区域上方
      })
      this.handleToAcceptDrag()
      // 接收当前高亮模块参数
      this.$http.$on('nowHightLightModules', res => {
        console.log(res)
        this.nowRightShowIndex = res
      })
    },
    // 左侧模块拖拽开始start处理函数
    highlignt_row_item (pos) {
      let this_ = this
      let p = $('.drag_area').offset()
      console.log(p, '--', pos, '--', $('.drag_area').width(), $('.drag_area').height())
      if (pos.left > p.left && pos.top > p.top &&
        pos.left < p.left + $('.drag_area').width() &&
        pos.top < p.top + $('.drag_area').height()) {
        console.log('ssssss')
        this_.isAddBottom = true
        $('.modules').each(function (idx, item) {
          p = $(this).offset()
          if (pos.left > p.left && pos.top > p.top &&
            pos.left < p.left + $(this).width() &&
            pos.top < p.top + $(this).height()
          ) {
            this_.insertModulesId = $(this)[0].__vue__.flag
            console.log($(this)[0].__vue__.flag)
            this_.isAddBottom = false
            $('.modules').removeClass('placeholder')
            $(this).addClass('placeholder')
          }
        })
      }
    },
    // 中间模块拖拽接收
    handleToAcceptDrag () {
      let this_ = this
      $('.drag_area').droppable({
        accept: '.dragLi',
        drop: function (event, ui) {
          $('.modules').removeClass('placeholder')
          console.log('test', ui)
          console.log(this_.insertModulesId, '--', this_.showModulesList)
          let insert = this_.insertModulesId + 1
          console.log(ui.draggable[0].getAttribute('dataId'))
          switch (Number(ui.draggable[0].getAttribute('dataId'))) {
            case 0:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 0)
              break
            case 1:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 1)
              break
            case 2:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 2)
              break
          }
          console.log(this_.showModulesList, this_.modulesData, insert)
        }
      })
    },
    // 左侧模块拖入中间区域后，中间区域数据处理函数
    handleToMiddleAcceptData (insertModulesId, showModulesList, insert, index) {
      // 判断id是否为-1，若是则插入尾部，否则插入指定位置
      console.log(insertModulesId, index)
      if (insertModulesId === -1 || this.isAddBottom) {
        console.log(index)
        this.MoveWhiteFlag = true
        this.handleToClickLeftModule(index, true)
      } else {
        this.MoveWhiteFlag = false
        console.log(this.showModulesList, this.nowRightShowIndex, insert, index)
        this.showModulesList.splice(insert, 0, index)
        this.$nextTick(() => {
          // this.modulesData.splice(insert, 0, this.handleToAddModules(index))
          if (this.nowRightShowIndex === insert) {
            // this.handleToModuleHight()
          }
        })
      }
    },
    //  点击左侧模块加到中间模块队列底部并高亮
    handleToClickLeftModule (id, flag) {
      console.log(id, flag)
      if (!flag) return
      this.showModulesList.push(id)
      console.log(id)
      let length = this.showModulesList.length - 1
      console.log(length)
      this.$nextTick(() => {
        this.nowRightShowIndex = length
      })
    },
    // 中部模块点击
    handleToClickModule (index) {
      console.log(index, this.modulesData)
      this.isClickModule = true
      this.$http.$emit('modulesClick', index)
      // this.handleToModuleHight()
    },
    // 中部icon点击处理事件
    handleToClickIcon ({ direction, flag }) {
      this.isClickIcon = true
      this.topAreaFlag = false
      switch (direction) {
        case 'up':
          let newArr1 = JSON.parse(JSON.stringify(this.showModulesList))
          // let modulesData1 = JSON.parse(JSON.stringify(this.modulesData))
          console.log(newArr1, '--', flag)
          this.oldIndex = flag
          // 顶部判断
          if (flag === 0) return
          let temp = newArr1[(flag - 1)]
          newArr1[(flag - 1)] = newArr1[flag]
          newArr1[flag] = temp
          // 改变边框
          let index = flag - 1
          console.log(newArr1)
          this.showModulesList = newArr1
          this.$nextTick(() => {
            // 保存数据顺序更改
            // let tempModules = JSON.parse(JSON.stringify(modulesData1[(flag - 1)]))
            // console.log(tempModules)
            // modulesData1[(flag - 1)] = modulesData1[flag]
            // modulesData1[flag] = tempModules
            // console.log(modulesData1)
            // this.modulesData = modulesData1
            if (this.nowRightShowIndex === index) {
              // this.handleToModuleHight()
            } else {
              this.nowRightShowIndex = index
            }
          })
          console.log(newArr1, this.modulesData)
          break
        case 'down':
          console.log(this.modulesData)
          let newArr2 = JSON.parse(JSON.stringify(this.showModulesList))
          // let modulesData2 = JSON.parse(JSON.stringify(this.modulesData))
          // console.log(modulesData2)
          // console.log(newArr2, '--', modulesData2, '123123123')
          this.oldIndex = flag
          let temp2 = newArr2[(flag + 1)]
          // 底部判断
          if ((newArr2.length - 1) === flag) return
          newArr2[(flag + 1)] = newArr2[flag]
          newArr2[flag] = temp2
          let indexD = flag + 1
          this.showModulesList = newArr2
          this.$nextTick(() => {
            // 保存数据顺序改变
            // let tempModules2 = JSON.parse(JSON.stringify(modulesData2[(flag + 1)]))
            // modulesData2[(flag + 1)] = modulesData2[flag]
            // modulesData2[flag] = tempModules2
            // console.log(modulesData2)
            // let arrFliterModules2 = modulesData2.filter(item => {
            //   return item
            // })
            // this.modulesData = arrFliterModules2
            if (this.nowRightShowIndex === indexD) {
              // this.handleToModuleHight()
            } else {
              this.nowRightShowIndex = indexD
            }
          })
          break
        case 'delete':
          this.deleteFlag = flag
          this.deleteVisible = true
      }
      console.log(this.modulesData)
    },
    // 中间模块是否删除弹窗点击确定事件
    handleToSureDelete (flag) {
      console.log(this.modulesData)
      let newArr3 = JSON.parse(JSON.stringify(this.showModulesList))
      newArr3.splice(flag, 1)
      // 如果数组为空就重置当前插入模块id
      if (!newArr3.length) {
        this.insertModulesId = -1
      }
      console.log(newArr3)
      this.showModulesList = newArr3
      this.$nextTick(() => {
        this.modulesData.splice(flag, 1)
        if (this.nowRightShowIndex > flag) {
          this.nowRightShowIndex--
        } else if (this.nowRightShowIndex === flag) {
          // 如果删除的是当前高亮模块, 则清空右侧传递数据
          this.nowRightModulesData = {}
          console.log(this.nowRightModulesData)
          this.nowRightShowIndex = null
        }
      })
      console.log(this.modulesData)
      this.deleteVisible = false
      console.log(this.showModulesList, this.modulesData)
    },
    // 中间区域元素开始拖动时处理函数
    handleToStart ({ oldIndex }) {
      console.log(oldIndex)
      this.middleHereFlag = true
      this.topAreaFlag = false
      let newArr = JSON.parse(JSON.stringify(this.showModulesList))
      this.oldElement = newArr[oldIndex]
      this.oldIndex = oldIndex
      console.log(this.oldElement, oldIndex, newArr)
      newArr[oldIndex] = -1
      console.log(newArr)
      this.showModulesList = newArr
      this.$nextTick(() => {
        this.nowRightShowIndex = -1
      })

      this.isDragging = true
    },
    // 中间区域元素停止拖动是处理函数
    handleToEnd (e) {
      let newArr = JSON.parse(JSON.stringify(this.showModulesList))
      console.log(this.oldIndex, this.newIndex, this.oldElement, newArr)
      let insertIndex = this.newIndex + 1
      if (this.topAreaFlag) {
        newArr.unshift(this.oldElement)
      } else {
        newArr.splice(insertIndex, 0, this.oldElement)
      }
      console.log(newArr)
      let newArrMiddle = JSON.parse(JSON.stringify(newArr))
      newArr.forEach((item, index) => {
        if (item === -1) {
          newArrMiddle.splice(index, 1)
        }
      })
      this.showModulesList = newArrMiddle
      console.log(this.modulesData)
      // this.isDragging = false
      if (this.oldIndex < this.newIndex) {
        insertIndex--
      }
      console.log(newArrMiddle)
      let length = newArrMiddle.length
      console.log(insertIndex, length, this.topAreaFlag)
      if (insertIndex === length) {
        insertIndex--
      } else if (this.topAreaFlag) {
        insertIndex = 0
      }
      console.log(insertIndex)
      this.$nextTick(() => {
        this.nowRightShowIndex = insertIndex
        this.middleHereFlag = false
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.formDecorationHome {
  padding: 10px;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 53px 20px;
    display: flex;
    justify-content: flex-start;
    .decLeft {
      width: 236px;
      .decLeftDrag {
        background-color: #fff;
        border: 1px solid transparent;
        border-color: #ddd;
        margin-bottom: 5px;
        .title {
          padding: 7px 14px;
          font-size: 14px;
          color: #333;
          background-color: #f5f5f5;
          border-color: #ddd;
        }
        .dragContainer {
          border-top: 1px solid #ddd;
          padding: 10px 11px 10px 0;
          display: flex;
          flex-wrap: wrap;
        }
      }
    }
    .baseStyle {
      border: 1px solid #eee;
      background: #f8f8f8;
      border-radius: 5px;
      line-height: 24px;
      width: 60px;
      height: 60px;
      cursor: move;
      position: relative;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      font-size: 12px;
      margin-left: 14px;
      margin-bottom: 10px;
      img {
        width: 34px;
        height: 20px;
      }
    }
    .decMiddle {
      width: 325px;
      border: 1px solid #e5e5e5;
      margin-left: 30px;
      background: #f5f5f5;
      height: 570px;
      .top {
        height: 55px;
      }
      .drag_area {
        height: 510px;
      }
    }
    .decRight {
      z-index: 100;
      width: 41.4%;
      margin-left: 20px;
    }
  }
  .footer {
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    position: fixed;
    bottom: 0;
    padding: 10px 0;
    left: 0;
    right: 0;
    span {
      color: #999;
      font-size: 12px;
    }
  }
}
</style>
