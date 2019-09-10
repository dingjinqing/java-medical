var _pages = {};

module.exports = {
  isPage(o) {
    return o.route
  },
  patchPageId(o){
    if (typeof o.getPageId != 'function') {
      o.getPageId = function () {
        return this.__wxWebviewId__;
      }
    }
  },
  addObj(o) {
    this.patchPageId(o);
    var pageId = o.getPageId();
    if (!_pages[pageId]) {
      _pages[pageId] = {
        page: null,
        components: []
      };
    }
    if (this.isPage(o)) {
      _pages[pageId].page = o;
    } else {
      if(_pages[pageId].page == null){
        var page_list = getCurrentPages();
        for(var i in page_list){
          this.patchPageId(page_list[i]);
          if (page_list[i].getPageId() == pageId){
            _pages[pageId].page = page_list[i];
          }
        }
      }
      
      _pages[pageId].components.push(o)
    }
  },
  removeObj(o) {
    if (this.isPage(o)) {
      delete _pages[o.getPageId()];
    } else {
      var pageObj = _pages[o.getPageId()];
      if (pageObj) {
        for (var i in pageObj.components) {
          if (pageObj.components[i] == o) {
            pageObj.components.splice(i, 1);
            break;
          }
        }
      }
    }
  },
  getPage(obj) {
    this.patchPageId(obj);
    var pageObj = _pages[obj.getPageId()];
    return pageObj ? pageObj.page : null;
  },
  getComponents(obj) {
    var pageObj = _pages[obj.getPageId()];
    return pageObj ? pageObj.components : {};
  },
  notify(fromObj, path, eventName, args) {
    var pageObj = _pages[fromObj.getPageId()];
    if (typeof pageObj == undefined) return;

    var page = pageObj.page;
    var components = pageObj.components;

    if (!this.isPage(fromObj) && (path == undefined || path === "*" || path == page.is)) {
      if (typeof page[eventName] == 'function') {
        page[eventName](fromObj, ...args)
      }
      if (typeof page.onEvent == 'function') {
        page.onEvent(fromObj, eventName, ...args)
      }
    }

    for (var i in components) {
      var c = components[i];
      if (fromObj != c && (path == undefined || path === "*" || path == c.is)) {
        if (typeof c[eventName] == 'function') {
          c[eventName](fromObj, ...args)
        }
        if (typeof c.onEvent == 'function') {
          c.onEvent(fromObj, eventName, ...args)
        }
      }
    }
  },
};
