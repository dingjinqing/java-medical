var addressList = []; //地址列表
var zipCodeList = []; //邮编列表
var zipCode = require("./zipCode.js");
var util = require('../../util')

console.log("正在加载省市区数据...");

const wx_getaddress = () => {
  return new Promise((resolve, reject) => {
    let array = [];
    let index = 0;
    let length = 7;
    // console.log('共计' + length + '条数据')
    for (let i = 0; i < length; i++) {
      if (wx.getStorageSync(i + "")) {
        index++;
        // console.log('第' + index + '条数据在缓存中读取完毕')
        array = [...array, ...JSON.parse(wx.getStorageSync(i + ""))];
        if (index == length) {
          resolve(array);
        }
      } else {
        setTimeout(() => {
          util.api('/api/wxapp/address/database', res => {
            if (res.error === 0) {
              index++;
              wx.setStorage({
                key: i + "",
                data: res.content
              });
              //  console.log('第' + index + '条数据加载完毕')
              if (res.content) {
                array = [...array, ...JSON.parse(res.content)];
              }
              if (index == length) {
                resolve(array);
              }
            }
          }, {index:i})
          // wx.request({
          //   url: "https://wangzc.wang/addressJson/" + i,
          //   method: "GET",
          //   success: function(res) {
          //     index++;
          //     wx.setStorage({
          //       key: i + "",
          //       data: JSON.stringify(res.data)
          //     });
          //     console.log('第' + index + '条数据加载完毕', res.data)
          //     array = [...array, ...res.data];
          //     if (index == length) {
          //       resolve(array);
          //     }
          //   }
          // });
        }, 500 * i);
      }
    }
  });
};

function getData() {
  wx_getaddress().then(res => {
    addressList = res;//地址赋值；这里可以加入自己写的接口进行赋值
    addressList.forEach(item => {
      formatAddresList(item, 1, "");
    });
    console.log(addressList);
    zipCodeList = zipCodeFormat(zipCode.variable);
    console.log("省市区数据挂载完毕！！");
  });
}
getData();
/**
 * 地址数据处理
 * @param addressList-各级数据对象
 * @param index-对应的省/市/县区/街道
 * @param province-只有直辖市会处理为  北京市北京市
 * @returns <array>
 */
function formatAddresList(addressList, index, province) {
  if (index === 1) {
    //省
    addressList.province = addressList.name;
  }
  if (index === 2) {
    //市
    if (addressList.name == "市辖区") {
      addressList.name = province.name;
    }
    addressList.city = addressList.name;
  }
  if (index === 3) {
    //区或者县
    addressList.county = addressList.name;
  }
  if (index === 4) {
    //街道
    addressList.street = addressList.name;
  }
  if (addressList.children) {
    index++;
    addressList.children.forEach(res => {
      formatAddresList(res, index, addressList);
    });
  }
}
/**
 * 解析邮编
 * @param
 * @returns <array>
 */
function zipCodeFormat(zipCode) {
  let list = [];
  zipCode.forEach(el => {
    if (el.child) {
      el.child.forEach(event => {
        if (event.child) {
          event.child.forEach(element => {
            list.push(element.zipcode);
          });
        }
      });
    }
  });
  return list;
}

var smartObj = {};
/**
 * 解析邮编
 * @param event识别的地址
 * @returns <obj>
 */
function smart(event) {
  let obj = {};
  event = stripscript(event); //过滤特殊字符
  let copyaddress = JSON.parse(JSON.stringify(event));
  console.log(event);
  copyaddress = copyaddress.split(/\s+/);
  console.log(copyaddress);
  
  copyaddress.forEach((res, index) => {
    if (res) {
      if (res === '收货人') {
        obj.name = copyaddress[index+1];
        return;
      } else if (res === '手机号码') {
        obj.phone = copyaddress[index+1];
        return;
      } else if (res === '详细地址') {
        obj.address = copyaddress[index+1];
        return;
      } else if (res === '所在地区') {
        return;
      } else if (res.indexOf('邮编') > -1||res.indexOf('邮政编码') > -1) {
        return;
      }
      let addressObj = smatrAddress(res, obj); // 快递信息
      obj = Object.assign(obj, addressObj); // 循环连接
    }
  });
  return obj;
}

/**
 * 解析快递信息
 * @param {*} event 
 */
function smatrAddress(event, obj) {
  smartObj = {};
  let address = event;
  address=  event.replace(/[\s-]/g, ''); //去除空格
  // address = stripscript(address); //过滤特殊字符

  //身份证号匹配
  if (IdentityCodeValid(address)) {
    smartObj.idCard = address;
    address = address.replace(address, "");
  }

  //电话匹配
  let phone = address.match(
    /(86-[1][0-9]{10}) | (86[1][0-9]{10})|([1][0-9]{10})/g
  );
  if (phone) {
    smartObj.phone = phone[0];
    address = address.replace(phone[0], "");
  }

  //邮编匹配
  zipCodeList.forEach(res => {
    if (address.indexOf(res) != -1) {
      let num = address.indexOf(res);
      let code = address.slice(num, num + 6);
      smartObj.zipCode = code;
      address = address.replace(code, "");
    }
  });

  let matchAddress = "";
  //省匹配 比如输入北京市朝阳区，会用北  北京  北京市 北京市朝 以此类推在addressList里的province中做匹配，会得到北京市  河北省 天津市等等；
  let matchProvince = []; //粗略匹配上的省份
  // for (let begIndex = 0; begIndex < address.length; begIndex++) {
  for (let endIndex = 0; endIndex < address.length; endIndex++) {
    //  if (endIndex > begIndex) {
    matchAddress = address.slice(0, endIndex + 2);
    addressList.forEach(res => {
      if (res["province"].indexOf(matchAddress) != -1) {
        matchProvince.push({
          province: res.province,
          provinceCode: res.code,
          matchValue: matchAddress,
          children: res.children
        });
      }
    });
    if (endIndex > matchProvince.length+1) {
      break;
    }
  }

  //统计筛选初略统计出的省份
  matchProvince.forEach(res => {
    res.index = 0;
    matchProvince.forEach(el => {
      if (res.province == el.province) {
        el.index++;
        if (res.matchValue.length > el.matchValue.length) {
          el.matchValue = res.matchValue;
        }
      }
    });
  });
  let selectedProvince = {}
  if (matchProvince.length != 0) {
    let province = matchProvince.reduce((p, v) => (p.index < v.index ? v : p));
    selectedProvince = province
    smartObj.province = province.province;
    smartObj.provinceCode = province.provinceCode;
    address = address.replace(province.matchValue, "");
  }
  //市查找
  let matchCity = []; //粗略匹配上的市
  matchAddress = "";
  for (let endIndex = 0; endIndex < address.length; endIndex++) {
    matchAddress = address.slice(0, endIndex + 2);
    if (selectedProvince.children) {
      selectedProvince.children.forEach(res => {
        if (res["city"].indexOf(matchAddress) != -1) {
          matchCity.push({
            city: res.city,
            cityCode: res.code,
            matchValue: matchAddress,
            children: res.children
          });
        }
      });
    } else {
      addressList.forEach(el => {
        if (el.code == smartObj.provinceCode || !smartObj.provinceCode) {
          el.children.forEach(res => {
            if (res["city"].indexOf(matchAddress) != -1) {
              matchCity.push({
                city: res.city,
                cityCode: res.code,
                matchValue: matchAddress,
                province: el.province,
                provinceCode: el.code
              });
            }
          });
        }
      });
    }

    if (endIndex > matchCity.length) {
      break;
    }
  }

  //统计筛选初略统计出的市
  matchCity.forEach(res => {
    res.index = 0;
    matchCity.forEach(el => {
      if (res.city == el.city) {
        el.index++;
        if (res.matchValue.length > el.matchValue.length) {
          el.matchValue = res.matchValue;
        }
      }
    });
  });
  let selectedCity = {}
  if (matchCity.length != 0) {
    let city = matchCity.reduce((p, v) => (p.index < v.index ? v : p));
    selectedCity = city
    smartObj.city = city.city;
    smartObj.cityCode = city.cityCode;
    if (!smartObj.province) {
      smartObj.province = city.province;
      smartObj.provinceCode = city.provinceCode;
    }
    address = address.replace(city.matchValue, "");
  }

  //区县查找
  let matchCounty = []; //粗略匹配上的区县
  matchAddress = "";
  for (let endIndex = 0; endIndex < address.length; endIndex++) {
    
    matchAddress = address.slice(0, endIndex + 2);
    if (selectedCity.children) {
      selectedCity.children.forEach(res => {
        if (res["county"].indexOf(matchAddress) != -1) {
          //省/市  || 省
          if (res.code.slice(0, 2) == smartObj.provinceCode) {
            matchCounty.push({
              county: res.county,
              countyCode: res.code,
              matchValue: matchAddress,
              children: res.children
            });
          }
        }
      });
    } else {
      addressList.forEach(el => {
        el.children.forEach(item => {
          //  if (item.name == smartObj.city) {
          item.children.forEach(res => {
            if (res["county"].indexOf(matchAddress) != -1) {
              //省/市  || 省
              if (smartObj.province) {
                if (res.code.slice(0, 2) == smartObj.provinceCode) {
                  matchCounty.push({
                    county: res.county,
                    countyCode: res.code,
                    city: item.city,
                    cityCode: item.code,
                    matchValue: matchAddress,
                    province: el.province,
                    provinceCode: el.code
                  });
                }
              } else if (!smartObj.province && !smartObj.city) {
                matchCounty.push({
                  county: res.county,
                  countyCode: res.code,
                  city: item.city,
                  cityCode: item.code,
                  matchValue: matchAddress,
                  province: el.province,
                  provinceCode: el.code
                });
              }
            }
          });
        });
      });
    }
    
    if (endIndex > matchCounty.length) {
      break;
    }
  }
  //统计筛选初略统计出的区县
  matchCounty.forEach(res => {
    res.index = 0;
    matchCounty.forEach(el => {
      if (res.city == el.city) {
        el.index++;
        if (res.matchValue.length > el.matchValue.length) {
          el.matchValue = res.matchValue;
        }
      }
    });
  });
  let selectedCounty = {}
  if (matchCounty.length != 0) {
    let city = matchCounty.reduce((p, v) => (p.index < v.index ? v : p));
    selectedCounty = city
    smartObj.county = city.county;
    smartObj.countyCode = city.countyCode;
    if (!smartObj.province) {
      smartObj.province = city.province;
      smartObj.provinceCode = city.provinceCode;
    }
    if (!smartObj.city) {
      smartObj.city = city.city;
      smartObj.cityCode = city.cityCode;
    }
    address = address.replace(city.matchValue, "");
  }

  //街道查找
  let matchStreet = []; //粗略匹配上的街道查
  matchAddress = "";
  for (let endIndex = 0; endIndex < address.length; endIndex++) {
    
    matchAddress = address.slice(0, endIndex + 3);
    if (selectedCounty.children) {
      selectedCounty.children.forEach(item => {
        if (item["street"].indexOf(matchAddress) != -1) {
          matchStreet.push({
            street: item.street,
            streetCode: item.code,
            matchValue: matchAddress
          });
        }
      });
    } else {
      addressList.forEach(el => {
        el.children.forEach(element => {
          if (element.name == smartObj.city) {
            element.children.forEach(item => {
              if (item.name == smartObj.county) {
                item.children.forEach(res => {
                  if (res["street"].indexOf(matchAddress) != -1) {
                    matchStreet.push({
                      street: res.street,
                      streetCode: res.code,
                      matchValue: matchAddress
                    });
                  }
                });
              }
            });
          }
        });
      });
    }
  }

  //统计筛选初略统计出的区县
  matchStreet.forEach(res => {
    res.index = 0;
    matchStreet.forEach(el => {
      if (res.city == el.city) {
        el.index++;
        if (res.matchValue.length > el.matchValue.length) {
          el.matchValue = res.matchValue;
        }
      }
    });
  });

  if (matchStreet.length != 0) {
    let city = matchStreet.reduce((p, v) => (p.index < v.index ? v : p));
    smartObj.street = city.street;
    smartObj.streetCode = city.streetCode;
    address = address.replace(city.matchValue, "");
  }
  
  // 详细地址
  if ((obj.province||smartObj.province) && (!obj.address || address == obj.address)) {
    smartObj.address = address;
    address = address.replace(address, "");
  }

  // 收货人
  if (address) {
    smartObj.name = address
  }

  return smartObj;
}
////过滤特殊字符
function stripscript(s) {
  // 座机号
  s = s.replace(/(\d{3})-(\d{4})-(\d{4})/g, "$1$2$3");
  s = s.replace(/(\d{3}) (\d{4}) (\d{4})/g, "$1$2$3");
  var pattern = new RegExp(
    "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“’。，、？-]"
  );
  var rs = "";
  for (var i = 0; i < s.length; i++) {
    rs = rs + s.substr(i, 1).replace(pattern, " ");
  }
  rs = rs.replace(/[\r\n]/g, " ");
  return rs;
}

function IdentityCodeValid(code) {
  let pass;
  var city = {
    11: "北京",
    12: "天津",
    13: "河北",
    14: "山西",
    15: "内蒙古",
    21: "辽宁",
    22: "吉林",
    23: "黑龙江 ",
    31: "上海",
    32: "江苏",
    33: "浙江",
    34: "安徽",
    35: "福建",
    36: "江西",
    37: "山东",
    41: "河南",
    42: "湖北 ",
    43: "湖南",
    44: "广东",
    45: "广西",
    46: "海南",
    50: "重庆",
    51: "四川",
    52: "贵州",
    53: "云南",
    54: "西藏 ",
    61: "陕西",
    62: "甘肃",
    63: "青海",
    64: "宁夏",
    65: "新疆",
    71: "台湾",
    81: "香港",
    82: "澳门",
    91: "国外 "
  };
  var tip = "";
  pass = true;

  if (!code || !/^\d{17}(\d|X)$/i.test(code)) {
    tip = "身份证号格式错误";
    pass = false;
  } else if (!city[code.substr(0, 2)]) {
    tip = "地址编码错误";
    pass = false;
  } else {
    //18位身份证需要验证最后一位校验位
    if (code.length == 18) {
      code = code.split("");
      //∑(ai×Wi)(mod 11)
      //加权因子
      var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
      //校验位
      var parity = [1, 0, "X", 9, 8, 7, 6, 5, 4, 3, 2];
      var sum = 0;
      var ai = 0;
      var wi = 0;
      for (var i = 0; i < 17; i++) {
        ai = code[i];
        wi = factor[i];
        sum += ai * wi;
      }
      var last = parity[sum % 11];
      if (parity[sum % 11] != code[17]) {
        tip = "校验位错误";
        pass = false;
      }
    }
  }
  return pass;
}

module.exports = {
  method: smart,
  getData: getData
};