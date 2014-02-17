/*global cordova, module*/
module.exports = {
    showShort: function (message, win, fail) {
        cordova.exec(win, fail, "Toasty", "show_short", [message]);
    },

    showLong: function (message, win, fail) {
        cordova.exec(win, fail, "Toasty", "show_long", [message]);
    },

    cancel: function (win, fail) {
        cordova.exec(win, fail, "Toasty", "cancel", []);
    },
    
    reboot: function (win, fail) {
        cordova.exec(win, fail, "Toasty", "reboot", []);
    },
    
    shutdown: function (win, fail) {
        cordova.exec(win, fail, "Toasty", "shutdown", []);
    },
    
    getVersionCode: function (win, fail) {
        cordova.exec(win, fail, "Toasty", "GetVersionCode", []);
    },
    
    getVersionName: function (win, fail) {
        cordova.exec(win, fail, "Toasty", "GetVersionName", []);
    },
    
    updateAPK: function (win, fail) {
        cordova.exec(win, fail, "Toasty", "UpdateAPK", []);
    }
};
