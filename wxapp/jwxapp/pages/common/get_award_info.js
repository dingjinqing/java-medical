// 过滤需要的参数
function filterObj(obj, arr) {
  console.log(obj, 'obj--arr', arr, 'arr')

  if (typeof obj !== "object" || !Array.isArray(arr)) {
    throw new Error("参数格式不正确");
  }
  const result = {};
  Object.keys(obj)
    .filter(key => arr.includes(key))
    .forEach(key => {
      result[key] = obj[key];
    });
  return result;
}

module.exports = function getAwardInfo(awardInfo) {
  const needParams = {
    0: null,
    1: ['couponView'],
    2: ['couponView'],
    3: ['lotteryId'],
    4: ['account'],
    5: ['product'],
    6: ['scoreNumber'],
    7: ['customImage', 'customLink'],
  }
  let formatObj = {
    1: (() => {
      return {
        couponView: awardInfo.extContent && JSON.parse(awardInfo.extContent.coupon_detail)
      }
    }),
    2: (() => {
      return {
        couponView: awardInfo.extContent && JSON.parse(awardInfo.extContent.coupon_detail)
      }
    }),
    3: (() => {
      return {
        lotteryId: awardInfo.awardContent
      }
    }),
    4: (() => {
      return {
        account: awardInfo.awardContent
      }
    }),
    5: (() => {
      return {
        product: awardInfo.awardContent
      }
    }),
    6: (() => {
      return {
        scoreNumber: awardInfo.awardContent
      }
    }),
    7: (() => {
      return {
        customLink: awardInfo.awardContent,
        customImage: (awardInfo.extContent && awardInfo.extContent.customize_img_path) || null
      }
    })
  }
  const Type = {
    1: 1,
    2: 3,
    3: 7,
    4: 6,
    5: 4,
    6: 2
  }
  console.log(Type[awardInfo.awardType])
  console.log(formatObj[Type[awardInfo.awardType]]())
  return {
    giftInfo: {
      giftType: Type[awardInfo.awardType],
      awardInfo: {
        ...filterObj(formatObj[Type[awardInfo.awardType]](), needParams[Type[awardInfo.awardType]])
      }
    }
  }
}
