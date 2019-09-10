var spec = {
  bindSpecChange(e) {
    this._sel_buy = e.detail;
  },
  bindClickSpecCell(e) {
    this.showSpec();
  },
  bindCloseSpec(e) {
    this.setData({
      show_spec: this.data.show_spec = false
    });
  },
  showSpec(use_exchange_card, no_spec_show) {
    if (no_spec_show || this._sel_buy.has_spec) {
      this.setData({
        show_spec: true,
        exchang_block: use_exchange_card ? 1 : 0
      });
    }
  },

};
module.exports = spec;