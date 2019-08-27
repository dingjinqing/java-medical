const routes = [
  {
    path: '/admin/home',
    name: 'adminHome',
    component: () => import('@/view/admin/home'),
    children: [
      {
        path: '/admin/home/shopMain',
        name: 'shopMain',
        component: () => import('@/view/admin/shopMain')
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
          // 商品管理/全部商品
          {
            path: '/admin/home/main/goodsManage/allGoods',
            redirect: '/admin/home/main/goodsManage/goodsForSale',
            name: 'sale_on',
            meta: {
              crumbTitle: 'router.goodsForSale',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/allGoods/allGoods'
              ),
            children: [
              // 商品管理/出售中商品
              {
                path: '/admin/home/main/goodsManage/goodsForSale',
                name: 'sale_on',
                meta: {
                  crumbTitle: 'router.goodsForSale',
                  meta: 'goods_manage',
                  category: 'sale_on'
                },

                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/allGoods/saleOn/saleOn'
                  )
              },
              // 商品管理/已售罄
              {
                path: '/admin/home/main/goodsManage/soldOutGoods',
                name: 'sale_end',
                meta: {
                  crumbTitle: 'router.soldOutGoods',
                  meta: 'goods_manage',
                  category: 'sale_on'
                },

                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/allGoods/saleOn/saleOn'
                  )
              },
              // 商品管理/仓库中
              {
                path: '/admin/home/main/goodsManage/goodsInTheWarehouse',
                name: 'sale_off',
                meta: {
                  crumbTitle: 'router.goodsInTheWarehouse',
                  meta: 'goods_manage',
                  category: 'sale_on'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/allGoods/saleOff/saleOff'
                  )
              }
            ]
          },
          // 商品管理 添加商品
          {
            path: '/admin/home/main/goodsManage/addingGoods',
            name: 'goods_add',
            meta: {
              crumbTitle: 'router.addingGoods',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoods'
              )
          },

          // 商品管理/运费模板
          {
            path: '/admin/home/main/goodsManage/deliverTemplate',
            name: 'deliver',
            redirect: '/admin/home/main/goodsManage/deliverTemplate/list',
            meta: {
              crumbTitle: 'router.deliverTemplate',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/deliverTemplate'
              ),
            children: [
              // 商品管理/运费模板/运费模板列表
              {
                path: '/admin/home/main/goodsManage/deliverTemplate/list',
                name: 'deliverTemplateList',
                meta: {
                  crumbTitle: 'router.deliverTemplate',
                  meta: 'goods_manage',
                  category: 'deliver'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/deliverTemplateList'
                  )
              },
              // 商品管理/运费模板/重量运费模板列表
              {
                path: '/admin/home/main/goodsManage/deliverTemplate/weightList',
                name: 'deliverTemplateWeightList',
                meta: {
                  crumbTitle: 'router.deliverTemplate',
                  meta: 'goods_manage',
                  category: 'deliver'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/deliverTemplateWeightList'
                  )
              },
              // 商品管理/运费模板/添加运费模板
              {
                path: '/admin/home/main/goodsManage/deliverTemplate/Add',
                name: 'deliverTemplateAdd',
                meta: {
                  crumbTitle: 'router.deliverTemplate',
                  meta: 'goods_manage',
                  category: 'deliver'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/deliverTemplateAdd'
                  )
              },
              // 商品管理/运费模板/添加重量运费模板
              {
                path: '/admin/home/main/goodsManage/deliverTemplate/weightAdd',
                name: 'deliverTemplateWeightAdd',
                meta: {
                  crumbTitle: 'router.deliverTemplate',
                  meta: 'goods_manage',
                  category: 'deliver'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/deliverTemplateWeightAdd'
                  )
              }
            ]
          },
          // 商品管理/商家分类管理
          {
            path: '/admin/home/main/goodsManage/businessSortManagement',
            name: 'sort',
            meta: {
              crumbTitle: 'router.businessSortManagement',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/businessSortManagement/goodsSort'
              )
          },
          // 商品管理/品牌管理
          {
            path: '/admin/home/main/goodsManage/brandManagement',
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
          // 商品管理/品牌管理/添加品牌
          {
            path: '/admin/home/main/goodsManage/addBrand',
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
          // 商品管理/评价管理
          {
            path: '/admin/home/main/goodsManage/evaluationManagement',
            name: 'comment',
            meta: {
              crumbTitle: 'router.evaluationManagement',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/evaluationManagement/evaluationManagement'
              )
          },
          // 商品管理/商品推荐
          {
            path: '/admin/home/main/goodsManage/goodsRecommend',
            name: 'recommend',
            meta: {
              crumbTitle: 'router.evaluationManagement',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/evaluationManagement/evaluationManagement'
              )
          },

          // 商品管理/商品标签
          {
            path: '/admin/home/main/goodsManage/goodsLabel',
            name: 'label',
            meta: {
              crumbTitle: 'router.goodsLabel',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/goodsLabel/goodsLabel'
              )
          },
          // 商品管理/商品导入
          {
            path: '/admin/home/main/goodsManage/goodsImport',
            name: 'goods_import',
            meta: {
              crumbTitle: 'router.goodsImport',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/goodsImport/goodsImport'
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
          // 分享有礼活动
          {
            path: '/admin/home/main/sharePoliteList',
            name: 'share_polite',
            meta: {
              crumbTitle: 'router.share_polite',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/sharePolite/sharePoliteList'
              )
          },
          // 分享有礼活动-添加
          {
            path: '/admin/home/main/addSharePolite',
            name: 'share_polite',
            meta: {
              crumbTitle: 'router.share_polite',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/sharePolite/addSharePolite'
              )
          },
          // 分享有礼活动-领取明细
          {
            path: '/admin/home/main/sharePoliteDetail',
            name: 'share_polite_detail',
            meta: {
              crumbTitle: 'router.share_polite_detail',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/sharePolite/sharePoliteDetail'
              )
          },
          // 砍价
          {
            path: '/admin/home/main/bargain',
            name: 'bargain',
            meta: {
              crumbTitle: 'router.bargain',
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
          // 分销
          {
            path: '/admin/home/main/distribution',
            name: 'distribution_info',
            meta: {
              crumbTitle: 'router.distribution_info',
              meta: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/distribution/distribution'
              )
          },
          // // 组团瓜分积分
          // {
          //   path: '/admin/home/main/divideIntegral',
          //   name: 'pin_integration',
          //   meta: {
          //     crumbTitle: 'router.pin_integration',
          //     meta: 'first_market_manage'
          //   },
          //   component: () =>
          //     import(
          //       '@/view/admin/index/leftNavComponents/first_market_manage/divideIntegral/divideIntegral'
          //     )
          // },
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
          // 优惠券礼包
          {
            path: '/admin/home/main/couponPackage',
            name: 'coupon_package',
            meta: {
              crumbTitle: 'router.coupon_Package',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/couponPackage/couponPackage'
              )
          },
          // 添加优惠券礼包
          {
            path: '/admin/home/main/couponPackage/add',
            name: 'coupon_Package_add',
            meta: {
              crumbTitle: 'router.coupon_Package_add',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/couponPackage/addCouponPackage'
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
          // 拼团抽奖活动
          {
            path: '/admin/home/main/lotteryDraw',
            name: 'group_draw',
            meta: {
              crumbTitle: 'router.group_draw',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/lotteryDraw/lotteryDraw'
              )
          },
          // 幸运大抽奖活动
          {
            path: '/admin/home/main/luckyDraw',
            name: 'lottery_activity',
            meta: {
              crumbTitle: 'router.lottery_activity',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/luckyDraw/luckyDraw'
              )
          },
          // 好友代付
          {
            path: '/admin/home/main/friendPay',
            name: 'friend_pay',
            meta: {
              crumbTitle: 'router.friend_pay',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/first_market/friendPay'
              )
          },
          // 赠品
          {
            path: '/admin/home/main/gift',
            name: 'gift_view',
            meta: {
              crumbTitle: 'router.gift',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/gift/gift'
              )
          },
          // 创建赠品活动
          {
            path: '/admin/home/main/gift/add',
            name: 'gift_add_view',
            meta: {
              crumbTitle: 'router.gift_add',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/gift/addGift'
              )
          },
          // 修改赠品活动
          {
            path: '/admin/home/main/gift/add/:id',
            name: 'gift_edit_view',
            meta: {
              crumbTitle: 'router.gift_edit',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/gift/addGift'
              )
          },
          // 赠送明细
          {
            path: '/admin/home/main/gift/giftDetail/:id',
            name: 'gift_detail_view',
            meta: {
              crumbTitle: 'router.gift_detail',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/gift/giftDetail.vue'
              )
          },
          // 瓜分积分活动
          {
            path: '/api/admin/market/integration/list',
            name: 'pin_integration',
            meta: {
              crumbTitle: 'router.pin_integration',
              meta: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/groupIntegration/groupIntegrationList'
              )
          },
          // 创建瓜分积分活动
          {
            path: '/api/admin/market/integration/add',
            name: 'group_integration_add',
            meta: {
              crumbTitle: 'router.pin_integration_add',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/groupIntegration/groupIntegrationAdd'
              )
          },
          // 编辑瓜分积分活动
          {
            path: '/api/admin/market/integration/edit/:id',
            name: 'group_integration_edit',
            meta: {
              crumbTitle: 'router.pin_integration_edit',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/groupIntegration/groupIntegrationAdd'
              )
          },
          // 瓜分积分活动--参与用户明细
          {
            path: '/api/admin/market/integration/detail/:id',
            name: 'group_integration_detail',
            meta: {
              crumbTitle: 'router.pin_integration_detail',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/groupIntegration/groupIntegrationDetail'
              )
          },
          // 瓜分积分活动--成团明细
          {
            path: '/api/admin/market/integration/success/:id',
            name: 'group_integration_success',
            meta: {
              crumbTitle: 'router.pin_integration_success',
              meta: 'first_market_manage',
              category: 'first_market_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/first_market_manage/groupIntegration/groupIntegrationSuccess'
              )
          },
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
