const routes = [
  {
    path: '/admin/home',
    name: 'adminHome',
    component: () => import('@/view/admin/home'),
    children: [
      {
        path: '/admin/home/shop_main',
        name: 'shopMain',
        component: () => import('@/view/admin/shop_main')
      },
      {
        path: '/admin/home/main',
        name: 'adminMain',
        component: () => import('@/view/admin/index/main'),
        children: [
          // 概况系列子路由
          {
            path: '/admin/home/main/overviewOfMall',
            name: 'shop_view',
            meta: {
              crumbTitle: '',
              meta: 'first_web_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_web_manage/overviewOfMall'
              )
          },
          {
            path: '/admin/home/main/overviewStatistics',
            name: 'analysis_basic',
            meta: {
              crumbTitle: '',
              meta: 'first_web_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_web_manage/overviewStatistics'
              )
          },
          {
            path: '/admin/home/main/realtimeoverview',
            name: 'situation',
            meta: {
              crumbTitle: '',
              meta: 'first_web_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_web_manage/overviewStatistics'
              )
          },
          {
            path: '/admin/home/main/userportrait',
            name: 'analysis_portrait',
            meta: {
              crumbTitle: '',
              meta: 'first_web_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_web_manage/overviewStatistics'
              )
          },
          // 小程序管理系列子路由
          // 页面分类
          {
            path: '/admin/home/main/page_classification',
            name: 'page_classification',
            meta: {
              crumbTitle: 'router.pageClassification',
              meta: 'first_web_decoration'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_web_decoration/pageClassification/pageClassification'
              )
          },
          {
            path: '/admin/home/main/freight_template',
            name: 'image_list',
            meta: {
              crumbTitle: 'router.pictureSpace',
              meta: 'first_web_decoration'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_web_decoration/PictureSpace/freight_template'
              )
          },
          {
            path: '/admin/home/main/shopStyle',
            name: 'shop_style',
            meta: {
              crumbTitle: 'router.shopStyle',
              meta: 'first_web_decoration'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_web_decoration/shopStyle/shopStyle'
              )
          },
          {
            path: '/admin/home/main/pictureSetting',
            name: 'picture_setting',
            meta: {
              crumbTitle: 'router.picture_setting',
              meta: 'first_web_decoration'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_web_decoration/picture_setting/picture_setting'
              )
          },
          {
            path: '/admin/home/main/decorationHome',
            name: 'decorationHome',
            meta: {
              crumbTitle: 'router.picture_setting',
              meta: 'first_web_decoration',
              category: 'picture_setting'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_web_decoration/picture_setting/decorationHome'
              )
          },
          {
            path: '/admin/home/main/bottomNavigation',
            name: 'image_manager',
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_web_decoration/bottomNavigation/bottomNavigation'
              ),
            meta: {
              crumbTitle: 'router.bottomNavigation',
              meta: 'first_web_decoration'
            },
            // 选择链接弹窗子组件路由
            children: [
              // 常用链接
              {
                path: '/admin/home/main/bottomNavigation/commonLinks',
                name: 'commonLinks',
                meta: {
                  crumbTitle: 'router.bottomNavigation',
                  meta: 'first_web_decoration',
                  category: 'bottomNavigation'
                },
                component: () =>
                  import('@/components/admin/selectLinksComponents/commonLinks')
              },
              // 商品链接
              {
                path: '/admin/home/main/bottomNavigation/commodityLinks',
                name: 'commodityLinks',
                meta: {
                  crumbTitle: 'router.bottomNavigation',
                  meta: 'first_web_decoration',
                  category: 'bottomNavigation'
                },
                component: () =>
                  import(
                    '@/components/admin/selectLinksComponents/commodityLinks'
                  )
              },
              // 自定义页面
              {
                path: '/admin/home/main/bottomNavigation/customPage',
                name: 'customPage',
                meta: {
                  crumbTitle: 'router.bottomNavigation',
                  meta: 'first_web_decoration',
                  category: 'bottomNavigation'
                },
                component: () =>
                  import('@/components/admin/selectLinksComponents/customPage')
              },
              // 营销活动
              {
                path: '/admin/home/main/bottomNavigation/groupDrawing',
                name: 'groupDrawing',
                meta: {
                  crumbTitle: 'router.bottomNavigation',
                  meta: 'first_web_decoration',
                  category: 'bottomNavigation'
                },
                component: () =>
                  import(
                    '@/components/admin/selectLinksComponents/groupDrawing'
                  )
              },
              // 商品分类
              {
                path:
                  '/admin/home/main/bottomNavigation/classificationOfCommodities',
                name: 'classificationOfCommodities',
                meta: {
                  crumbTitle: 'router.bottomNavigation',
                  meta: 'first_web_decoration',
                  category: 'bottomNavigation'
                },
                component: () =>
                  import(
                    '@/components/admin/selectLinksComponents/classificationOfCommodities'
                  )
              },
              // 网页跳转
              {
                path: '/admin/home/main/bottomNavigation/pageJump',
                name: 'pageJump',
                meta: {
                  crumbTitle: 'router.bottomNavigation',
                  meta: 'first_web_decoration',
                  category: 'bottomNavigation'
                },
                component: () =>
                  import('@/components/admin/selectLinksComponents/pageJump')
              },
              // 小程序跳转
              {
                path: '/admin/home/main/bottomNavigation/smallProgramJump',
                name: 'smallProgramJump',
                meta: {
                  crumbTitle: 'router.bottomNavigation',
                  meta: 'first_web_decoration',
                  category: 'bottomNavigation'
                },
                component: () =>
                  import(
                    '@/components/admin/selectLinksComponents/smallProgramJump'
                  )
              },
              // 表单页面&&门店
              {
                path: '/admin/home/main/bottomNavigation/formPage',
                name: 'formPage',
                meta: {
                  crumbTitle: 'router.bottomNavigation',
                  meta: 'first_web_decoration',
                  category: 'bottomNavigation'
                },
                component: () =>
                  import('@/components/admin/selectLinksComponents/formPage')
              }
            ]
          },
          // 商品管理系列
          // 全部商品
          {
            path: '/admin/home/main/goods_manage/allGoods',
            redirect: '/admin/home/main/goods_manage/sale_on',
            name: 'sale_on',
            meta: {
              crumbTitle: 'router.saleOn',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/allGoods/allGoods'
              ),

            children: [
              // 商品管理/出售中商品
              {
                path: '/admin/home/main/goods_manage/sale_on',
                name: 'sale_on',
                meta: {
                  crumbTitle: 'router.saleOn',
                  meta: 'goods_manage'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/allGoods/saleOn/saleOn'
                  )
              },
              // 商品管理/已售罄
              {
                path: '/admin/home/main/goods_manage/sale_end',
                name: 'sale_end',
                meta: {
                  crumbTitle: 'router.saleEnd',
                  meta: 'goods_manage'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/allGoods/saleOn/saleOn'
                  )
              },
              // 商品管理/仓库中
              {
                path: '/admin/home/main/goods_manage/sale_off',
                name: 'sale_off',
                meta: {
                  crumbTitle: 'router.saleOff',
                  meta: 'goods_manage'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/allGoods/saleOff/saleOff'
                  )
              }
            ]
          },

          // 品牌管理
          {
            path: '/admin/home/main/brandManagement',
            name: 'brand',
            meta: {
              crumbTitle: 'router.brandManagement',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/brandManagement/brandManagement'
              )
          },
          // 添加品牌
          {
            path: '/admin/home/main/addBrand',
            name: 'addBrand',
            meta: {
              crumbTitle: 'router.brandAddManagement',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/brandManagement/addBrand'
              )
          },
          {
            path: '/admin/home/main/evaluationManagement',
            name: 'comment',
            meta: {
              crumbTitle: '',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/evaluationManagement/evaluationManagement'
              )
          },
          // 添加商品
          {
            path: '/admin/home/main/addingGoods',
            redirect: '/admin/home/main/addingGoods/basic',
            name: 'goods_add',
            meta: {
              crumbTitle: 'router.addingGoods',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoods'
              ),
            children: [
              // 编辑基本信息
              {
                path: '/admin/home/main/addingGoods/basic',
                name: 'basic',
                meta: {
                  crumbTitle: 'router.addingGoods',
                  meta: 'goods_manage',
                  category: 'goods_add'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoodsProductInfo'
                  )
              },
              // 编辑商品详情
              {
                path: '/admin/home/main/addingGoods/details',
                name: 'details',
                meta: {
                  crumbTitle: 'router.addingGoods',
                  meta: 'goods_manage',
                  category: 'goods_add'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoodsDetails'
                  )
              },
              // 编辑分销信息
              {
                path: '/admin/home/main/addingGoods/distribution',
                name: 'distribution',
                meta: {
                  crumbTitle: 'router.addingGoods',
                  meta: 'goods_manage',
                  category: 'goods_add'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoodsDistributionInfo'
                  )
              }
            ]
          },
          // 运费模板
          {
            path: '/admin/home/main/deliver',
            name: 'deliver',
            redirect: '/admin/home/main/deliver/template/list',
            meta: {
              crumbTitle: 'router.deliverTemplate',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/deliver'
              ),
            children: [
              {
                meta: {
                  crumbTitle: 'router.deliverTemplate',
                  meta: 'goods_manage'
                },
                path: '/admin/home/main/deliver/template/list',
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/templateList'
                  )
              },
              {
                path: '/admin/home/main/deliver/template/weight/list',
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/templateWeightList'
                  )
              },
              {
                path: '/admin/home/main/deliver/template/add',
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/templateAdd'
                  )
              },
              {
                path: '/admin/home/main/deliver/template/weight/add',
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/templateWeightAdd'
                  )
              }
            ]
          },
          // 商家分类管理
          {
            path: '/admin/home/main/goods/sort',
            name: 'sort',
            meta: {
              crumbTitle: 'router.goodsSort',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/goodsSort/goodsSort'
              )
          },
          // 订单管理系列子路由
          {
            path: '/admin/home/main/first_trade_manageL',
            name: 'order',
            meta: {
              crumbTitle: '',
              meta: 'first_trade_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_trade_manageL/first_trade_manageL'
              )
          },
          // 营销管理系列子路由
          {
            path: '/admin/home/main/first_market_manage',
            name: 'first_market_manage',
            meta: {
              crumbTitle: 'router.market_manage',
              meta: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/first_market/firstMarketManage'
              )
          },
          // 砍价
          {
            path: '/admin/home/main/bargain',
            name: 'kanjia',
            meta: {
              crumbTitle: 'router.kanjia',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/bargain/bargainIndex'
              )
          },
          // 多人拼团
          {
            path: '/admin/home/main/spellGroup',
            name: 'pin_group',
            meta: {
              crumbTitle: 'router.pin_group',
              meta: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/spellGroup/spellGroup'
              )
          },
          // 普通优惠券
          {
            path: '/admin/home/main/ordinaryCoupon',
            name: 'ordinary_coupon',
            meta: {
              crumbTitle: 'router.ordinary_coupon',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/first_market/ordinaryCoupon'
              )
          },
          // 好友助力
          {
            path: '/admin/home/main/friendHelp',
            name: 'promote',
            meta: {
              crumbTitle: 'router.promote',
              meta: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/friendHelp/friendHelp'
              )
          },
          // 添加优惠券活动
          {
            path: '/admin/home/main/addyCoupon',
            name: 'add_coupon',
            meta: {
              crumbTitle: 'router.ordinary_coupon',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/first_market/addCoupon'
              )
          },
          // 赠品
          {
            path: '/admin/home/main/gift',
            name: 'gift_view',
            meta: {
              crumbTitle: 'router.gift',
              meta: 'first_market_manage',
              category: 'first_market_manage',
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/gift/gift'
              )
          },
          // 会员管理系列子路由
          {
            path: '/admin/home/main/membershipList',
            name: 'user_list',
            meta: {
              crumbTitle: 'router.membershipList',
              meta: 'user_manger'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/user_manger/membershipList/membershipList'
              )
          },
          {
            path: '/admin/home/main/membershipInformation',
            name: 'membershipInformation',
            meta: {
              crumbTitle: 'router.memberEditorList',
              meta: 'user_manger',
              category: 'user_list'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/user_manger/membershipList/membershipInformation'
              )
          },
          {
            path: '/admin/home/main/receiveDetail',
            name: 'receiveDetail',
            meta: {
              crumbTitle: 'router.receiveDetail',
              meta: 'user_manger',
              category: 'user_list'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/user_manger/membershipList/receiveDetail'
              )
          },
          {
            path: '/admin/home/main/balanceDetail',
            name: 'balanceDetail',
            meta: {
              crumbTitle: 'router.balanceDetail',
              meta: 'user_manger',
              category: 'user_list'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/user_manger/membershipList/balanceDetail'
              )
          },
          {
            path: '/admin/home/main/integralDetail',
            name: 'integralDetail',
            meta: {
              crumbTitle: 'router.integralDetail',
              meta: 'user_manger',
              category: 'user_list'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/user_manger/membershipList/integralDetail'
              )
          },
          {
            path: '/admin/home/main/membershipIntroduction',
            name: 'user_import',
            meta: {
              crumbTitle: 'router.membershipIntroduction',
              meta: 'user_manger'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/user_manger/membershipIntroduction/membershipIntroduction'
              )
          },
          // 会员卡模块路由
          {
            path: '/admin/home/main/user_card',
            name: 'user_card',
            meta: {
              crumbTitle: 'router.userCard',
              meta: 'user_manger'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/user_manger/membershipCard/membershipCard'
              )
          },
          {
            path: '/admin/home/main/limitTimes',
            name: 'limitTimes',
            meta: {
              crumbTitle: 'router.limitTimes',
              meta: 'user_manger',
              category: 'user_card'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/user_manger/membershipCard/membershipCard'
              )
          },
          {
            path: '/admin/home/main/GradeCard',
            name: 'GradeCard',
            meta: {
              crumbTitle: 'router.GradeCard',
              meta: 'user_manger',
              category: 'user_card'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/user_manger/membershipCard/membershipCard'
              )
          },
          // 会员卡详情页
          {
            path: '/admin/home/main/membershipCardDetail',
            name: 'membershipCardDetail',
            meta: {
              crumbTitle: 'router.userCard',
              meta: 'user_manger',
              category: 'user_card'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/user_manger/membershipCard/membershipCardDetail'
              )
          },
          // 门店管理系列子路由
          {
            path: '/admin/home/main/store_manage',
            name: 'store_list',
            meta: {
              crumbTitle: '',
              meta: 'store_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/store_manage/store_manage'
              )
          },
          // 基础配置系列子路由
          {
            path: '/admin/home/main/base_manger/:isAuth',
            name: 'config_list',
            meta: {
              crumbTitle: 'router.base_manger',
              meta: 'base_manger'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/base_manger/base_manger'
              )
          }
        ]
      }
    ]
  },
  {
    path: '/admin/selectlinks',
    name: 'selectLinks',
    component: () => import('@/components/admin/selectLinks')
  }
]
export default routes
