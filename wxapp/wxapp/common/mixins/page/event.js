import { PageSettings } from "../../base/settings.js"

function pageEventOptions() {
  var options = {};
  PageSettings.lifecycle.forEach(name => {
    options[name[0]] = {};
    var obj = name.length == 1 ? options : options[name[0]];
    var n = name.length == 1 ? name[0] : name[1];
    var ev = name.length == 1 ? name[0] : name[0] + name[1].charAt(0).toUpperCase + name[1].substring(1);
    (function (obj, n, ev) {
      obj[n] = function () {
        this.notify("*", ev, ...arguments);
      };
    })(obj, n, ev);
  });
  return options;
}

export var event = pageEventOptions();
