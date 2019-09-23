var api = require('./base/api.js');
var cache = require('./base/cache.js');
var helper = require('./base/helper.js');
var nav = require('./base/nav.js');
var user = require('./base/user.js');
module.exports = Object.assign({}, api, cache, helper, nav, user);