/**
 * Questionnaire Administrator Form
 * Adds, removes and updates questionnaires
 * Developed by Alejandro Germenos
 * Dublin, CA. Mar 21, 2015
 */

function createNewDate(){
    var day = new Date();
    var strDay = (day.getMonth()+1) + "/" + (day.getDate()) + "/" + day.getFullYear();
    return strDay;
}

function QuestionnaireViewModel() {
    var self = this;

    self.id = 0;

    self.nextId = function() {
        if (!self.id) {
            self.id = 0;
        }
        return (++self.id);
    };

    self.user= {
        id:50,
        name:"Alejandro",
        lastName:"Germenos",
        email:"agermenos@gmail.com"
    };

    self.statuses = [
        {id:"NEW", text:"new"},
        {id:"EDITING", text:"editing"},
        {id:"OPERATIONAL", text:"operational"},
        {id:"DISABLED", text:"disabled"}
    ];

    self.questionnaire=ko.observable(new Questionnaire(0,"new questionnaire", null, self.statuses[0]));

    self.questionnaires= ko.observableArray([]);

    self.questions=ko.observableArray([]);

    self.messenger=function(message, type, icon){
        $.notify({
            // options
            icon: 'glyphicon '+icon,
            message: message
        },{
            // settings
            type: type
        });
    };

    // Operations
    self.addQuestionnaire = function() {
        self.questionnaires.push(new Questionnaire(0, "new questionnaire", null, self.statuses[0]));
    };
    self.removeQuestionnaire = function(questionnaire) { self.questionnaires.remove(questionnaire) };

    self.editQuestionnaire = function(questionnaire) {
        self.questionnaire(questionnaire);
        self.loadQuestions(self.questionnaire().questions);
       // AJAX_LIB.callAJAX('http://localhost:8080/services/questions/'+questionnaire.id, 'GET', null,  self.loadQuestions);
        //self.updateMap();
        $("#modalWindow").fadeIn("slow");
        //$("#cancelQuestionnaireButton").fadeIn("slow");
        //

    };

    self.updateMap = function(){
        self.questions().map(function(obj){
            obj.idx;
            var button = document.getElementById('bind_'+obj.idx);

            click_bind = (function(){
                return $('#'+obj.idx).toggle();
            });
            button.onclick = click_bind;
        });
    };

    self.addTxtQuestion= function (){
        self.questions.push(new Question( null, null, "New Text Question", "TEXT", ko.observableArray([]), self.nextId(), self.questionnaire.id));
        self.updateMap();
    };

    self.addMOQuestion= function (){
        self.questions.push(new Question(null, null, "New Multiple Choice Question", "MULTIPLE_CHOICE", ko.observableArray([new Question(0,0,"Dummy", "MULTIPLE_CHOICE", [], 0, self.questionnaire().id)]), self.nextId(), self.questionnaire().id));
        self.updateMap();
    };

    self.addSOQuestion= function (){
        self.questions.push(new Question(null, null, "New Single Choice Question", "SINGLE_CHOICE", ko.observableArray([new Question(0,0,"Dummy", "SINGLE_CHOICE", [], 0, self.questionnaire().id)]), self.nextId(), self.questionnaire().id));
        self.updateMap();
    };

    self.removeQuestion = function(question){
        self.questions.remove(question);
        self.updateMap();
    };

    self.addSOAnswer = function(question){
        question.answers.push(new Question(null, question.id, "new answer", "SINGLE_CHOICE", ko.observableArray([]), 0, self.questionnaire().id));
    };

    self.addMOAnswer = function(question){

        question.answers.push(new Question(null, question.id, "new answer", "MULTIPLE_CHOICE", ko.observableArray([]), 0, self.questionnaire().id));

    };

    self.storeQuestionnaire=function(){
        var jsonQs = ko.toJSON(self.questions);
        AJAX_LIB.callAJAX('http://localhost:8080/services/questionnaire/' + self.questionnaire().id , 'PUT', jsonQs, function() {self.messenger("Questionnaire uploaded", "success", "glyphicon-thumbs-up"); } );
    };

    self.cancelQuestionnaire=function(){
        $("#modalWindow").fadeOut();
        $("#cancelQuestionnaireButton").fadeOut();
    };

    self.removeAnswer=function(question, answer){
        question.answers.remove(answer);
    };

    self.loadQuestionnaire=function(data){
        self.questionnaires(JSON.parse(data));
    };

    self.loadQuestions=function(data) {
        self.id=0;
        data = self.fixQuestions(data);
        self.questions(data);
        self.updateMap();
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
    };


    self.fixQuestions=function (questions){
        var i=0;
        var return_questions=[];
        var answers=[];
        for (i;i<questions.length;i++){
            var question = questions[i];
            if (question.answers && question.answers.length>0) {
                answers = self.fixQuestions(question.answers);
            }
            return_questions.push(new Question(question.id, question.parent, question.question, question.type, ko.observableArray(answers), currentViewModel.nextId(), question.questionnaireId));
        }
        return return_questions;
    }
}

function Question(id, parent, question, type, answers, idx, questionnaireId){
    var self=this;
    self.id = id;
    self.question = question;
    self.type = type;
    self.parent = parent;
    self.answers =answers;
    self.idx = idx;
    self.questionnaireId = questionnaireId;
}

// Class to represent a row in the seat reservations grid
function Questionnaire(id, name, date, status) {
    var self = this;
    self.id = id;
    self.name = name;
    self.created = date?date:createNewDate();
    self.status = ko.observable(status);
}

var currentViewModel =new QuestionnaireViewModel();
ko.applyBindings(currentViewModel);
AJAX_LIB.callAJAX('http://localhost:8080/services/questionnaire/50', 'GET', null,  currentViewModel.loadQuestionnaire);