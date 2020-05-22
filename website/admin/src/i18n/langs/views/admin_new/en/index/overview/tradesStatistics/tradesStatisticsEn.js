export const en = {
  // 地域分布国际化
  areaDistribute: 'Regional Distribution',
  selectMonth: 'Select Month',
  province: 'Top Provinces',
  paymentAmount: 'Payment Amount',
  paymentNumber: 'Number of payments',
  visitorNumber: 'Number of visitors',
  visitToPayRate: 'Visit-payment conversion rate',
  orderNumber: 'Order number',
  areaTipsList: [
    { title: 'Payment Amount', content: 'During the statistical time, the sum of all payment orders for the users visited by the region (the group is included in the payment amount at the time of formation; the delivery payment is included in the payment amount at the time of shipment, without excluding the refund amount)' },
    { title: 'Number of payments', content: 'The number of customers who place an order in the user and the payment is successful within the statistical time, and one person makes a number of payments as one person (excluding the refund order)' },
    { title: 'Number of visitors', content: 'the number of all visitors to the user during the statistical time' },
    { title: 'Visit-payment conversion rate', content: 'The number of payers/visitors in the territory during the statistics period' },
    { title: 'Order number', content: 'The number of orders received in the area during the statistics period' }
  ],

  // 标签成分分析国际化
  labelComponentsAnalysis: 'Label Component Analysis',
  timeSelect: 'Time Screening',
  startTime: 'Start Date',
  endTime: 'End Date',
  year: 'Year',
  month: 'Month',
  day: 'Day',
  label: 'Label',
  userNumber: 'User Number',
  hasMobileNumber: 'Number of customers with mobile number',
  payTimes: 'Number of payments',
  payMoney: 'Amount of payment (RMB)',
  payGoodsNumber: 'Number of payment items',
  timeRange: [
    { value: 1, label: 'The latest 1 day' },
    { value: 7, label: 'The latest 7 day' },
    { value: 30, label: 'The latest 30 day' },
    { value: 0, label: 'custom' }
  ],
  labelTipsList: [
    { title: 'Number of payments', content: ' In the statistical time, the number of successful payment orders for the customer under this label, one order corresponds to a unique order number (the group is included in the payment order when the group is formed; cash on delivery is being issued When the goods are included in the payment order, the refund order is not excluded)' },
    { title: 'Amount of payment (RMB).', content: 'The total amount of all payment orders of the customer under this label within the statistical time (the group will be included in the payment amount when the group is grouped; cash on delivery when the product is shipped Include payment amount, excluding refund amount)' },
    { title: 'The number of payment', content: 'In the statistical period, the number of customers who have placed orders and paid successfully under this label. One person who makes multiple payments is counted as one person (excluding refund orders)' },
    { title: 'Number of payment items', content: 'Sum of the number of products successfully ordered by the customer under this label during the statistical period (excluding refund orders)' },
    { title: 'Number of customers with mobile number', content: 'The number of customers who have placed a mobile phone number on this label and paid successfully (excluding the refund order) within the statistical period' }
  ]
}
