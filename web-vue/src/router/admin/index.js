import marketing from '@/router/admin/marketing'
import member from '@/router/admin/member'
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
              // 商品管理/运费模板/添加运费模板列表
              {
                path: '/admin/home/main/goodsManage/deliverTemplate/add',
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
              // 商品管理/运费模板/添加重量运费模板列表
              {
                path:
                  '/admin/home/main/goodsManage/deliverTemplate/weightListAdd',
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
              },
              // 商品管理/运费模板/运费模板编辑
              {
                path: '/admin/home/main/goodsManage/deliverTemplate/edit',
                name: 'deliverTemplateEdit',
                meta: {
                  crumbTitle: 'router.deliverTemplate',
                  meta: 'goods_manage',
                  category: 'deliver'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/deliverTemplate/deliverTemplateEdit'
                  )
              }
            ]
          },
          // 商品管理/商家分类管理
          {
            path: '/admin/home/main/goodsManage/businessSortManagement',
            redirect:
              '/admin/home/main/goodsManage/businessSortManagement/list',
            name: 'sort',
            meta: {
              crumbTitle: 'router.businessSortManagement',
              meta: 'goods_manage'
            },
            component: () =>
              import(
                '@/view/admin/index/leftNavComponents/goods_manage/businessSortManagement/businessSortManagement'
              ),
            children: [
              //  商品管理/商家分类管理/分类列表
              {
                path:
                  '/admin/home/main/goodsManage/businessSortManagement/list',
                name: 'list',
                meta: {
                  crumbTitle: 'router.businessSortManagement',
                  meta: 'goods_manage',
                  category: 'sort'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/businessSortManagement/businessSortList'
                  )
              },
              {
                path:
                  '/admin/home/main/goodsManage/businessSortManagement/addSort',
                name: 'addSort',
                meta: {
                  crumbTitle: 'router.businessSortManagement',
                  meta: 'goods_manage',
                  category: 'sort'
                },
                component: () =>
                  import(
                    '@/view/admin/index/leftNavComponents/goods_manage/businessSortManagement/addSort'
                  )
              }
            ]
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
              meta: 'goods_manage',
              category: 'brand'
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
        ].concat(marketing, member)
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
