<template>
  <div class="decHomeContainer">
    <div class="decHomeMain">
      <div class="top">
        <span>组件库</span><span>可拖拽使用</span>
      </div>
      <div class="content">
        <div class="decLeft">
          <el-tabs
            :stretch='true'
            v-model="activeName"
          >
            <el-tab-pane
              label="图文组件"
              name="first"
            >
            </el-tab-pane>
            <el-tab-pane
              label="商品组件"
              name="second"
            >
            </el-tab-pane>
            <el-tab-pane
              label="营销组件"
              name="third"
            >
            </el-tab-pane>
          </el-tabs>
          <vue-scroll
            :ops="ops"
            style="height:520px"
          >
            <div id="listLeft">
              <div
                v-for="(item,key) in nowShowLeftModules"
                :key="key"
                class="picTextConDivList third_drag"
                @click="handleToClickLeftModule(item.id)"
              >
                <img :src="item.imgUrl">
                {{item.text}}

              </div>
            </div>
          </vue-scroll>
        </div>
        <div class="decMiddle">
          <div class="decTop"></div>
          <vue-scroll
            :ops="ops"
            style="height:510px"
          >
            <div class="decContent">

              <div
                class="drag_area"
                :class="zbFlag?'zwHeight':''"
              >
                <!--放这里-->
                <div
                  class="hereDaily"
                  :class="topAreaFlag?'setHere':''"
                  @mouseover="dragTopOver"
                  @mouseout="dragTopOut"
                  v-if="isDragging"
                >
                  <span :class="topAreaFlag?'setHereSpan':''">放这里</span>
                </div>
                <!--占位提示-->
                <div
                  class="zbTips"
                  v-if="!showModulesList.length"
                >
                  <div class="drag_notice">拖拽左侧模块进行装修</div>
                </div>
                <!--拖拽区域-->
                <draggable
                  class="list-group"
                  :style="showModulesList.length?'padding-bottom:10px':'padding-bottom:127px'"
                  element="div"
                  v-model="showModulesList"
                  :options="dragOptions"
                  @start="handleToStart"
                  @end="handleToEnd"
                >
                  <!--模块列表-->
                  <div
                    v-for="(item,index) in showModulesList"
                    :key="index"
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
                  <!--模块列表结束-->
                </draggable>
              </div>

            </div>
          </vue-scroll>
        </div>
        <div class="decRight">
          <PageSetup
            :nowRightShowMoudlesIndex='nowRightShowMoudlesIndex'
            :nowRightModulesData="nowRightModulesData"
            :nowRightShowIndex='nowRightShowIndex'
            @handleToClearIndex='handleToClearIndex'
            @handleToBackMiddleData='handleToBackMiddleData'
          />
        </div>
      </div>
    </div>
    <!--保存-->
    <div class="footer">
      <div>
        <el-button
          type="primary"
          size="small"
          @click="handleToFooter(0)"
        >保存并发布</el-button>
        <el-button
          size="small"
          @click="handleToFooter(1)"
        >保存为草稿</el-button>
        <el-button
          size="small"
          @click="handleToFooter(2)"
        >预览效果</el-button>
      </div>
    </div>
    <!--中间模块是否删除弹窗-->
    <el-dialog
      title="提醒"
      :visible.sync="deleteVisible"
      width="30%"
    >
      <span>确认要删除吗？</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="deleteVisible = false"
        >取 消</el-button>
        <el-button
          size="small"
          type="primary"
          @click="handleToSureDelete(deleteFlag)"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import vuescroll from 'vuescroll'
import draggable from 'vuedraggable'
import Vue from 'vue'
import 'vuescroll/dist/vuescroll.css'
import $ from 'jquery'
Vue.use(vuescroll)
require('webpack-jquery-ui')
require('webpack-jquery-ui/css')
export default {
  components: {
    vuescroll,
    draggable,
    // 营销组件库
    MembershipCard: () => import('./decorationModules/marketingComponents/membershipCard'),
    Coupon: () => import('./decorationModules/marketingComponents/Coupon'),
    // 右侧显示出口组件
    PageSetup: () => import('./pageSetup'),
    // 商品组件库
    Commodity: () => import('./decorationModules/commodityComponents/commodity')
  },
  data () {
    return {
      deleteVisible: false,
      deleteFlag: null,
      middleModulesList: [null, 'MembershipCard', 'Coupon', 'Commodity'],
      ops: {
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
          background: '#eee'
        }
      },
      activeName: 'third',
      pivTextConArr: [
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_rotation.png',
          text: '轮播图',
          id: 11
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_guide.png',
          text: '图片导航',
          id: 12
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_img_advertist.png',
          text: '图片广告',
          id: 13
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_imgwindow.png',
          text: '魔方多图',
          id: 14
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/hot-area.png',
          text: '图片热区',
          id: 15
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/text_image.png',
          text: '左图右文',
          id: 16
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_text.png',
          text: '文本模块',
          id: 17
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_rich_text.png',
          text: '富文本',
          id: 18
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_helpblank.png',
          text: '辅助空白',
          id: 19
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_helpline.png',
          text: '辅助线',
          id: 20
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_title.png',
          text: '标题模块',
          id: 21
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/icon_video.png',
          text: '视频模块',
          id: 22
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/shop_announce.png',
          text: '店铺公告',
          id: 23
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/official_account.png',
          text: '公众号',
          id: 24
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/service.png',
          text: '客服模块',
          id: 25
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/drag_phone.png',
          text: '电话模块',
          id: 26
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_navigation.png',
          text: '店招设置',
          id: 27
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/icon_map.png',
          text: '地图模块',
          id: 28
        }
      ],
      goodsTextConArr: [
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/deco_goods.png',
          text: '商品',
          id: 8
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/dg_search.png',
          text: '商品搜索',
          id: 9
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/goods_group.png',
          text: '商品分组',
          id: 10
        }
      ],
      marketingTextConArr: [
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/deco_card.png',
          text: '会员卡',
          id: 1
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/deco_voucher.png',
          text: '优惠卷',
          id: 2
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/bargain.png',
          text: '砍价',
          id: 3
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/icon_integral_deco.png',
          text: '积分兑换',
          id: 4
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/secKill.png',
          text: '秒杀',
          id: 5
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/fight_group.png',
          text: '拼团抽奖',
          id: 6
        },
        {
          imgUrl: this.$imageHost + '/image/admin/new_shop_beautify/pin_integration.png',
          text: '瓜分积分',
          id: 7
        }
      ],
      isDragging: false,
      nowShowLeftModules: [],
      showModulesList: [],
      insertModulesId: -1,
      drag_flag: false,
      oldIndex: -1,
      newIndex: -1,
      oldElement: null,
      zbFlag: false,
      topAreaFlag: false,
      nowRightShowIndex: null,
      nowRightShowMoudlesIndex: null,
      modulesData: [], // 模块数据
      middleHereFlag: false,
      nowRightModulesData: {},
      dragOptions: {
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
      if (newData.length) {
        this.zbFlag = true
      } else {
        this.zbFlag = false
      }
    },
    nowRightShowIndex (newData) {
      console.log(newData, this.activeName, this.showModulesList)
      this.handleToModuleHight()
    },
    activeName (newData) {
      this.initLeftModulesShow(newData)
    }
  },
  updated () {
    console.log(this.nowRightShowIndex)
    this.$http.$emit('modulesClick', this.nowRightShowIndex)
  },
  mounted () {
    // 初始化数据
    this.$nextTick(() => {
      this.init_drag_event()
    })
    this.initLeftModulesShow(this.activeName)
  },
  methods: {
    // 初始化左侧模块显示
    initLeftModulesShow (activeName) {
      switch (activeName) {
        case 'first':
          this.nowShowLeftModules = this.pivTextConArr
          break
        case 'second':
          this.nowShowLeftModules = this.goodsTextConArr
          break
        case 'third':
          this.nowShowLeftModules = this.marketingTextConArr
      }
    },
    // 初始化拖拽事件
    init_drag_event () {
      // 中间区域拖拽插入数据处理
      // this.middleDragData()
      let this_ = this
      // 左侧模块向中间区域拖拽
      $('.third_drag').draggable({
        appendTo: '.decLeft',
        helper: 'clone',
        start: function () {

        },
        drag: function (ev, ui) {
          // console.log($(ui.helper).offset())
          // console.log(ev)
          this_.highlignt_row_item($(ui.helper).offset())
        },
        stop: function () {
          // let last = this_.showModulesList
          // setTimeout(() => {
          this_.$nextTick(() => {
            let hightMoudleIndex = this_.insertModulesId + 1
            console.log(hightMoudleIndex)
            if (this_.nowRightShowIndex === -1) {
              this_.nowRightShowIndex = 0
            } else {
              this_.nowRightShowIndex = hightMoudleIndex
            }
          })

          console.log(this_.nowRightShowIndex)
          // this_.$http.$emit('decCard', this_.nowRightShowIndex)
          // }, 200)
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
        // console.log('ssssss')

        $('.modules').each(function (idx, item) {
          p = $(this).offset()
          if (pos.left > p.left && pos.top > p.top &&
            pos.left < p.left + $(this).width() &&
            pos.top < p.top + $(this).height()
          ) {
            this_.insertModulesId = $(this)[0].__vue__.flag
            console.log($(this)[0].__vue__.flag)
            $('.modules').removeClass('placeholder')
            $(this).addClass('placeholder')
          }
        })
      }
    },
    // 中间模块拖拽接收
    handleToAcceptDrag () {
      let this_ = this
      // console.log('test')
      $('.drag_area').droppable({
        // activeClass: 'ui-state-default',
        // hoverClass: 'ui-state-hover',
        accept: '.third_drag',
        drop: function (event, ui) {
          // console.log(ui.draggable[0].innerText)
          // console.log(this_.dataId)
          console.log('test')
          console.log(this_.insertModulesId, '--', this_.showModulesList)
          let insert = this_.insertModulesId + 1
          console.log(insert)
          switch (ui.draggable[0].innerText) {
            case '会员卡':
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 1)
              break
            case '优惠卷':
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 2)
              break
            case '商品':
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 3)
          }
          console.log(this_.showModulesList, this_.modulesData, insert)
        }
      })
    },
    // 左侧模块拖入中间区域后，中间区域数据处理函数
    handleToMiddleAcceptData (insertModulesId, showModulesList, insert, index) {
      // 判断id是否为-1，若是则插入尾部，否则插入指定位置
      if (insertModulesId === -1) {
        showModulesList.push(index)
      } else {
        showModulesList.splice(insert, 0, index)
        if (this.nowRightShowIndex === insert) {
          this.handleToModuleHight()
        }
        // this_.handleToModuleHight()
      }
    },
    // 中间区域元素开始拖动时处理函数
    handleToStart ({ oldIndex }) {
      console.log(oldIndex)

      this.middleHereFlag = true
      this.topAreaFlag = false
      let newArr = JSON.parse(JSON.stringify(this.showModulesList))
      this.oldElement = newArr[oldIndex]
      this.oldIndex = oldIndex
      console.log(newArr.length, oldIndex)
      // let arrLength = newArr.length - 1
      // newArr.splice(oldIndex, 1)
      newArr[oldIndex] = -1
      console.log(newArr)
      this.showModulesList = newArr

      // let data = newArr
      this.$nextTick(() => {
        this.nowRightShowIndex = -1
        // this.$http.$emit('decCard', -1, 1)
      })

      this.isDragging = true
    },
    // 中间区域元素停止拖动是处理函数
    handleToEnd (e) {
      // let this_ = this
      console.log(this.oldIndex, this.newIndex, this.oldElement)

      let newArr = JSON.parse(JSON.stringify(this.showModulesList))
      let insertIndex = this.newIndex + 1
      if (this.topAreaFlag) {
        newArr.unshift(this.oldElement)
      } else {
        newArr.splice(insertIndex, 0, this.oldElement)
      }
      if (this.oldIndex < this.newIndex) {
        insertIndex--
      }
      console.log(newArr)
      let newArrMiddle = JSON.parse(JSON.stringify(newArr))

      newArr.forEach((item, index) => {
        if (item === -1) {
          newArrMiddle.splice(index, 1)
        }
      })
      this.showModulesList = newArrMiddle
      this.isDragging = false
      console.log(insertIndex)
      // let hightMoudleIndex = this_.insertModulesId + 1
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
        // this_.$http.$emit('decCard', insertIndex, -1)
      })
    },
    // 中间区域模块icon点击数据接收统一处理
    handleToClickIcon ({ direction, flag }) {
      console.log(1111)
      this.topAreaFlag = false
      switch (direction) {
        case 'up':
          let newArr1 = JSON.parse(JSON.stringify(this.showModulesList))
          console.log(newArr1, '--', flag)
          this.oldIndex = flag
          // 顶部判断
          if (flag === 0) return
          let temp = newArr1[(flag - 1)]
          newArr1[(flag - 1)] = newArr1[flag]
          newArr1[flag] = temp
          let arrFliter = newArr1.filter(item => {
            return item
          })
          // 改变边框
          let index = flag - 1
          console.log(newArr1)
          this.showModulesList = arrFliter
          // let data = this.showModulesList
          // this.$http.$emit('decCard', data, -1)
          this.$nextTick(() => {
            this.nowRightShowIndex = index
            // this.$http.$emit('modulesClick', index)
          })
          console.log(newArr1, '--' + this.showModulesList)
          break
        case 'down':
          let newArr2 = JSON.parse(JSON.stringify(this.showModulesList))
          console.log(newArr2, '--', flag, '123123123')
          this.oldIndex = flag
          let temp2 = newArr2[(flag + 1)]
          // 底部判断
          if ((newArr2.length - 1) === flag) return
          newArr2[(flag + 1)] = newArr2[flag]
          newArr2[flag] = temp2
          let arrFliterD = newArr2.filter(item => {
            return item
          })
          let indexD = flag + 1
          console.log(arrFliterD)
          this.showModulesList = arrFliterD
          // let dataD = this.showModulesList
          // this.$http.$emit('decCard', dataD, -1)
          this.$nextTick(() => {
            // this.$http.$emit('modulesClick', indexD)
            this.nowRightShowIndex = indexD
          })

          break
        case 'delete':
          this.deleteFlag = flag
          this.deleteVisible = true
      }
    },
    // 中间模块是否删除弹窗点击确定事件
    handleToSureDelete (flag) {
      let newArr3 = JSON.parse(JSON.stringify(this.showModulesList))
      console.log(this.nowRightShowIndex)
      console.log(this.nowRightShowIndex, flag)
      if (this.nowRightShowIndex > flag) {
        this.nowRightShowIndex--
      } else if (this.nowRightShowIndex === flag) {
        this.nowRightShowIndex = null
      }

      console.log(this.nowRightShowIndex)

      console.log(newArr3, flag)
      newArr3.splice(flag, 1)
      this.modulesData.splice(flag, 1)
      // 如果数组为空就重置当前插入模块id
      if (!newArr3.length) {
        this.insertModulesId = -1
      }
      console.log(newArr3)
      this.showModulesList = newArr3
      this.deleteVisible = false
    },
    // 中间区域拖拽插入数据处理
    middleDragData (res) {
      console.log(res)
      this.newIndex = res
    },
    // 顶部滑动
    dragTopOver () {
      this.topAreaFlag = true
    },
    // 顶部划出
    dragTopOut () {
      this.topAreaFlag = false
    },
    // 底部保存点击统一处理
    handleToFooter (flag) {
      switch (flag) {
        case 0:
          break
        case 1:
          break
        case 2:
      }
    },
    handleToClickModule (index) {
      console.log(index)
      this.$http.$emit('modulesClick', index)
      // this.handleToModuleHight()
    },
    // 当前高亮模块数据处理向右侧传递事件
    handleToModuleHight () {
      let flag = true
      this.showModulesList.forEach(item => {
        if (item === -1) {
          flag = false
        }
      })
      console.log(flag)
      if (!flag) return
      // this.nowRightShowIndex 当前高亮模块在模块数组池中的index

      console.log(this.nowRightShowIndex, this.activeName, this.showModulesList)

      console.log(this.showModulesList, this.modulesData)
      this.handleToSaveModules(this.showModulesList, this.modulesData)

      // this.nowRightShowMoudlesIndex  当前高亮模块类型的index
      console.log(this.showModulesList, this.nowRightShowIndex)
      this.nowRightShowMoudlesIndex = this.showModulesList[this.nowRightShowIndex]
      console.log(this.nowRightShowMoudlesIndex)
      console.log(this.insertModulesId)
      this.nowRightModulesData = this.modulesData[this.nowRightShowIndex]
      // this.$store.commit('TOCHANGE_SENDMODULESDATA', this.modulesData[this.nowRightShowIndex])
      console.log(this.showModulesList, this.modulesData)
    },
    // 当中间模块数组showModulesList被插入新的数据时、保存数组处理函数
    handleToSaveModules (showModulesList, modulesData) {
      if (this.showModulesList.length > this.modulesData.length) {
        console.log(this.showModulesList[this.nowRightShowIndex])
        let obj = this.handleToAddModules(this.showModulesList[this.nowRightShowIndex])
        console.log(obj)
        this.modulesData.splice(this.nowRightShowIndex, 0, obj)
        console.log(this.modulesData)
      } else if (this.showModulesList.length === this.modulesData.length) {
        console.log(this.oldIndex, this.modulesData, this.topAreaFlag)
        if (this.oldIndex === -1) return
        let temp = this.modulesData[this.oldIndex]
        console.log(this.topAreaFlag, temp)
        if (this.topAreaFlag) {
          this.modulesData.splice(this.oldIndex, 1)
          this.modulesData.unshift(temp)
        } else {
          this.modulesData[this.oldIndex] = this.modulesData[this.nowRightShowIndex]
          this.modulesData[this.nowRightShowIndex] = temp
        }
        this.oldIndex = -1
      }
      console.log(this.oldIndex, this.nowRightShowIndex)
      let newArr = JSON.parse(JSON.stringify(this.modulesData))
      this.modulesData = []
      this.modulesData = newArr
      console.log(this.modulesData)
      // this.$nextTick(() => {
      //   this.$forceUpdate()
      // })
    },
    //  点击左侧模块加到中间模块队列底部并高亮
    handleToClickLeftModule (id) {
      this.showModulesList.push(id)
      console.log(id)
      let length = this.showModulesList.length - 1
      console.log(length)
      this.$nextTick(() => {
        this.nowRightShowIndex = length
      })
    },
    // 当中部模块数据排序发生变化时处理保存模块数组的排序
    handleToSortModulesData (insert, obj) {
      console.log(insert, this.showModulesList, this.modulesData)
      if (this.showModulesList.length <= 1) return
      console.log(insert, this.showModulesList, this.modulesData)
    },
    // 右侧点击页面设置重置中部显示
    handleToClearIndex () {
      this.nowRightShowIndex = null
    },
    // 模块数据填充处理
    handleToAddModules (index) {
      let obj = {}
      switch (index) {
        case 1:
          obj.cardName = '会员卡'
          obj.backgroundColor = '#ecc98f'
          obj.tips = '********'
          obj.isHidden = false
          break
        case 2:
          obj.name = '优惠卷'
      }
      // let obj = { // 传递当前模块json数据模拟
      //   modulesIndex: index,
      //   name: name,
      //   sorIndex: this.nowRightShowIndex
      // }
      return obj
    },
    // 右侧编辑回显数据
    handleToBackMiddleData (data) {
      this.modulesData[this.nowRightShowIndex] = data
      console.log(this.modulesData)
      this.$forceUpdate()
      console.log(this.modulesData[this.nowRightShowIndex])
      console.log(data)
    }
  }
}
</script>
<style scoped lang="scss">
.decHomeContainer {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  padding-bottom: 60px;
  .decHomeMain {
    height: 100%;
    overflow-y: auto;
    position: relative;
    background-color: #fff;
    padding: 10px 20px;
    .top {
      margin-bottom: 12px;
      span:nth-of-type(2) {
        padding-left: 5px;
        color: #999;
      }
    }
    .content {
      display: flex;
      justify-content: flex-start;
      .decLeft,
      .decMiddle {
        width: 254px;
        margin: 2px;
        /deep/ .__vuescroll {
          background-color: #fff;
        }
        // padding: 0 2px;
        .hereDaily {
          height: 5px;
          span {
            display: none;
          }
          .setHereSpan {
            display: block;
          }
        }
        .setHere {
          height: 40px;
          text-align: center;
          line-height: 40px;
          background-color: #8baeff;
          font-size: 14px;
          color: #fff;
          border: 1px dashed #2589ff;
        }
        .picTextConDivList {
          float: left;
          border: 1px solid #e5e5e5;
          background: #f8f8f8;
          border-radius: 5px;
          line-height: 24px;
          width: 100px;
          height: 84px;
          margin: 5px 0 10px 14px;
          cursor: move;
          position: relative;
          display: flex;
          justify-content: center;
          align-items: center;
          flex-direction: column;
          // z-index: 10000;
        }
      }
      .decMiddle {
        width: 385px;
        border: 1px solid #e5e5e5;
        margin-left: 30px;
        .decTop {
          height: 64px;
          background: url(../../../../../../assets/adminImg/page_name.png)
            no-repeat;
        }
        .decContent {
          height: 510px;
          background: #fff;
          position: relative;
          // padding-bottom: 30px;
          // .zwHeight {
          //   padding-bottom: 30px;
          // }
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
        }
      }
      .decRight {
        z-index: 100;
        width: 41.4%;
        margin-left: 20px;
      }
    }
  }
  .list-group {
    height: 100%;
    // padding-bottom: 100px;
  }
  // .third_drag {
  //   padding-bottom: 20px;
  // }
  .footer {
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    position: fixed;
    bottom: 0;
    padding: 10px 0;
    left: 0;
    right: 0;
  }
}
.active {
  z-index: 100000 !important;
}
</style>
