export const cn = {
  bargainType: '活动类型',
  bargainType1Tip: '砍到指定金额结算',
  bargainType2Tip: '砍到任意金额结算',
  actGoods: '活动商品',
  selectGoods: '选择商品',
  reselect: '重新选择',
  actGoodsTip: '所有参与砍价的商品，均需要用户将价格砍到底价后才可以砍价成功， 若某商品同一时间段内同时参与了砍价和拼团活动，则优先进行砍价活动',
  goodsName: '商品名称',
  goodsOriginalStock: '商品原库存',
  bargainStock: '砍价库存',
  goodsOriginalPrice: '商品原价',
  sttlementAmount: '结算金额',
  bargainReservePrice: '砍价底价',
  default0: '默认0元',
  sttlementAmountTip: '保存后不可编辑',
  singleBargainMoney: '单次帮砍金额',
  fixedMoney: '固定金额',
  randomMoney: '随机金额',
  getRandomMoneyBetween: '之间取随机数',
  expectToParticipateInBargaining: '期望参与砍价人次',
  people: '人',
  expectPeopleMin: '期望人次最少为3',
  expectPeopleTip: '填写人数为发起人发起砍价后，预计将价格砍到底价时需要参与砍价活动帮助该发起人进行砍价的用户数， 默认为100，保存后不可编辑',
  goodsFirstBargainProportion: '商品首次砍价可砍价比例区间',
  proportionIntervalTip: '比例必须在0~50%之间',
  proportionInterval: '用户发起砍价后，首次砍价可以砍掉的金额占商品价格的比例 ，该比例在填写区间内随机产生。 不填写则按照系统规则计算， 默认为空，为空表示不填写。 例如填写20%到50%，商品价格为100元，则用户发起砍价， 首次给自己砍价时，系统会随机取该 比例区间数字，例如随机为35%， 则该用户发起砍价后首次砍价金额为100*35%*（系统砍价系数）， 系统砍价系数按照系统逻辑计算。若系统砍价系数为0.5，则本次砍价金额为100*35%*0.5=17.5元。 即该用户给自己 可砍掉17.5元。',
  friendsBargainCoupon: '好友砍价优惠券',
  friendsBargainCouponTip: '向帮忙砍价的用户赠送优惠券，可促使帮砍用户在店铺内下单，提高交易量',
  addCoupon: '添加优惠券',
  couponLimitTip: '最多添加5张优惠券，已过期和已停用的优惠券不能添加',
  encouragementAward: '鼓励奖',
  encouragementAwardTip: '买家砍价失败后给予一定奖励，可提升买家复购'
}

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
  encouragementAwardTip: 'Buyers give certain rewards after failing to bargain, which can enhance buyer\'s repurchase'
}
