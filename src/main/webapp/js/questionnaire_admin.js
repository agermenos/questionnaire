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

    self.questionnaires= ko.observableArray([
        {id:1, name:"My test Questionnaire", date:"3/21/2015", status:self.statuses[0]},
        {id:2, name:"Cities of Mexico", date:"3/21/2015", status:self.statuses[1]},
        {id:3, name:"Cities of California", date:"3/21/2015", status:self.statuses[2]},
        {id:4, name:"Age Test", date:"3/21/2015", status:self.statuses[3]},
        {id:5, name:"Fitness Test", date:"3/21/2015", status:self.statuses[0]}
    ]);

    self.questions=ko.observableArray([]);

    // Operations
    self.addQuestionnaire = function() {
        self.questionnaires.push(new Questionnaire(0, "new questionnaire", null, self.statuses[0]));
    };
    self.removeQuestionnaire = function(questionnaire) { self.questionnaires.remove(questionnaire) };

    self.editQuestionnaire = function(questionnaire) {
        self.questionnaire(questionnaire);
        $("#modalWindow").fadeIn("slow");
        $("#cancelQuestionnaireButton").fadeIn("slow");
        AJAX_LIB.callAJAX('http://localhost:8080/services/questions/150', 'GET', null,  self.proceedEdit);
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
        self.questions.push(new Question( 0, null, "New Text Question", "TEXT", ko.observableArray([]), self.nextId()));
        self.updateMap();
    };

    self.addMOQuestion= function (){
        self.questions.push(new Question(0, null, "New Multiple Choice Question", "MULTIPLE_CHOICE", ko.observableArray([new Question(0,0,"Dummy", "MULTIPLE_CHOICE", [], 0)]), self.nextId()));
        self.updateMap();
    };

    self.addSOQuestion= function (){
        self.questions.push(new Question(0, null, "New Single Choice Question", "SINGLE_CHOICE", ko.observableArray([new Question(0,0,"Dummy", "SINGLE_CHOICE", [], 0)]), self.nextId()));
        self.updateMap();
    };

    self.removeQuestion = function(question){
        self.questions.remove(question);
        self.updateMap();
    };

    self.addSOAnswer = function(question){
        question.answers.push(new Question(0, question.id, "new answer", "SINGLE_CHOICE", ko.observableArray([]), 0));
    };

    self.addMOAnswer = function(question){

        question.answers.push(new Question(0, question.id, "new answer", "MULTIPLE_CHOICE", ko.observableArray([]), 0));

    };

    self.cancelQuestionnaire=function(){
        $("#modalWindow").fadeOut();
        $("#cancelQuestionnaireButton").fadeOut();
    };

    self.removeAnswer=function(question, answer){
        question.answers.remove(answer);
    };

    self.proceedEdit=function(data) {
        self.id=0;
        data = self.fixQuestions(JSON.parse(data));
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
            return_questions.push(new Question(question.id, question.parent, question.question, question.type, ko.observableArray(answers), currentViewModel.nextId()));
        }
        return return_questions;
    }
}

function Question(id, parent, question, type, answers, idx){
    var self=this;
    self.id = id;
    self.question = question;
    self.type = type;
    self.parent = parent;
    self.answers =answers;
    self.idx = idx;
}

// Class to represent a row in the seat reservations grid
function Questionnaire(id, name, date, status) {
    var self = this;
    self.id = id;
    self.name = name;
    self.date = date?date:createNewDate();
    self.status = ko.observable(status);
}

var currentViewModel =new QuestionnaireViewModel();
ko.applyBindings(currentViewModel);