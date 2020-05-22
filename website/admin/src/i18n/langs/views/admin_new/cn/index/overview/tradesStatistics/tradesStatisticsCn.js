export const cn = {
  // 地域分布国际化
  areaDistribute: '地域分布',
  selectMonth: '选择月',
  province: 'Top省份',
  paymentAmount: '付款金额',
  paymentNumber: '付款人数',
  visitorNumber: '访客人数',
  visitToPayRate: '访问-付款转化率',
  orderNumber: '订单数',
  areaTipsList: [
    { title: '付款金额', content: '统计时间内，该地域访问用户的所有付款订单金额之和（拼团在成团时计入付款金额；货到付款在发货时计入付款金额，不剔除退款金额）' },
    { title: '付款人数', content: '统计时间内，该地域访问用户中下单并且付款成功的客户数，一人多次付款记为一人（不剔除退款订单）' },
    { title: '访客数', content: '统计时间内，该地域访问用户的所有访客数' },
    { title: '访问-付款转化率', content: '统计时间内，该地域付款人数/该地域访客数' },
    { title: '订单数', content: '统计时间内，该地区收货订单数 ' }
  ],

  // 标签成分分析国际化
  labelComponentsAnalysis: '标签成分分析',
  timeSelect: '时间筛选',
  startTime: '开始日期',
  endTime: '结束日期',
  year: '年',
  month: '月',
  day: '日',
  label: '标签',
  userNumber: '用户数',
  hasMobileNumber: '有手机号客户数',
  payTimes: '付款笔数',
  payMoney: '付款金额（元）',
  payGoodsNumber: '付款商品件数',
  timeRange: [
    { value: 1, label: '最新1天' },
    { value: 7, label: '最新7天' },
    { value: 30, label: '最新30天' },
    { value: 0, label: '自定义' }
  ],
  labelTipsList: [
    { title: '付款笔数', content: '统计时间内，该标签下客户的成功付款订单数，一个订单对应唯一一个订单号（拼团在成团时计入付款订单；货到付款在发货时计入付款订单，不剔除退款订单）' },
    { title: '付款金额（元）', content: '统计时间内，该标签下客户的所有付款订单金额之和（拼团在成团时计入付款金额；货到付款在发货时计入付款金额，不剔除退款金额）' },
    { title: '付款人数', content: '统计时间内，该标签下客户下单并且付款成功的客户数，一人多次付款记为一人（不剔除退款订单）' },
    { title: '付款商品件数', content: '统计时间内， 该标签下客户成功付款订单的商品件数之和（不剔除退款订单）' },
    { title: '有手机号客户数', content: '统计时间内， 该标签下下单并且付款成功的有手机号码的客户数（不剔除退款订单）' }
  ]
}
