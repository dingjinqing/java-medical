var cache = {
  /**
   * 得到缓存值
   * @param {string} key
   * @returns {*}
   */
  getCache(key) {
    return wx.getStorageSync(key)
  },

  /**
   * 设置缓存
   * @param {string} key
   * @param value
   */
  setCache(key, value) {
    try {
      wx.setStorageSync(key, value);
    } catch (e) {
      console.log("setCache err:" + e);
    }
  },

  /**
   * 移除缓存
   * @param {string} key
   */
  removeCache(key) {
    try {
      wx.removeStorageSync(key)
    } catch (e) {
      console.log("removeCache err:" + e);
    }
  }

}

module.exports = cache;