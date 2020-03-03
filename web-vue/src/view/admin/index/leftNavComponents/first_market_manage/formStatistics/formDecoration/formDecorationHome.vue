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
              @click="handleToClickLeftModule(item.id,item.flag)"
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
        <vue-scroll
          :ops="ops"
          style="height:510px"
        >
          <!--中间拖拽接收区域-->
          <div class="drag_area">
            <!--顶部占位-->
            <div
              class="hereDaily"
              :class="topAreaFlag?'setHere':''"
              @mouseover="dragTopOver()"
              @mouseout="dragTopOut()"
              v-if="isDragging"
            >
            </div>
            <!--占位提示-->
            <div
              class="zbTips"
              v-if="!showModulesList.length"
              style='z-index:100'
            >
              <div class="drag_notice">{{$t('decorationHome.seizeASeat')}}</div>
            </div>
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
                    @middleDragData='middleDragData'
                    :backData='modulesData[index]'
                  ></components>
                </div>

              </div>
            </draggable>
            <!--底部提交显示-->
            <div
              class="btn_tijiao"
              :style="`color:${pageSetData.font_color?pageSetData.font_color:'#fff'};background-color: ${pageSetData.bg_color?pageSetData.bg_color:'#ff6666'}`"
            >
              {{pageSetData.notice_name?pageSetData.notice_name:'提交'}}</div>
          </div>
        </vue-scroll>
      </div>
      <div class="decRight">
        <PageSetup
          :nowRightShowMoudlesIndex='nowRightShowMoudlesIndex'
          :nowRightModulesData="nowRightModulesData"
          :pageSetData='pageSetData'
          @handleToClearIndex='handleToClearIndex'
          @handleToBackMiddleData='handleToBackMiddleData'
          @hanelToPageSet='hanelToPageSet'
        />
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
          @click="handleToClickSave(0)"
        >保存</el-button>
        <el-button
          size="small"
          @click="handleToClickSave(1)"
        >保存并发布</el-button>
        <span>发布后不可更改</span>
      </div>
    </div>
  </div>
</template>
<script>
import vuescroll from 'vuescroll'
import 'vuescroll/dist/vuescroll.css'
import Vue from 'vue'
import draggable from 'vuedraggable'
import $ from 'jquery'
import decMixins from '@/mixins/decorationModulesMixins/formdecorationModulesMixins'
import { formDecorationAdd } from '@/api/admin/marketManage/formDecoration' // 装修方法混入
Vue.use(vuescroll)
require('webpack-jquery-ui')
require('webpack-jquery-ui/css')
export default {
  mixins: [decMixins],
  components: {
    draggable,
    // 右侧显示出口组件
    PageSetup: () => import('./pageSetup'),
    // 表单元素模块池
    Name: () => import('./decorationModules/formModule/name'), // 姓名模块
    CellPhoneNumber: () => import('./decorationModules/formModule/cellPhoneNumber'), // 手机号模块
    ProvinceAndCity: () => import('./decorationModules/formModule/provinceAndCity'), // 省市区模块
    Email: () => import('./decorationModules/formModule/email'), // 邮箱模块
    Gender: () => import('./decorationModules/formModule/gender'), // 性别模块
    DropDown: () => import('./decorationModules/formModule/dropDown'), // 下拉模块
    InputBox: () => import('./decorationModules/formModule/inputBox'), // 输入框波快
    Option: () => import('./decorationModules/formModule/option'), // 选项模块
    DateModule: () => import('./decorationModules/formModule/dateModule'), // 日期模块
    PictureUpload: () => import('./decorationModules/formModule/pictureUpload'), // 图片上传模块
    VideoUpload: () => import('./decorationModules/formModule/videoUpload'), // 视频上传模块
    // 图文类模块池
    RotationChart: () => import('./decorationModules/formModule/rotationChart'), // 轮播图模块
    RichText: () => import('./decorationModules/formModule/richText') // 富文本模块
  },
  data () {
    return {
      middleHereFlag: false, // 中间拖动滑过模块出现的空白占位控制变量
      nowRightShowIndex: null, // 中间高亮模块索引
      middleModulesList: ['Name', 'CellPhoneNumber', 'ProvinceAndCity', 'Email', 'Gender', 'DropDown', 'InputBox', 'Option', 'DateModule', 'PictureUpload', 'VideoUpload', 'RotationChart', 'RichText'], // 中间显示模块名称池
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
      },
      oldIndex: -1, // 中部拖动相关
      newIndex: -1, // 中部拖动相关
      oldElement: null, // 中部拖动相关
      isDragging: false, // 中间模块拖至顶部相关
      topAreaFlag: false, // 中间模块拖至顶部相关
      ops: { // 中间部分滚动配置
        vuescroll: {
          mode: 'native'
        },
        scrollPanel: {},
        rail: {
          keepShow: true
        },
        bar: {
          hoverStyle: true,
          onlyShowBarOnScroll: false, // 是否只有滚动的时候才显示滚动条
          background: '#c1c1c1'
        }
      },
      pageSetData: {
        'page_name': '', // 标题input值
        'is_forever_valid': '1', // 有效期radio
        'has_bottom': '0', // 底部导航radio
        'start_time': '', // 开始时间
        'end_time': '', // 结束时间
        'post_times': '1', // 提交次数限制radio
        'day_times': '1', // 每天input值
        'total_times': '1', // 累计input值
        'get_times': '0', // 总反馈数量限制input值
        'notice_name': '', // 提交按钮文字
        'font_color': '#ffffff', // 提交按钮文字颜色
        'bg_color': '#ff6666', // 提交按钮背景颜色
        'bg_img': '', // 表单海报背景图片
        'set_own_link': 0, // 自定义跳转checked
        'custom_btn_name': '', // 自定义按钮名称
        'custom_link_path': '', // 跳转链接
        'custom_link_name': '', // 跳转链接名称
        'send_coupon': 0, // 参与送优惠卷checkbox
        'send_coupon_list': [], // 选择的优惠卷数据列表
        'send_score': 0, // 参与送积分选中checkbox
        'send_score_number': null, // 输入的送积分input值
        'authorized_name': 0, // 授权手机号
        'authorized_mobile': 0 // 授权用户信息
      }, // 初始向右侧传递的表单信息配置数据
      nowRightShowMoudlesIndex: null, // 当前右侧显示模块索引
      nowRightModulesData: {}, // 当前右侧显示的中部高亮模块数据
      isClickPageSetIcon: false, // 是否是右侧店家顶部icon引起的高亮模块索引变化
      cur_idx: 100, // 初始模块保存编号
      modulesData: [] // 当前所有模块保存数据
    }
  },
  watch: {
    showModulesList (newData) {
      console.log(this.nowRightShowIndex, newData)
      console.log(newData)
      this.$http.$emit('modulesClick', this.nowRightShowIndex)
    },
    nowRightShowIndex (newData) {
      if (this.isClickPageSetIcon) { // 如果是点击的页面设置的内容则终止响应操作
        this.isClickPageSetIcon = false
        return
      }
      this.handleToModuleHight()
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
            case 3:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 3)
              break
            case 4:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 4)
              break
            case 5:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 5)
              break
            case 6:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 6)
              break
            case 7:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 7)
              break
            case 8:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 8)
              break
            case 9:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 9)
              break
            case 10:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 10)
              break
            case 11:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 11)
              break
            case 12:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 12)
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
          this.modulesData.splice(insert, 0, this.handleToAddModules(index))
          console.log(this.nowRightShowIndex, insert)
          if (this.nowRightShowIndex === insert) {
            this.handleToModuleHight()
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
      this.handleToModuleHight()
    },
    // 中部icon点击处理事件
    handleToClickIcon ({ direction, flag }) {
      this.isClickIcon = true
      this.topAreaFlag = false
      switch (direction) {
        case 'up':
          let newArr1 = JSON.parse(JSON.stringify(this.showModulesList))
          let modulesData1 = JSON.parse(JSON.stringify(this.modulesData))
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
            let tempModules = JSON.parse(JSON.stringify(modulesData1[(flag - 1)]))
            console.log(tempModules)
            modulesData1[(flag - 1)] = modulesData1[flag]
            modulesData1[flag] = tempModules
            console.log(modulesData1)
            this.modulesData = modulesData1
            if (this.nowRightShowIndex === index) {
              this.handleToModuleHight()
            } else {
              this.nowRightShowIndex = index
            }
          })
          console.log(newArr1, this.modulesData)
          break
        case 'down':
          console.log(this.modulesData)
          let newArr2 = JSON.parse(JSON.stringify(this.showModulesList))
          let modulesData2 = JSON.parse(JSON.stringify(this.modulesData))
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
            let tempModules2 = JSON.parse(JSON.stringify(modulesData2[(flag + 1)]))
            modulesData2[(flag + 1)] = modulesData2[flag]
            modulesData2[flag] = tempModules2
            console.log(modulesData2)
            let arrFliterModules2 = modulesData2.filter(item => {
              return item
            })
            this.modulesData = arrFliterModules2
            if (this.nowRightShowIndex === indexD) {
              this.handleToModuleHight()
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
      console.log(this.newIndex)
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
      this.isDragging = false
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
    },
    // 中间区域拖拽插入数据记录
    middleDragData (res) {
      console.log(res)
      this.newIndex = res
    },
    // 中间模块拖至顶部滑动
    dragTopOver () {
      console.log('滑过顶部')
      this.topAreaFlag = true
    },
    // 中间模块顶部划出
    dragTopOut () {
      this.topAreaFlag = false
    },
    // 右侧点击表单信息展开icon重置中部显示
    handleToClearIndex (flag) {
      this.isClickPageSetIcon = true
      this.nowRightShowIndex = -1
    },
    // 右侧编辑回显数据
    handleToBackMiddleData (data) {
      this.modulesData[this.nowRightShowIndex] = data
      this.$forceUpdate()
    },
    // 表单信息右侧编辑传回数据
    hanelToPageSet (res) {
      console.log(res)
      this.pageSetData = res
    },
    // 当前高亮模块数据处理向右侧传递事件
    handleToModuleHight () {
      let flag = true
      this.showModulesList.forEach(item => {
        if (item === -1) {
          flag = false
        }
      })
      if (!flag) return
      this.handleToSaveModules(this.showModulesList, this.modulesData)
      this.nowRightShowMoudlesIndex = -1
      console.log(this.showModulesList, this.modulesData, this.nowRightShowIndex)
      this.$nextTick(() => {
        this.nowRightShowMoudlesIndex = this.showModulesList[this.nowRightShowIndex]
        this.nowRightModulesData = this.modulesData[this.nowRightShowIndex]
      })
    },
    // 当中间模块数组showModulesList被插入新的数据时、保存数组处理函数
    handleToSaveModules (showModulesList, modulesData) {
      console.log(this.showModulesList, this.modulesData)
      if (this.showModulesList.length > this.modulesData.length) {
        console.log(this.showModulesList[this.nowRightShowIndex])
        let obj = this.handleToAddModules(this.showModulesList[this.nowRightShowIndex])
        console.log(this.cur_idx)
        this.cur_idx = this.cur_idx + 1
        console.log(this.cur_idx)
        obj.cur_idx = this.cur_idx
        this.modulesData.splice(this.nowRightShowIndex, 0, obj)
        console.log(this.modulesData)
      } else if (this.showModulesList.length === this.modulesData.length) {
        console.log(this.isClickIcon, this.showModulesList, this.modulesData)
        if (this.isClickIcon) { // 如果中部点击的是icon则终止
          this.isClickIcon = false
          return
        }
        console.log(this.isClickModule)
        if (this.isClickModule) { // 如果是模块点击触发
          this.isClickModule = false
          return
        }
        console.log(this.oldIndex, this.newIndex, this.modulesData, this.topAreaFlag, this.nowRightShowIndex)
        if (this.oldIndex === -1) {
          console.log(this.modulesData, this.nowRightShowIndex, this.modulesData[this.nowRightShowIndex])
          if (this.modulesData[this.nowRightShowIndex]) {
            console.log(this.cur_idx)
            this.modulesData[this.nowRightShowIndex].cur_idx = this.cur_idx + 1
            let newArr = JSON.parse(JSON.stringify(this.modulesData))
            this.modulesData = null
            this.modulesData = newArr
            this.cur_idx = this.cur_idx + 1
            console.log(newArr)
          }
          return
        }
        let temp = this.modulesData[this.oldIndex]
        console.log(temp, this.modulesData[this.nowRightShowIndex])
        if (this.topAreaFlag) {
          this.modulesData.splice(this.oldIndex, 1)
          this.modulesData.unshift(temp)
        } else {
          this.modulesData.splice(this.oldIndex, 1)
          console.log(this.modulesData)
          this.modulesData.splice(this.nowRightShowIndex, 0, temp)
        }
        this.oldIndex = -1
      }
    },
    //  底部点击保存综合处理
    handleToClickSave (index) {
      let saveMosulesData = JSON.parse(JSON.stringify(this.modulesData))
      // 对模块某些数据进行非空校验
      let judgeFlag = this.handleToJudgeModulesData(saveMosulesData)
      console.log(judgeFlag)
      if (!judgeFlag) return
      // 处理数据结构
      let lastData = this.handleToSaveModulesData(saveMosulesData, this.pageSetData, this.cur_idx)
      console.log(lastData)
      // 保存处理
      if (index === 0) {
        lastData.status = 0
        formDecorationAdd(lastData).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success({
              message: '保存成功',
              showClose: true
            })
          } else {
            this.$message.success({
              message: '保存失败',
              showClose: true
            })
          }
        })
      } else {
        lastData.status = 1
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.formDecorationHome {
  padding: 10px;
  /deep/ .__vuescroll {
    background-color: #f5f5f5 !important;
  }
  /deep/ .__rail-is-vertical {
    z-index: 1000 !important;
  }
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
        height: 100%;
        position: relative;
        .hereDaily {
          height: 10px;
          z-index: 1000;
          margin-top: -5px;
          .setHereSpan {
            display: block;
          }
        }
        .setHere {
          height: 30px;
        }
        .zbTips {
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          .drag_notice {
            width: 90%;
            margin: 10px auto 0;
            border: 1px dashed #d0d0d0;
            background: #fff;
            padding: 50px 0;
            text-align: center;
            color: #9f9f9f;
            font-size: 15px;
          }
        }
        .btn_tijiao {
          width: 90%;
          margin: 10px auto 0;
          background: #f66;
          color: #fff;
          font-size: 15px;
          text-align: center;
          padding: 10px 0;
          border-radius: 5px;
        }
      }
    }
    .decRight {
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
