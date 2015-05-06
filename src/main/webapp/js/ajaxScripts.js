/**
 * Created by agermenos on 4/21/15.
 */
var AJAX_LIB = function () {
    return {
        callAJAX : function (url, type, params, callBack) {
            var request = $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                url: url,
                type: type,
                data: params,
                dataType: "text"
            });
            request.done(function(msg) {
                callBack(msg);
            });
            request.fail(function(msg) {
                $.notify({
                    // options
                    message: 'Error: ' + msg.statusText
                },{
                    // settings
                    type: 'danger'
                });

            });
            request.always(function() {
                console.log( "complete" );
            });
        }
    };
}();