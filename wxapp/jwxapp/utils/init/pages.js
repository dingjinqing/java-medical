var helper = require("../base/helper.js")
var _pages = {};

module.exports = {
  isPage(o) {
    return o.route
  },
  patchPageId(o) {
    if (typeof o.getPageId != 'function') {
      o.getPageId = function () {
        return this.__wxWebviewId__;
      }
    }
    return o;
  },
  initPageId(pageId) {
    if (!_pages[pageId]) {
      _pages[pageId] = {
        page: null,
        components: [],
        events: {},
      };
    }
  },
  addObj(o) {
    this.patchPageId(o);
    var pageId = o.getPageId();
    this.initPageId(pageId);
    if (this.isPage(o)) {
      _pages[pageId].page = o;
    } else {
      if (_pages[pageId].page == null) {
        var page_list = getCurrentPages();
        for (var i in page_list) {
          this.patchPageId(page_list[i]);
          if (page_list[i].getPageId() == pageId) {
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

  $on(fromObj, event, fn) {
    var pageId = this.patchPageId(fromObj).getPageId();
    this.initPageId(pageId);
    if (helper.isArr(event)) {
      event.forEach((item) => {
        if (helper.isStr(item)) {
          this.$on(fromObj, item, fn);
        } else if (helper.isObject(item)) {
          this.$on(fromObj, item.event, item.fn);
        }
      })
    } else {
      (_pages[pageId].events[event] || (_pages[pageId].events[event] = [])).push({ fn: fn, obj: fromObj });
    }
  },
  $off(fromObj, event, fn) {
    var pageId = this.patchPageId(fromObj).getPageId();
    if (!event && !fn) return;

    if (!_pages[pageId]) return;

    if (helper.isArr(event)) {
      event.forEach((item) => {
        if (helper.isStr(item)) {
          this.$off(fromObj, item, fn);
        } else if (helper.isObj(item)) {
          this.$off(fromObj, item.event, item.fn);
        }
      });
      return;
    }

    let evts = _pages[pageId].events[event];
    if (!evts) return;
    let i = evts.length;
    while (i--) {
      let tmp = evts[i];
      if (tmp.obj == fromObj && (!fn || tmp.fn === fn)) {
        evts.splice(i, 1);
      }
    }
  },
  $emit(fromObj, event) {
    var pageId = this.patchPageId(fromObj).getPageId();
    this.initPageId(pageId);
    let lowerCaseEvent = event.toLowerCase();
    let args = helper.toArray(arguments, 2);
    let evts = _pages[pageId].events[event];
    if (!evts) return;
    evts.forEach(evt => {
      try {
        evt.fn.apply(evt.obj, args);
      } catch (e) {
        console.error(e, evt.obj, `event handler for "${event}"`);
      }
    });
  },

  notify(fromObj, path, eventName) {
    var args = helper.toArray(arguments, 3);
    var pageObj = _pages[fromObj.getPageId()];
    if (typeof pageObj == undefined) return;

    var page = pageObj.page;
    var components = pageObj.components;

    if (!this.isPage(fromObj) && (path == undefined || path === "*" || path == page.is)) {
      if (typeof page[eventName] == 'function') {
        page[eventName].apply(page, args)
      }
      if (typeof page.onEvent == 'function') {
        page.onEvent(fromObj, eventName, ...args)
      }
    }

    for (var i in components) {
      var c = components[i];
      if (fromObj != c && (path == undefined || path === "*" || path == c.is)) {
        if (typeof c[eventName] == 'function') {
          c[eventName].apply(c, args)
        }
        if (typeof c.onEvent == 'function') {
          c.onEvent(fromObj, eventName, ...args)
        }
      }
    }
  },
};
