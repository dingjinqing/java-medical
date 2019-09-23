global.wxPage({
  onShow() {
    this.loadSetting(this._options);
  },
  onPullDownRefresh() {
    this.loadSetting(this._options);
  }
})