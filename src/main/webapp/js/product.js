/**
 * Created by Alejandro on 2/21/2015.
 */
(function() {
    var app=angular.module('store-directives',[]);

    app.directive("question", function() {
        return {
            restrict: "E",
            templateUrl: "question.html",
            controller: function() {
                this.question = 1;

            },
            controllerAs: "question"
        };
    });

})();