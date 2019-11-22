export const en = {
  bargainType: 'bargain type',
  bargainType1Tip: 'Cut to the specified amount',
  bargainType2Tip: 'Cut to any amount',
  actGoods: 'activity goods',
  selectGoods: 'select goods',
  reselect: 'reselect',
  actGoodsTip: 'All goods that participate in bargaining require the user to cut the price to the bottom price before they can successfully bargain. If a product participates in bargaining and grouping activities at the same time, priority is given to bargaining',
  goodsName: 'goods name',
  goodsOriginalStock: 'goods original stock',
  bargainStock: 'bargain stock',
  goodsOriginalPrice: 'goods original price',
  sttlementAmount: 'sttlement amount',
  bargainReservePrice: 'bargain reserve price',
  default0: 'default 0',
  sttlementAmountTip: 'cannot be edited after saving',
  singleBargainMoney: 'single cut amount',
  fixedMoney: 'fixed amount',
  randomMoney: 'random amount',
  getRandomMoneyBetween: 'get random amount between',
  expectToParticipateInBargaining: 'expect to participate in bargaining',
  people: 'people',
  expectPeopleMin: 'expect a minimum of 3 people',
  expectPeopleTip: 'The number of users is the number of users who are expected to participate in the bargaining activity to help the sponsor to bargain when the price is cut by the sponsor. The default is 100. It cannot be edited after saving',
  goodsFirstBargainProportion: 'the first price of goods can be bargained',
  proportionIntervalTip: 'The ratio must be between 0 and 50%',
  proportionInterval: 'After the user initiates the bargaining, the amount that the first bargain can be cut off accounts for the proportion of the commodity price, and the ratio is randomly generated within the filling interval. If it is not filled in, it will be calculated according to the system rules. The default is empty. If it is empty, it means no filling. For example, if you fill in 20% to 50% and the price of the product is 100 yuan, the user will start bargaining. When you bargain for the first time, the system will randomly take the ratio range number, for example, 35% randomly, then the user will start the bargain for the first time. The bargaining amount is 100*35%* (system bargaining factor), and the system bargaining factor is calculated according to system logic. If the system bargaining factor is 0.5, the bargaining amount is 100*35%*0.5=17.5 yuan. That is, the user can cut himself off 17.5 yuan',
  friendsBargainCoupon: 'friends bargain coupon',
  friendsBargainCouponTip: 'Giving coupons to users who help to bargain, can help cut users to place orders in the store and increase trading volume',
  addCoupon: 'add coupons',
  couponLimitTip: 'Add up to 5 coupons, expired and disabled coupons cannot be added',
  encouragementAward: 'encouragement award',
  encouragementAwardTip: 'Buyers give certain rewards after failing to bargain, which can enhance buyer\'s repurchase',

  // 校验
  vaildGoodsSelect: 'Please select an event product!',
  vaildStock: 'Please fill in the bargain stock!',
  vaildExpectationPrice: 'Please fill in the price of the bargain!',
  vaildExpectationNumber: 'Please select an event product!',
  vaildExpectationNumberMin: 'Expect to participate in the bargaining minimum of 3!',
  vaildProportionalinterval1: 'Please fill in the first bargain price to bargain!',
  vaildProportionalinterval2: 'The first bargaining price can be set in the wrong price range!',
  vaildCalculatedAmount1: 'Please fill in the settlement amount!',
  vaildCalculatedAmount2: 'The settlement amount is incorrectly filled!',
  vaildFixedMoney: 'Please fill in the fixed amount!',
  vaildRandomAmount1: 'Please fill in the random help cut amount!',
  vaildRandomAmount2: 'Randomly help cut the amount to fill in the wrong!'
}
