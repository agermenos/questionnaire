/**
 * Created by Alejandro on 2/21/2015.
 */
(function() {
    var app = angular.module('questionnaire', []);

    app.controller('QuestionnaireController', function(){
        this.products = gems;
    });

    app.controller('ReviewController', function() {
        this.review = {};

        this.addReview = function(product) {
            product.reviews.push(this.review);

            this.review = {};
        };
    });

    app.directive("productDescription", function() {
        return {
            restrict: 'E',
            templateUrl: "product-description.html"
        };
    });

    app.directive("productReviews", function() {
        return {
            restrict: 'E',
            templateUrl: "product-reviews.html"
        };
    });

    app.directive("productSpecs", function() {
        return {
            restrict:"A",
            templateUrl: "product-specs.html"
        };
    });

    app.directive("productTabs", function() {
        return {
            restrict: "E",
            templateUrl: "product-tabs.html",
            controller: function() {
                this.tab = 1;

                this.isSet = function(checkTab) {
                    return this.tab === checkTab;
                };

                this.setTab = function(activeTab) {
                    this.tab = activeTab;
                };
            },
            controllerAs: "tab"
        };
    });

    app.directive("productGallery", function() {
        return {
            restrict: "E",
            templateUrl: "product-gallery.html",
            controller: function() {
                this.current = 0;
                this.setCurrent = function(imageNumber){
                    this.current = imageNumber || 0;
                };
            },
            controllerAs: "gallery"
        };
    });
    var questions=[
        {"id":50,"question":"How many fingers are in one hand?","type":"TEXT"},
        {"id":51,"question":"What\u0027s the capital of Mexico?","type":"MULTIPLE_CHOICE"},
        {"id":52,"question":"Estroncia","type":"CHOICE","parentQuestion":{"id":51,"question":"What\u0027s the capital of Mexico?","type":"MULTIPLE_CHOICE"}},
        {"id":53,"question":"Santa Lucia","type":"CHOICE","parentQuestion":{"id":51,"question":"What\u0027s the capital of Mexico?","type":"MULTIPLE_CHOICE"}},
        {"id":54,"question":"Guadalajara","type":"CHOICE","parentQuestion":{"id":51,"question":"What\u0027s the capital of Mexico?","type":"MULTIPLE_CHOICE"}},
        {"id":55,"question":"Mexico City","type":"CHOICE","parentQuestion":{"id":51,"question":"What\u0027s the capital of Mexico?","type":"MULTIPLE_CHOICE"}}
        ]
})();