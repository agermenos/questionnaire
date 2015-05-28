/**
 * Created by agermenos on 5/26/15.
 */

var UTILS = function () {
    return {
        formatDate:function(day){
            if (day.prototype != Object){
                day = new Date();
            }
            var strDay = (day.getMonth()+1) + "/" + (day.getDate()) + "/" + day.getFullYear();
            return strDay;
        },

        createNewDate:function(){
            var day = new Date();
            return UTILS.formatDate(day);
        }
    };
}();