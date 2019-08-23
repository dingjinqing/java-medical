var _pages = {};

export var pages = {
  isPage(o) {
    return o.route
  },
  addObj(o) {
    var pageId = o.getPageId();
    if (!_pages[pageId]) {
      _pages[pageId] = {
        page: o,
        components: {}
      };
    }
    if (this.isPage(o)) {
      _pages[pageId].page = o;
    } else {
      _pages[pageId].components[o.__wxWebviewId__] = o;
    }
  },
  removeObj(o) {
    if (this.isPage(o)) {
      delete _pages[o.getPageId()];
    } else {
      if (_pages[o.getPageId()]) {
        delete _pages[o.getPageId()].components[o.__wxWebviewId__];
      }
    }
  },
  notify(fromObj, path, eventName,args) {
    var pageObj = _pages[fromObj.getPageId()];
    if (typeof pageObj == undefined) return;

    var page = pageObj.page;
    var components = pageObj.components;

    if (!this.isPage(fromObj) && typeof page.onEvent == 'function') {
      if (path == undefined || path === "*" || path == page.is) {
        page.onEvent(fromObj, eventName, ...args)
      }
    }

    for (var i in components) {
      var c = components[i];
      if (fromObj != c && typeof c.onEvent == 'function') {
        if (path == undefined || path === "*" || path == c.is) {
          c.onEvent(fromObj, eventName, ...args)
        }
      }
    }
  },
};
