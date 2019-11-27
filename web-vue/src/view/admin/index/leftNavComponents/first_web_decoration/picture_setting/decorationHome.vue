<template>
  <div class="decHomeContainer">
    <div class="decHomeMain">
      <div class="top">
        <span>{{$t('decorationHome.libraryName')}}</span><span>{{$t('decorationHome.libraryNameTips')}}</span>
      </div>
      <div class="content">
        <div class="decLeft">
          <el-tabs v-model="activeName">
            <el-tab-pane
              :label="$t('decorationHome.imageAndText')"
              name="first"
            >
            </el-tab-pane>
            <el-tab-pane
              :label="$t('decorationHome.commodityComponents')"
              name="second"
            >
            </el-tab-pane>
            <el-tab-pane
              :label="$t('decorationHome.marketingComponent')"
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
                :dataId="item.id"
                @click="handleToClickLeftModule(item.id)"
                :style="leftComClass?'justify-content:space-between ':''"
              >
                <img :src="item.imgUrl">
                <span
                  :title='item.text'
                  :class="leftComClass"
                >{{item.text}}</span>

              </div>
            </div>
          </vue-scroll>
        </div>
        <div class="decMiddle">
          <div class="decTop">
            <span>{{pageSetData.page_name}}</span>
          </div>
          <vue-scroll
            :ops="ops"
            style="height:530px"
          >
            <div
              class="decContent"
              :style="pageSetData.bg_types==='1'?`backgroundImage:url('${pageSetData.page_bg_image}');background-repeat:no-repeat;background-size:cover`:`background-color:${pageSetData.page_bg_color}`"
            >

              <div
                class="drag_area"
                :class="zbFlag?'zwHeight':''"
                style="min-height:530px;"
              >
                <!--放这里-->
                <div
                  class="hereDaily"
                  :class="topAreaFlag?'setHere':''"
                  @mouseover="dragTopOver()"
                  @mouseout="dragTopOut()"
                  v-if="isDragging"
                >
                  <span :class="topAreaFlag?'setHereSpan':''">{{$t('decorationHome.putItHere')}}</span>
                </div>
                <!--占位提示-->
                <div
                  class="zbTips"
                  v-if="!showModulesList.length"
                  style='z-index:1000'
                >
                  <div class="drag_notice">{{$t('decorationHome.seizeASeat')}}</div>
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
                    <div
                      :style="pageSetData.show_margin==='1'?`margin-bottom:${pageSetData.margin_val}px`:''"
                      @click.prevent="handleToClickModule(index)"
                    >
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
            :pageSetData='pageSetData'
            @handleToClearIndex='handleToClearIndex'
            @handleToBackMiddleData='handleToBackMiddleData'
            @hanelToPageSet='hanelToPageSet'
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
        >{{$t('pageSetUp.saveAndPublish')}}</el-button>
        <el-button
          size="small"
          @click="handleToFooter(1)"
        >{{$t('pageSetUp.saveAsDraft')}}</el-button>
        <el-button
          size="small"
          @click="handleToFooter(2)"
        >{{$t('pageSetUp.previewEffect')}}</el-button>
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
    <!--二次保存确认-->
    <el-dialog
      title="页面发布提醒"
      :visible.sync="saveTwoDialogVisible"
      width="30%"
      :center='true'
    >
      <span>页面将自动保存为草稿并发布到线上</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="saveTwoDialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToSaveTwo()"
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
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
import { saveDecorationPage, editSave } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting'
import { pageEdit } from '@/api/admin/decoration/pageSet.js'
Vue.use(vuescroll)
require('webpack-jquery-ui')
require('webpack-jquery-ui/css')
export default {
  mixins: [decMixins],
  components: {
    vuescroll,
    draggable,
    // 营销组件库
    MembershipCard: () => import('./decorationModules/marketingComponents/membershipCard'), // 会员卡
    Coupon: () => import('./decorationModules/marketingComponents/Coupon'), // 优惠券
    Bargain: () => import('./decorationModules/marketingComponents/Bargain'), // 砍价
    Spike: () => import('./decorationModules/marketingComponents/Spike'), // 秒杀
    FightGroup: () => import('./decorationModules/marketingComponents/fightGroup'), // 拼团抽奖
    // 右侧显示出口组件
    PageSetup: () => import('./pageSetup'),
    // 商品组件库
    Commodity: () => import('./decorationModules/commodityComponents/commodity'), // 商品
    CommoditySearch: () => import('./decorationModules/commodityComponents/commoditySearch'), // 商品搜索
    CommodityGrouping: () => import('./decorationModules/commodityComponents/commodityGrouping'), // 商品分组
    // 图文组件库
    PictureNavigation: () => import('./decorationModules/graphicAndTextComponents/pictureNavigation'), // 图片导航
    CarouselPicture: () => import('./decorationModules/graphicAndTextComponents/CarouselPicture'), // 轮播图
    PictureAds: () => import('./decorationModules/graphicAndTextComponents/pictureAds'), //  图片广告
    MagicMap: () => import('./decorationModules/graphicAndTextComponents/magicMap'), // 魔方多图
    ShopRecruit: () => import('./decorationModules/graphicAndTextComponents/ShopRecruit'), // 店招设置
    MapModule: () => import('./decorationModules/graphicAndTextComponents/MapModule'), // 地图模块
    LeftWingRightPicture: () => import('./decorationModules/graphicAndTextComponents/leftWingRightPicture'), // 左图右文模块
    TextModule: () => import('./decorationModules/graphicAndTextComponents/textModule'), // 文本模块
    RichText: () => import('./decorationModules/graphicAndTextComponents/richText'), // 富文本
    AuxiliaryBlank: () => import('./decorationModules/graphicAndTextComponents/auxiliaryBlank'), // 辅助空白
    Guide: () => import('./decorationModules/graphicAndTextComponents/guide'), // 辅助线
    TitleModule: () => import('./decorationModules/graphicAndTextComponents/titleModule'), // 标题模块
    VideoModule: () => import('./decorationModules/graphicAndTextComponents/videoModule'), // 视频模块
    ShopNotices: () => import('./decorationModules/graphicAndTextComponents/shopNotices'), // 店铺公告模块
    OfficialAccount: () => import('./decorationModules/graphicAndTextComponents/officialAccount'), // 公众号模块
    CustomerServiceModule: () => import('./decorationModules/graphicAndTextComponents/customerServiceModule') // 客服模块
  },
  data () {
    return {
      saveTwoDialogVisible: false, // 二次弹窗flag
      leftComClass: false, // 左边组件库适配中英文
      deleteVisible: false,
      deleteFlag: null,
      middleModulesList: [null, 'MembershipCard', 'Coupon', 'Bargain', 'zb', 'Spike', 'FightGroup', 'zb', 'Commodity', 'CommoditySearch', 'CommodityGrouping', 'CarouselPicture', 'PictureNavigation', 'PictureAds', 'MagicMap', 'zb', 'LeftWingRightPicture', 'TextModule', 'RichText', 'AuxiliaryBlank', 'Guide', 'TitleModule', 'VideoModule', 'ShopNotices', 'OfficialAccount', 'CustomerServiceModule', 'zb', 'ShopRecruit', 'MapModule'],
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
      activeName: 'first',
      pivTextConArr: [],
      goodsTextConArr: [],
      marketingTextConArr: [],
      isDragging: false,
      nowShowLeftModules: [],
      showModulesList: [],
      insertModulesId: -1, // 左侧模块将要插入位置
      drag_flag: false,
      oldIndex: -1,
      newIndex: -1,
      oldElement: null,
      zbFlag: false,
      topAreaFlag: false,
      nowRightShowIndex: null, // 现在右侧显示的模块的id
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
      },
      pageSetData: {},
      cur_idx: 100,
      MoveWhiteFlag: false, // 是否移入的是底部空白部分
      isEditSave: false, // 页面配置列表点击编辑跳转过来的标识
      editPageData: null,
      page_id: null, // 编辑回显page_id
      page_type: null, // 编辑回显page_type
      page_enabled: null, // 编辑回显page_enabled
      page_tpl_type: null, //  编辑回显page_tpl_type
      isAddBottom: false, // 是否添加到底部flag
      isNewEnterFirstSaveSucess: -1 //  新建进来并且是非第一次保存记录id
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
      console.log(newData, this.activeName, this.nowRightModulesData)
      this.handleToModuleHight()
    },
    activeName (newData) {
      this.initLeftModulesShow(newData)
    },
    lang () {
      this.pivTextConArr = this.$t('decorationHome.pivTextConArr')
      this.goodsTextConArr = this.$t('decorationHome.goodsTextConArr')
      this.marketingTextConArr = this.$t('decorationHome.marketingTextConArr')
      this.initLeftModulesShow(this.activeName)
    }
  },
  updated () {
    console.log(this.nowRightShowIndex)
    this.$http.$emit('modulesClick', this.nowRightShowIndex)
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    console.log(this.$route)
    if (Number(this.$route.query.pageId) !== -1) { // 判断是否是页面列表配置页面点击编辑跳转而来
      this.isEditSave = true
      pageEdit({ pageId: this.$route.query.pageId }).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.page_id = res.content.page_id
          this.page_type = res.content.page_type
          this.page_enabled = res.content.page_enabled
          this.page_tpl_type = res.content.page_tpl_type

          this.isEditSave = true
          console.log(res.content.page_content)
          let content = JSON.parse(res.content.page_content)
          this.editPageData = content
          console.log(JSON.parse(res.content.page_content))
          this.pageSetData.page_name = res.content.page_name
          this.pageSetData.cat_id = res.content.cat_id
          content.page_cfg.cat_id = JSON.stringify(content.page_cfg.cat_id)
          this.pageSetData = content.page_cfg
          console.log(content)
          this.cur_idx = content.page_cfg.last_cur_idx
          console.log(this.cur_idx)
          let moduleDataCopy = JSON.parse(JSON.stringify(content))
          delete moduleDataCopy.page_cfg
          console.log(moduleDataCopy)
          let arr = []
          Object.keys(moduleDataCopy).forEach((item, index) => {
            arr.push(moduleDataCopy[item])
          })
          console.log(arr)
          this.modulesData = arr
          this.handleToTurnModulesName(arr)
        }
      })
    } else {
      this.pageSetData = {
        'is_ok': 1,
        'cat_id': '',
        'page_name': '',
        'bg_types': '0',
        'has_bottom': '0',
        'page_bg_color': '#ffffff',
        'page_bg_image': '',
        'show_margin': '0',
        'margin_val': '0',
        'last_cur_idx': this.cur_idx,
        'pictorial': {
          'is_add': '0',
          'user_visibility': '0',
          'share_btn_name': '',
          'share_desc': '',
          'share_img_path': '',
          'name_length': 0
        }
      }
    }
  },
  methods: {
    // 模块名称转化
    handleToTurnModulesName (data) {
      let showModuleArr = []
      let arr = JSON.parse(JSON.stringify(data))
      arr.forEach(item => {
        showModuleArr.push(this.modulesName(item.module_name))
      })
      console.log(showModuleArr)
      this.showModulesList = showModuleArr
    },
    // 模块名数据池
    modulesName (name) {
      let moduleNameId = null
      switch (name) {
        case 'm_card':
          moduleNameId = 1
          break
        case 'm_coupon':
          moduleNameId = 2
          break
        case 'm_bargain':
          moduleNameId = 3
          break
        case 'm_seckill':
          moduleNameId = 5
          break
        case 'm_group_draw':
          moduleNameId = 6
          break
        case 'm_goods':
          moduleNameId = 8
          break
        case 'm_goods_search':
          moduleNameId = 9
          break
        case 'm_goods_group':
          moduleNameId = 10
          break
        case 'm_scroll_image':
          moduleNameId = 11
          break
        case 'm_image_guide':
          moduleNameId = 12
          break
        case 'm_image_adver':
          moduleNameId = 13
          break
        case 'm_magic_cube':
          moduleNameId = 14
          break
        case 'm_shop':
          moduleNameId = 27
          break
        case 'm_text_image':
          moduleNameId = 16
          break
        case 'm_text':
          moduleNameId = 17
          break
        case 'm_rich_text':
          moduleNameId = 18
          break
        case 'm_blank':
          moduleNameId = 19
          break
        case 'm_dashed_line':
          moduleNameId = 20
          break
        case 'm_title':
          moduleNameId = 21
          break
        case 'm_video':
          moduleNameId = 22
          break
        case 'm_shop_announce':
          moduleNameId = 23
          break
        case 'm_official_accounts':
          moduleNameId = 24
          break
        case 'm_service':
          moduleNameId = 25
      }
      return moduleNameId
    },
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
      this.$nextTick(() => {
        this.init_drag_event()
      })
    },
    // 页面设置回显
    hanelToPageSet (res) {
      console.log(res)
      this.pageSetData = res
    },
    // 初始化拖拽事件
    init_drag_event () {
      let this_ = this
      // 左侧模块向中间区域拖拽
      $('.third_drag').draggable({
        appendTo: '.decLeft',
        helper: 'clone',
        start: function () {
          console.log('开始拖动')
          // this_.topAreaFlag = false
          // this_.isDragging = true
        },
        drag: function (ev, ui) {
          // console.log($(ui.helper).offset())
          // console.log(ev)

          this_.highlignt_row_item($(ui.helper).offset())
        },
        stop: function () {
          // let last = this_.showModulesList
          // setTimeout(() => {
          console.log('qqqqqqqq')
          this_.isDragging = false
          if (this_.MoveWhiteFlag) return
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
      // console.log('test')
      $('.drag_area').droppable({
        // activeClass: 'ui-state-default',
        // hoverClass: 'ui-state-hover',
        accept: '.third_drag',
        drop: function (event, ui) {
          // console.log(ui.draggable[0].innerText)
          // console.log(this_.dataId)
          console.log('test', ui)
          console.log(this_.insertModulesId, '--', this_.showModulesList)
          let insert = this_.insertModulesId + 1
          console.log(typeof ui.draggable[0].getAttribute('dataId'))
          switch (Number(ui.draggable[0].getAttribute('dataId'))) {
            case 1:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 1)
              break
            case 2:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 2)
              break
            case 3:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 3)
              break
            case 5:
              this_.handleToMiddleAcceptData(this._insertModulesId, this._showModulesList, insert, 5)
              break
            case 6:
              this_.handleToMiddleAcceptData(this._insertModulesId, this._showModulesList, insert, 6)
              break
            case 8:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 8)
              break
            case 9:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 9)
              break
            case 12:
              this_.handleToMiddleAcceptData(this_.insertModulesId, this_.showModulesList, insert, 12)
              break
            case 11:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 11)
              break
            case 10:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 10)
              break
            case 13:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 13)
              break
            case 14:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 14)
              break
            case 16:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 16)
              break
            case 17:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 17)
              break
            case 18:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 18)
              break
            case 19:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 19)
              break
            case 20:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 20)
              break
            case 21:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 21)
              break
            case 22:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 22)
              break
            case 23:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 23)
              break
            case 24:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 24)
              break
            // case 25:
            //   this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 25)
            //   break
            case 27:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 27)
              break
            case 28:
              this_.handleToMiddleAcceptData(this_.inertModulesId, this_.showModulesList, insert, 28)
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
        this.MoveWhiteFlag = true
        this.handleToClickLeftModule(index)
        // setTimeout(() => {
        //   this.nowRightShowIndex = this.showModulesList.length - 1
        // }, 100)
      } else {
        this.MoveWhiteFlag = false
        console.log(this.nowRightShowIndex, insert, index)
        this.showModulesList.splice(insert, 0, index)
        this.$nextTick(() => {
          if (this.nowRightShowIndex === insert) {
            this.handleToModuleHight()
          }
        })

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
      console.log(this.modulesData)
      let newArr3 = JSON.parse(JSON.stringify(this.showModulesList))
      console.log(this.nowRightShowIndex)
      console.log(this.nowRightShowIndex, flag)

      console.log(this.nowRightShowIndex)

      console.log(newArr3, flag)
      newArr3.splice(flag, 1)
      console.log(this.modulesData[flag])
      // if (this.modulesData[flag].module_name === 'm_image_guide') {
      //   this.modulesData[flag].nav_group.forEach((item, index) => {
      //     item.nav_link = ''
      //     item.nav_src = ''
      //   })
      // }

      // this.modulesData.splice(flag, 1)
      console.log(flag)

      // this.modulesData = modulesData
      console.log(this.modulesData)
      // 如果数组为空就重置当前插入模块id
      if (!newArr3.length) {
        this.insertModulesId = -1
      }
      console.log(newArr3)
      this.modulesData.splice(flag, 1)
      this.showModulesList = newArr3
      this.$nextTick(() => {
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
    // 中间区域拖拽插入数据处理
    middleDragData (res) {
      console.log(res)
      this.newIndex = res
    },
    // 顶部滑动
    dragTopOver () {
      console.log('滑过顶部')
      this.topAreaFlag = true
    },
    // 顶部划出
    dragTopOut () {
      this.topAreaFlag = false
    },
    // 模块点击
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
      console.log(this.nowRightShowIndex, this.showModulesList, this.modulesData)
      this.handleToSaveModules(this.showModulesList, this.modulesData)
      this.$nextTick(() => {
        this.nowRightShowMoudlesIndex = this.showModulesList[this.nowRightShowIndex]
        console.log(this.nowRightShowIndex, this.modulesData)
        // this.nowRightModulesData = null
        this.nowRightModulesData = this.modulesData[this.nowRightShowIndex]
      })

      console.log(this.nowRightModulesData, this.modulesData[this.nowRightShowIndex])
      // this.$forceUpdate()
      // this.nowRightShowMoudlesIndex  当前高亮模块类型的index
      console.log(this.showModulesList, this.nowRightShowIndex)

      console.log(this.nowRightShowMoudlesIndex)
      console.log(this.modulesData, this.nowRightShowIndex)

      // this.$store.commit('TOCHANGE_SENDMODULESDATA', this.modulesData[this.nowRightShowIndex])
      console.log(this.showModulesList, this.modulesData)
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
      this.modulesData = null
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
    // 右侧编辑回显数据
    handleToBackMiddleData (data) {
      console.log(data)
      this.modulesData[this.nowRightShowIndex] = data
      console.log(this.modulesData)
      this.$forceUpdate()
      console.log(this.modulesData[this.nowRightShowIndex])
      console.log(data)
    },
    // 二次保存并发布确认
    handleToSaveTwo (flag) {
      this.handleToSave(0)
    },
    // 保存处理事件
    handleToSave (flag) {
      let saveMosulesData = JSON.parse(JSON.stringify(this.modulesData))
      // 对模块某些数据进行非空校验
      let judgeFlag = this.handleToJudgeModulesData(saveMosulesData)
      if (!judgeFlag) return

      console.log(saveMosulesData)
      console.log(this.pageSetData, this.cur_idx)
      this.pageSetData.last_cur_idx = this.cur_idx
      let data = this.handleToSaveModulesData(saveMosulesData, this.pageSetData)
      console.log(data)
      console.log(saveMosulesData, this.modulesData, this.pageSetData)
      console.log(localStorage.getItem('V-ShopId'))
      if (!this.pageSetData.cat_id) {
        this.pageSetData.cat_id = 0
      }
      let params = {
        shopId: Number(localStorage.getItem('V-ShopId')),
        pageName: this.pageSetData.page_name,
        pageContent: JSON.stringify(data),
        pagePublishContent: JSON.stringify(data),
        pageState: '',
        catId: this.pageSetData.cat_id,
        last_cur_idx: this.cur_idx
      }
      console.log(this.isEditSave)
      let pageState = null
      switch (flag) {
        case 0:
          pageState = 1
          break
        case 1:
          pageState = 0
          break
        case 2:
      }
      params.pageState = pageState
      console.log(flag)
      if (flag === 0 || flag === 1) {
        console.log(params)
        console.log(data)
        if (this.isEditSave || (this.isNewEnterFirstSaveSucess !== -1)) { // 编辑保存
          let id = ''
          if ((this.isNewEnterFirstSaveSucess !== -1)) {
            id = this.isNewEnterFirstSaveSucess
          } else {
            id = this.page_id
          }
          let editParams = {
            'pageId': id,
            'shopId': Number(localStorage.getItem('V-ShopId')),
            'pageName': this.pageSetData.page_name,
            'pageType': this.page_type,
            'pageEnabled': this.page_enabled,
            'pageTplType': this.page_tpl_type,
            'pageContent': JSON.stringify(data),
            'pagePublishContent': JSON.stringify(data),
            'pageState': pageState,
            'catId': this.pageSetData.cat_id,
            'last_cur_idx': this.cur_idx
          }
          editSave(editParams).then((res) => {
            console.log(res)

            if (res.error === 0) {
              this.$message.success({
                message: '保存成功',
                showClose: true,
                duration: 1000
              })
            }
          })
        } else if (!this.isEditSave && (this.isNewEnterFirstSaveSucess === -1)) { // 新建保存
          saveDecorationPage(params).then(res => {
            console.log(res)
            if (res.error === 0) {
              this.isNewEnterFirstSaveSucess = res.content
              this.$message.success({
                message: '保存成功',
                showClose: true,
                duration: 1000
              })
            }
          })
        }
      } else {
        this.$message.success({
          message: '预览测试',
          showClose: true,
          duration: 1000
        })
      }
      this.saveTwoDialogVisible = false
      console.log(params)
    },
    // 底部保存等按钮点击统一处理
    handleToFooter (flag) {
      console.log(flag)
      let saveMosulesData = JSON.parse(JSON.stringify(this.modulesData))
      // 对模块某些数据进行非空校验
      let judgeFlag = this.handleToJudgeModulesData(saveMosulesData)
      if (!judgeFlag) return

      if (flag === 0) {
        this.saveTwoDialogVisible = true
      } else {
        this.handleToSave(flag)
      }
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
          z-index: 1000;
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
          .leftComClass {
            display: block;
            margin-top: -16px;
            text-align: center;
            padding: 0 5px;
            height: 47px;
            line-height: 19px;
            display: flex;
            align-items: center;
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 2;
            overflow: hidden;
          }
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
          display: flex;
          justify-content: center;
          align-items: flex-end;
          span {
            font-size: 16px;
            color: #000;
            font-weight: 800;
          }
        }
        .decContent {
          min-height: 530px;
          background: #fff;
          position: relative;
          overflow-x: hidden;
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
