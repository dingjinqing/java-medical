export default {
  methods: {
    // 页面装修模块数据填充处理
    handleToAddModules (index) {
      let obj = {}
      switch (index) {
        case 1: // 会员卡模块
          obj = {
            module_name: 'm_card', // 模块名称
            id: '', // 会员卡id
            hidden_card: 0, // 是否用户领取后隐藏会员卡
            cardName: 'V1代理卡', // 会员卡名称
            cardState: 1, // 会员卡使用状态
            cardGrade: 'v1', // 会员卡等级
            receiveDay: '有效期:永久有效', // 有效期
            cardType: 0, // 会员卡等级
            legal: '会员折扣9折', // 会员卡描述
            exchangCountLegal: '', // 会员卡折扣描述
            bgType: '0', // 背景类型
            bgColor: '#ecc98f', // 背景颜色
            bgImg: '', // 背景图片
            isPay: '2',
            payType: '0',
            payFee: '0.00'
          }
          break
        case 2: // 优惠卷模块
          obj = {
            module_name: 'm_coupon', // 模块名称
            coupon_arr: [
              // 选中的优惠卷数组
              // {
              //   'act_code': 'voucher', // 是否是打折卷  discount：打折卷   voucher不是打折卷
              //   'denomination': '5', // 面额
              //   'consume_text': '无门槛', // 使用门槛
              //   'receive_text': '剩余93张', // 卡卷剩余数
              //   'coupon_id': -1, // 优惠卷id
              //   'use_score': '0', // 是否可以积分兑换
              //   'score_number': '' // 需要积分数
              // }
            ]
          }
          break
        case 3: // 砍价
          obj = {
            module_name: 'm_bargain',
            list_style: '0',
            goods_price: true,
            goods_count_down: true,
            free_btn: true,
            bargain_goods: []
          }
          break
        case 5: // 秒杀
          obj = {
            module_name: 'm_seckill',
            list_style: '0',
            goods_price: true,
            goods_count_down: true,
            seckill_goods: []
          }
          break
        case 6: // 拼团抽奖
          obj = {
            module_name: 'm_group_draw',
            group_draw_id: '',
            name_set: '0',
            group_draw_name: '',
            show_clock: '1',
            font_color: '#ffffff',
            module_bg: '0',
            module_img: ''
          }
          break
        case 8: // 商品模块
          obj = {
            module_name: 'm_goods', // 模块名称
            title: '', // 标题  y
            title_link: '', // 标题链接 y
            tit_center: false, // 标题位置 false不选中标题居中  true选中标题居中 y
            recommend_type: '0', // 模块推荐 0自动推荐  1手动推荐 y
            goods_items: [
              // 商品列表数据 y
            ],
            col_type: '4', // 4  1  2  3  0  y
            goods_display: '0', // 大图展示显示模块radio值
            goods_num: '4', // 模块推荐-商品数量  y
            min_price: '', // 模块推荐-商品价格最低价格  y
            max_price: '', // 模块推荐-商品价格最高价格 y
            keywords: '', // 模块推荐-关键词 y
            sort_type: '1', // 模块推荐-排序规则 y
            img_url: '', // 图标 y
            goods_module_title: '0', // 模块标题类型  0不设置  1文字标题 2图片标题  y
            img_title_url: '', // 标题图片 y
            hide_name: '1', // 商品名称 y
            hide_price: '0', // 商品价格 y
            hide_label: '0', // 商品标签 y
            cart_btn: '1', // 购买按钮 y
            cart_btn_choose: '0', // 购买按钮radio y
            other_message: '0', // 其它信息 y
            if_radius: '0', // 模块角度 0直角  1圆角 y
            goods_module_style: '0', // 0白底无边框  1边框投影 2白底有边框 y
            goods_area: 'sort', // 模块推荐-商品范围  //all 没选  sort：商家分类 cat:平台分类  brand:品牌分类  label:标签分类 y
            goods_area_data: [], // 商品范围选中后弹窗选中的数据  //多个数据也是字符串，非数组  y
            goods_type: '0', // 活动商品 0请选择 .. y
            show_market: '1', // 其他信息选中 隐藏radio字段  1市场价 2销量 3评价数 y
            goods_module_bg: '0', // 背景颜色 0与页面一致  1自定义 y
            goods_bg_color: '#f5f5f5', // 背景自定义颜色 y
            goodsListData: [] // 传递商品列表数据
          }
          break
        case 9: // 商品搜索模块
          obj = {
            module_name: 'm_goods_search', // 模块名称
            search_style: '1', // 框体样式
            search_font: '1', // 框体高度
            box_color: '#eee', // 框体颜色
            back_color: '#fff', // 背景颜色
            search_sort: '0', // 商家分类是否显示
            sort_bg_color: '#666666' // 图标颜色
          }
          break
        case 10: // 商品分组模块
          obj = {
            module_name: 'm_goods_group',
            sort_group_arr: [],
            menu_style: '0',
            position_style: '0',
            shop_style: '1',
            if_radius: '0',
            sort_length: 1,
            module_style: '1',
            group_display: '1',
            show_market: '1',
            goods_module_bg: '0',
            goods_bg_color: '#f5f5f5',
            show_name: 0,
            show_price: 0,
            cart_btn: 1,
            other_message: 0
          }
          break
        case 11: // 轮播图模块
          obj = {
            module_name: 'm_scroll_image', // 模块名称
            img_items: [], // 轮播图片列表
            is_preview: '0' // 是否在小程序中可以预览原图
          }
          break
        case 12: // 图片导航模块
          obj = {
            module_name: 'm_image_guide', // 模块名称
            nav_style: '1', // 导航样式  1 为样式一   2 为样式二
            font_color: 'rgba(255, 69, 0, 1)', // 字体颜色
            bg_color: 'rgba(255, 255, 255, 1)', // 背景颜色
            nav_group: [
              // 导航列表
              {
                nav_name: '导航一', // 导航名称
                nav_link: '', // 导航链接
                nav_src: '' // 导航左侧图片路径
              },
              {
                nav_name: '导航二',
                nav_link: '',
                nav_src: ''
              },
              {
                nav_name: '导航三',
                nav_link: '',
                nav_src: ''
              },
              {
                nav_name: '导航四',
                nav_link: '',
                nav_src: ''
              }
            ]
          }
          break
        case 13: // 图片广告模块
          obj = {
            module_name: 'm_image_adver', // 模块名称
            image_type: '0', // 选择模板类型
            is_preview: '0', // 预览原图radio
            image_space: '', // 图片间隙
            module_title: '', // 模块标题
            image_list: [
              // 底部图片列表
              // { // 图片路径
              //   'image': 'http://mpdevimg2.weipubao.cn/upload/0/image/20191024/crop_WyZbpaHeYoNcF8d6.jpeg',
              //   'width': '560', // 图片宽度
              //   'height': '700', // 图片高度
              //   'title': '1', // 文本
              //   'link': '', //   链接
              //   'can_show': '0', //  显示设置raido
              //   'whetherToExpand': '0'  //  是否展开flag
              // }
            ]
          }
          break
        case 14: // 魔方多图
          obj = {
            module_name: 'm_magic_cube', // 模块名称
            table_type: 1, // 模板类型
            table_size: { // 布局尺寸
              rows: 2,
              cols: 4
            },
            data: {
              // block_0: {
              //   name: 'block_0',
              //   x: 1,
              //   y: 1,
              //   rows: 2,
              //   cols: 2,
              //   img_url:
              //     'http://mpdevimg2.weipubao.cn/upload/0/image/20191028/crop_NBaCqTWlYNcQkz9I.jpeg'
              // },
              // block_1: {
              //   name: 'block_1',
              //   x: 1,
              //   y: 3,
              //   rows: 2,
              //   cols: 2,
              //   img_url:
              //     'http://mpdevimg2.weipubao.cn/upload/0/image/20191024/crop_WyZbpaHeYoNcF8d6.jpeg',
              //   jump_link: 'pages/auth/auth'
              // }
            },
            isAllCheckFull: true // 布局是否填充完毕
          }
          break
        case 16: // 左图右文
          obj = {
            'module_name': 'm_text_image', // 模块名称
            'ti_type': '0', // 文本样式类型
            'img_style': '0', // 图片样式类型
            'img_url': '', // 图片路径
            'title_link': '', // 链接
            'rich_text': '' // 文本内容
          }
          break
        case 17: // 文本模块
          obj = {
            'module_name': 'm_text', // 模块名称
            'title': '', // 文本
            'fonts_size': '1', // 字体大小
            'fonts_color': '#333333', // 字体颜色
            'bgs_color': '#ffffff', // 背景颜色
            'show_pos': '1', // 显示位置
            'title_link': '' // 链接
          }
          break
        case 18: // 富文本
          obj = {
            'module_name': 'm_rich_text', // 模块名称
            'rich_text': '' // 富文本内容
          }
          break
        case 19: // 辅助空白
          obj = {
            'module_name': 'm_blank', // 模块名称
            'blank_height': '10' // 空白高度
          }
          break
        case 20: // 辅助线
          obj = {
            'module_name': 'm_dashed_line' // 模块名称
          }
          break
        case 21: // 标题模块
          obj = {
            'module_name': 'm_title', // 模块名称
            'title': '', // 标题
            'title_model': '1', // 标题模板
            'title_link': '', // 链接
            'tit_center': '1', // 显示位置
            'color': '#333333', // 字体颜色
            'bg_color': '#ffffff', // 背景颜色
            'title_date': '', // 日期
            'title_author': '', // 作者
            'link_title': '', // 链接标题
            'img_url': '' // 图标
          }
          break
        case 22: // 视频模块
          obj = {
            'module_name': 'm_video', // 模块名称
            'video_url': '', // 视频路径
            'video_img': '', // 封面图片路径
            'video_size': '', // 视频大小
            'video_width': '', // 视频宽度
            'video_height': '', // 视频高度
            'video_title': '', // 视频标题
            'video_poster': '1', //   1 为使用原视频封面    2 为使用自定义封面
            'video_showurl': '', // 当前显示的视频完整路径
            'video_showpath': '', // 当前显示的封面完整路径
            'custom_url': '', // 自定义封面保存路径
            'custom_path': '' // 自定义封面显示完整路径
          }
          break
        case 23: // 店铺公告
          obj = {
            'module_name': 'm_shop_announce', // 模块名称
            'shop_text': '', // 内容
            'font_color': '#333333', // 字体颜色
            'bg_color': '#fcf9dd', // 背景颜色
            'title_link': '', // 链接路径
            'announce_position': '0' // 显示位置
          }
          break
        case 24: // 公众号
          obj = {
            'module_name': 'm_official_accounts' // 模块名称
          }
          break
        case 25: // 客服模块
          obj = {
            'module_name': 'm_service',
            'service': '1'
          }
          break
        case 27: // 店招设置
          obj = {
            module_name: 'm_shop',
            shop_name: '微铺宝电商运营',
            shop_notice: '公告',
            shop_bg_path: '/image/admin/shop_beautify/beau1.png',
            bg_url: '/image/admin/shop_beautify/beau1.png',
            bg_fullUrl: '',
            ok_ajax: 1
          }
          break
        case 28: // 地图模块
          obj = {
            module_name: 'm_map',
            province: '北京市',
            province_code: '110000',
            city: '北京市',
            city_code: '110100',
            area: '东城区',
            area_code: '110101',
            address: '东直门',
            map_show: '1',
            latitude: '39.92855',
            longitude: '116.41637'
          }
      }
      console.log(obj)
      return obj
    },
    // 对保存模块数据进行校验
    handleToJudgeModulesData (data) {
      console.log(data, this.pageSetData)
      // 判断页面设置页面名称是否为空
      if (!this.pageSetData.page_name) {
        this.$message.error({
          message: '页面名称不能为空',
          showClose: true
        })
        return false
      }
      // 判断页面设置分享海报的按钮名称是否为空
      if (
        this.pageSetData.pictorial.is_add === '1' &&
        !this.pageSetData.pictorial.share_btn_name
      ) {
        this.$message.error({
          message: '页面分享按钮不能为空',
          showClose: true
        })
        return false
      }
      // 判断页面设置分享海报的分享语是否为空
      if (
        this.pageSetData.pictorial.is_add === '1' &&
        !this.pageSetData.pictorial.share_desc
      ) {
        this.$message.error({
          message: '页面分享语不能为空',
          showClose: true
        })
        return false
      }
      // 判断页面设置分享海报的分享图不能为空
      if (
        this.pageSetData.pictorial.is_add === '1' &&
        !this.pageSetData.pictorial.share_img_path
      ) {
        this.$message.error({
          message: '页面分享图不能为空',
          showClose: true
        })
        return false
      }
      let flag = true
      //  模块私有校验
      data.forEach((item, index) => {
        switch (item.module_name) {
          case 'm_card': // 会员卡相关校验
            console.log(item)
            if (!item.id) {
              flag = false
              this.$message.error({
                message: '请选择会员卡',
                showClose: true
              })
            }
            break
          case 'm_image_guide': // 图片导航相关校验
            item.nav_group.forEach((item, index) => {
              if (!item.nav_src) {
                flag = false
                this.$message.error({
                  message: '请上传图片导航模块图片',
                  showClose: true
                })
              }
            })
            break
          case 'm_scroll_image': // 轮播图相关校验
            console.log(item)
            if (item.img_items.length <= 0) {
              flag = false
              this.$message.error({
                message: '请添加轮播图片',
                showClose: true
              })
            }
            break
          case 'm_image_adver':
            console.log(item.image_list)
            if (!item.image_list.length) {
              flag = false
              this.$message.error({
                message: '请添加图片广告',
                showClose: true
              })
            }
            break
          case 'm_bargain':
            if (item.bargain_goods && item.bargain_goods.length === 0) {
              this.$message.error({
                message: '请选择砍价活动商品',
                showClose: true
              })
              flag = false
            }
            break
          case 'm_seckill':
            if (item.seckill_goods && item.seckill_goods.length === 0) {
              this.$message.error({
                message: '请选择秒杀活动商品',
                showClose: true
              })
              flag = false
            }
            break
          case 'm_group_draw':
            if (item.group_draw_id === '') {
              this.$message.error({
                message: '请选择拼团抽奖活动',
                showClose: true
              })
              flag = true
            }
            break
          case 'm_magic_cube':
            if (item.table_type === 8) {
              if (!item.isAllCheckFull) {
                this.$message.error({
                  message: '请选择上传图片',
                  showClose: true
                })
                flag = false
              }
            } else {
              Object.keys(item.data).forEach((itemC, indexC) => {
                if (!(item.data[itemC].img_url)) {
                  this.$message.error({
                    message: '请上传图片',
                    showClose: true
                  })
                  flag = false
                }
              })
            }
            break
          case 'm_text_image':
            if (!item.img_url) {
              this.$message.error({
                message: '请上传图片',
                showClose: true
              })
              flag = false
            }
        }
      })
      console.log(flag)
      return flag
    },
    // 处理保存数据
    handleToSaveModulesData (data, pageSetData) {
      console.log(data, pageSetData)
      let obj = {}
      data.forEach(item => {
        obj[`c_${item.cur_idx}`] = item
      })
      console.log(obj)
      obj['page_cfg'] = pageSetData
      return obj
    }
  }
}
