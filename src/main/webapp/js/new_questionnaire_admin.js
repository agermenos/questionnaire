/**
 * Created by Alejandro on 5/13/2015.
 */

data = '[{"id":50,"name":"Basic Questionnaire","created":"Thu Feb 12 12:34:03 PST 2015","modified":"Thu Feb 12 12:34:03 PST 2015","status":"CREATED","userId":50,"questions":[{"id":50,"question":"How many fingers are in one hand?","type":"TEXT","answers":[],"questionnaireId":50},{"id":51,"question":"What\u0027s the capital of Mexico?","type":"MULTIPLE_CHOICE","answers":[{"id":55,"parent":51,"question":"Mexico City","type":"CHOICE","answers":[]},{"id":53,"parent":51,"question":"Santa Lucia","type":"CHOICE","answers":[]},{"id":54,"parent":51,"question":"Guadalajara","type":"CHOICE","answers":[]},{"id":52,"parent":51,"question":"Estroncia","type":"CHOICE","answers":[]}],"questionnaireId":50}]}]';
QUESTIONNAIRE_admin  = (function () {
    $( document ).ready(function() {
        QUESTIONNAIRE_admin.loadQuestionnaire(data);
        ko.applyBindings(QUESTIONNAIRE_admin);
    });

    statuses = [
        {id:"NEW", text:"new"},
        {id:"EDITING", text:"editing"},
        {id:"OPERATIONAL", text:"operational"},
        {id:"DISABLED", text:"disabled"}
    ];

    var fixQuestions= function (questions){
        var i=0;
        var return_questions=[];
        var answers=[];
        for (i;i<questions.length;i++){
            var question = questions[i];
            if (question.answers && question.answers.length>0) {
                answers = fixQuestions(question.answers);
            }
            return_questions.push(new Question(question.id, question.parent,
                question.question, question.type, ko.observableArray(answers),
                getNextId(), question.questionnaireId));
        }
        return return_questions;
    };

    var updateMap = function(){
        QUESTIONNAIRE_admin.questions().map(function(obj){
            obj.idx = getNextId();
            var button = document.getElementById('bind_'+obj.idx);
            button.onclick = (function(){
                return $('#'+obj.idx).toggle();
            });
        });
    };

    var message= function(message, type, icon){
        $.notify({
            // options
            icon: 'glyphicon '+icon,
            message: message
        },{
            // settings
            type: type
        });
    };

    var setClicks = function(){
        $("#iem_menu_subscribe").click(function(){$("#iem_subscriptions_modal").modal();});
        $("#iem_subscribe_addSource").click(function(){$("#iem_susbscribe_sourceSpan").show();});
        $("#iem_subscribe_register").click(function(){console.log("Registering...");});
        $("#iem_subscribe_test").click(function(){console.log("Testing service."); IEM_subscription.testService();});
    };

    var getNextId = function() {
        if (!QUESTIONNAIRE_admin.id) {
            QUESTIONNAIRE_admin.id = 0;
        }
        return (++QUESTIONNAIRE_admin.id);
    };

    var questionnaire= ko.observable(new Object);
    var questions = ko.observable([]);

    var Question= function (id, parent, question, type, answers, idx, questionnaireId){
        QUESTIONNAIRE_admin.id = id;
        QUESTIONNAIRE_admin.question = question;
        QUESTIONNAIRE_admin.type = type;
        QUESTIONNAIRE_admin.parent = parent;
        QUESTIONNAIRE_admin.answers =answers;
        QUESTIONNAIRE_admin.idx = idx;
        QUESTIONNAIRE_admin.questionnaireId = questionnaireId;
    };

    var Questionnaire= function (id, name, date, status) {
        var QUESTIONNAIRE_admin = this;
        QUESTIONNAIRE_admin.id = id;
        QUESTIONNAIRE_admin.name = name;
        QUESTIONNAIRE_admin.created = date?date:createNewDate();
        QUESTIONNAIRE_admin.status = ko.observable(status);
    };

    return {

        questionnaire:questionnaire,
        questions:questions,
        validateForm: function(){
            var return_value=true;
            $("[required]").each(function(i){
                if (this.value.trim().length==0) {
                    return_value=false;
                    IEM_subscription.message("Required field is empty!", "error");
                    console.log (this.id + " is required");
                }
            });
            var email = $("[validate=email]").val();
            var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            if (email.trim().length>0) {
                var valid_email = regex.test(email);
                return_value = return_value&&valid_email;
                if (!valid_email) {
                    IEM_subscription.message("Email is not right", "error");
                }
            }
            return return_value;
        },
        // Operations
        addQuestionnaire : function() {
            QUESTIONNAIRE_admin.questionnaires.push(new Questionnaire(0, "new questionnaire", null, QUESTIONNAIRE_admin.statuses[0]));
        },        
        
        removeQuestionnaire : function(questionnaire) { 
            QUESTIONNAIRE_admin.questionnaires.remove(questionnaire) 
        },
    
        editQuestionnaire : function(questionnaire) {
            QUESTIONNAIRE_admin.questionnaire(questionnaire);
            QUESTIONNAIRE_admin.questions= ko.observable(QUESTIONNAIRE_admin.questionnaire().questions);
            //AJAX_LIB.callAJAX('http://localhost:8080/services/questions/'+questionnaire.id, 'GET', null,  QUESTIONNAIRE_admin.loadQuestions);
            updateMap();
            $("#modalWindow").fadeIn("slow");
            //$("#cancelQuestionnaireButton").fadeIn("slow");
            //
    
        },
    
        addTxtQuestion: function (){
            QUESTIONNAIRE_admin.questions.push(new Question( null, null, "New Text Question", "TEXT", ko.observableArray([]), QUESTIONNAIRE_admin.nextId(), QUESTIONNAIRE_admin.questionnaire.id));
            QUESTIONNAIRE_admin.updateMap();
        },
    
        addMOQuestion: function (){
            QUESTIONNAIRE_admin.questions.push(new Question(null, null, "New Multiple Choice Question", "MULTIPLE_CHOICE", ko.observableArray([new Question(0,0,"Dummy", "MULTIPLE_CHOICE", [], 0, QUESTIONNAIRE_admin.questionnaire().id)]), QUESTIONNAIRE_admin.nextId(), QUESTIONNAIRE_admin.questionnaire().id));
            QUESTIONNAIRE_admin.updateMap();
        },
    
        addSOQuestion: function (){
            QUESTIONNAIRE_admin.questions.push(new Question(null, null, "New Single Choice Question", "SINGLE_CHOICE", ko.observableArray([new Question(0,0,"Dummy", "SINGLE_CHOICE", [], 0, QUESTIONNAIRE_admin.questionnaire().id)]), QUESTIONNAIRE_admin.nextId(), QUESTIONNAIRE_admin.questionnaire().id));
            QUESTIONNAIRE_admin.updateMap();
        },
    
        removeQuestion : function(question){
            QUESTIONNAIRE_admin.questions.remove(question);
            QUESTIONNAIRE_admin.updateMap();
        },
    
        addSOAnswer : function(question){
            question.answers.push(new Question(null, null, "new answer", "SINGLE_CHOICE", ko.observableArray([]), 0, QUESTIONNAIRE_admin.questionnaire().id));
        },
    
        addMOAnswer : function(question) {
            question.answers.push(new Question(null, null, "new answer", "MULTIPLE_CHOICE", ko.observableArray([]), 0, QUESTIONNAIRE_admin.questionnaire().id));
        },
    
        storeQuestionnaire:function(){
            var jsonQs = ko.toJSON(QUESTIONNAIRE_admin.questions);
            AJAX_LIB.callAJAX('http://localhost:8080/services/questionnaire/' + QUESTIONNAIRE_admin.questionnaire().id , 'PUT', jsonQs, function() {QUESTIONNAIRE_admin.messenger("Questionnaire uploaded", "success", "glyphicon-thumbs-up"); } );
        },
    
        cancelQuestionnaire:function(){
            $("#modalWindow").fadeOut();
            $("#cancelQuestionnaireButton").fadeOut();
        },
    
        removeAnswer:function(question, answer){
            question.answers.remove(answer);
        },
    
        loadQuestionnaire: function(data){
            QUESTIONNAIRE_admin.questionnaires = ko.observable(JSON.parse(data));
            console.log (QUESTIONNAIRE_admin.questionnaires());
        },
    
        loadQuestions: function(data) {
            QUESTIONNAIRE_admin.id=0;
            data = fixQuestions(JSON.parse(data));
            QUESTIONNAIRE_admin.questions(data);
            QUESTIONNAIRE_admin.updateMap();
            $('#questionnaire_name').click(function() {
                $('#questionnaire_name').css('display', 'none');
    
                $('#editable_questionnaire_name')
                    .val($('#questionnaire_name').text())
                    .css('display', '')
                    .focus();
            });
    
            $('#editable_questionnaire_name').blur(function() {
                $('#editable_questionnaire_name').css('display', 'none');
                $('#questionnaire_name')
                    .text($('#editable_questionnaire_name').val())
                    .css('display', '');
            });
        }

    };
})();